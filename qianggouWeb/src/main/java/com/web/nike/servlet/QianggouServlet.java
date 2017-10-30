package com.web.nike.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.nike.add.Addproduct;
import com.web.nike.add.OpenProduct;
import com.web.nike.util.PreData;

public class QianggouServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuilder output = new StringBuilder("");

		String url = request.getParameter("productUrl");

		while (!initPage(url)) {
			long t1 = System.currentTimeMillis();
			while (true) {
				long t2 = System.currentTimeMillis();
				if (t2 > (t1 + 1000)) {
					break;
				}
			}
		}

		Long cutDownTime = PreData.productPageInfo.getCutDownTime();
//		while (true) {
//			if (System.currentTimeMillis() > cutDownTime + 2200) {
//				break;
//			}
//		}
		Addproduct addproduct = new Addproduct(url);
		addproduct.exec();

		request.setAttribute("arg1", output.toString());
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("post request");
		this.doGet(req, resp);
	}

	private boolean initPage(String url) {
		OpenProduct openProduct = new OpenProduct(url);
		return openProduct.initProductPageInfo();
	}
}
