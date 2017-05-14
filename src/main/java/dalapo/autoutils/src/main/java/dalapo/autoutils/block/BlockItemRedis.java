package dalapo.autoutils.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import dalapo.autoutils.AutoUtils;
import dalapo.autoutils.helper.ChatHelper;
import dalapo.autoutils.helper.TextureRegistryHelper;
import dalapo.autoutils.logging.Logger;
import dalapo.autoutils.packet.PacketHandler;
import dalapo.autoutils.reference.GUIIDList;
import dalapo.autoutils.tileentity.TileEntityItemRedis;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockItemRedis extends AutoUtilBlock
{
	public BlockItemRedis(Material mtl, String name)
	{
		super(mtl, name);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack is)
	{
		world.addTileEntity(createTileEntity(world, state));
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer ep, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		super.onBlockActivated(world, pos, state, ep, hand, heldItem, side, hitX, hitY, hitZ);
		if (world.isRemote)
		{
			ep.openGui(AutoUtils.instance, GUIIDList.DISTRIBUTOR, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityItemRedis();
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos)
	{
		return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		if (!world.isRemote)
		{
			Logger.info(String.format("Entity: %s, %s, %s", (int)entity.posX, (int)entity.posY, (int)entity.posZ));
			Logger.info(String.format("Block: %s, %s, %s", pos.getX(), pos.getY(), pos.getZ()));
			Logger.info(entity instanceof EntityItem);
			if (!(entity instanceof EntityItem)) return;
			if ((int)entity.posX == pos.getX() && (int)(entity.posY + 0.1) > pos.getY() && (int)entity.posZ == pos.getZ())
			{
				ItemStack is = ((EntityItem)entity).getEntityItem();
				entity.setDead();
				((TileEntityItemRedis)(world.getTileEntity(pos))).redistributeItems(is);
			}
		}
	}
}