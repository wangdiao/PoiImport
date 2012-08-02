package com.wang.dataimport;

import java.util.Map;

/**
 * 为ImportContext提供数据处理接口，每次操作处理单行数据
 * @author wangdi_ao
 * 2012-07-29
 */
public interface DataStrategy {
	
	/**
	 * 将取到的datamap数据做预处理
	 * @param datamap key为表头，value对应当前值
	 */
	public abstract void prepareData(Map<String, String> datamap);

	/**
	 * 预处理后所做的操作
	 * @return
	 */
	public abstract boolean save();

	/**
	 * 对该类销毁前的处理，如关闭数据库连接等
	 * @return
	 */
	public abstract boolean close();

}
