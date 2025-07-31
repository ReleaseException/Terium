package cloud.terium.common.networking.packet.service;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.Packet;
import cloud.terium.common.node.INode;
import cloud.terium.common.services.ICloudService;

import java.util.Optional;

public record PacketPlayOutSuccessfullyServiceStarted(String serviceName, String node) implements Packet {

    public Optional<ICloudService> parsedCloudService() {
        return TeriumCommon.getTeriumFramework().getProvider().getServiceProvider().getServiceByName(serviceName);
    }

    public Optional<INode> parsedNode() {
        return TeriumCommon.getTeriumFramework().getProvider().getNodeProvider().getNodeByName(node);
    }
}