package com.cos.photogramstart.Util;

public class Script {
	public static String back(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert('"+msg+"');"); // 팝업창 띄우기
		sb.append("history.back();"); //뒤로가기
		sb.append("</script>");
		return sb.toString();
		
	}
}
