package cloud.terium.common.networking.packet.service;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.Packet;
import cloud.terium.common.services.ICloudService;
import cloud.terium.common.services.ServiceState;

import java.util.HashMap;
import java.util.Optional;

public record PacketPlayOutUpdateService(String serviceName, int players, double memory, ServiceState serviceState,
                                         boolean locked, HashMap<String, Object> propertyCache) implements Packet {

    public Optional<ICloudService> parsedCloudService() {
        return TeriumCommon.getTeriumFramework().getProvider().getServiceProvider().getServiceByName(serviceName);
    }
}