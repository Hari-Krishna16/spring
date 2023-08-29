package de.zeroco.spring.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import de.zeroco.spring.dbutility.DbUtility;

@Repository
public class EmployeeDao {

	public static final String URL = "jdbc:mysql://localhost:3306/apm?characterEncoding=utf8";
	public static final String USER = "admin";
	public static final String PASSWORD = "@Chakri007";
	public static final String[] COLUMNS = { "name", "number", "email", "date_of_birth", "age" };
	public static final String[] COLUMN = { "pk_id","name", "number", "email", "date_of_birth", "age" };

	List<String> list = new ArrayList<String>();

	public int save(List<Object> list) {
		int columnId;
		Connection connection = DbUtility.getConnection(URL, USER, PASSWORD);
		columnId = DbUtility.getGeneratedKey(connection, "apm", "employee", Arrays.asList(COLUMNS), list);
		return columnId;
	}

	public int delete(Object id) {
		int rowsDeleted = 0;
		Connection connection = DbUtility.getConnection(URL, USER, PASSWORD);
		rowsDeleted = DbUtility.delete(connection, "apm", "employee", "pk_id", id);
		DbUtility.closeConnection(connection);
		return rowsDeleted;
	}

	public Map<String, Object> get(Object id) {
		Connection connection = DbUtility.getConnection(URL, USER, PASSWORD);
		Map<String, Object> dataSet = DbUtility.get(connection, "apm", "employee", list, "pk_id", Arrays.asList(id));
		DbUtility.closeConnection(connection);
		return dataSet;
	}

	public List<Map<String, Object>> list() {
		Connection connection = DbUtility.getConnection(URL, USER, PASSWORD);
		List<Map<String, Object>> dataSet = DbUtility.list("apm", "employee", list);
		DbUtility.closeConnection(connection);
		return dataSet;
	}

	public int update(int id, String name, String number, String email, String dateOfBirth, int age) {
		Connection connection = DbUtility.getConnection(URL, USER, PASSWORD);
		int updatedRowsNumber = DbUtility.update(connection, "apm", "employee", Arrays.asList(COLUMN),
				Arrays.asList((Object) id, name, number,  email, dateOfBirth, age), "pk_id",(Object) id);
		DbUtility.closeConnection(connection);
		return updatedRowsNumber;
	}
}
