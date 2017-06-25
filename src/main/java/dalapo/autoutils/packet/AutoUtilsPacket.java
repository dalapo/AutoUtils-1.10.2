package dalapo.autoutils.packet;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import dalapo.autoutils.logging.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public abstract class AutoUtilsPacket implements IMessage {
	public abstract int getID();
	protected World world;
	protected EntityPlayer player;
	
	protected Handler handler = new Handler();
	
	@SideOnly(Side.CLIENT)
	private final void doHandle(NetHandlerPlayClient net)
	{
		Logger.info("Entered client-side doHandle in AutoUtilsPacket");
		actuallyDoHandle(this, (WorldClient)Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().thePlayer, true);
	}
	
	private final void doHandle(NetHandlerPlayServer net)
	{
		Logger.info("Entered server-side doHandle in AutoUtilsPacket");
		actuallyDoHandle(this, net.playerEntity.worldObj, net.playerEntity, false);
	}
	
	protected abstract void actuallyDoHandle(AutoUtilsPacket message, World world, EntityPlayer ep, boolean isClient);
	
	public static class Handler implements IMessageHandler<AutoUtilsPacket, IMessage>
	{
		@Override
		public IMessage onMessage(final AutoUtilsPacket message, final MessageContext ctx) {
			// Magic!
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(new Runnable() {
				@Override
				public void run() {
					if (ctx.side.equals(Side.CLIENT)) message.doHandle(ctx.getClientHandler());
					else message.doHandle(ctx.getServerHandler());
				}
			});
			return null;
		}
	}
}
