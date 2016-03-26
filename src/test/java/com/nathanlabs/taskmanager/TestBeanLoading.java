package com.nathanlabs.taskmanager;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

import com.nathanlabs.taskmanager.context.AppConfig;
import com.nathanlabs.taskmanager.domain.Task;
import com.nathanlabs.taskmanager.exception.TaskException;
import com.nathanlabs.taskmanager.service.TaskManagerService;

@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class TestBeanLoading extends AbstractTestNGSpringContextTests {
    private static Logger LOG = LoggerFactory.getLogger(TestBeanLoading.class);

    @Autowired
    TaskManagerService service;

    @Autowired
    Validator validator;

    @Test
    public void testBeanLoading() throws TaskException {
        Task task = new Task(null, "Test task 1.", null, 24, null);
        task = service.createTask(task);

        task.setDurationInHours(48);
        task.setSubject("Updated test task 1.");

        service.updateTask(task);

        task = service.getTask(task.getId());
        LOG.debug(String.format("Updated Task: id=%s, duration=%s, subject=%s", task.getId(),
                task.getDurationInHours(), task.getSubject()));

        service.removeTask(task.getId());

        assert (service.getTask(task.getId()) == null);
    }

    @Test
    public void testBeanValidation() {
        Task task = new Task(null, null, null, 0, null);
        Set<ConstraintViolation<Task>> errors = validator.validate(task);
        assert errors.size() == 1;
    }
}