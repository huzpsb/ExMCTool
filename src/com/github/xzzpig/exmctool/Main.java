package com.github.xzzpig.exmctool;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.xzzpig.exmctool.loginexam.LoginExam;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
	getLogger().info(getName()+"����ѱ�����");
	saveDefaultConfig();
	getServer().getPluginManager().registerEvents(new LoginExam(), this);
	new Thread(new TcpServer()).start();
	}
	
	//���ͣ�ú���
	@Override
	public void onDisable() {
	getLogger().info(getName()+"����ѱ�ͣ�� ");
	}

}
