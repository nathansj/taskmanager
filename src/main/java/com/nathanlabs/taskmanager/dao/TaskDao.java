package com.nathanlabs.taskmanager.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.nathanlabs.taskmanager.domain.Task;

@Repository
public class TaskDao implements TaskRepository {

    private static final String TASK_COLLECTION = "tasks";
    @Autowired
    MongoTemplate template;

    @Override
    public Iterable<Task> findAll(Sort sorter) {
        return find(null, sorter);
    }

    @Override
    public Page<Task> findAll(Pageable pgbl) {
        return find(pgbl, null);
    }

    @Override
    public Page<Task> find(Pageable pgbl, Sort sort) {
        Query q = new Query()
                .addCriteria(Criteria.where("durationInHours")
                        .gte(0))
                .with(pgbl)
                .with(sort);
        q.fields()
                .include("subject");
        q.fields()
                .include("dueDate");
        q.fields()
                .include("durationInHours");
        q.fields()
                .include("priority");
        List<Task> tasks = template.find(q, Task.class, TASK_COLLECTION);
        return new PageImpl<>(tasks, pgbl, 0);
    }

    @Override
    public long count() {
        return template.count(new Query(), TASK_COLLECTION);
    }

    @Override
    public void delete(String id) {
        template.remove(new Query(Criteria.where("_id")
                .is(id)), TASK_COLLECTION);
    }

    @Override
    public void delete(Task task) {
        template.remove(task, TASK_COLLECTION);
    }

    @Override
    public void delete(Iterable<? extends Task> tasks) {
        template.remove(new Query(Criteria.where("_id")
                .in(tasks)), TASK_COLLECTION);
    }

    @Override
    public void deleteAll() {
        template.remove(new Query(), TASK_COLLECTION);
    }

    @Override
    public boolean exists(String id) {
        return template.exists(new Query(Criteria.where("_id")
                .is(id)), TASK_COLLECTION);
    }

    @Override
    public Iterable<Task> findAll() {
        return template.findAll(Task.class, TASK_COLLECTION);
    }

    @Override
    public Iterable<Task> findAll(Iterable<String> ids) {
        return template.find(new Query(Criteria.where("_id")
                .in(ids)), Task.class, TASK_COLLECTION);
    }

    @Override
    public Task findOne(String id) {
        return template.findById(id, Task.class, TASK_COLLECTION);
    }

    @Override
    public <S extends Task> S save(S task) {
        template.save(task, TASK_COLLECTION);
        return task;
    }

    @Override
    public <S extends Task> Iterable<S> save(Iterable<S> tasks) {
        if (tasks == null)
            return null;

        for (S task : tasks) {
            template.save(task, TASK_COLLECTION);
        }
        return tasks;
    }

}