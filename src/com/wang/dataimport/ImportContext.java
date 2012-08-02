package com.wang.dataimport;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 对电子表格处理的类，通过dataSave操作数据
 * @author wang
 * 2012-07-29
 */
public class ImportContext {

	private DataStrategy dataStrategy;
	private List<String> headlist;

	/**
	 * 根据xls文件名处理电子表格，提取每行数据做对应DataSave的操作
	 * @param fileStr 待处理的文件名
	 * @param headrownum 文件表头的行号，从0开始计算
	 * @return 操作是否成功
	 */
	public boolean saveFileXLS(String fileStr, int headrownum) {
		try {
			File file = new File(fileStr);
			FileInputStream fileIS = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(fileIS);
			HSSFSheet sheet = workbook.getSheetAt(0);

			HSSFRow row;
			List<String> rowList = new ArrayList<String>();
			for (int i = 1; i <= sheet.getLastRowNum(); ++i) {
				row = sheet.getRow(i);
				if (row == null)
					continue;
				rowList.clear();
				for (int j = 0; j < row.getLastCellNum(); j++) {
					String str = null;
					try {
						HSSFCell cell = row.getCell(j);
						if (cell != null)
							str = row.getCell(j).getStringCellValue();
					} catch (IllegalStateException e1) {
						try {
							str = String.valueOf((int) row.getCell(j)
									.getNumericCellValue());
							String regex = "^((\\d+.?\\d+)[Ee]{1}(\\d+))$";
							Pattern pattern = Pattern.compile(regex);
							Matcher m = pattern.matcher(str);
							if (m.matches()) {
								DecimalFormat df = new DecimalFormat("####");
								str = df.format(Double.parseDouble(str));
							}
						} catch (IllegalStateException e2) {
						}
					}
					if (str != null)
						str = str.trim();
					rowList.add(str);
				}
				if (i == headrownum) {
					headlist = new ArrayList<String>(rowList);
				} else if (i > headrownum) {
					Map<String, String> dataMap = getDataMap(rowList);
					if (dataMap != null && dataMap.size() > 0) {
						getDataStrategy().prepareData(dataMap);
						getDataStrategy().save();
					}
				}
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}


	public Map<String, String> getDataMap(List<String> datalist) {
		if (datalist.size() < headlist.size())
			return null;
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < headlist.size(); i++) {
			map.put(headlist.get(i), datalist.get(i));
		}
		return map;
	}


	public DataStrategy getDataStrategy() {
		return dataStrategy;
	}


	public void setDataStrategy(DataStrategy dataStrategy) {
		this.dataStrategy = dataStrategy;
	}

}
