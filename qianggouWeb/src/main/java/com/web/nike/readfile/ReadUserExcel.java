package com.web.nike.readfile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.web.nike.bean.UserLoginInfo;
import com.web.nike.util.PreData;

public class ReadUserExcel {

	public void read(InputStream is) {

		XSSFWorkbook xwb = null;
		List<UserLoginInfo> importList = new ArrayList<UserLoginInfo>();
		UserLoginInfo importTemp = null;

		try {
			xwb = new XSSFWorkbook(is);
			XSSFSheet sheet = xwb.getSheetAt(0);
			int rowstart = sheet.getFirstRowNum();
			int rowEnd = sheet.getLastRowNum();
			for (int j = rowstart + 1; j <= rowEnd; j++) {
				XSSFRow row = sheet.getRow(j);

				String userName = getCellStr(row.getCell(0));
				String userPwd = getCellStr(row.getCell(1));
				String autoSize = getCellNum(row.getCell(2));
				String proxyIp = getCellStr(row.getCell(3));
				String proxyPort = getCellStr(row.getCell(4));
				String proxyUser = getCellStr(row.getCell(5));
				String proxyPwd = getCellStr(row.getCell(6));

				importTemp = new UserLoginInfo(userName, userPwd, autoSize, proxyIp, proxyPort, proxyUser, proxyPwd);
				importList.add(importTemp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		PreData.addUsersInfo(importList);
	}

	private String getCellStr(XSSFCell cell) {
		String temp = null;
		if (cell.getCellType() == 0) {
			Double dt = cell.getNumericCellValue();
			BigDecimal bdt = new BigDecimal(dt);
			temp = bdt.toString();
			if (temp.indexOf(".") != -1) {
				temp = temp.substring(0, temp.indexOf("."));
			}
		} else {
			temp = cell.getStringCellValue();
		}
		return temp;
	}

	private String getCellNum(XSSFCell cell) {
		String temp = null;
		if (cell.getCellType() == 0) {
			Double dt = cell.getNumericCellValue();
			temp = dt.toString();

		} else {
			temp = cell.getStringCellValue();
		}
		return temp;
	}
	
	public void load(List<UserLoginInfo> userList) {
		PreData.addUsersInfo(userList);
	}
}
