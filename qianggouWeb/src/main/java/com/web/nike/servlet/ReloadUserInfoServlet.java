package com.web.nike.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.web.nike.add.OpenProduct;
import com.web.nike.bean.UserLoginInfo;
import com.web.nike.login.UsersLogin;
import com.web.nike.readfile.ReadUserExcel;
import com.web.nike.util.PreData;
import com.web.nike.util.StringUtil;

public class ReloadUserInfoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("get request");
		String contentType = request.getContentType();
		StringBuilder output = new StringBuilder("");
		if (contentType != null && contentType.indexOf("multipart/form-data") >= 0) {
			InputStream is = null;
			try {
				List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				if (items != null && items.size() > 0) {
					System.out.println("items size is :" + items.size());
					for (FileItem item : items) {
						if (item.isFormField()) {
						} else {
							System.out.println(item.getFieldName());
							is = item.getInputStream();
							ReadUserExcel readUserExcel = new ReadUserExcel();
							readUserExcel.read(is);
						}
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
				output.append("读取用户信息错误。");
			}
		}

		try {
			initMainPage();
			UsersLogin userLogin = new UsersLogin();
			userLogin.exec();
		} catch (Exception e) {
			output.append("登录用户错误。");
		}

		if (StringUtil.isEmpty(output.toString())) {
			List<UserLoginInfo> userList = PreData.getLoginUserList(false);
			if (userList != null && userList.size() > 0) {
				output.append("以下用户登录失败，请确认用户密码是否正确，或者代理Ip是否稳定可用。" + "<br>");
				for (UserLoginInfo user : userList) {
					output.append(" " + user.getUserName());
				}
			} else {
				output.append("登录完毕。");
			}
		}

		List<UserLoginInfo> userList1 = PreData.getLoginUserList(true);
		for (UserLoginInfo user : userList1) {
			System.out.println(user.getUserName());
		}

		request.setAttribute("arg0", output.toString());
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("post request");
		this.doGet(req, resp);
	}

	private void initMainPage() {
		OpenProduct openProduct = new OpenProduct();
		openProduct.initMainPage();
	}

}
