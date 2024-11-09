package com.example.demo.dto;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import jakarta.persistence.*;


import com.example.demo.model.*;;

@Repository
public class TasksDTO implements ITaskRepository {

    @PersistenceContext
    EntityManager entityManager;

    //RETORNAR TODOS LOS REGISTROS DE LA TABLA:
    @Override
    public List<Task> selectAll() {
        String sql = "From tasks";
        return (List<Task>)  entityManager.createQuery(sql).getResultList();
    }

    //RETORNAR UN REGISTRO DE LA TABLA:
    @Override
    public Object getTask(int bookId) {
        return  entityManager.find(Task.class, bookId);
    }

    @Override
    public Object insertTask(Object newTask) {
        entityManager.persist(newTask);
        Task t = getLastInsertedTask();
        return t;
    }

    Task getLastInsertedTask()
    {
        String sql = "FROM tasks order by id DESC";
        Query myQuery = entityManager.createQuery(sql);
        myQuery.setMaxResults(1);
        Task t = (Task) myQuery.getSingleResult();
        return t;
    }

    @Override
    public Object updateTask(int taskId, Object task) {
       Task taskToUpdate = (Task) getTask(taskId);
       Task newTask = (Task) task;
       taskToUpdate.setDescr(newTask.getDescr());
       taskToUpdate.setDuration(newTask.getDuration());
       //taskId.setFinished(task.isFinished());
       //actualizar todos lo
       entityManager.flush();
       return newTask;

    }

    @Override
    public Object deleteTask(int taskId) {
       return null;
    }




    @Override
    public void flush() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'flush'");
    }

    @Override
    public Object saveAndFlush(Object entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAndFlush'");
    }

    @Override
    public List saveAllAndFlush(Iterable entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAllAndFlush'");
    }

    @Override
    public void deleteAllInBatch(Iterable entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllInBatch'");
    }

    @Override
    public void deleteAllByIdInBatch(Iterable ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllByIdInBatch'");
    }

    @Override
    public void deleteAllInBatch() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllInBatch'");
    }

    @Override
    public Object getOne(Object id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOne'");
    }

    @Override
    public Object getById(Object id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public Object getReferenceById(Object id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReferenceById'");
    }

    @Override
    public List findAll(Example example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public List findAll(Example example, Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public List findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public List findAllById(Iterable ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllById'");
    }

    @Override
    public List saveAll(Iterable entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public void delete(Object entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }

    @Override
    public void deleteAll(Iterable entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }

    @Override
    public void deleteAllById(Iterable ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllById'");
    }

    @Override
    public void deleteById(Object id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public boolean existsById(Object id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }

    @Override
    public Optional findById(Object id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Object save(Object entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List findAll(Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Page findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public long count(Example example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public boolean exists(Example example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exists'");
    }

    @Override
    public Page findAll(Example example, Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Object findBy(Example example, Function queryFunction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findBy'");
    }

    @Override
    public Optional findOne(Example example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOne'");
    }

    
    
}
