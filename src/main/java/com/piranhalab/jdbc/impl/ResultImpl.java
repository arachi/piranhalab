/**
 *
 */
package com.piranhalab.jdbc.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.common.base.CaseFormat;
import com.piranhalab.jdbc.Column;
import com.piranhalab.jdbc.JdbcException;
import com.piranhalab.jdbc.Result;

/**
 * @author fgx
 *
 */
class ResultImpl implements Result {
	/**
	 * 検索結果の開始位置
	 */
	private Integer offset = null;
	/**
	 * 検索結果の取得制限件数
	 */
	private Integer limit = null;
	/**
	 * 次の検索結果の開始位置。ない場合はnull
	 */
	private Integer next = null;
	/**
	 * 検索結果
	 */
	private List<Object> data = null;

	/**
	 * ヘッダ情報
	 */
	private Column[] headers  = null;

	/**
	 *
	 * @param rs
	 * @param offset
	 * @param limit
	 * @param isArray
	 * @param isHeader
	 * @param columnCase
	 * @param objectCase
	 * @param header
	 * @throws SQLException
	 */
	ResultImpl(ResultSet rs, int offset, int limit, boolean isArray, boolean isHeader, CaseFormat columnCase, CaseFormat objectCase, String[] columns) throws JdbcException{
		super();
		this.offset = offset;
		this.limit = limit;

		try {
			ResultSetMetaData meta = rs.getMetaData();
			if(columns != null){
				headers = ColumnImpl.getHeaders(meta, columns);
			}
			int columnCount = meta.getColumnCount();
			String [] columnLabels = null;
			if(isHeader || !isArray){
				columnLabels = ColumnImpl.getLabels(meta);
			}
			rs.absolute(offset);
			data = new ArrayList<Object>();
			if(isArray){
				if (isHeader){
					data.add(columnLabels);
				}
			} else if (columnCase != null && objectCase != null){
				for(int i = 0; i < columnLabels.length; i++ ){
					columnLabels[i] = columnCase.to(objectCase, columnLabels[i]);
				}
			}
			List<Object> rowArray = null;
			LinkedHashMap<String, Object> rowMap = null;
			for(int count = 0; count < limit && rs.next(); count++){
				if(isArray){
					rowArray = new ArrayList<Object>();
					data.add(rowArray);
				} else {
					rowMap = new LinkedHashMap<String, Object>();
					data.add(rowMap);
				}
				for(int column = 0; column < columnCount; column++){
					Object value = rs.getObject(column + 1);
					if(isArray){
						rowArray.add(value);
					} else {
						rowMap.put(columnLabels[column], value);
					}
				}

			}
			if(rs.next()){
				next = offset + limit;
			}
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
	}

	/**
	 *
	 * @param data
	 */
	ResultImpl(List<Object> data) {
		super();
		this.data = data;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Result#getOffset()
	 */
	@Override
	public Integer getOffset() {
		return offset;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Result#getLimt()
	 */
	@Override
	public Integer getLimt() {
		return limit;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Result#getNext()
	 */
	@Override
	public Integer getNext() {
		return next;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Result#getData()
	 */
	@Override
	public List<Object> getData() {
		return data;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Result#getHeader()
	 */
	@Override
	public Column[] getHeader() {
		return headers;
	}

}
