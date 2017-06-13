package dalapo.autoutils.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;
import dalapo.autoutils.reference.BlockIDList;
import dalapo.autoutils.reference.ItemIDList;
import dalapo.autoutils.reference.NameList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipeRegistry {
	public static void init()
	{
		GameRegistry.addRecipe(new ItemStack(ModRegistry.blocks.get(BlockIDList.STACK_MOVER)), new Object[] {
				"WWW", "HPD", "WWW", 'W', Blocks.PLANKS, 'H', Blocks.HOPPER, 'P', Blocks.PISTON, 'D', Blocks.DROPPER});
		GameRegistry.addRecipe(new ItemStack(ModRegistry.blocks.get(BlockIDList.FILTER_STACK_MOVER)), new Object[] {
				"QQQ", "LML", "QQQ", 'Q', Items.QUARTZ, 'L', new ItemStack(Items.DYE, 1, 4), 'M', ModRegistry.blocks.get(BlockIDList.STACK_MOVER)});
		GameRegistry.addRecipe(new ItemStack(ModRegistry.blocks.get(BlockIDList.REDSTONE_NOTIFIER)),
				" T ", "WQW", "WWW", 'T', Blocks.REDSTONE_TORCH, 'W', Blocks.PLANKS, 'Q', Blocks.QUARTZ_BLOCK);
		GameRegistry.addRecipe(new ItemStack(ModRegistry.items.get(ItemIDList.WATCHER)),
				"QTQ", "SSS", 'Q', Items.QUARTZ, 'T', Blocks.REDSTONE_TORCH, 'S', new ItemStack(Blocks.STONE_SLAB, 1, 0));
		GameRegistry.addRecipe(new ItemStack(ModRegistry.blocks.get(BlockIDList.SEQUENCE_PLACER)),
				"III", "CDT", "III", 'I', Blocks.PLANKS, 'C', Blocks.CHEST, 'D', Items.DIAMOND, 'T', Blocks.DISPENSER);
		GameRegistry.addRecipe(new ItemStack(ModRegistry.blocks.get(BlockIDList.REDSTONE_PANEL)),
				"RR", "SS", "SS", 'R', Items.REDSTONE, 'S', Blocks.STONE_SLAB);
		GameRegistry.addRecipe(new ItemStack(ModRegistry.blocks.get(BlockIDList.ITEM_REDIS)),
				"WHW", "RQG", "WLW", 'W', Blocks.PLANKS, 'H', Blocks.HOPPER, 'R', Items.REDSTONE, 'Q', Blocks.QUARTZ_BLOCK, 'L', new ItemStack(Items.DYE, 1, 4), 'G', Items.GLOWSTONE_DUST);
	}
}
