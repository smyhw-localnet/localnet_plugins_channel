package online.smyhw.localnet.plugins.channel;

import online.smyhw.localnet.data.DataManager;

public class data 
{
	public static int get(String User)
	{
		int temp1 = lnp.CLconfig.get_int("data."+User);
		if(temp1==0) 
		{
			int pd=lnp.CLconfig.get_int("config.default");
			set(User,pd);
			return pd;
		}
		return temp1;
	}
	
	public static boolean set(String User,int channel)
	{
		lnp.CLconfig.set("data."+User, channel);
		return DataManager.SaveConfig("./configs/channel.config", lnp.CLconfig);
	}
}
