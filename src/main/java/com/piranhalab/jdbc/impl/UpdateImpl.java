/**
 *
 */
package com.piranhalab.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.piranhalab.jdbc.JdbcException;
import com.piranhalab.jdbc.Update;

/**
 * @author fgx
 *
 */
public class UpdateImpl extends AbstractSqlParametersHolder<Update, Integer> implements Update {

	/**
	 *
	 * @param dataSource
	 * @param sql
	 */
	UpdateImpl(DataSource dataSource, String sql) {
		super(dataSource, sql);
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Update#params(java.util.List)
	 */
	@Override
	public Update params(List<Object> params) throws JdbcException {
		setParams(params);
		return this;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Update#params(java.util.Map)
	 */
	@Override
	public Update params(Map<String, Object> params) throws JdbcException {
		setParams(params);
		return this;
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Update#execute()
	 */
	@Override
	public Integer execute() throws JdbcException {
		return super.execute(false);
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Update#execute(boolean)
	 */
	@Override
	public Integer execute(boolean isBatch) throws JdbcException {
		return super.execute(isBatch);
	}

	/**
	 *
	 */
	@Override
	protected Integer execute(Connection conn) throws JdbcException {
		int result = 0;
		PreparedStatement stmt = null;
		List<? extends Object> paramList = getParamList();
		try{
			if(paramList.isEmpty()){
				stmt = getPrepared(conn, null, null);
				try{
					result = stmt.executeUpdate();
				} finally {
					stmt.close();
				}
			} else {
				try{
					for(Object param : paramList){
						stmt = getPrepared(conn, param, stmt);
						if(isBatch()){
							stmt.addBatch();
						} else {
							result += stmt.executeUpdate();
						}
					}
					if(isBatch()){
						for(int count: stmt.executeBatch()){
							result += count;
						}
					}
				} finally {
					if(stmt != null){
						stmt.close();
					}
				}
			}
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
		return result;
	}

}
