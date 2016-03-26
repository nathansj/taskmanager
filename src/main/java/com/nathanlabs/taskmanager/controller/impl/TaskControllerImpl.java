package com.nathanlabs.taskmanager.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nathanlabs.taskmanager.controller.TaskController;
import com.nathanlabs.taskmanager.domain.Task;
import com.nathanlabs.taskmanager.exception.TaskException;
import com.nathanlabs.taskmanager.service.TaskManagerService;

@RestController
@RequestMapping(path = "/tasks")
public class TaskControllerImpl implements TaskController {

    @Autowired
    TaskManagerService service;

    @Override
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String createTask(@RequestBody Task task) throws TaskException {
        task = service.createTask(task);
        return task.getId();
    }

    @Override
    @RequestMapping(method = RequestMethod.PUT)
    public void updateTask(Task task) throws TaskException {
        service.updateTask(task);
    }

    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Task getTask(@PathVariable String id) throws TaskException {
        return service.getTask(id);
    }

    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable String id) throws TaskException {
        service.removeTask(id);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public Page<Task> findAllActiveTasks(@RequestParam(required = false, defaultValue = "0") int pageNo,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "dueDate") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") Direction dir) throws TaskException {
        return service.find(pageNo, pageSize, sortBy, dir);
    }

}
