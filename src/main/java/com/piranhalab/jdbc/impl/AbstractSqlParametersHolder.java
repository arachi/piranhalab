/**
 *
 */
package com.piranhalab.jdbc.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.piranhalab.jdbc.JdbcException;

/**
 * @author fgx
 *
 */
public abstract class AbstractSqlParametersHolder<T, R> extends AbstractSqlHolder<T, R> {

	private List<List<Object>> paramLists = null;

	private List<Map<String, Object>> paramMaps = null;

	private boolean batch = false;

	/**
	 *
	 * @param dataSource
	 * @param sql
	 */
	AbstractSqlParametersHolder(DataSource dataSource, String sql) {
		super(dataSource, sql);
		paramLists = new ArrayList<List<Object>>();
		paramMaps = new ArrayList<Map<String, Object>>();
	}

	/**
	 *
	 * @return
	 */
	protected List<? extends Object> getParamList() {
		List<? extends Object> result = null;
		if(paramLists != null){
			result = paramLists;
		} else if(paramMaps != null){
			result = paramMaps;
		}
		return result;
	}

	/**
	 * @return isBatch
	 */
	public boolean isBatch() {
		return batch;
	}

	/*
	 * (非 Javadoc)
	 * @see com.pirahna.jdbc.impl.AbstractSqlHolder#setParams(java.util.List)
	 */
	@Override
	public void setParams(List<Object> params) throws JdbcException {
		super.setParams(params);
		paramLists.add(params);
	}

	/*
	 * (非 Javadoc)
	 * @see com.pirahna.jdbc.impl.AbstractSqlHolder#setParams(java.util.Map)
	 */
	@Override
	public void setParams(Map<String, Object> params) throws JdbcException {
		super.setParams(params);
		paramMaps.add(params);
	}

	/**
	 *
	 * @param isBatch
	 * @return
	 * @throws JdbcException
	 */
	protected R execute(boolean batch) throws JdbcException {
		this.batch = batch;
		return super.execute();
	}


}
