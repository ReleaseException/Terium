package cloud.terium.networking.client;

import cloud.terium.networking.handler.ChannelHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.Getter;

@Getter
public class TeriumClient implements AutoCloseable {

    private final Bootstrap bootstrap;
    private final Channel channel;
    private final ChannelFuture channelFuture;
    private final EventLoopGroup eventLoopGroup;

    public TeriumClient(String host, int port) {
        this.eventLoopGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();

        try {
            this.bootstrap = new Bootstrap()
                    .group(eventLoopGroup)
                    .channel(Epoll.isAvailable() ? EpollSocketChannel.class : NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                    .option(ChannelOption.TCP_FASTOPEN_CONNECT, Epoll.isAvailable())
                    .handler(new ChannelHandler());
            this.channelFuture = this.bootstrap.connect(host, port).sync();
            this.channel = this.channelFuture.channel();
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to start terium-client", exception);
        }
    }

    @Override
    public void close() throws Exception {
        if (channel != null && channel.isActive()) {
            channel.close().sync();
        }
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
    }

}