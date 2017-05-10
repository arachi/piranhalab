/**
 *
 */
package com.piranhalab.jdbc.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.piranhalab.jdbc.JdbcException;

/**
 * @author fgx
 *
 */
abstract class AbstractSqlHolder<T, R> extends AbstractDataSourceHolder<R> {

	private String sql;

	private ListParameterHolder listParam = null;

	private MapParameterHolder mapParam = null;

	AbstractSqlHolder(DataSource dataSource, String sql) {
		super(dataSource);
		this.sql = sql;
	}


	/**
	 *
	 * @param conn
	 * @param params
	 * @return
	 * @throws JdbcException
	 */
	@SuppressWarnings("unchecked")
	public PreparedStatement getPrepared(Connection conn, Object params, PreparedStatement stmt) throws JdbcException {
		if(listParam != null){
			stmt = listParam.getPrepared(conn, (List<Object>)params, stmt);
		} else if (mapParam != null){
			stmt = mapParam.getPrepared(conn, (Map<String, Object>)params, stmt);
		} else {
			stmt = (new ListParameterHolder(sql)).getPrepared(conn, null, stmt);
		}
		return stmt;
	}


	/**
	 *
	 * @param conn
	 * @param params
	 * @return
	 * @throws JdbcException
	 */
	@SuppressWarnings("unchecked")
	public CallableStatement getCallable(Connection conn, Object params, int [] in, Map<Integer, String> out) throws JdbcException {
		CallableStatement stmt = null;
		if(listParam != null){
			stmt = listParam.getCallable(conn, (List<Object>)params, in, out);
		} else if (mapParam != null){
			stmt = mapParam.getCallable(conn, (Map<String, Object>)params, in, out);
		} else {
			stmt = (new ListParameterHolder(sql)).getCallable(conn, null, in, out);
		}
		return stmt;
	}

	/**
	 *
	 * @param params
	 * @return
	 * @throws JdbcException
	 */
	protected void setParams(List<Object> params)  throws JdbcException{
		if(mapParam != null){
			throw new JdbcException(new IllegalArgumentException("params must belong List<Object>"));
		}
		if(listParam == null){
			listParam = new ListParameterHolder(sql);
		}
	}

	/**
	 *
	 * @param params
	 * @return
	 * @throws JdbcException
	 */
	protected void setParams(Map<String, Object> params)  throws JdbcException{
		if(listParam != null){
			throw new JdbcException(new IllegalArgumentException("params must belong Map<String, Object>"));
		}
		if(mapParam == null){
			mapParam = new MapParameterHolder(sql);
		}
	}



	/**
	 *
	 * @param params
	 * @return
	 * @throws JdbcException
	 */
	public abstract T params(List<Object> params) throws JdbcException;

	/**
	 *
	 * @param params
	 * @return
	 * @throws JdbcException
	 */
	public abstract T params(Map<String, Object> params) throws JdbcException;

}
