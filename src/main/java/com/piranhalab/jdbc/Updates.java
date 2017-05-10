/**
 *
 */
package com.piranhalab.jdbc;

import java.util.List;

/**
 * @author fgx
 *
 */
public interface Updates {

	/**
	 *
	 * @return
	 * @throws JdbcException
	 */
	List<Integer> execute() throws JdbcException;

	/**
	 *
	 * @param isBatch
	 * @return
	 * @throws JdbcException
	 */
	List<Integer> execute(boolean isBatch) throws JdbcException;

	/**
	 *
	 * @param sql
	 * @return
	 */
	Update add(String sql);
}
