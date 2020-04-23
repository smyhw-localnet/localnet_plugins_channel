package online.smyhw.localnet.plugins.channel;

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
		message.info("得到指令:"+cmd+";num="+CommandFJ.js(cmd));
		if(CommandFJ.js(cmd)>2)
		{
			String temp1 = CommandFJ.fj(cmd, 1);
			String temp2 = CommandFJ.fj(cmd, 2);
			int pd = Integer.parseInt(temp1);
			data.set(temp2, pd);
			DataManager.SaveConfig("./configs/channel.config", CLconfig);
			User.sendto("已经将终端<"+temp2+">切换到频道:"+pd);
			return;
		}
		else
		{
			String temp1 = CommandFJ.fj(cmd, 1);
			int pd = Integer.parseInt(temp1);
			data.set(User.ID, pd);
			DataManager.SaveConfig("./configs/channel.config", CLconfig);
			User.sendto("已经切换到频道:"+pd);
			return;
		}
	}
	
	public static void lr(ChatINFO_Event dd)
	{
		if(data.get(dd.From_User.ID)!=data.get(dd.To_User.ID))
		{
			dd.Cancel=true;
		}
	}
}
