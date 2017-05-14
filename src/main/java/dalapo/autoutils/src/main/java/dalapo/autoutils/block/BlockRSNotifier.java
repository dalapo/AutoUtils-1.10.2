package dalapo.autoutils.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

public class BlockRSNotifier extends AutoUtilBlock {

	public BlockRSNotifier(Material mtl, String name) {
		super(mtl, name);
		setCreativeTab(CreativeTabs.REDSTONE);
	}
}