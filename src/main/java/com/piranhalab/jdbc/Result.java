/**
 *
 */
package com.piranhalab.jdbc;

import java.util.List;

/**
 * @author fgx
 *
 */
public interface Result {
	/**
	 *
	 * @return
	 */
	Integer getOffset();
	/**
	 *
	 * @return
	 */
	Integer getLimt();
	/**
	 *
	 * @return
	 */
	Integer getNext();
	/**
	 *
	 * @return
	 */
	List<Object> getData();
	/**
	 *
	 * @return
	 */
	Column[] getHeader();
}
