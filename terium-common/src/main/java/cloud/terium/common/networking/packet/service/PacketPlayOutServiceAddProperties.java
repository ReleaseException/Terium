package cloud.terium.common.networking.packet.service;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.Packet;
import cloud.terium.common.services.ICloudService;

import java.util.HashMap;
import java.util.Optional;

public record PacketPlayOutServiceAddProperties(String serviceName, HashMap<String, Object> propertiesCache) implements Packet {

    public Optional<ICloudService> parsedCloudService() {
        return TeriumCommon.getTeriumFramework().getProvider().getServiceProvider().getServiceByName(serviceName);
    }
}