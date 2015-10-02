package com.github.xzzpig.exmctool.tcp;

import java.io.IOException;
import java.net.Socket;

import com.github.xzzpig.BukkitTools.MD5;
import com.github.xzzpig.exmctool.Main;
import com.github.xzzpig.exmctool.Vars;
import com.github.xzzpig.exmctool.loginexam.LoginPlayer;

public class ClientSolver implements Runnable{
	Socket s;
	String[] data;
	
	public ClientSolver(Socket s,String data) {
		this.s = s;
		this.data = data.split(" ");
	}
	
	public void run() {
		String  data2 = "";
		for(String s:data)
			data2 = data2+s+" ";
		System.out.println("[ExMCTool]"+LoginPlayer.Get(s).getName()+":"+data2);
		Solve();
	}
	
	private void Solve() {
		switch(GetArry(0)){
		case "name" :
			LoginPlayer.Get(s).Rebulid(GetArry(1),s);
			System.out.println("[ExMCTool]�ɹ���ȡ�������:"+LoginPlayer.Get(s).getName());
			break;
		case "login" :
			LoginPlayer.SolveData(LoginPlayer.Get(s), GetArry(1),GetArry(2));
			break;
		case "changepassword" :
			if(!GetArry(1).equalsIgnoreCase(MD5.GetMD5Code(Vars.adminkey))){
				try {
					s.getOutputStream().write("changepassword result fail adminkey".getBytes());
				} catch (IOException e) {e.printStackTrace();}
				System.out.println("[ExMCTool]"+LoginPlayer.Get(s).getName()+"����ʧ��(�����������)");
			}
			else if(!Vars.passwords.containsKey(LoginPlayer.Get(s).getName())){
				Vars.passwords.put(LoginPlayer.Get(s).getName(),GetArry(3));
				try {
					s.getOutputStream().write("changepassword result success".getBytes());
				} catch (IOException e) {e.printStackTrace();}
				System.out.println("[ExMCTool]"+LoginPlayer.Get(s).getName()+"���ܳɹ�");
			}
			else if(Vars.passwords.get(LoginPlayer.Get(s).getName()).equalsIgnoreCase("null")||
					Vars.passwords.get(LoginPlayer.Get(s).getName()).equalsIgnoreCase(GetArry(2))){
				Vars.passwords.put(LoginPlayer.Get(s).getName(), GetArry(3));
				try {
					s.getOutputStream().write("changepassword result success".getBytes());
				} catch (IOException e) {e.printStackTrace();}
				System.out.println("[ExMCTool]"+LoginPlayer.Get(s).getName()+"���ܳɹ�");
			}
			else{
				try {
					s.getOutputStream().write("changepassword result fail password".getBytes());
					System.out.println("here");//TODO
				} catch (IOException e) {e.printStackTrace();}
				System.out.println("[ExMCTool]"+LoginPlayer.Get(s).getName()+"����ʧ��(ԭ�������)");
			}
			Main.SaveConfigs();
			LoginPlayer.Get(s).Rebulid(LoginPlayer.Get(s).getName(),s);
			break;
		}
	}
	
	private String GetArry(int i) {
		String r = "";
		if(data.length<i){
			r = "";
		}
		r = data[i];
		return r;
	}
}
