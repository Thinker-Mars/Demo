package com.cone.thu.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * 用于返回json数据
*@author Cone
*@Version 2019年8月3日 下午3:24:49
*/
public class ResponseUtil {
	
	public static void write(HttpServletResponse response,Object object)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(object);
		out.flush();
		out.close();
	}


}
