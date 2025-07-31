package cloud.terium.common.networking.packet.node;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.Packet;
import cloud.terium.common.node.INode;

import java.util.Optional;

public record PacketPlayOutNodeUpdate(String node, long usedMemory, long maxMemory) implements Packet {

    public Optional<INode> parsedNode() {
        return TeriumCommon.getTeriumFramework().getProvider().getNodeProvider().getNodeByName(node);
    }
}