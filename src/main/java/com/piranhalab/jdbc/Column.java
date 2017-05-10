/**
 *
 */
package com.piranhalab.jdbc;

/**
 * @author fgx
 *
 */
public interface Column {

	String CATALOG_NAME = "catalogName";

	String COLUMN_CLASS_NAME = "columnClassName";

	String COLUMN_DISPLAY_SIZE = "columnDisplaySize";

	String COLUMN_LABEL = "columnLabel";

	String COLUMN_NAME = "columnName";

	String COLUMN_TYPE_NAME = "columnTypeName";

	String PRECISION = "precision";

	String SCALE = "scale";

	String SCHEMA_NAME = "schemaName";

	String TABLE_NAME = "tableName";

	String AUTO_INCREMENT = "autoIncrement";

	String CASE_SENSITIVE = "caseSensitive";

	String CURRENCY = "currency";

	String DEFINITELY_WRITABLE = "definitelyWritable";

	String NULLABLE = "nullable";

	String READ_ONLY = "readOnly";

	String SEARCHABLE = "searchable";

	String SIGNED = "signed";

	String WRITABLE = "writable";

	/**
	 * 列の表のカタログ名を取得します。
	 * 適用不可の場合は""を返します。
	 *
	 * @return 表のカタログ名。適用不可の場合は"。
	 */
	String getCatalogName();

	/**
	 * 列のクラス名(Javaクラスの完全指定された名前)を返します。
	 * 列から値を検索するためにResultSet.getObjectメソッドが呼び出されると、
	 * このJavaクラスのインスタンスが生成されます。
	 * ResultSet.getObjectは、このメソッドによって返されたクラスの
	 * サブクラスを返すことがあります。
	 *
	 * @return 指定された列の値を取り出すためにResultSet.getObjectメソッドによって使用される
	 * 			Javaプログラミング言語のクラスの完全指定された名前。
	 * 			カスタム・マッピングに使用されるクラス名。
	 */
	String getColumnClassName();

	/**
	 * 列の通常の最大幅を文字数で示します。
	 *
	 * @return 幅として許可される通常の最大文字数。
	 */
	Integer getColumnDisplaySize();

	/**
	 * 印刷や表示に使用する、指定された列の推奨タイトルを取得します。
	 * 通常、推奨タイトルは、SQL AS節として指定されます。
	 * SQL ASが指定されていない場合、getColumnLabelから返される値は、
	 * getColumnNameメソッドによって返される値と同じになります。
	 *
	 * @return 列の推奨タイトル。
	 */
	String getColumnLabel();

	/**
	 * 列の名前を取得します。
	 *
	 * @return 列の名前。
	 */
	String getColumnName();

	/**
	 * 列のデータベース固有の型名を取得します。
	 * 列の型がユーザー定義型の場合は、完全指定された型名を返します。
	 *
	 * @return データベースが使用する型名。列の型がユーザー定義型の場合は、完全指定された型名。
	 */
	String getColumnTypeName();

	/**
	 * 指定された列に指定された列の精度(サイズ)を取得します。
	 * 数値データの場合は、最大精度です。
	 * 文字データの場合は、文字数です。
	 * 日時データ型の場合は、String表現の文字数です(小数点以下の秒の構成要素の最大許容精度を仮定)。
	 * バイナリ・データの場合は、バイト数です。
	 * ROWIDデータ型の場合は、バイト数です。
	 * 列サイズが適用できないデータ型の場合は、0が返されます。
	 *
	 * @return 列の精度(サイズ)。
	 */
	Integer getPrecision();

	/**
	 * 列のスケール(小数点以下の桁数)を取得します。
	 * スケールが適用できないデータ型の場合は、0が返されます。
	 *
	 * @return 列のスケール(小数点以下の桁数)
	 */
	Integer getScale();

	/**
	 * 列の表のスキーマを取得します。
	 * 適用不可の場合は""を返します。
	 *
	 * @return スキーマ名。適用不可の場合は""。
	 */
	String getSchemaName();

	/**
	 * 列の表名を取得します。
	 * 適用不可の場合は""を返します。
	 *
	 * @return 表名。適用不可の場合は""。
	 */
	String getTableName();

	/**
	 * 列が自動的に番号付けされるかどうかを示します。
	 *
	 * @return 自動的に番号付けされる場合はBoolean.TRUEを、番号付けされない場合はBoolean.FALSEを返します。
	 */
	Boolean isAutoIncrement();

	/**
	 * 列の大文字小文字が区別されるかどうかを示します。
	 *
	 * @return 大文字小文字が区別される場合はBoolean.TRUEを、区別されされない場合はBoolean.FALSEを返します。
	 */
	Boolean isCaseSensitive();

	/**
	 * 列がキャッシュの値かどうかを示します。
	 *
	 * @return キャッシュの値の場合はBoolean.TRUEを、キャッシュ値でない場合はBoolean.FALSEを返します。
	 */
	Boolean isCurrency();

	/**
	 * 列の書込みが必ず成功するかどうかを示します。
	 *
	 * @return 必ず成功する場合はBoolean.TRUEを、必ずしも成功しない場合はBoolean.FALSEを返します。
	 */
	Boolean isDefinitelyWritable();

	/**
	 * 列にNULL値が許可されるかどうかを示します。
	 *
	 * @return Null値可なら"yes"、不可なら"no"、不明なら"unknown"を返します。
	 */
	String getNullable();

	/**
	 * 列が絶対的に書込み可能でないかどうかを示します。
	 *
	 * @return 書き込み不可能の場合はBoolean.TRUEを、書き込み可能な場合はBoolean.FALSEを返します。
	 */
	Boolean isReadOnly();

	/**
	 * 列をwhere節で使用できるかどうかを示します。
	 *
	 * @return where節で使用できる場合はBoolean.TRUEを、使用できない場合はBoolean.FALSEを返します。
	 */
	Boolean	isSearchable();

	/**
	 * 列の値が符号付き数値かどうかを示します。
	 *
	 * @return 符号付き数値の場合はBoolean.TRUEを、符号付き数値でない場合はBoolean.FALSEを返します。
	 */
	Boolean isSigned();

	/**
	 * 列への書込みを成功させることができるかどうかを示します。
	 *
	 * @return 書込みを成功させることができる場合はBoolean.TRUEを、成功させることができない場合はBoolean.FALSEを返します。
	 */
	Boolean isWritable();
}
