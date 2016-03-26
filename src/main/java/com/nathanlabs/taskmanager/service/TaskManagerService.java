package com.nathanlabs.taskmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.nathanlabs.taskmanager.domain.Task;
import com.nathanlabs.taskmanager.exception.TaskException;

public interface TaskManagerService {
    public Task createTask(Task task) throws TaskException;

    public void updateTask(Task task) throws TaskException;

    public void removeTask(String id) throws TaskException;

    public Task getTask(String taskId) throws TaskException;

    public Page<Task> find(int pageNo, int pageSize, String sortBy, Sort.Direction dir) throws TaskException;
}