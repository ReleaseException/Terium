package cloud.terium.common.networking.packet.node;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.Packet;
import cloud.terium.common.node.INode;

import java.net.InetSocketAddress;
import java.util.Optional;

public record PacketPlayOutNodeStarted(String node, InetSocketAddress address, long maxMemory, String masterKey) implements Packet {

    public Optional<INode> parsedNode() {
        return TeriumCommon.getTeriumFramework().getProvider().getNodeProvider().getNodeByName(node);
    }
}