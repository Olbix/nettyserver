package pl.wlodarski.nio.components;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import pl.wlodarski.nio.EyetrackerMsg;

import java.util.Map;

@Slf4j
public class EyetrackerServerHandler extends SimpleChannelInboundHandler<EyetrackerMsg> {

    private static final ChannelGroup CHANNELS = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private final ClientRegistry clientRegistry = ClientRegistry.getInstance();

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        CHANNELS.add(ctx.channel());
        log.info("Client added " + ctx.channel().id().asLongText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EyetrackerMsg msg) {
        Map<String, EyetrackerMsg> map = clientRegistry.getAndUpdateData(msg, getChannelId(ctx));
        CHANNELS.forEach(c -> c.writeAndFlush(map));
    }

    private ChannelId getChannelId(ChannelHandlerContext ctx) {
        return ctx.channel().id();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        clientRegistry.deleteClientData(ctx.channel().id());
        CHANNELS.remove(ctx.channel());
    }
}
