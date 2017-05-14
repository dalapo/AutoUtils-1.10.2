package dalapo.autoutils;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import dalapo.autoutils.gui.AutoUtilsGuiHandler;
import dalapo.autoutils.logging.Logger;
import dalapo.autoutils.packet.PacketHandler;
import dalapo.autoutils.registry.ModRegistry;
import dalapo.autoutils.registry.RecipeRegistry;

public class CommonProxy {
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		// This code doesn't run and I have no idea why
		Logger.info("Entered CommonProxy.preInit");
		ModRegistry.init();
		PacketHandler.registerMessages("AutoUtils");
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		Logger.info("Entered CommonProxy.init");
		NetworkRegistry.INSTANCE.registerGuiHandler(AutoUtils.instance, new AutoUtilsGuiHandler());
		RecipeRegistry.init();
	}
	
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		Logger.info("Entered CommonProxy.postInit");
	}
}