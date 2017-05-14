package dalapo.autoutils.tileentity;

import dalapo.autoutils.helper.MiscHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;

public abstract class TileEntityBasicInventory extends TileEntityAutoUtils implements IInventory {

	public ItemStack[] inventory;
	private String name;
//	protected static int stackLimit = 64;
	
	public TileEntityBasicInventory(int size, String name)
	{
		inventory = new ItemStack[size];
		this.name = name;
	}
	
	public int getSizeInventory()
	{
		return inventory.length;
	}
	@Override
	public ItemStack getStackInSlot(int slot) {
		if (!MiscHelper.isInRange(slot, 0, getSizeInventory())) return null;
		return this.inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int num) {
		if (!MiscHelper.isInRange(slot, 0, getSizeInventory()) || inventory[slot] == null) return null;
		
		ItemStack itemstack;
		if (inventory[slot].stackSize > num)
		{
			itemstack = new ItemStack(inventory[slot].getItem(), num);
			inventory[slot].stackSize -= num;
			markDirty();
			return itemstack;
		}
		// If we get to this point, we are trying to remove more items than in the slot
		itemstack = inventory[slot];
		inventory[slot] = null;
		markDirty();
		return itemstack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		if (!MiscHelper.isInRange(slot, 0, getSizeInventory())) return;
		if (itemstack == null || itemstack.stackSize == 0)
		{
			inventory[slot] = null;
			return;
		}
		if (itemstack.stackSize > getInventoryStackLimit()) itemstack.stackSize = getInventoryStackLimit();
		inventory[slot] = itemstack;
		markDirty();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer ep) {
		return worldObj.getTileEntity(getPos()) == this &&
				ep.getDistanceSq(getPos().getX()+0.5, getPos().getY()+0.5, getPos().getZ()+0.5) <= 64;
	}

	public abstract ITextComponent getDisplayName();

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		return true;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		for (int i=0; i<getSizeInventory(); i++)
		{
			NBTTagCompound stackTag = new NBTTagCompound();
			stackTag.setByte("Slot", (byte)i);
			ItemStack is = getStackInSlot(i);
			if (is != null) is.writeToNBT(stackTag);
			list.appendTag(stackTag);
		}
		nbt.setTag("Items", list);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("Items", 10);
		for (int i=0; i<list.tagCount(); i++)
		{
			NBTTagCompound tag = list.getCompoundTagAt(i);
			int slot = tag.getByte("Slot");
			ItemStack itemstack = ItemStack.loadItemStackFromNBT(tag);
			inventory[slot] = itemstack;
		}
	}
	
	@Override
	public void clear()
	{
		for (int i=0; i<getSizeInventory(); i++)
		{
			inventory[i] = null;
		}
	}

	@Override
	public abstract String getName();

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (!MiscHelper.isInRange(index, 0, getSizeInventory()-1)) return null;
		ItemStack is = getStackInSlot(index);
		inventory[index] = null;
		return is;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}
}
