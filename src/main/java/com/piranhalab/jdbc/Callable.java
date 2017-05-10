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
public interface Callable {

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
	Callable params(List<Object> params) throws JdbcException;

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
	Callable params(Map<String, Object> params) throws JdbcException;

	/**
	 *
	 * @return
	 * @throws JdbcException
	 */
	Result execute() throws JdbcException;


}
