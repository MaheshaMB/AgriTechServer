package com.shavika.agritech.api.db;

import java.sql.SQLException;
import java.util.List;

public abstract interface SessionFactory<T> {

	public abstract int save(T paramT) throws SQLException;

	public abstract int save(List<T> domainObjs) throws SQLException;

	public abstract int update(T domainObj) throws SQLException;

	public abstract int update(List<T> domainObjs) throws SQLException;

	public abstract int deleteById(Class<T> domainObj, long id) throws SQLException;

	public abstract int deleteById(Class<T> domainObj, List<Long> id) throws SQLException;

	public abstract int deleteAsSoft(Class<T> domainObj, long id) throws SQLException;

	public abstract int deleteAsSoft(Class<T> domainObj, List<Long> id) throws SQLException;

	public abstract Object find(Class<T> paramClass, long paramInt) throws SQLException;

	public abstract List<T> findAll(Class<T> paramClass) throws SQLException;

	public abstract boolean isRecordExist(Class<T> domainObj, long id) throws SQLException;
}
