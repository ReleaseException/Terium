package cloud.terium.common.networking.packet.player;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.Packet;
import cloud.terium.common.services.ICloudService;

import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.UUID;

public record PacketPlayOutCloudPlayerAdd(String username, UUID uniquedId, InetSocketAddress address, String value,
                                          String signature, String cloudService) implements Packet {

    public Optional<ICloudService> parsedCloudService() {
        return TeriumCommon.getTeriumFramework().getProvider().getServiceProvider().getServiceByName(cloudService);
    }
}