package com.shavika.test.database;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shavika.agritech.api.db.SessionFactory;
import com.shavika.agritech.api.db.SessionFactoryImpl;

public class DBQueries {

	public synchronized static String[] getTableColumn() throws SQLException, Exception {
		String sql = "SELECT * FROM EMPLOYEE";
		try {
			PreparedStatement ps = DataBaseConnection.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();

			int count = metaData.getColumnCount(); // number of column
			// System.out.println("Column count = " + count);
			String columnName[] = new String[count];

			for (int i = 1; i <= count; i++)
				columnName[i - 1] = metaData.getColumnLabel(i);

			return columnName;
		} catch (Exception e) {
			return null;
		} finally {
			DataBaseConnection.closeConnection();
		}
	}

	private static JsonObject convertBeantoobject() {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = null;
		Employee employee = createDummyObject();
		JsonObject returnObject = null;
		try {
			// Convert object to JSON string and save into a file directly
			mapper.writeValue(new File("D:\\employee.json"), employee);

			// Convert BEAN object to JSON string
			jsonInString = mapper.writeValueAsString(employee);

			// Convert JSON string to JSON object
			JsonObject jObj = ConvertStringToJsonObject(jsonInString);
			System.out.println(jObj);
			returnObject = jObj;
			// JsonReader reader = Json.createReader(new StringReader(jsonInString));
			// returnObject = reader.readObject();

			// Convert JSON object to JSON string
			jsonInString = ConvertJsonObjectToString(jObj);
			System.out.println(jsonInString);

			// Convert object to JSON string and pretty print
			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(employee);
			System.out.println(jsonInString);

		} catch (JsonGenerationException e) {
			returnObject = null;
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
			returnObject = null;
		} catch (IOException e) {
			e.printStackTrace();
			returnObject = null;
		}
		return returnObject;
	}

	private static Employee createDummyObject() {

		Employee employee = new Employee();
		employee.setId(1);
		employee.setFirst_name("Sri@");
		employee.setLast_name("shavika @");
		employee.setMiddle_name("ss@");
		employee.setEmployee_id("100111");
		employee.setIs_deleted(true);
		employee.setGender("M");
		employee.setCreated_on(1455620010);
		employee.setModified_on(1455620110);
		return employee;

	}

	public static JsonObject ConvertStringToJsonObject(String data) {
		JsonObject jsonObject = null;
		String jsonStringData = null;
		jsonStringData = data;
		if (!jsonStringData.isEmpty()) {
			JsonReader reader = Json.createReader(new StringReader(jsonStringData));
			jsonObject = reader.readObject();
			reader.close();
		}
		return jsonObject;
	}

	public static String ConvertJsonObjectToString(JsonObject Object) {
		JsonObject jsonObject = null;
		jsonObject = Object;
		StringWriter stringWriter = new StringWriter();
		if (!jsonObject.isEmpty()) {
			JsonWriter writer = Json.createWriter(stringWriter);
			writer.writeObject(jsonObject);
			writer.close();
		}
		return stringWriter.getBuffer().toString();
	}

	public static Object getDatabyJsonObject(JsonObject jsonObject, String key) {
		if (jsonObject != null && key != null) {
			try {
				String keyValue = key.toLowerCase();
				return (Object) jsonObject.get(keyValue);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	public static void main(String[] args) throws SQLException, Exception {
		// InsertTable();
		Employee employee = createDummyObject();
		SessionFactory<Employee> session = new SessionFactoryImpl<Employee>();
		session.save(employee);

	}

	private static Object[] objectBulid() throws SQLException, Exception {

		String[] columnsName = getTableColumn();
		JsonObject jObject = convertBeantoobject();
		Object[] object = new Object[columnsName.length];

		for (int j = 0; j < columnsName.length; j++) {
			String column = columnsName[j].trim();
			// if ("id".equals(column.toLowerCase()))
			// continue;

			System.out.println("[" + j + "," + column + "]");
			object[j] = getDatabyJsonObject(jObject, column);

		} // for loop
		return object;
	}

	public synchronized static void InsertTable() throws SQLException, Exception {
		String sql = "INSERT INTO EMPLOYEE (FIRST_NAME, LAST_NAME, MIDDLE_NAME, EMPLOYEE_ID, GENDER, IS_DELETED, CREATED_ON, MODIFIED_ON) VALUES (?,?,?, ?,?,?, ?,?)";
		try {
			Object[] parameters = objectBulid();
			PreparedStatement ps = DataBaseConnection.getConnection().prepareStatement(sql);
			for (int i = 0; i < parameters.length; i++) {
				if (i == 0)
					continue;
				try {
					Object param = parameters[i];
					int index = i;
					setParameters(ps, index, param);
				} catch (SQLException e) {
					System.out.println("Caught exception in updateParameters(): " + e);
				}
			}
			int count = ps.executeUpdate();
			System.out.println("count==============>" + count);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataBaseConnection.closeConnection();
		}
	}

	private static void setParameters(PreparedStatement statement, int index, Object value) throws SQLException {

		if (value == null || (value.getClass().getName().contains("JsonValue$1"))) {
			/* JsonValue$1 -> null */
			statement.setNull(index, java.sql.Types.INTEGER);
		} else if (value.getClass().getName().contains("JsonValue$2")) {
			/* JsonValue$2 -> true */
			Boolean b = (value.toString().equals("true")) ? true : false;
			statement.setBoolean(index, b.booleanValue());
		} else if (value.getClass().getName().contains("JsonValue$3")) {
			/* JsonValue$3 -> false */
			Boolean b = (value.toString().equals("true")) ? true : false;
			statement.setBoolean(index, b.booleanValue());
		} else if (value instanceof Boolean) {
			Boolean b = (Boolean) value;
			statement.setBoolean(index, b.booleanValue());
		} else if (value instanceof Byte) {
			Byte b = (Byte) value;
			statement.setByte(index, b.byteValue());
		} else if (value instanceof java.sql.Date) {
			java.sql.Date d = (java.sql.Date) value;
			statement.setDate(index, d);
		} else if (value instanceof Double) {
			Double d = (Double) value;
			statement.setDouble(index, d.doubleValue());
		} else if (value instanceof Float) {
			Float f = (Float) value;
			statement.setFloat(index, f.floatValue());
		} else if (value instanceof Integer || (value.getClass().getName().contains("JsonIntegerImpl"))) {
			Integer in = (Integer) value;
			statement.setInt(index, in.intValue());
		} else if (value instanceof Long || (value.getClass().getName().contains("JsonNumberImpl"))) {
			String str = value.toString();
			long li = Long.parseLong(str);
			Long l = (Long) li;
			statement.setLong(index, l.longValue());
		} else if (value instanceof Short) {
			Short sh = (Short) value;
			statement.setShort(index, sh.shortValue());
		} else if (value instanceof String || (value.getClass().getName().contains("JsonStringImpl"))) {
			String st = (String) value.toString();
			st = st.replaceAll("\"", "");
			statement.setString(index, st);
		} else if (value instanceof Time) {
			Time t = (Time) value;
			statement.setTime(index, t);
		} else if (value instanceof Timestamp) {
			Timestamp t = (Timestamp) value;
			statement.setTimestamp(index, t);
		} else {
			System.out.println("Unsupported type " + value.getClass().getName() + " in while updating parameters at # " + (index));
		}
	}

}
