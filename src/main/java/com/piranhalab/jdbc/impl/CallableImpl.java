/**
 *
 */
package com.piranhalab.jdbc.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import com.piranhalab.jdbc.Callable;
import com.piranhalab.jdbc.JdbcException;
import com.piranhalab.jdbc.Result;

/**
 * @author fgx
 *
 */
public class CallableImpl extends AbstractSqlParametersHolder<Callable, Result> implements Callable {

	private int[] in;
	private Map<Integer, String> out;

	CallableImpl(DataSource dataSource, String sql, int[] in, Map<Integer, String> out) {
		super(dataSource, sql);
		this.in = in;
		this.out = out;

	}

	/**
	 *
	 * @param dataSource
	 * @param sql
	 * @param in
	 * @param out
	 */
	CallableImpl(DataSource dataSource, String sql, String[] in, Map<String, String> out) {
		super(dataSource, sql);
		// TODO
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Callable#params(java.util.List)
	 */
	@Override
	public Callable params(List<Object> params) throws JdbcException {
		setParams(params);
		return this;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Callable#params(java.util.Map)
	 */
	@Override
	public Callable params(Map<String, Object> params) throws JdbcException {
		setParams(params);
		return this;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Callable#execute()
	 */
	@Override
	public Result execute() throws JdbcException {
		return super.execute();
	}

	/**
	 *
	 */
	@Override
	protected Result execute(Connection conn) throws JdbcException {
		Result result = null;
		CallableStatement stmt = null;
		List<? extends Object> paramList = getParamList();
		List<Object> data = new ArrayList<Object>();
		try{
			if(paramList.isEmpty()){
				stmt = getCallable(conn, null, in, out);
				try{
					data.add(getRow(stmt, out.keySet()));
					stmt.execute();
				} finally {
					stmt.close();
				}
			} else {
				for(Object param : paramList){
					stmt = getCallable(conn, param, in, out);
					try{
						data.add(getRow(stmt, out.keySet()));
						stmt.execute();
					} finally {
						stmt.close();
					}
				}
			}
			result = new ResultImpl(data);
		} catch (SQLException e) {
				throw new JdbcException(e);
			}
		return result;
	}

	/**
	 *
	 * @param stmt
	 * @param out
	 * @return
	 * @throws JdbcException
	 */
	private List<Object> getRow(CallableStatement stmt, Set<Integer> out) throws JdbcException{
		List<Object> result = new ArrayList<Object>();
		try {
			for(int pos : out){
				result.add(stmt.getObject(pos));
			}
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
		return result;
	}

}
