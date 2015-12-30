package com.github.xzzpig.exmctool.listener;
import com.github.xzzpig.exmctool.*;
import com.github.xzzpig.exmctool.event.*;

import org.bukkit.event.*;

public class AppListener implements Listener
{
	{
		System.out.println("[ExMCTool]App客户端已开启");
	}
	
	@EventHandler
	public void onlogin(AppDataReachEvent event){
		String[] data = event.getStringData().split(" ");
		if(!data[0].equalsIgnoreCase("canlogin"))
			return;
		LoginError error = event.getPlayerClient().isLoginPassed();
		if(error != null){
			System.out.println("[ExMCTool]"+event.getPlayerClient().getName()+"App登录失败,原因:"+error);
			event.getClient().sendData(("login deny "+error.getErrorMessage()).getBytes());
			return;
		}
		event.getPlayerClient().sendData("login pass".getBytes());
	}
}
