package cloud.terium.common.networking.packet.group;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.Packet;
import cloud.terium.common.services.groups.ICloudServiceGroup;

import java.util.Optional;

public record PacketPlayOutGroupReload(String serviceGroup) implements Packet {

    public Optional<ICloudServiceGroup> parsedServiceGroup() {
        return TeriumCommon.getTeriumFramework().getProvider().getServiceGroupProvider().getServiceGroupByName(serviceGroup);
    }
}