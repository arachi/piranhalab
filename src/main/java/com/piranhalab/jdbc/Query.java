/**
 *
 */
package com.piranhalab.jdbc;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.google.common.base.CaseFormat;

/**
 * プリコンパイル済SQL文を解釈するためのインタフェースです。
 * パラメータ等の設定についてはMartin Fowlerらの
 * 「流れるようなインターフェース(fluent interface)」を使って表記出来ます(下記参照)。
 *
 * <code>
 * JdbcFactory factory = JdbcFactoryImpl,create(dataSource);
 * Result result = factory.query("SELECT ID, NAME, BIRTHDAY, SALARY FROM EMPLOYEES WHERE SALARY > ?")
 * 		.objects()
 * 		.header("label")
 * 		.params(Arrays.asList({300000}))
 * 		.execute();
 * </code>
 * @author Arachi
 *
 */
public interface Query {

	/**
	 * パラメータを設定します。
	 * 引数は要素がオブジェクト型のListで、
	 * プリコンパイルされたSQL文の置換文字?に、
	 * 要素の順番に当てはめられます。
	 * このインスタンス自身を返します。
	 *
	 * @param params パラメータを表すオブジェクトのList
	 * @return このインスタンス自身
	 */
	Query params(List<Object> params)  throws JdbcException;

	/**
	 * パラメータを設定します。
	 * 引数はキーが文字列型、値がオブジェクト型のMapとして与えられ、
	 * プリコンパイルされたSQL文の置換文字列「:文字列」に
	 * 当てはめられます。引数のキー文字列に該当しない場合はnullが
	 * 与えられたものと解釈されます。
	 * このインスタンス自身を返します。
	 *
	 * @param params パラメータを表す、キーが文字列型、値がオブジェクト型のMapt
	 * @return このインスタンス自身
	 */
	Query params(Map<String, Object> params)  throws JdbcException;

	/**
	 * executeの戻り値のdataプロパティの列情報をList型にします。
	 * 具体的にはexecute()メソッドの結果を返すResultインタフェースのgetDataで
	 * 列を表すオブジェクトのListをListにしたもの(2次元配列)を返すようにします。
	 * このメソッド自体はこのインスタンス自身を返します。
	 *
	 * @return このインスタンス自身
	 */
	Query arrays();

	/**
	 * executeの戻り値のdataプロパティの列情報をList型にします。
	 * 具体的にはexecute()メソッドの結果を返すResultインタフェースのgetDataで
	 * 列を表すオブジェクトのListをListにしたもの(2次元配列)を返すようにします。
	 * 引数がtrueの場合、getDataで返されるListの最初の要素はヘッダのラベルになります。
	 * ラベルは、ResultSetMetaDataのgetColumnLabelで取得されるものです。
	 * したがって、SELECT文の場合、通常は列名、列名にASで表示名が設定された場合は
	 * 表示名がヘッダのラベルになります。
	 * このメソッド自体はこのインスタンス自身を返します。
	 *
	 * @param heading 	trueの場合、dataプロパティの最初の要素はヘッダのラベルになる。
	 * @return このインスタンス自身
	 */
	Query arrays(boolean heading);

	/**
	 * executeの戻り値のdataプロパティの列情報をMap型にします。
	 * 具体的にはexecute()メソッドの結果を返すResultインタフェースのgetDataで
	 * 列のヘッダをキー、値を値としたMapのListを返すようにします。
	 * このメソッド自体はこのインスタンス自身を返します。
	 *
	 * @return このインスタンス自身
	 */
	Query objects();

	/**
	 * executeの戻り値のdataプロパティの列情報をMap型にします。
	 * 具体的にはexecute()メソッドの結果を返すResultインタフェースのgetDataで
	 * 列のヘッダをキー、値を値としたMapのListを返すようにします。
	 * 引数は、列のラベルのフォーマットと、それをMapのキーでどう変換するかを指定します。
	 * いずれかがnullの場合は、変換を行わないように設定します。
	 *
	 * このメソッド自体はこのインスタンス自身を返します。
	 *
	 * @param columnFormat
	 * @param memberFormat
	 * @return このインスタンス自身
	 */
	Query objects(CaseFormat columnFormat, CaseFormat memberFormat);

	/**
	 * executeの戻り値のheadersプロパティでヘッダ情報を返すようにします。
	 *
	 * このメソッド自体はこのインスタンス自身を返します。
	 *
	 * @return このインスタンス自身
	 */
	Query header(String ...column);

	/**
	 *
	 * @return
	 * @throws SQLException
	 */
	Result execute() throws JdbcException;

	/**
	 *
	 * @param offset
	 * @return
	 * @throws SQLException
	 */
	Result execute(int offset) throws JdbcException;

	/**
	 *
	 * @param offset
	 * @param limit
	 * @return
	 * @throws SQLException
	 */
	Result execute(int offset, int limit) throws JdbcException;
}
