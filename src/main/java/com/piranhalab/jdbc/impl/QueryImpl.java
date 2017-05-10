/**
 *
 */
package com.piranhalab.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.google.common.base.CaseFormat;
import com.piranhalab.jdbc.JdbcException;
import com.piranhalab.jdbc.Query;
import com.piranhalab.jdbc.Result;

/**
 * @author fgx
 *
 */
public class QueryImpl extends AbstractSqlParameterHolder<Query, Result> implements Query {

	private int defalitLimit;

	private int offset;

	private int limit;

	private boolean isArray = false;

	private boolean heading = false;

	private CaseFormat columnFormat;

	private CaseFormat memberFormat;

	private String[] columns;

	/**
	 *
	 * @param dataSource
	 * @param sql
	 */
	QueryImpl(DataSource dataSource, String sql, int defaultLimit) {
		super(dataSource, sql);
		this.defalitLimit = defaultLimit;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Query#params(java.util.List)
	 */
	@Override
	public Query params(List<Object> params) throws JdbcException {
		super.setParams(params);
		return this;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Query#params(java.util.Map)
	 */
	@Override
	public Query params(Map<String, Object> params) throws JdbcException {
		super.setParams(params);
		return this;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Query#arrays()
	 */
	@Override
	public Query arrays() {
		arrays(false);
		return this;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Query#arrays(boolean)
	 */
	@Override
	public Query arrays(boolean heading) {
		isArray = true;
		this.heading = heading;
		return this;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Query#objects()
	 */
	@Override
	public Query objects() {
		isArray = false;
		return this;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Query#objects(com.google.common.base.CaseFormat, com.google.common.base.CaseFormat)
	 */
	@Override
	public Query objects(CaseFormat columnFormat, CaseFormat memberFormat) {
		isArray = false;
		this.columnFormat = columnFormat;
		this.memberFormat = memberFormat;
		return this;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Query#header(java.lang.String[])
	 */
	@Override
	public Query header(String... columns) {
		this.columns = columns;
		return this;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Query#execute()
	 */
	@Override
	public Result execute() throws JdbcException {
		return execute(0, defalitLimit);
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Query#execute(int)
	 */
	@Override
	public Result execute(int offset) throws JdbcException {
		return execute(offset, defalitLimit);
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Query#execute(int, int)
	 */
	@Override
	public Result execute(int offset, int limit) throws JdbcException {
		this.offset = offset;
		this.limit = limit;
		return super.execute();
	}

	/*
	 * (非 Javadoc)
	 * @see com.pirahna.jdbc.impl.AbstractDataSourceHolder#execute(java.sql.Connection)
	 */
	@Override
	protected Result execute(Connection conn) throws JdbcException {
		Result result = null;
		PreparedStatement stmt = null;
		try{
			stmt = getPrepared(conn, getParam(), null);
			try{
				ResultSet rs = stmt.executeQuery();
				try{
					result = new ResultImpl(rs, offset, limit, isArray, heading, columnFormat, memberFormat, columns);
				} finally {
					rs.close();
				}
			} finally {
				stmt.close();
			}
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
		return result;
	}

}
