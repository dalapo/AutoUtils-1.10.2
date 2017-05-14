package dalapo.autoutils;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import dalapo.autoutils.gui.AutoUtilsGuiHandler;
import dalapo.autoutils.logging.Logger;
import dalapo.autoutils.registry.ModRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);
		Logger.info("Entered ClientProxy.preInit");
		ModRegistry.initModels();
	}
	
	@Override
	public void init(FMLInitializationEvent e)
	{
		super.init(e);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e)
	{
		super.postInit(e);
	}
}