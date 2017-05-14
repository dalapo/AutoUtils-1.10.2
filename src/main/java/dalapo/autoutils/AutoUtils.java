package dalapo.autoutils;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import dalapo.autoutils.block.AutoUtilBlock;
import dalapo.autoutils.item.AutoUtilItem;
import dalapo.autoutils.logging.Logger;
import dalapo.autoutils.packet.PacketHandler;
import dalapo.autoutils.reference.*;
import dalapo.autoutils.registry.*;

@Mod(modid=NameList.MODID, name=NameList.MODNAME, version=NameList.VERSION)
public class AutoUtils
{
	public static Block[] blocks;
	public static Item[] items;
	@SidedProxy(clientSide="dalapo.autoutils.ClientProxy", serverSide="dalapo.autoutils.ServerProxy")
	public static CommonProxy proxy;
	
	@Instance(NameList.MODID)
	public static AutoUtils instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Logger.info("Entered AutoUtils.preInit");
		ModRegistry.init();
		PacketHandler.registerMessages("AutoUtils");
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		Logger.info("Entered AutoUtils.init");
		proxy.init(event);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		Logger.info("Entered AutoUtils.postInit");
		proxy.postInit(event);
	}
}