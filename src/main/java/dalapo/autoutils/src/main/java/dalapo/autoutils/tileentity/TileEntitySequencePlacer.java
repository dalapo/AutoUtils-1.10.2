package dalapo.autoutils.tileentity;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import dalapo.autoutils.helper.MiscHelper;
import dalapo.autoutils.helper.StateList;
import dalapo.autoutils.logging.Logger;
import dalapo.autoutils.reference.ItemIDList;
import dalapo.autoutils.reference.UsefulLists;
import dalapo.autoutils.registry.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class TileEntitySequencePlacer extends TileEntityBasicInventory implements ActionOnRedstone
{
	private int nextSlot;
	private boolean isPowered = false;
	public static final String id = "SequencePlacer";
	
	public TileEntitySequencePlacer() {
		super(18, "sequenceplacer");
		Logger.info("Created TileEntitySequencePlacer");
		nextSlot = 0;
	}
	
	private void placeNextBlock()
	{
		EnumFacing front = worldObj.getBlockState(pos).getValue(StateList.DIRECTIONS);
		if (!worldObj.isAirBlock(MiscHelper.getAdjacent(getPos(), front))) return;
		ItemStack itemstack = getStackInSlot(nextSlot);
		boolean flag = false;
		for (int i=0; i<18; i++)
		{
			itemstack = getStackInSlot(nextSlot);
			if (itemstack != null && itemstack.getItem() instanceof ItemBlock)
			{
				flag = true;
				decrStackSize(nextSlot, 1);
			}
			nextSlot++;
			if (nextSlot > 17) nextSlot = 0;
			if (flag) break;
		}
		if (!flag) return;
		{
			System.out.println(itemstack);
			Block block = ((ItemBlock)itemstack.getItem()).getBlock();
			worldObj.setBlockState(MiscHelper.getAdjacent(getPos(), front), block.getStateForPlacement(worldObj, MiscHelper.getAdjacent(getPos(), front), front, 0, 0, 0, itemstack.getItemDamage(), FakePlayerFactory.get((WorldServer)worldObj, new GameProfile(UUID.randomUUID(), "AutoUtilsFake")), itemstack));
//			worldObj.setBlock(MiscHelper.getAdjacent(getPos(), front), ((ItemBlock)itemstack.getItem()).getBlock());
//			worldObj.setBlockMetadataWithNotify(MiscHelper.getAdjacent(getPos(), front), itemstack.getItemDamage(), 3);
		}
	}

	@Override
	public void performAction() {
		if (worldObj.isBlockPowered(getPos()))
		{
			if (!isPowered)
			{
				placeNextBlock();
				isPowered = true;
			}
		}
		else isPowered = false;
		
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString("Block Order");
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return itemstack.getItem() instanceof ItemBlock;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("nextSlot", nextSlot);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		nextSlot = nbt.getInteger("nextSlot");
	}

	@Override
	public String getName() {
		return "Sequence placer";
	}
}