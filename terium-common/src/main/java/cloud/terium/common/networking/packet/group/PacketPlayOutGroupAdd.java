package cloud.terium.common.networking.packet.group;


import cloud.terium.common.networking.Packet;
import cloud.terium.common.services.ServiceType;

import java.util.List;

public record PacketPlayOutGroupAdd(
        String name,
        String groupTitle,
        String node,
        List<String> templates,
        ServiceType serviceType,
        String version,
        boolean maintenance,
        boolean isStatic,
        boolean hasPort,
        int port,
        int maximumPlayers,
        int memory,
        int minimalServices,
        int maximalServices
) implements Packet {}
