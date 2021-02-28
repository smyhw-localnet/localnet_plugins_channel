package online.smyhw.localnet.plugins.channel;

import online.smyhw.localnet.LNlib;
import online.smyhw.localnet.message;
import online.smyhw.localnet.command.cmdManager;
import online.smyhw.localnet.data.DataManager;
import online.smyhw.localnet.data.config;
import online.smyhw.localnet.event.ChatINFO_Event;
import online.smyhw.localnet.event.EventManager;
import online.smyhw.localnet.lib.CommandFJ;
import online.smyhw.localnet.network.Client_sl;

public class lnp 
{
	public static config CLconfig = new config();
	public static void plugin_loaded()
	{
		message.info("频道插件加载");
		try 
		{
			cmdManager.add_cmd("cl", lnp.class.getMethod("cmd", new Class[]{Client_sl.class,String.class}));
			cmdManager.add_cmd("cb", lnp.class.getMethod("cmd_bc", new Class[]{Client_sl.class,String.class}));
			EventManager.AddListener("ChatINFO", lnp.class.getMethod("lr", new Class[] {ChatINFO_Event.class}));
		} 
		catch (Exception e) 
		{
			message.warning("频道插件加载错误!",e);
		}
		CLconfig = DataManager.LoadConfig("./configs/channel.config");
	}
	
	public static void cmd(Client_sl User,String cmd)
	{
//		message.info("得到指令:"+cmd+";num="+CommandFJ.js(cmd));
		if(CommandFJ.js(cmd)==0) 
		{
			User.sendMsg("使用方法: cl <频道> [终端ID]");
		}
		if(CommandFJ.js(cmd)>2)
		{
			String temp1 = CommandFJ.fj(cmd, 1);
			String temp2 = CommandFJ.fj(cmd, 2);
			int pd;
			try 
			{
				pd = Integer.parseInt(temp1);
			}
			catch(NumberFormatException e) {
				User.sendMsg("给定的参数<"+temp1+">无法被识别为数字");
				return;
			}
			if(data.set(temp2, pd))
				User.sendMsg("已经将终端<"+temp2+">切换到频道:"+pd);
			else
				User.sendNote("文件IO", "配置保存失败");
			return;
		}
		else
		{
			String temp1 = CommandFJ.fj(cmd, 1);
			int pd;
			try 
			{
				pd = Integer.parseInt(temp1);
			}
			catch(NumberFormatException e) {
				User.sendMsg("给定的参数<"+temp1+">无法被识别为数字");
				return;
			}
			if(data.set(User.remoteID, pd))
			{
				User.sendMsg("已经切换到频道:"+pd);
			}
			else
				User.sendNote("文件IO", "配置保存失败");
			return;
		}
	}
	
	public static void cmd_bc(Client_sl User,String cmd)
	{
		if(CommandFJ.js(cmd)!=2)
		{
			User.sendNote("参数不匹配", "使用方法: bc <消息>");
			return;
		}
		LNlib.SendAll(CommandFJ.fj(cmd, 1), User);
		return;
	}
	
	public static void lr(ChatINFO_Event dd)
	{
		//特殊频道，全局广播
		if(data.get(dd.From_User.remoteID)==1) {return;}
		if(data.get(dd.From_User.remoteID)!=data.get(dd.To_User.remoteID))
		{
			dd.Cancel=true;
		}
	}
}
