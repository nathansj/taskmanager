package com.nathanlabs.taskmanager.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.nathanlabs.taskmanager.domain.Task;
import com.nathanlabs.taskmanager.exception.TaskException;

public interface TaskController {

    String createTask(Task task) throws TaskException;

    void updateTask(Task task) throws TaskException;

    Task getTask(String id) throws TaskException;

    void deleteTask(String id) throws TaskException;

    Page<Task> findAllActiveTasks(int pageNo, int pageSize, String sortBy, Sort.Direction dir) throws TaskException;
}
