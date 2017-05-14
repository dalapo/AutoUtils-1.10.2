package dalapo.autoutils.gui;

import net.minecraftforge.fml.common.network.IGuiHandler;
import dalapo.autoutils.reference.GUIIDList;
import dalapo.autoutils.tileentity.TileEntityBasicInventory;
import dalapo.autoutils.tileentity.TileEntityItemRedis;
import dalapo.autoutils.tileentity.TileEntityStackMover;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AutoUtilsGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		if (ID == GUIIDList.STACKMOVER) return new ContainerBasicInventory(3, 3, (TileEntityBasicInventory)world.getTileEntity(pos), player.inventory);
		if (ID == GUIIDList.ORDERPLACER) return new ContainerBasicInventory(2, 9, (TileEntityBasicInventory)world.getTileEntity(pos), player.inventory);
		if (ID == GUIIDList.DISTRIBUTOR) return null;
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		if (ID == GUIIDList.STACKMOVER) return new GUIBasicInventory(new ContainerBasicInventory(3, 3, (TileEntityBasicInventory)world.getTileEntity(pos), player.inventory), player.inventory, "stackfilter", (TileEntityBasicInventory)world.getTileEntity(pos));
		if (ID == GUIIDList.ORDERPLACER) return new GUIBasicInventory(new ContainerBasicInventory(2, 9, (TileEntityBasicInventory)world.getTileEntity(pos), player.inventory), player.inventory, "sequencedropper", (TileEntityBasicInventory)world.getTileEntity(pos));
		if (ID == GUIIDList.DISTRIBUTOR) return new GUIItemRedis("itemredis", (TileEntityItemRedis)world.getTileEntity(pos));
		return null;
	}
}