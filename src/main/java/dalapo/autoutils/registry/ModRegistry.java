package dalapo.autoutils.registry;

import java.util.List;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import dalapo.autoutils.block.AutoUtilBlock;
import dalapo.autoutils.block.BlockItemRedis;
import dalapo.autoutils.block.BlockRSNotifier;
import dalapo.autoutils.block.BlockSequencePlacer;
import dalapo.autoutils.block.BlockSidedEmitter;
import dalapo.autoutils.block.BlockStackMover;
import dalapo.autoutils.item.AutoUtilItem;
import dalapo.autoutils.item.ItemRedstoneWatcher;
import dalapo.autoutils.logging.Logger;
import dalapo.autoutils.tileentity.TileEntityItemRedis;
import dalapo.autoutils.tileentity.TileEntitySequencePlacer;
import dalapo.autoutils.tileentity.TileEntityStackMover;
import dalapo.autoutils.reference.NameList;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

@GameRegistry.ObjectHolder(NameList.MODID)
public class ModRegistry {
	
	public static List<AutoUtilBlock> blocks = new ArrayList<AutoUtilBlock>();
	public static List<AutoUtilItem> items = new ArrayList<AutoUtilItem>();
	public static List<Class<? extends TileEntity>> tiles = new ArrayList<Class<? extends TileEntity>>();
	
	public static void initBlocks()
	{
		Logger.info("Entered initBlocks");
		blocks.add(new BlockStackMover(Material.WOOD, "stackmover", false));
		blocks.add(new BlockStackMover(Material.IRON, "filtermover", true));
		blocks.add(new BlockRSNotifier(Material.WOOD, "rednotifier"));
		blocks.add(new BlockSequencePlacer(Material.ROCK, "sequenceplacer"));
		blocks.add(new BlockItemRedis(Material.WOOD, "itemredis"));
		blocks.add(new BlockSidedEmitter(Material.ROCK, "sidedrs"));

		for (AutoUtilBlock b : blocks)
		{
			GameRegistry.register(b);
			GameRegistry.register(new ItemBlock(b), b.getRegistryName());
		}
	}
	
	public static void initItems()
	{
		items.add(new ItemRedstoneWatcher("watcher"));
		for (AutoUtilItem i : items)
		{
			GameRegistry.register(i);
		}
	}
	
	public static void initTiles()
	{
		GameRegistry.registerTileEntity(TileEntityStackMover.class, NameList.MODID + "_stackmover");
		GameRegistry.registerTileEntity(TileEntitySequencePlacer.class, NameList.MODID + "_sequenceplacer");
		GameRegistry.registerTileEntity(TileEntityItemRedis.class, NameList.MODID + "_itemredis");
	}
	
//	@SideOnly(Side.CLIENT)
	public static void initModels()
	{
		for (AutoUtilBlock b : blocks)
		{
			b.initModel();
		}
		
		for (AutoUtilItem i : items)
		{
			i.initModel();
		}
	}
	public static void init()
	{
		Logger.info("Entered ModRegistry.init");
		initBlocks();
		initItems();
		initTiles();
	}
	public static int getSize()
	{
		return blocks.size();
	}
}