package dalapo.autoutils.item;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import dalapo.autoutils.block.BlockRSNotifier;
import dalapo.autoutils.helper.ChatHelper;
import dalapo.autoutils.helper.TextureRegistryHelper;
import dalapo.autoutils.logging.Logger;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRedstoneWatcher extends AutoUtilItem {
		
		public ItemRedstoneWatcher(String name)
		{
			super(name);
			setHasSubtypes(true);
			setMaxDamage(0);
			setMaxStackSize(1);
		}
		
		/*
		@Override
		public int getDamage(ItemStack itemstack)
		{
			boolean isActive = itemstack.getTagCompound().getBoolean("Active");
			return isActive ? 1 : 0;
		}
		*/
		
		@Override
		public void onUpdate(ItemStack itemstack, World world, Entity ep, int i, boolean b)
		{
			super.onUpdate(itemstack, world, ep, i, b);
			if (!itemstack.hasTagCompound()) return;
			// 1.7.10 legacy code...
			int x = itemstack.getTagCompound().getInteger("bound_x");
			int y = itemstack.getTagCompound().getInteger("bound_y");
			int z = itemstack.getTagCompound().getInteger("bound_z");
			if (world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof BlockRSNotifier && world.isBlockPowered(new BlockPos(x, y, z)))
			{
				itemstack.setItemDamage(1);
			}
			else itemstack.setItemDamage(0);
			
			if (!(world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof BlockRSNotifier))
			{
				itemstack.setTagCompound(null);
			}
		}
		
		@Override
		public EnumActionResult onItemUse(ItemStack itemstack, EntityPlayer ep, World world, BlockPos pos, EnumHand hand, EnumFacing side, float pX, float pY, float pZ)
		{
			if (!(world.getBlockState(pos).getBlock() instanceof BlockRSNotifier)) return EnumActionResult.PASS;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("bound_x", pos.getX());
			nbt.setInteger("bound_y", pos.getY());
			nbt.setInteger("bound_z", pos.getZ());
			itemstack.setTagCompound(nbt);
			ChatHelper.sendCoords("Linked to ", pos);
			return EnumActionResult.SUCCESS;
		}
}