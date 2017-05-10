/**
 *
 */
package com.piranhalab.jdbc.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.piranhalab.jdbc.JdbcException;

/**
 * @author fgx
 *
 */
abstract class AbstractDataSourceHolder<R> {
	private DataSource dataSource;

	/**
	 * @param dataSource
	 */
	AbstractDataSourceHolder(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}


	/**
	 * @return dataSource
	 */
	protected final DataSource getDataSource() {
		return dataSource;
	}


	/**
	 * @return dataSource
	 * @throws JdbcException
	 */
	protected final Connection getConnection() throws JdbcException {
		Connection result = null;
		try {
			result = dataSource.getConnection();
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
		return result;
	}


	/**
	 *
	 * @return
	 * @throws JdbcException
	 */
	protected R execute()  throws JdbcException {
		R result = null;
		try{
			Connection conn =  getConnection();
			try{
				conn.setAutoCommit(false);;
				result = execute(conn);
				conn.commit();
			} finally {
				conn.rollback();
				conn.close();
			}
		} catch (SQLException e){
			throw new JdbcException(e);
		}
		return result;
	}

	/**
	 *
	 * @param conn
	 * @return
	 * @throws JdbcException
	 */
	protected abstract R execute(Connection conn) throws JdbcException;

}
