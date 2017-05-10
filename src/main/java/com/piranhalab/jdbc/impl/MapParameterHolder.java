/**
 *
 */
package com.piranhalab.jdbc.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.piranhalab.jdbc.JdbcException;

/**
 * @author fgx
 *
 */
class MapParameterHolder implements ParameterHolder<Map<String, Object>>{

	private String sql;
	private String[] paramNames;

	static final Pattern pattern = Pattern.compile("\\?(\\w+)");

	/**
	 *
	 * @param sql
	 */
	MapParameterHolder(String sql){
		List<String> names = new ArrayList<String>();
		Matcher matcher = pattern.matcher(sql);
		StringBuilder tmp = new StringBuilder();
		int pos = 0;
		while(matcher.find(pos)){
			names.add(matcher.group(1));
			tmp.append(sql.substring(pos, matcher.start() + 1));
			pos = matcher.end();
		}
		sql = tmp.append(sql.substring(pos)).toString();
	}

	/**
	 *
	 * @param conn
	 * @param params
	 * @return
	 * @throws JdbcException
	 */
	public PreparedStatement getPrepared(Connection conn, Map<String,Object> params, PreparedStatement stmt) throws JdbcException{
		try{
			if(stmt == null){
				stmt = conn.prepareStatement(sql);
			}
			try {
				setParams(stmt, params);
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
	public CallableStatement getCallable(Connection conn, Map<String,Object> params, int [] in, Map<Integer, String> out) throws JdbcException {
		CallableStatement stmt = null;
		try{
			stmt = conn.prepareCall(sql);
			try {
				setParams(stmt, params);
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
	public void setParams(PreparedStatement stmt, Map<String,Object> params) throws JdbcException{
		try {
			int index = 1;
			for(String name : paramNames){
				Object value = params.get(name);
				if(value == null) {
					stmt.setNull(index, Types.NULL);
				} else {
					stmt.setObject(index, value);
				}
				index++;
			}
		} catch (SQLException e) {
			 throw new JdbcException(e);
		}
	}


}
