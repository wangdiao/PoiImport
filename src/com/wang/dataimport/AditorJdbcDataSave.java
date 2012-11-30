package com.wang.dataimport;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wang.jdbc.CommonDao;

public class AditorJdbcDataSave implements DataStrategy {
	
	private final static Logger logger = Logger
			.getLogger(AditorJdbcDataSave.class);
	
	private CommonDao dbc;
	private List<String> datalist = new ArrayList<String>();
	private String orgid;

	public AditorJdbcDataSave(String filename) throws Exception {
		dbc = new CommonDao();
		String sql = "select t.orgid from t_b_organizationinfo t where t.orgfullname = ?";
		orgid = dbc.queryOne(sql, filename).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see isbn.dataimport.xls.DataSave#save()
	 */
	public boolean save() {
		boolean r = true;;
		if (datalist.size() == 0)
			return false;
		try {
			String sql = "insert into T_B_AUDITOR (AUDITORID, NAME, ORGID, SEX, IDCARDNO, TITLE, PHONE, MOBILE, AUDITORRIGHT, AUDITORSTATUS, RECORDSTATUS, FIELD1) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			int res = dbc.execute(sql, datalist.toArray());
			if (res < 1)
				r = false;
		} catch (SQLException e) {
			r = false;
			logger.error(e.getMessage());
		}
		
		return r;
	}

	public boolean close() {
		return true;
	}

	private String getAuditorright(String r1, String r2, String r3) {
		Integer res = 0;
		if (isRightTrue(r1))
			res += 1;
		if (isRightTrue(r2))
			res += 2;
		if (isRightTrue(r3))
			res += 4;
		return res.toString();
	}

	private boolean isRightTrue(String r) {
		if (r == null || "".equals(r.trim()) || "否".equals(r.trim())
				|| "0".equals(r.trim()) || "无".equals(r.trim()))
			return false;
		else
			return true;
	}

	public void prepareData(Map<String, String> datamap) {
		datalist.clear();
		try {
			String sql = "select seq_t_b_auditor.nextval As auditorid from dual";
			String auditorid = dbc.queryOne(sql).toString();
			String name = datamap.get("姓名");

			String sex = datamap.get("性别");
			String idcardno = datamap.get("证书编号");
			String idcardnoii = datamap.get("身份证号");
			String title = datamap.get("职称");
			String phone = null;
			String mobile = null;
			String auditorright = getAuditorright(datamap.get("初审资格"),
					datamap.get("复审资格"), datamap.get("终审资格"));
			String auditorstatus = "1";
			String recordstatus = "1";
			String field1 = datamap.get("所属部门");
			if (name == null || "".equals(name) || idcardnoii == null
					|| "".equals(idcardnoii))
				return;
			datalist.add(auditorid);
			datalist.add(name);
			datalist.add(orgid);
			datalist.add(sex);
			datalist.add(idcardno);
			datalist.add(title);
			datalist.add(phone);
			datalist.add(mobile);
			datalist.add(auditorright);
			datalist.add(auditorstatus);
			datalist.add(recordstatus);
			datalist.add(field1);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
