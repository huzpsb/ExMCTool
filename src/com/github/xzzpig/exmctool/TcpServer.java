package com.github.xzzpig.exmctool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.bukkit.Bukkit;

import com.github.xzzpig.exmctool.tcp.ClientListener;

public class TcpServer implements Runnable{
	
	private static HashMap<String, Socket> Client = new HashMap<String, Socket>();
	public static HashMap<Socket, Boolean> login = new HashMap<Socket, Boolean>();
	
	@SuppressWarnings("resource")
	@Override
	public void run() {
		ServerSocket ss = null;
		int port = Bukkit.getPluginManager().getPlugin("ExMCTool").getConfig().getInt("port", 10727);
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("[ExMCTool]Wrong:�˿���ռ�ã�TCP����ʧ��");
			return;
		}
		System.out.println("[ExMCTool]TCP�����������");
		while(true){
			Socket s = null;
			try {s = ss.accept();} catch (IOException e) {}
			Client.put(s.getInetAddress().getHostName(), s);
			System.out.println("[ExMCTool]�ͻ���"+s.getInetAddress().getHostName()+"������");
			ClientListener.NewListener(s);
		}
	}

	public static Socket getClient(String iP) {
		if(!Client.containsKey(iP))
			Client.put(iP, null);
		return Client.get(iP);
	}
	public static HashMap<String, Socket> getClient() {
		return Client;
	}
}
