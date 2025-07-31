package cloud.terium.networking.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ChannelHandler extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline()
                .addLast("logging", new LoggingHandler(LogLevel.DEBUG))
                .addLast("packet-decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(getClass().getClassLoader())))
                .addLast("packet-encoder", new ObjectEncoder());

    }

}
