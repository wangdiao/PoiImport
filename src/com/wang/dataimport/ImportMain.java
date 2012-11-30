package com.wang.dataimport;

import java.util.List;

import com.wang.common.FileHelper;

public class ImportMain {

	/**
	 * @param args 无
	 */
	public static void main(String[] args) {
		ImportContext context = new ImportContext();
		List<String> list = FileHelper.getAllFilesByExt(null, "xls");
		for (String path : list) {
			String filename = FileHelper.getFileNameNoExt(path);
			try {
				DataStrategy strategy = new AditorJdbcDataSave(filename);
				context.setDataStrategy(strategy);
				context.saveFileXLS(path, 2);//北京上海等头为两行的改为1
				strategy.close();
				System.out.println(filename+"导入完成!!!!");
			} catch (Exception e) {
				System.out.println(filename+"无法导入:数据库无此出版社");
				e.printStackTrace();
			}
		}
		System.out.println("导入完成！！！！！！！！！！！！！！");
	}
}
