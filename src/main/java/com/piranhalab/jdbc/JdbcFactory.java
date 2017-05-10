/**
 *
 */
package com.piranhalab.jdbc;

import java.util.Map;

/**
 * @author fgx
 *
 */
public interface JdbcFactory {
	/**
	 *
	 * @param sql
	 * @return
	 */
	Query query(String sql);

	Update update(String sql);

	Updates updates();

	Callable procedure(String sql, int[] in, Map<Integer, String> out);

	Callable procedure(String sql, String[] in, Map<String, String> out);

	Callable function(String sql, String returnSqlTypeName);

}
