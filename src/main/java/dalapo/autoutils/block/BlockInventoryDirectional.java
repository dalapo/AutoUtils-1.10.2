package dalapo.autoutils.block;

import javax.annotation.Nonnull;

import dalapo.autoutils.logging.Logger;
import dalapo.autoutils.tileentity.ActionOnRedstone;
import dalapo.autoutils.tileentity.TileEntityStackMover;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockInventoryDirectional extends BlockDirectional implements ITileEntityProvider {

	public BlockInventoryDirectional(Material mtl, String name) {
		super(mtl, name);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack itemstack)
	{
		super.onBlockPlacedBy(world, pos, state, placer, itemstack);
		Logger.info("Entered onBlockPlacedBy in BlockInventoryDirectional");
		world.addTileEntity(createNewTileEntity(world, 0));
	}
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block)
	{
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof ActionOnRedstone)
		{
			((ActionOnRedstone)te).performAction();
		}
	}
	
	@Override
	public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state)
	{
		IInventory inv = (IInventory)world.getTileEntity(pos);
		for (int i=0; i<inv.getSizeInventory(); i++)
		{
			if (inv.getStackInSlot(i) != null)
			{
				EntityItem ei = new EntityItem(world, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, inv.getStackInSlot(i));
				world.spawnEntityInWorld(ei);
			}
		}
	}
}