package cz.etn.etnshop.controller.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestParser {

	public static RequestParseResult parseRequest(HttpServletRequest request) throws Exception{

		RequestParseResult rpr = new RequestParseResult();
		if(request.getParameter("id") != null) {
			rpr.setId(Integer.parseInt(request.getParameter("id")));
		}
		if(request.getParameter("name") != null) {
			rpr.setName(request.getParameter("name"));
		}
		if(request.getParameter("serial1") != null) {
			rpr.setSerial1(Integer.parseInt(request.getParameter("serial1")));
		}
		if(request.getParameter("serial2") != null) {
			rpr.setSerial2(Integer.parseInt(request.getParameter("serial2")));
		}

		return rpr;
	}

}
