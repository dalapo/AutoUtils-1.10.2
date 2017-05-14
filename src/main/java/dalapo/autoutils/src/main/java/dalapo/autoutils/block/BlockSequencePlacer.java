package dalapo.autoutils.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import dalapo.autoutils.AutoUtils;
import dalapo.autoutils.helper.TextureRegistryHelper;
import dalapo.autoutils.logging.Logger;
import dalapo.autoutils.reference.GUIIDList;
import dalapo.autoutils.tileentity.TileEntitySequencePlacer;
import dalapo.autoutils.tileentity.TileEntityStackMover;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSequencePlacer extends BlockInventoryDirectional
{
	public BlockSequencePlacer(Material mtl, String name)
	{
		super(mtl, name);
		setCreativeTab(CreativeTabs.REDSTONE);
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack itemstack)
	{
		super.onBlockPlacedBy(world, pos, state, placer, itemstack);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ep, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		super.onBlockActivated(world, pos, state, ep, hand, heldItem, side, hitX, hitY, hitZ);
		if (!world.isRemote)
		{
			ep.openGui(AutoUtils.instance, GUIIDList.ORDERPLACER, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		Logger.info("Entered createTileEntity in BlockSequencePlacer");
		return new TileEntitySequencePlacer();
	}
}