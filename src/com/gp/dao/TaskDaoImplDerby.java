package com.gp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.*;

import com.gp.entity.Task;

/**
 * Implementation of methods to access the database
 * Derby
 *
 */
public class TaskDaoImplDerby implements TaskDao {
	public static final String DB_NAME = "Training";
	public static final String TABLE_NAME = "tasks";

	public static final String QUERY_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME+ "(id integer, e varchar(200), r varchar(50))";
	public static final String QUERY_DROP_TABLE = "DROP TABLE " + TABLE_NAME;
	public static final String QUERY_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
	public static final String QUERY_SELECT_MAX_ID = "SELECT MAX(id) FROM " + TABLE_NAME;
	public static final String QUERY_INSERT = "INSERT INTO " + TABLE_NAME + " VALUES"; 				// ...+(id,e,r)
	public static final String QUERY_DELETE_STATEMENT = "DELETE FROM " + TABLE_NAME +" WHERE id="; 	//...+id
	public static final String QUERY_UPDATE_STATEMENT ="UPDATE "+TABLE_NAME;						//...+  SET e='', r=''  WHERE id=0

	private static final Logger log = LogManager.getLogger();
	private DerbyDBManager db = null;

	/**
	 * Constructor
	 * Create DBManager 
	 */
	public TaskDaoImplDerby() {
		db = new DerbyDBManager(DB_NAME);
		if (this.getMaxId() == 0)
			this.reCreateTable();
	}
	
	/**
	 * Create table
	 */
	public void reCreateTable(){
		try {
			db.executeUpdate(QUERY_DROP_TABLE);
			db.executeUpdate(QUERY_CREATE_TABLE);
			log.info("QUERY_CREATE_TABLE OK:");
		} catch (SQLException e) {
			log.error("QUERY_CREATE_TABLE FAILED");
		}
	}

	/**
	 * Get max id task from table
	 * @return max id
	 */
	private int getMaxId(){
		int maxId = 0;
		try {
			ResultSet rs = db.executeQuery(QUERY_SELECT_MAX_ID);
			rs.next();
			if(rs.getString(1)!=null)
				maxId = Integer.valueOf(rs.getString(1));
		} catch (SQLException e) {
			log.error("Get Max Id FAILED:"+TABLE_NAME);
		}
		return maxId;
	}
	
	/**
	 * Edit task in db
	 */
	@Override
	public void update(Task t) {
		try {
			db.executeUpdate(QUERY_UPDATE_STATEMENT 
					+ "  SET e='" + t.getExpression() + "', r='"
					+t.getResult()+"'  WHERE id="
					+String.valueOf(t.getId()));
			log.info("Update OK:" + t.toString());
		} catch (SQLException e) {
			log.error("Update FAILED:" + t.toString());
		}
	}

	/**
	 * delete task from db
	 */
	@Override
	public void delete(int id) {
		try {
			db.executeUpdate(QUERY_DELETE_STATEMENT+id);
		} catch (SQLException e) {
			log.error("delete FAILED: id=" + id);
		}
	}

	/**
	 * add new task in db
	 */
	@Override
	public void create(Task t) {
		t.setId(getMaxId()+1);
		try {
			db.executeUpdate(QUERY_INSERT + "(" 
					+String.valueOf(t.getId()) + ",'" 
					+ t.getExpression() + "','"
					+ t.getResult() + "')");
			log.info("Create OK:" + t.toString());
		} catch (SQLException e) {
			log.error("Create FAILED:" + t.toString());
		}
	}

	/**
	 * get task by id
	 * @return task
	 */
	@Override
	public Task read(int id) {
		Task t = new Task();
		try {
			ResultSet rs = db.executeQuery(QUERY_SELECT_ALL + " WHERE id=" + id);
			rs.next();
			if(rs.getString(1)==null) t.setId(0);
			t.setId(Integer.valueOf(rs.getString(1)));
			t.setExpression(rs.getString(2));
			t.setResult(rs.getString(3));
		} catch (SQLException e) {
			log.error("read FAILED: id=" + id);
		}
		return t;
	}

	/**
	 * read all tastk from db
	 * @return list tasks
	 */
	@Override
	public List<Task> readAll() {
		List<Task> list = new ArrayList<Task>();
		try {
			ResultSet rs = db.executeQuery(QUERY_SELECT_ALL);
			while (rs.next()) {
				Task t = new Task();
				if(rs.getString(1)==null) t.setId(0);
				t.setId(Integer.valueOf(rs.getString(1)));
				t.setExpression(rs.getString(2));
				t.setResult(rs.getString(3));
				list.add(t);
			}
		} catch (SQLException e) {
			log.error("readAll FAILED");
		}
		return list;
	}
	
/**
 * read last task in table (max id)
 * @return task
 */
	public Task readLast() {
		Task t = read(getMaxId());		
		return t;
	}
	
}
