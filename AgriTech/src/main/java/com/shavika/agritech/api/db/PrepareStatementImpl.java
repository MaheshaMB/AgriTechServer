package com.shavika.agritech.api.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shavika.agritech.utils.CommonUtil;
import com.shavika.agritech.utils.DateTimeUtil;

public class PrepareStatementImpl {

	private static final Logger LOGGER = Logger.getLogger(PrepareStatementImpl.class);

	private static final String LOG_MSG_EXECUTING = "****** Executing query ***** ";
	private static final String LOG_MSG_ADDING = "******** Adding query to batch **** ";

	/**
	 * 
	 * 
	 */
	private Class clazz = null;
	private String clazzName = null;
	Connection connection = null;
	private List<Object> dataObj = null;
	private ResultSetMetaData metaData = null;
	private List<String> columnName = null;

	public PrepareStatementImpl() {
		super();
	}

	public PrepareStatementImpl(List<Object> dataObj) throws SQLException {
		super();
		this.dataObj = dataObj;
		this.columnName = new ArrayList<String>();
		this.init();
		getTableColumns();
	}

	public PrepareStatementImpl(Class clazz) throws SQLException {
		super();
		this.clazz = clazz;
		this.init();
	}

	private void init() throws SQLException {
		this.clazzName = (this.clazz != null) ? getClassName(this.clazz) : ((dataObj.get(0) != null) ? getClassName(dataObj.get(0)) : null);
		this.connection = DBConnection.getConnection();
		this.connection.setAutoCommit(false);
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	private void getTableColumns() throws SQLException {
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.getClazzQuery(clazzName);
			rs = connection.prepareStatement(sql).executeQuery();
			metaData = rs.getMetaData();
			int count = metaData.getColumnCount();

			for (int i = 1; i <= count; i++)
				columnName.add(metaData.getColumnLabel(i).toString());

		} catch (Exception e) {
			LOGGER.error("Failed to get Columns in table - " + clazzName, e);
		} finally {
			if (rs != null)
				rs.close();
			rs = null;
		}
	}

	/**
	 * @method Insert
	 * @return
	 * @throws SQLException
	 */
	public int BuildInsertQuery() throws SQLException {
		PreparedStatement ps = null;
		ObjectMapper mapper = null;
		try {
			String query = QueryBuilder.getInsertQuery(clazzName, columnName);
			LOGGER.info(LOG_MSG_EXECUTING + query);

			ps = connection.prepareStatement(query);
			long startTime = CommonUtil.gettimesmilisec();
			int columnSize = metaData.getColumnCount();

			for (Object object : dataObj) {
				mapper = new ObjectMapper();
				// Convert BEAN object to JSON string
				String jsonInString = mapper.writeValueAsString(object);
				// Convert JSON string to JSON object
				JsonObject jsonObject = CommonUtil.ConvertStringToJsonObject(jsonInString);
				// LOGGER.debug("jsonObject==>" + jsonObject);

				for (int index = 1; index <= columnSize; index++) {
					int type = metaData.getColumnType(index);
					String column = metaData.getColumnLabel(index).toString().toLowerCase();
					// boolean lastColumn = (index == columnSize) ? true : false;
					if (column.toLowerCase().equals("id"))
						continue;
					Object obj = CommonUtil.getDatabyJsonObject(jsonObject, column);
					setParameters(ps, type, (index - 1), obj);
				}
				ps.addBatch();
				LOGGER.info(LOG_MSG_ADDING + clazzName);
			}
			int[] count = ps.executeBatch();
			long endTime = CommonUtil.gettimesmilisec();
			float seconds = ((startTime - endTime) / 1000);
			LOGGER.debug("Query execution over. Took: " + seconds + " seconds to execute the query for " + count.length + " records...");
		} catch (Exception e) {
			LOGGER.error("Failed to Build Insert query for clazz - " + clazzName + " " + e.getMessage());
		} finally {
			if (ps != null)
				ps.close();
			ps = null;
			closeconnection();
		}
		return 1;
	}

	/**
	 * @method Updated
	 * @return
	 * @throws SQLException
	 */
	public int BuildUpdateQuery() throws SQLException {
		PreparedStatement ps = null;
		ObjectMapper mapper = null;
		int count = 0;
		Object idObj = null;
		try {
			String query = QueryBuilder.getUpdateQuery(clazzName, columnName);
			LOGGER.info(LOG_MSG_EXECUTING + query);

			ps = connection.prepareStatement(query);
			long startTime = CommonUtil.gettimesmilisec();
			int columnSize = metaData.getColumnCount();

			for (Object object : dataObj) {
				idObj = new Object();
				mapper = new ObjectMapper();
				// Convert BEAN object to JSON string
				String jsonInString = mapper.writeValueAsString(object);
				// Convert JSON string to JSON object
				JsonObject jsonObject = CommonUtil.ConvertStringToJsonObject(jsonInString);
				// LOGGER.debug("jsonObject==>" + jsonObject);
				int index1 = 1;
				for (int index = 1; index <= columnSize; index++) {
					int type = metaData.getColumnType(index);
					String column = metaData.getColumnLabel(index).toString().toLowerCase();
					// boolean lastColumn = (index == columnSize) ? true : false;
					if (column.toLowerCase().equals("id")) {
						idObj = CommonUtil.getDatabyJsonObject(jsonObject, column);
						continue;
					} else if (column.toLowerCase().equals("created_on")) {
						continue;
					}
					Object obj = CommonUtil.getDatabyJsonObject(jsonObject, column);
					setParameters(ps, type, index1, obj);
					index1++;
				} // for loop
				setParameters(ps, -5, index1, idObj);
				ps.addBatch();
				LOGGER.info(LOG_MSG_ADDING + clazzName);
			}
			count = ps.executeUpdate();
			long endTime = CommonUtil.gettimesmilisec();
			float seconds = ((startTime - endTime) / 1000);
			LOGGER.debug("Query execution over. Took: " + seconds + " seconds to execute the query for " + count + " records...");
		} catch (Exception e) {
			LOGGER.error("Failed to Build Update query for clazz - " + clazzName + e.getMessage());
		} finally {
			if (ps != null)
				ps.close();
			ps = null;
			closeconnection();
		}
		return count;
	}

	/**
	 * @method Soft delete
	 * 
	 * @throws SQLException
	 */
	public int BuildSoftDeleteQuery(List<Long> id) throws SQLException {
		PreparedStatement ps = null;
		int count = 0;
		try {
			String query = QueryBuilder.getSoftDelateQuery(clazzName);
			LOGGER.info(LOG_MSG_EXECUTING + query);
			ps = connection.prepareStatement(query);
			long startTime = CommonUtil.gettimesmilisec();
			for (Long params : id) {
				ps.setBoolean(1, true);
				ps.setLong(2, CommonUtil.gettimesmilisec());
				ps.setLong(3, params);
				ps.addBatch();
			}
			LOGGER.info(LOG_MSG_ADDING + clazzName);
			count = ps.executeUpdate();
			long endTime = CommonUtil.gettimesmilisec();
			float seconds = ((startTime - endTime) / 1000);
			LOGGER.debug("Query execution over. Took: " + seconds + " seconds to execute the query for " + count + " records...");
		} catch (Exception e) {
			LOGGER.error("Failed to Build Update query for clazz - " + clazzName + " " + e.getMessage());
		} finally {
			if (ps != null)
				ps.close();
			ps = null;
			closeconnection();
		}
		return count;
	}

	/**
	 * @mehtod Delete
	 * 
	 * @throws SQLException
	 */
	public int BuildDeleteQuery(List<Long> id) throws SQLException {
		PreparedStatement ps = null;
		int count = 0;
		try {
			String query = QueryBuilder.getDelateQuery(clazzName);
			LOGGER.info(LOG_MSG_EXECUTING + query);

			ps = connection.prepareStatement(query);
			long startTime = CommonUtil.gettimesmilisec();
			for (Long params : id) {
				ps.setLong(1, params);
				ps.addBatch();
			}
			LOGGER.info(LOG_MSG_ADDING + clazzName);
			count = ps.executeUpdate();
			long endTime = CommonUtil.gettimesmilisec();
			float seconds = ((startTime - endTime) / 1000);
			LOGGER.debug("Query execution over. Took: " + seconds + " seconds to execute the query for " + count + " records...");
		} catch (Exception e) {
			LOGGER.error("Failed to Build hard delete query for clazz - " + clazzName + " " + e.getMessage());
		} finally {
			if (ps != null)
				ps.close();
			ps = null;
			closeconnection();
		}
		return count;
	}

	/**
	 * @method Find
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Object> findQuery(boolean isFindAll, long id) throws SQLException {
		List<Object> allObj = null;
		ResultSet rs = null;
		try {
			String sql = (isFindAll) ? QueryBuilder.findAllQuery(clazzName) : QueryBuilder.findQuery(clazzName);
			LOGGER.info(LOG_MSG_EXECUTING + sql);
			allObj = new ArrayList<Object>();

			PreparedStatement ps = connection.prepareStatement(sql);
			if (!isFindAll)
				ps.setLong(1, id);

			rs = ps.executeQuery();
			grabObject(rs, allObj, clazz);
		} catch (Exception e) {
			LOGGER.error("Failed to get Columns in table - " + clazzName, e);
		} finally {
			if (rs != null)
				rs.close();
			rs = null;
		}
		return allObj;
	}

	private void grabObject(ResultSet rs, List<Object> allObj, Class object) throws SQLException {
		try {
			while (rs.next()) {
				ResultSetMetaData metaData = rs.getMetaData();
				StringBuilder sbObj = new StringBuilder().append("{");
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					int type = metaData.getColumnType(i);
					String column = metaData.getColumnLabel(i).toString().toLowerCase();
					boolean lastColumn = (i == metaData.getColumnCount()) ? true : false;
					setParameters(rs, sbObj, type, column, i, lastColumn);
				}
				sbObj.append("}");

				// LOGGER.debug("sbObj===>" + sbObj.toString());
				Object obj = CommonUtil.convertObjecttoBean(sbObj.toString(), object);
				allObj.add(obj);
			}
		} catch (Exception e) {
			LOGGER.error("Failed to Build hard delete query for clazz - " + object.getName() + e.getMessage());
		}
	}

	private static void setParameters(PreparedStatement ps, int type, int index, Object value) throws SQLException {
		LOGGER.debug("setParameters==>" + index + " [" + type + "] [" + value + "]");

		if ((type == 91)) {
			LOGGER.debug("jav.sql.Date==>" + value);
			String str = value.toString();
			long li = Long.parseLong(str);
			ps.setDate(index, DateTimeUtil.sqlDate(li));
		} else if (value == null || (value.getClass().getName().contains("JsonValue$1"))) {
			/* JsonValue$1 -> null */
			ps.setNull(index, java.sql.Types.INTEGER);
		} else if (value.getClass().getName().contains("JsonValue$2")) {
			/* JsonValue$2 -> true */
			Boolean b = (value.toString().equals("true")) ? true : false;
			ps.setBoolean(index, b.booleanValue());
		} else if (value.getClass().getName().contains("JsonValue$3")) {
			/* JsonValue$3 -> false */
			Boolean b = (value.toString().equals("true")) ? true : false;
			ps.setBoolean(index, b.booleanValue());
		} else if (value instanceof Integer || (value.getClass().getName().contains("JsonIntegerImpl"))) {
			Integer in = (Integer) value;
			ps.setInt(index, in.intValue());
		} else if ((value instanceof Long && type != 91) || (value.getClass().getName().contains("JsonNumberImpl"))) {
			String str = value.toString();
			long li = Long.parseLong(str);
			Long l = (Long) li;
			ps.setLong(index, l.longValue());
		} else if (value instanceof String || (value.getClass().getName().contains("JsonStringImpl"))) {
			String st = (String) value.toString();
			st = st.replaceAll("\"", "");
			ps.setString(index, st);
		} else {
			LOGGER.info("Unknown type " + value.getClass().getName() + " in while updating parameters at # " + (index));
		}
	}

	private static void setParameters(ResultSet rs, StringBuilder strbuiObj, int type, String column, int index, boolean lastColumn) throws SQLException {
		if (type == Types.VARCHAR || type == Types.CHAR) {
			strbuiObj.append("\"").append(column).append("\"").append(":").append("\"").append(rs.getString(index)).append("\"").append((lastColumn) ? "" : ", ");
		} else if (type == Types.BIGINT) {
			strbuiObj.append("\"").append(column).append("\"").append(":").append(rs.getLong(index)).append((lastColumn) ? "" : ",");
		} else if (type == Types.TINYINT || type == -7) {
			strbuiObj.append("\"").append(column).append("\"").append(":").append(rs.getBoolean(index)).append((lastColumn) ? "" : ",");
		} else {
			LOGGER.debug("Unknown Column =" + column + " with type " + type);
		}
	}

	private String getClassName(Object domainObj) {
		String clazz = domainObj.getClass().getName();
		return clazz.substring((clazz.lastIndexOf(".") + 1), clazz.length()).toLowerCase().trim();
	}

	private String getClassName(Class domainObj) {
		String clazz = domainObj.getName();
		return clazz.substring((clazz.lastIndexOf(".") + 1), clazz.length()).toLowerCase().trim();
	}

	private void closeconnection() {
		if (connection != null) {
			try {
				if (!connection.isClosed()) {
					connection.commit();
					connection.setAutoCommit(true);
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				LOGGER.error("DB connection close exception...", e);
			}
		}
	}
}
