/**
 *
 */
package com.piranhalab.jdbc.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.base.CaseFormat;
import com.piranhalab.jdbc.Column;
import com.piranhalab.jdbc.JdbcException;

/**
 * @author fgx
 *
 */
class ColumnImpl implements Column{
	/**
	 * 表のカタログ名。適用不可の場合は""。
	 */
	private String catalogName = null;

	/**
	 * 列のクラス名。Javaクラスの完全指定された名前。
	 */
	private String columnClassName = null;

	/**
	 * 通常の最大幅(文字数)
	 */
	private Integer columnDisplaySize = null;

	/**
	 * 表示に使用する列の推奨タイトル
	 */
	private String columnLabel = null;

	/**
	 * 列の名前
	 */
	private String columnName = null;

	/**
	 * 列のデータベース固有の型名
	 */
	private String columnTypeName = null;

	/**
	 * 列のサイズ
	 */
	private Integer precision = null;

	/**
	 * 列の小数点以下の桁数
	 */
	private Integer scale = null;

	/**
	 * 列の表のスキーマ名
	 */
	private String schemaName = null;

	/**
	 * 列の表名
	 */
	private String tableName = null;

	/**
	 * 列が自動的に番号付けされるかどうか
	 */
	private Boolean autoIncrement = null;

	/**
	 * 列の大文字小文字が区別されるかどうか
	 */
	private Boolean caseSensitive = null;

	/**
	 * 列がキャッシュの値かどうか
	 */
	private Boolean currency = null;

	/**
	 * 列の書込みが必ず成功するかどうか
	 */
	private Boolean definitelyWritable = null;

	/**
	 * 列にNULL値が許可されるかどうか
	 */
	private Integer nullable = null;

	/**
	 * 列が絶対的に書込み可能でないかどうか
	 */
	private Boolean readOnly = null;

	/**
	 * 列をwhere節で使用できるかどうか
	 */
	private Boolean searchable = null;

	/**
	 * 列の値が符号付き数値かどうか
	 */
	private Boolean signed = null;

	/**
	 * 列への書込みを成功させることができるかどうか
	 */
	private Boolean writable = null;



	/**
	 *
	 * @param meta
	 * @param column
	 * @param settingFields
	 * @throws SQLException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	ColumnImpl(ResultSetMetaData meta, int column, Map<Field, Method> settingFields) throws JdbcException {
		try{
			for(Field field : settingFields.keySet()){
				Method method = settingFields.get(field);
				field.set(this, method.invoke(meta, new Object[]{ new Integer(column) }));
			}
		} catch (IllegalAccessException e) {
			throw new JdbcException(e);
		} catch (IllegalArgumentException e) {
			throw new JdbcException(e);
		} catch (InvocationTargetException e) {
			throw new JdbcException(e);
		}
	}

	/**
	 *
	 * @param columns
	 * @return
	 * @throws NoSuchFieldException
	 * @throws NoSuchMethodException
	 */
	static Map<Field, Method> getSettingFields(String[] columns) throws JdbcException{
		Map<Field, Method> results = new LinkedHashMap<Field, Method>();
		try{
			for(String column : columns) {
				Field field;
				field = ColumnImpl.class.getDeclaredField(column);
				field.setAccessible(true);
				String methodName = "get" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, column);
				Method method = ResultSetMetaData.class.getMethod(methodName, new Class[]{ int.class });
				results.put(field, method);
			}
		} catch (NoSuchFieldException e) {
			throw new JdbcException(e);
		} catch (NoSuchMethodException e) {
			throw new JdbcException(e);
		}
		return results;
	}

	/**
	 *
	 * @param meta
	 * @param columns
	 * @return
	 * @throws SQLException
	 * @throws NoSuchFieldException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	static Column[] getHeaders(ResultSetMetaData meta, String[] columns) throws JdbcException{
		Column[] results = null;
		try{
			Map<Field, Method> settingFields = getSettingFields(columns);
			int count = meta.getColumnCount();
			results = new Column[count];
			for(int i = 0; i < count; i++){
				results[i] = new ColumnImpl(meta, i + 1, settingFields);
			}
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
		return results;
	}

	/**
	 *
	 * @param meta
	 * @return
	 * @throws JdbcException
	 */
	static String[] getLabels(ResultSetMetaData meta) throws JdbcException{
		String[] results = null;
		try{
			int count = meta.getColumnCount();
			results = new String[count];
			for(int i = 0; i < count; i++){
				results[i] = meta.getColumnLabel(i + 1);
			}
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
		return results;
	}


	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#getCatalogName()
	 */
	@Override
	public String getCatalogName() {
		return catalogName;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#getClassName()
	 */
	@Override
	public String getColumnClassName() {
		return columnClassName;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#getDisplaySize()
	 */
	@Override
	public Integer getColumnDisplaySize() {
		return columnDisplaySize;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#getLabel()
	 */
	@Override
	public String getColumnLabel() {
		return columnLabel;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#getName()
	 */
	@Override
	public String getColumnName() {
		return columnName;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#getTypeName()
	 */
	@Override
	public String getColumnTypeName() {
		return columnTypeName;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#getPrecision()
	 */
	@Override
	public Integer getPrecision() {
		return precision;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#getScale()
	 */
	@Override
	public Integer getScale() {
		return scale;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#getSchemaName()
	 */
	@Override
	public String getSchemaName() {
		return schemaName;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#getTableName()
	 */
	@Override
	public String getTableName() {
		return tableName;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#isAutoIncrement()
	 */
	@Override
	public Boolean isAutoIncrement() {
		return autoIncrement;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#isCaseSensitive()
	 */
	@Override
	public Boolean isCaseSensitive() {
		return caseSensitive;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#isCurrency()
	 */
	@Override
	public Boolean isCurrency() {
		return currency;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#isDefinitelyWritable()
	 */
	@Override
	public Boolean isDefinitelyWritable() {
		return definitelyWritable;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#getNullable()
	 */
	@Override
	public String getNullable() {
		String result = null;
		if(nullable != null){
			switch (nullable) {
			case ResultSetMetaData.columnNullable:
				result = "yes";
				break;
			case ResultSetMetaData.columnNoNulls:
				result = "no";
				break;
			case ResultSetMetaData.columnNullableUnknown:
				result = "unknown";
				break;
			}
		}
		return result;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#isReadOnly()
	 */
	@Override
	public Boolean isReadOnly() {
		return readOnly;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#isSearchable()
	 */
	@Override
	public Boolean isSearchable() {
		return searchable;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#isSigned()
	 */
	@Override
	public Boolean isSigned() {
		return signed;
	}

	/* (非 Javadoc)
	 * @see com.piranhalab.jdbc.Column#isWritable()
	 */
	@Override
	public Boolean isWritable() {
		return writable;
	}

}
