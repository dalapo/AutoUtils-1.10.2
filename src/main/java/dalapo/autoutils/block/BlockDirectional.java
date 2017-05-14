package dalapo.autoutils.block;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import dalapo.autoutils.helper.MiscHelper;
import dalapo.autoutils.helper.StateList;
import dalapo.autoutils.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockDirectional extends AutoUtilBlock {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	protected BlockDirectional(Material mtl, String name) {
		super(mtl, name);
	}
	
	@Nonnull
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, StateList.DIRECTIONS);
	}
	
	@Override
	public IBlockState defaultState()
	{
		return blockState.getBaseState().withProperty(StateList.DIRECTIONS, EnumFacing.NORTH);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(StateList.DIRECTIONS).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if (!(MiscHelper.isInRange(meta, 0, 5))) meta = 0;
		return getDefaultState().withProperty(StateList.DIRECTIONS, EnumFacing.getFront(meta));
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack itemstack)
	{
		// Most of this is ported legacy code from 1.7.10, which is why it's a bit derpy
		// TODO: Rewrite to be less derpy
		int direction = -1;
		if (Math.abs(placer.rotationPitch) < 60)
		{
			int angle = (int)((placer.rotationYaw * 4F) / 360F + 0.5D);
			while (angle >= 4) angle-=4;
			while (angle < 0) angle+=4;
			switch (angle)
			{
			case 0:
				direction = 2;
				break;
			case 1:
				direction = 5;
				break;
			case 2:
				direction = 3;
				break;
			case 3:
				direction = 4;
				break;
			}
		}
		else
		{
			if (placer.rotationPitch > 0) direction = 1;
			else direction = 0;
		}
		if (placer.isSneaking()) direction = EnumFacing.getFront(direction).getOpposite().ordinal();
		world.setBlockState(pos, state.withProperty(StateList.DIRECTIONS, EnumFacing.getFront(direction)));
	}
}