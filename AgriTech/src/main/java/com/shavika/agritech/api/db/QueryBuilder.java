package com.shavika.agritech.api.db;

import java.util.List;

public class QueryBuilder {

	private static final String SPACE = " ";
	private static final String ASSIGNMENT_OPT = " = ";
	private static final String OPEN_BRACKET = "(";
	private static final String CLOSE_BRACKET = ")";
	private static final String QUESTION_MARK = "?";
	private static final String COMMA = ",";
	private static final String SEMI_COLON = ";";

	private static final String QUERY_FOR_SELECT = "SELECT * FROM";
	private static final String QUERY_INSERT_INTO = "INSERT INTO";
	private static final String QUERY_UPDATE = "UPDATE";
	private static final String QUERY_DELETE = "DELETE FROM";

	private static final String VALUE_KEYWORD = "VALUES";
	private static final String SET_KEYWORD = "SET";
	private static final String WHERE_KEYWORD = "WHERE";
	private static final String FROM_KEYWORD = "FROM";

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static String getClazzQuery(String name) {
		return new StringBuilder().append(QUERY_FOR_SELECT).append(SPACE).append(name).toString();
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static String countReservoirlevel(String date) {
		return new StringBuilder().append("SELECT * FROM reservoirlevel where reported_date = str_to_date('").append(date).append("','%d.%m.%Y')").toString();
	}

	/**
	 * 
	 * @param name
	 * @param columnName
	 * @return
	 */
	public static String getInsertQuery(String name, List<String> columnName) {
		if (columnName.size() == 0)
			return null;
		StringBuilder strBuild = new StringBuilder().append(QUERY_INSERT_INTO).append(SPACE).append(name.toUpperCase()).append(SPACE);
		columnsParameter(strBuild, columnName);
		return strBuild.toString();
	}

	private static void columnsParameter(StringBuilder sb, List<String> columnName) {
		if (columnName.size() > 0) {
			sb.append(OPEN_BRACKET).append(SPACE);
			for (int i = 1; i < columnName.size(); i++) {
				String str = columnName.get(i);
				sb.append(str).append((i == (columnName.size() - 1)) ? "" : COMMA).append(SPACE);
			}
			sb.append(CLOSE_BRACKET).append(SPACE);
			columnsvalues(sb, columnName.size());
		}
	}

	private static void columnsvalues(StringBuilder sb, int questionMaekSize) {
		if (questionMaekSize > 0) {
			sb.append(VALUE_KEYWORD).append(SPACE).append(OPEN_BRACKET);
			for (int i = 1; i < questionMaekSize; i++) {
				sb.append(QUESTION_MARK).append((i == (questionMaekSize - 1)) ? "" : COMMA).append(SPACE);
			}
			sb.append(CLOSE_BRACKET).append(SPACE);
		}
	}

	/**
	 * 
	 * @param name
	 * @param columnName
	 * @return
	 */
	public static String getUpdateQuery(String name, List<String> columnName) {
		if (columnName.size() == 0)
			return null;
		StringBuilder strBuild = new StringBuilder().append(QUERY_UPDATE).append(SPACE).append(name.toUpperCase()).append(SPACE).append(SET_KEYWORD).append(SPACE);
		setParameter(strBuild, columnName);
		return strBuild.toString();
	}

	private static void setParameter(StringBuilder sb, List<String> columnName) {
		if (columnName.size() > 0) {
			for (int i = 1; i < columnName.size(); i++) {
				String str = columnName.get(i);
				if (str.toUpperCase().contains("CREATED_ON"))
					continue;
				sb.append(str).append(ASSIGNMENT_OPT).append(QUESTION_MARK).append((i == (columnName.size() - 1)) ? "" : COMMA).append(SPACE);
			}
			sb.append(WHERE_KEYWORD).append(SPACE).append("ID").append(ASSIGNMENT_OPT).append(QUESTION_MARK).append(SPACE);
		}
	}

	/**
	 * 
	 * @param name
	 * @param columnName
	 * @return
	 */
	public static String getSoftDelateQuery(String name) {
		if (name == null || name.length() == 0)
			return null;

		StringBuilder strBuild = new StringBuilder().append(QUERY_UPDATE).append(SPACE).append(name.toUpperCase()).append(SPACE).append(SET_KEYWORD).append(SPACE);
		strBuild.append("IS_DELETED").append(ASSIGNMENT_OPT).append(QUESTION_MARK).append(COMMA).append(SPACE).append("MODIFIED_ON").append(ASSIGNMENT_OPT)
				.append(QUESTION_MARK).append(SPACE);
		strBuild.append(WHERE_KEYWORD).append(SPACE).append("ID").append(ASSIGNMENT_OPT).append(QUESTION_MARK).append(SPACE);
		return strBuild.toString();
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static String getDelateQuery(String name) {
		if (name == null || name.length() == 0)
			return null;
		StringBuilder strBuild = new StringBuilder().append(QUERY_DELETE).append(SPACE).append(name).append(SPACE);
		strBuild.append(WHERE_KEYWORD).append(SPACE).append("ID").append(ASSIGNMENT_OPT).append(QUESTION_MARK).append(SPACE);
		return strBuild.toString();

	}

	public static String findQuery(String name) {
		if (name == null || name.length() == 0)
			return null;
		StringBuilder strBuild = new StringBuilder().append(QUERY_FOR_SELECT).append(SPACE).append(name).append(SPACE);
		strBuild.append(WHERE_KEYWORD).append(SPACE).append("ID").append(ASSIGNMENT_OPT).append(QUESTION_MARK).append(SPACE);
		return strBuild.toString();

	}

	public static String findAllQuery(String name) {
		if (name == null || name.length() == 0)
			return null;
		StringBuilder strBuild = new StringBuilder().append(QUERY_FOR_SELECT).append(SPACE).append(name).append(SPACE);
		return strBuild.toString();

	}

}
