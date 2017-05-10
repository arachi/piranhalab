/**
 *
 */
package com.piranhalab.jdbc.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.piranhalab.jdbc.JdbcException;

/**
 * @author fgx
 *
 */
class ListParameterHolder implements ParameterHolder<List<Object>>{

	private String sql;

	public ListParameterHolder(String sql) {
		this.sql = sql;
	}

	/*
	 * (非 Javadoc)
	 * @see com.pirahna.jdbc.impl.ParameterHolder#getPreparedStatement(java.sql.Connection, java.lang.Object)
	 */
	@Override
	public PreparedStatement getPrepared(Connection conn, List<Object> params, PreparedStatement stmt) throws JdbcException{
		try{
			if(stmt == null){
				stmt = conn.prepareStatement(sql);
			}
			try {
				if(params != null){
					setParams(stmt, params);
				}
			} catch (JdbcException e) {
				stmt.close();
			}
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
		return stmt;
	}

	/*
	 * (非 Javadoc)
	 * @see com.pirahna.jdbc.impl.ParameterHolder#getCallable(java.sql.Connection, java.lang.Object)
	 */
	@Override
	public CallableStatement getCallable(Connection conn, List<Object> params, int [] in,  Map<Integer, String> out) throws JdbcException {
		CallableStatement stmt = null;
		try{
			stmt = conn.prepareCall(sql);
			try {
				setParams(stmt, params, in);
				for(int pos: out.keySet()){
					stmt.registerOutParameter(pos, JDBCType.valueOf(out.get(pos)));
				}
			} catch (JdbcException e) {
				stmt.close();
			}
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
		return stmt;
	}


	/*
	 * (非 Javadoc)
	 * @see com.pirahna.jdbc.impl.ParameterHolder#getPreparedStatement(java.sql.PreparedStatement, java.lang.Object)
	 */
	@Override
	public void setParams(PreparedStatement stmt, List<Object> params) throws JdbcException{
		try {
			int index = 1;
			for(Object param : params){
				if(param == null){
					stmt.setNull(index,  java.sql.Types.NULL);
				} else {
					stmt.setObject(index, param);
				}
				index++;
			}
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
	}

	/*
	 *
	 */
	public void setParams(PreparedStatement stmt, List<Object> params, int [] in) throws JdbcException{
		if(in == null){
			in = new int[params.size() - 1];
			for(int i = 0;  i < in.length - 1; i++){
				in[i] = i + 2;
			}
		}
		try {
			for (int index = 0; index < params.size(); index++) {
				Object value = params.get(index);
				if(value == null){
					stmt.setNull(in[index],  java.sql.Types.NULL);
				} else {
					stmt.setObject(in[index], value);
				}
			}
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
	}

}
