package com.wang.jdbc;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.wang.common.ConfigSingleton;

public class CommonDao {
	
	private QueryRunner run;

	public CommonDao() throws Exception {
		Properties properties = ConfigSingleton.getInstance().getProperties();
		DataSource ds = BasicDataSourceFactory.createDataSource(properties);
		run = new QueryRunner(ds);
	}
	
	/**
	 * 执行sql查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> query(String sql, Object... params)
			throws SQLException {
		ResultSetHandler<List<Map<String, Object>>> handler = new MapListHandler();
		List<Map<String, Object>> list = run.query(sql, handler, params);
		return list;
	}

	/**
	 * 执行sql查询返回第一行
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> queryOneMap(String sql, Object... params)
			throws SQLException {
		ResultSetHandler<Map<String, Object>> handler = new MapHandler();
		Map<String, Object> map = run.query(sql, handler, params);
		return map;
	}

	/**
	 * 执行sql查询返回单一值
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Object queryOne(String sql, Object... params) throws SQLException {
		ResultSetHandler<Object> handler = new ScalarHandler<Object>();
		Object res = run.query(sql, handler, params);
		return res;
	}

	/**
	 * 执行sql操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int execute(String sql, Object... params) throws SQLException {
		return run.update(sql, params);
	}

	/**
	 * 批量执行sql操作 Execute a batch of SQL INSERT, UPDATE, or DELETE queries. The
	 * Connection is retrieved from the DataSource set in the constructor. This
	 * Connection must be in auto-commit mode or the update will not be saved.
	 * 
	 * @param sql
	 * @param params
	 *            An array of query replacement parameters. Each row in this
	 *            array is one set of batch replacement values
	 * @return The number of rows updated per statement
	 * @throws SQLException
	 */
	public int[] batch(String sql, Object[][] params) throws SQLException {
		return run.batch(sql, params);
	}

}
