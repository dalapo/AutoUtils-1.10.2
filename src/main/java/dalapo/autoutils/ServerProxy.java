package dalapo.autoutils;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends CommonProxy {
	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		super.init(e);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		super.postInit(e);
	}
}