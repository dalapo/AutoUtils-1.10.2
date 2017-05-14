package dalapo.autoutils.packet;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AbstractMessageHandler<T extends IMessage> implements IMessageHandler<T, IMessage> {

	@Override
	public IMessage onMessage(T message, MessageContext ctx) {
		return null;
	}
}