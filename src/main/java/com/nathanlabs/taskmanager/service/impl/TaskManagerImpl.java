package com.nathanlabs.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.nathanlabs.taskmanager.dao.TaskDao;
import com.nathanlabs.taskmanager.domain.Task;
import com.nathanlabs.taskmanager.exception.TaskException;
import com.nathanlabs.taskmanager.service.TaskManagerService;

@Service
public class TaskManagerImpl implements TaskManagerService {

    @Autowired
    private TaskDao dao;

    public Task createTask(Task task) throws TaskException {
        if (task == null || task.getId() != null)
            throw new java.lang.IllegalArgumentException("Task to create is null or id exists.");

        dao.save(task);
        return task;
    }

    public void updateTask(Task task) throws TaskException {
        assert (task != null && task.getId() != null);
        dao.save(task);
    }

    public void removeTask(String id) throws TaskException {
        assert (id != null);
        dao.delete(id);
    }

    public Task getTask(String taskId) throws TaskException {
        assert (taskId != null);
        return dao.findOne(taskId);
    }

    @Override
    public Page<Task> find(int pageNo, int pageSize, String sortBy, Direction dir) throws TaskException {
        final Pageable pageable = new PageRequest(pageNo, pageSize);
        final Sort sort = new Sort(dir, sortBy);
        return dao.find(pageable, sort);
    }
}