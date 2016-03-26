package com.nathanlabs.taskmanager.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.nathanlabs.taskmanager.domain.Task;

public interface TaskRepository extends PagingAndSortingRepository<Task, String> {

    Page<Task> find(Pageable pgbl, Sort sort);
}
