package com.gp.dao;

import java.util.List;

import com.gp.entity.Task;

/**
 * Iterface DAO
 * (Template)
 */
public interface TaskDao {
	void create(Task t);
	Task read(int id);
	void update(Task t);
	void delete(int id);
	List<Task> readAll();
}