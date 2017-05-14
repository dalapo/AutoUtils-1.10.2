package dalapo.autoutils.packet;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import dalapo.autoutils.helper.ChatHelper;
import dalapo.autoutils.logging.Logger;
import dalapo.autoutils.tileentity.TileEntityItemRedis;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PacketItemRedis extends AutoUtilsPacket
{
	BlockPos pos;
	int[] vals = new int[5];
	int side;
	int change;
	boolean toggle;
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		for (int i=0; i<5; i++)
		{
			vals[i] = buf.readInt();
		}
		side = buf.readInt();
		change = buf.readInt();
		toggle = buf.readBoolean();
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		for (int i : vals)
		{
			buf.writeInt(i);
		}
		buf.writeInt(side);
		buf.writeInt(change);
		buf.writeBoolean(toggle);
	}
	
	public PacketItemRedis(TileEntityItemRedis te, int s, int c, boolean toggle)
	{
		handler = new Handler();
		pos = te.getPos();
		side = s;
		change = c;
		this.toggle = toggle;
	}
	
	public PacketItemRedis()
	{
		// Apparently this is a thing that you need
	}
	
	@Override
	protected void actuallyDoHandle(AutoUtilsPacket message, World world, EntityPlayer ep, boolean isToClient)
	{
		if (!isToClient)
		{
			TileEntityItemRedis te = (TileEntityItemRedis)world.getTileEntity(((PacketItemRedis)message).pos);
			if (!(te instanceof TileEntityItemRedis)) throw new RuntimeException("Minecraft updates broke the server!");
			((TileEntityItemRedis)te).changeRatio(((PacketItemRedis)message).side, ((PacketItemRedis)message).change);
			if (((PacketItemRedis)message).toggle) te.toggleSplit(); // Server-side
			te.markDirty();
			for (int i=0; i<5; i++)
			{
				vals[i] = te.getRatio(i);
			}
			((PacketItemRedis)message).toggle = te.shouldSplit();
			PacketHandler.sendToPlayer(message, (EntityPlayerMP)ep);
		}
		else
		{
			TileEntityItemRedis te = (TileEntityItemRedis)world.getTileEntity(((PacketItemRedis)message).pos);
			if (!(te instanceof TileEntityItemRedis)) throw new RuntimeException("Minecraft updates broke the client!");
			for (int i=0; i<5; i++)
			{
				te.setRatio(i, vals[i]);

				Logger.info(vals[i]);
			}
			if (((PacketItemRedis)message).change == 0) ChatHelper.sendMessage(toggle ? "Now splitting stacks" : "Now keeping stacks together");
			te.markDirty();
		}
	}
	
	public int getID()
	{
		return 1;
	}
}