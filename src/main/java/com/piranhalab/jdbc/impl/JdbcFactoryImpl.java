/**
 *
 */
package com.piranhalab.jdbc.impl;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.piranhalab.jdbc.Callable;
import com.piranhalab.jdbc.JdbcFactory;
import com.piranhalab.jdbc.Query;
import com.piranhalab.jdbc.Update;
import com.piranhalab.jdbc.Updates;

/**
 * @author fgx
 *
 */
public class JdbcFactoryImpl implements JdbcFactory {

	private DataSource dataSource;
	private int defaultLimit;


	public JdbcFactoryImpl(DataSource dataSource, int defaultLimit) {
		this.dataSource = dataSource;
		this.defaultLimit = defaultLimit;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.JdbcFactory#query(java.lang.String)
	 */
	@Override
	public Query query(String sql) {
		Query result = new QueryImpl(dataSource, sql, defaultLimit);
		return result;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.JdbcFactory#update(java.lang.String)
	 */
	@Override
	public Update update(String sql) {
		Update result = new UpdateImpl(dataSource, sql);
		return result;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.JdbcFactory#updates()
	 */
	@Override
	public Updates updates() {
		Updates result = new UpdatesImpl(dataSource);
		return result;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.JdbcFactory#procedure(java.lang.String, int[], java.util.Map)
	 */
	@Override
	public Callable procedure(String sql, int[] in, Map<Integer, String> out) {
		Callable result = new CallableImpl(dataSource, sql, in, out);
		return result;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.JdbcFactory#procedure(java.lang.String, java.lang.String[], java.util.Map)
	 */
	@Override
	public Callable procedure(String sql, String[] in, Map<String, String> out) {
		Callable result = new CallableImpl(dataSource, sql, in, out);
		return result;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.JdbcFactory#function(java.lang.String, java.lang.String)
	 */
	@Override
	public Callable function(String sql, String returnSqlTypeName) {
		Map<Integer, String> out = new HashMap<Integer, String>();
		out.put(1, returnSqlTypeName);
		Callable result = new CallableImpl(dataSource, sql, null, out);
		return result;
	}

}
