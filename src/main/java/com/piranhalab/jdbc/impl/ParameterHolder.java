/**
 *
 */
package com.piranhalab.jdbc.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

import com.piranhalab.jdbc.JdbcException;

/**
 * @author fgx
 *
 */
interface ParameterHolder <T>{
	/**
	 *
	 * @param conn
	 * @param params
	 * @param stmt
	 * @return
	 * @throws JdbcException
	 */

	PreparedStatement getPrepared(Connection conn, T params, PreparedStatement stmt) throws JdbcException;

	/**
	 *
	 * @param conn
	 * @param param
	 * @return
	 */
	CallableStatement getCallable(Connection conn, T params, int [] in, Map<Integer, String> out) throws JdbcException;


	/**
	 *
	 * @param statement
	 * @param param
	 * @return
	 */
	void setParams(PreparedStatement stmt, T params) throws JdbcException;

}
