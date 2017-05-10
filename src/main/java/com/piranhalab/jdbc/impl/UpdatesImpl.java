/**
 *
 */
package com.piranhalab.jdbc.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.piranhalab.jdbc.JdbcException;
import com.piranhalab.jdbc.Update;
import com.piranhalab.jdbc.Updates;

/**
 * @author fgx
 *
 */
public class UpdatesImpl extends AbstractDataSourceHolder<List<Integer>> implements Updates {

	/**
	 *
	 */
	private List<Update> updates;

	/**
	 *
	 */
	private boolean isBatch = false;


	UpdatesImpl(DataSource dataSource) {
		super(dataSource);
		updates = new ArrayList<Update>();
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Updates#execute()
	 */
	@Override
	public List<Integer> execute() throws JdbcException {
		return execute(false);
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Updates#execute(boolean)
	 */
	@Override
	public List<Integer> execute(boolean isBatch) throws JdbcException {
		this.isBatch = isBatch;
		return super.execute();
	}

	/* (非 Javadoc)
	 * @see com.pirahna.jdbc.Updates#add(java.lang.String)
	 */
	@Override
	public Update add(String sql) {
		Update update = new UpdateImpl(getDataSource(), sql);
		updates.add(update);
		return update;
	}

	/*
	 * (非 Javadoc)
	 * @see com.pirahna.jdbc.impl.AbstractDataSourceHolder#execute(java.sql.Connection)
	 */
	@Override
	protected List<Integer> execute(Connection conn) throws JdbcException {
		List<Integer> result = new ArrayList<Integer>();
		for(Update update: updates){
			result.add(update.execute(isBatch));
		}
		return result;
	}

}
