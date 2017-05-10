/**
 *
 */
package com.piranhalab.jdbc.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.piranhalab.jdbc.JdbcException;

/**
 * @author fgx
 *
 */
abstract class AbstractSqlParameterHolder<T, R> extends AbstractSqlHolder<T, R> {

	private List<Object> paramList = null;

	private Map<String, Object> paramMap = null;

	/**
	 *
	 * @param dataSource
	 * @param sql
	 */
	AbstractSqlParameterHolder(DataSource dataSource, String sql) {
		super(dataSource, sql);
	}

	/**
	 *
	 * @return
	 */
	public Object getParam() {
		Object result = null;
		if(paramList != null){
			result = paramList;

		} else if(paramMap != null){
			result = paramMap;
		}
		return result;
	}

	/**
	 *
	 */
	@Override
	protected void setParams(List<Object> params) throws JdbcException {
		super.setParams(params);
		paramList = params;
	}

	/**
	 *
	 */
	@Override
	protected void setParams(Map<String, Object> params) throws JdbcException {
		super.setParams(params);
		paramMap = params;
	}



}
