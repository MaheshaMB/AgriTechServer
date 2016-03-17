package com.shavika.agritech.api.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionFactoryImpl<T> implements SessionFactory<T> {

	public static boolean isFindAll = false;

	@Override
	public int save(T domainObj) throws SQLException {
		List<T> objectList = new ArrayList<T>();
		objectList.add((T) domainObj);
		return save(objectList);
	}

	@Override
	public int save(List<T> domainObjs) throws SQLException {
		return new PrepareStatementImpl((List<Object>) domainObjs).BuildInsertQuery();
	}

	@Override
	public int update(T domainObj) throws SQLException {
		List<T> objectList = new ArrayList<T>();
		objectList.add((T) domainObj);
		return update(objectList);
	}

	@Override
	public int update(List<T> domainObjs) throws SQLException {
		return new PrepareStatementImpl((List<Object>) domainObjs).BuildUpdateQuery();
	}

	@Override
	public int deleteById(Class<T> domainObj, long id) throws SQLException {
		List<Long> idList = new ArrayList<Long>();
		idList.add(id);
		return deleteById(domainObj, idList);
	}

	@Override
	public int deleteById(Class<T> domainObj, List<Long> id) throws SQLException {
		return new PrepareStatementImpl(domainObj).BuildDeleteQuery(id);
	}

	@Override
	public int deleteAsSoft(Class<T> domainObj, long id) throws SQLException {
		List<Long> idList = new ArrayList<Long>();
		idList.add(id);
		return deleteAsSoft(domainObj, idList);
	}

	@Override
	public int deleteAsSoft(Class<T> domainObj, List<Long> id) throws SQLException {
		return new PrepareStatementImpl(domainObj).BuildSoftDeleteQuery(id);
	}

	@Override
	public Object find(Class<T> domainObj, long paramInt) throws SQLException {
		isFindAll = false;
		List<Object> lists = new PrepareStatementImpl(domainObj).findQuery(isFindAll, paramInt);
		return (Object) lists.get(0);
	}

	@Override
	public List<T> findAll(Class<T> domainObj) throws SQLException {
		isFindAll = true;
		return (List<T>) new PrepareStatementImpl(domainObj).findQuery(isFindAll, 0);
	}

	@Override
	public boolean isRecordExist(Class<T> domainObj, long id) throws SQLException {
		return (find(domainObj, id) != null) ? true : false;
	}

	private String getClassName(T domainObj) {
		String clazz = domainObj.getClass().getName();
		return clazz.substring((clazz.lastIndexOf(".") + 1), clazz.length()).toLowerCase().trim();
	}

}
