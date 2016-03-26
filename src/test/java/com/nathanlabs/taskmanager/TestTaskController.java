package com.nathanlabs.taskmanager;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nathanlabs.taskmanager.context.AppConfig;
import com.nathanlabs.taskmanager.domain.Task;
import com.nathanlabs.taskmanager.domain.Task.Priority;

@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class TestTaskController extends AbstractTestNGSpringContextTests {
    MockMvc mockMvc;
    RestTemplate template;

    @Autowired
    WebApplicationContext wac;

    @BeforeMethod
    public void setup() {
        mockMvc = webAppContextSetup(wac).build();
        template = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
    }

    @DataProvider
    Object[][] taskData() {
        return new Object[][] {
                { "Task 1", new Date(), 12f, Priority.ONE },
                { "Task 2", new Date(), 14f, Priority.TWO },
                { "Task 3", new Date(), 16f, Priority.THREE },
                { "Task 4", new Date(), 18f, Priority.FOUR },
                { "Task 5", new Date(), 20f, Priority.FIVE },
        };
    }

    @Test(dataProvider = "taskData", enabled = true)
    public void testCreate(final String subject, final Date dueDate, final float duration, final Priority priority)
            throws Exception {
        Task task = new Task(null, subject, dueDate, duration, priority);
        template.postForObject("/tasks", task, String.class);
    }

    @Test(enabled = true)
    public void testDelete() throws Exception {
        Page<Task> taskPage = template.getForObject(
                "http://localhost/tasks?pageNo=0&pageSize=20&sortBy=dueDate&dir=ASC",
                PageSub.class);
        taskPage.forEach(new Consumer<Task>() {

            @Override
            public void accept(Task t) {
                template.delete("http://localhost/tasks/" + t.getId());
            }

            @Override
            public Consumer<Task> andThen(Consumer<? super Task> after) {
                return this;
            }
        });
    }

    public static class PageSub extends PageImpl<Task> implements Page<Task> {

        private static final long serialVersionUID = 1L;

        @JsonCreator
        public PageSub(@JsonProperty("content") List<Task> content, @JsonProperty("pageable") Pageable pgbl) {
            super(content, pgbl, 0);
        }

    }
}
