/**
 *
 */
package com.piranhalab.jdbc;

import java.util.List;
import java.util.Map;

/**
 * @author fgx
 *
 */
public interface Update {
	/**
	 *
	 * @param params
	 * @return
	 */
	Update params(List<Object> params)  throws JdbcException;

	/**
	 *
	 * @param params
	 * @return
	 */
	Update params(Map<String, Object> params)  throws JdbcException;

	/**
	 *
	 * @return
	 */
	Integer execute() throws JdbcException;

	/**
	 *
	 * @param isBatch
	 * @return
	 */
	Integer execute(boolean isBatch) throws JdbcException;
}
