package dalapo.autoutils.tileentity;

import dalapo.autoutils.helper.MiscHelper;
import dalapo.autoutils.helper.StateList;
import dalapo.autoutils.helper.TileEntityHelper;
import dalapo.autoutils.reference.UsefulLists;
import dalapo.autoutils.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class TileEntityStackMover extends TileEntityBasicInventory implements ISidedInventory, ActionOnRedstone {
//	ItemStack[] filter;
	private boolean isPowered = false;
	public static final String id = "StackMover";
	private int filterSlot = 0;
	public TileEntityStackMover()
	{
		super(9, "stackmover");
	}
	
	public void performAction() // Called when a block adjacent to the TE updates.
	{
		if (worldObj.isBlockPowered(pos))
		{
			if (!isPowered)
			{
				Logger.info("About to enter transferStack()");
				transferStack();
				isPowered = true;
			}
		}
		else isPowered = false;
	}
	
	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentString("Filter");
	}
	
	private boolean shouldFilter()
	{
		for (int i=0; i<9; i++)
		{
			if (getStackInSlot(i) != null) return true;
		}
		return false;
	}
	
	private int checkFilter(ItemStack is)
	{
		if (!shouldFilter()) return 9;
		for (int i=0; i<9; i++)
		{
			if (getStackInSlot(i) != null && getStackInSlot(i).isItemEqual(is)) return i;
		}
		return -1;
	}
	
	private int[] getSlot(IInventory te, IInventory dest, int side, boolean insert)
	{
		// pair[0] = filter slot, pair[1] = inv slot
		if (te == null) return null;
		for (int i=0; i<te.getSizeInventory(); i++)
		{
			if (!insert && te.getStackInSlot(i) == null) continue;
			int filterSlot = checkFilter(te.getStackInSlot(i));
			if (TileEntityHelper.isValidSlotForSide(te, side, i) && te.getStackInSlot(i) != null && filterSlot != -1)
			{
				ItemStack wouldMove = te.getStackInSlot(i);
				// 1.7.10 legacy code; screw EnumFacing; considering re-implementing ForgeDirection myself
				if (TileEntityHelper.hasSpaceForItem(dest, wouldMove, EnumFacing.getFront(side).getOpposite().ordinal())) return new int[] {filterSlot, i};
			}
		}
		return null;
	}
	
	public void transferStack()
	{
		EnumFacing front = worldObj.getBlockState(getPos()).getValue(StateList.DIRECTIONS);
		
		TileEntity pull = worldObj.getTileEntity(MiscHelper.getAdjacent(getPos(), front));
		TileEntity push = worldObj.getTileEntity(MiscHelper.getAdjacent(getPos(), front.getOpposite()));
		if (!(pull instanceof IInventory)) return; // Nowhere to pull from
		int[] pair = getSlot((IInventory)pull, (IInventory)push, front.getOpposite().ordinal(), false);
		if (pair == null) return; // Nothing to pull
		
		// Condition for reaching here: The TE has found an ItemStack to move
		// The slot of the ItemStack is stored in pair[1]
		int moveSize = pair[0] == 9 ? 64 : getStackInSlot(pair[0]).stackSize;
		ItemStack toMove = ((IInventory)pull).decrStackSize(pair[1], moveSize);
		
		if (!(push instanceof IInventory))
		{
			BlockPos pos = MiscHelper.getAdjacent(getPos(), front.getOpposite());
			EntityItem drop = new EntityItem(worldObj, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, toMove);
			drop.motionX = 0;
			drop.motionY = 0;
			drop.motionZ = 0;
			worldObj.spawnEntityInWorld(drop);
		}
		
//		if (push != null && push instanceof IInventory)
		else
		{
			ItemStack remaining = TileEntityHelper.tryInsertItem((IInventory)push, toMove, front.ordinal());
			if (((IInventory)pull).getStackInSlot(pair[1]) == null)
			{
				((IInventory)pull).setInventorySlotContents(pair[1], remaining);
			}
			else
			{
				ItemStack is = ((IInventory)pull).getStackInSlot(pair[1]);
				// Note: It is impossible for this statement to give an illegal (>64) stack size.
				if (remaining != null) is.stackSize += remaining.stackSize;
				((IInventory)pull).setInventorySlotContents(pair[1], is);
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		return true;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
	}

	// The Stack Mover's inventory (filter) does not interact with automation in any way
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return null;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}

	@Override
	public String getName() {
		return "Filter";
	}
}