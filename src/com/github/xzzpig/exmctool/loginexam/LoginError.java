package com.github.xzzpig.exmctool.loginexam;

public class LoginError {
	public static String getError(int id){
		String error = "";
		switch(id){
		case 1:
			error = "�޷�����������";
			break;
		case 2:
			error = "��֤����";
			break;
		case 3:
			error = "�������";
			break;
		}
		
		return error;
	}
}
