package com.github.xzzpig.exmctool;
import com.github.xzzpig.BukkitTools.*;
import java.io.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class Cilent_Player extends Cilent
{
	private String name,password;
	public String uncheckpass,uncheckkey,uncheckplayer;
	public boolean loginexam,login;
	public Cilent_Player(Cilent cilent){
		super(cilent.s);
		cilent.remove();
		askForName();
		this.password = TConfig.getConfigFile("ExMCTool","login.yml").getString("login.password."+name,"NONE");
	}
	
	public static Cilent_Player valueOf(Player player){
		for(Cilent c:cilents){
			if(c.type != CilentType.Player)
				continue;
			if(((Cilent_Player)c).getPlayer() == player)
				return (Cilent_Player)c;
		}
		return null;
	}
	public static Cilent_Player valueOf(String player){
		for(Cilent c:cilents){
			if(c.type != CilentType.Player)
				continue;
			if(((Cilent_Player)c).getName().equalsIgnoreCase(player))
				return (Cilent_Player)c;
		}
		return null;
	}
	
	protected void askForName(){
		try{
			s.getOutputStream().write("player".getBytes());
		}
		catch(IOException e){
			System.out.println("[ExMCTool]获取玩家ID请求发送失败");
		}
	}
	
	public boolean setName(String name){
		this.name = name;
		return true;
	}
	public String getName(){
		return name;
	}
	
	public Player getPlayer(){
		return Bukkit.getPlayer(name);
	}
	
	public void startLoginExam(){
		uncheckkey = null;
		uncheckpass = null;
		uncheckplayer = null;
		loginexam = true;
		sendData("login player".getBytes());
		sendData("login key".getBytes());
		sendData("login password".getBytes());
	}
	
	public LoginError isLoginPassed(){
		if(!loginexam)
			return LoginError.UnStartLogin;
		if(login)
			return null;
		Thread t = new Thread(new Runnable(){
				@Override
				public void run(){
					try{
						Thread.sleep(1000);
					}
					catch(InterruptedException e){}
				}
		});
		t.start();
		while(t.isAlive()&&(uncheckpass==null||uncheckkey==null||uncheckplayer==null)){}
		if(uncheckpass==null||uncheckkey==null||uncheckplayer==null)
			return LoginError.NoData;
		if(!password.equalsIgnoreCase(uncheckpass))
			return LoginError.DifferentPass;
		if(!Vars.loginkey.equalsIgnoreCase(uncheckkey))
			return LoginError.DifferentKey;
		if(!name.equalsIgnoreCase(uncheckplayer))
			return LoginError.DifferentID;
		return null;
	}
}
