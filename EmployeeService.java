package de.zeroco.spring.services;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.zeroco.spring.dao.EmployeeDao;
import de.zeroco.spring.dbutility.DbUtility;
import de.zeroco.spring.dbutility.Utility;

@Service
public class EmployeeService {

	public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/apm?characterEncoding=utf8";
	public static final String USER = "admin";
	public static final String USER_PASSWORD = "@Chakri007";

	List<String> columns = new ArrayList<String>();

	@Autowired
	EmployeeDao dao;

	public int save(String name, String number, String email, String dateOfBirth) {
		Object values[] = { name, number, email, dateOfBirth, getAge(dateOfBirth) };
		Connection connection = DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD);
		List<Map<String, Object>> dataSet = list();
		DbUtility.closeConnection(connection);
		for (Map<String, Object> map : dataSet) {
			if (map.containsValue(number) || map.containsValue(email)) {
				return 0;
			}
		}
		return dao.save(Arrays.asList(values));
	}

	public int getAge(String dateOfBirth) {
		LocalDate CurrentDate = LocalDate.now();
		LocalDate dob = LocalDate.parse(dateOfBirth);
		Period period = Period.between(dob, CurrentDate);
		int age = period.getYears();
		return age;
	}

	public int delete(Object id) {
		Connection connection = DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD);
		Map<String, Object> dataSet = DbUtility.get(connection, "apm", "employee", Arrays.asList("pk_id"), "pk_id",
				Arrays.asList(id));
		DbUtility.closeConnection(connection);
		if (!Utility.isBlank(dataSet)) {
			return dao.delete(id);
		} else {
			return 0;
		}
	}

	public Map<String, Object> get(Object id) {
		Connection connection = DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD);
		Map<String, Object> dataSet = DbUtility.get(connection, "apm", "employee", columns, "pk_id", Arrays.asList((Object)id));
		DbUtility.closeConnection(connection);
		if ((!Utility.isBlank(dataSet))) {
			return dao.get(id);
		} else {
			return dataSet;
		}
	}

	public List<Map<String, Object>> list() {
		Connection connection = DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD);
		List<Map<String, Object>> dataSet = DbUtility.list("apm", "employee", columns);
		DbUtility.closeConnection(connection);
		if ((!Utility.isBlank(dataSet))) {
			return dao.list();
		} else {
			return dataSet;
		}
	}

	public int update(int id, String name, String number, String email, String dateOfBirth) {
		Connection connection = DbUtility.getConnection(DATABASE_URL, USER, USER_PASSWORD);
		List<Map<String, Object>> dataSet = list();
		DbUtility.closeConnection(connection);
		for (Map<String, Object> map : dataSet) {
			if (map.containsValue(number) || map.containsValue(email)) {
				if(map.containsValue(id)) {
					continue;
				}
				return 0;
			}
		}
		return dao.update(id, name, number, email, dateOfBirth, getAge(dateOfBirth));
	}
}
