package dalapo.autoutils.block;

import java.util.List;

import dalapo.autoutils.AutoUtils;
import dalapo.autoutils.helper.MiscHelper;
import dalapo.autoutils.logging.Logger;
import dalapo.autoutils.reference.GUIIDList;
import dalapo.autoutils.reference.NameList;
import dalapo.autoutils.tileentity.TileEntityStackMover;
import dalapo.autoutils.helper.TextureRegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStackMover extends BlockInventoryDirectional {
	
	private boolean isFiltered;
	
	public BlockStackMover(Material mtl, String name, boolean filter) {
		super(mtl, name);
		this.isFiltered = filter;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ep, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		super.onBlockActivated(world, pos, state, ep, hand, heldItem, side, hitX, hitY, hitZ);
		if (!isFiltered) return false;
		if (!world.isRemote)
		{
			ep.openGui(AutoUtils.instance, GUIIDList.STACKMOVER, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
//		Logger.info("Entered createTileEntity in BlockStackMover");
		return new TileEntityStackMover();
	}
}