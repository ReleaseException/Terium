package cloud.terium.cloudsystem.common.event.events.service.template;

import cloud.terium.cloudsystem.cluster.ClusterStartup;
import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.template.PacketPlayOutTemplateCreate;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.event.Event;
import lombok.Getter;

@Getter
public class TemplateCreateEvent extends Event {

    private final String name;

    public TemplateCreateEvent(String name) {
        this.name = name;
        if (ClusterStartup.getCluster() != null)
            TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutTemplateCreate(name));
    }
}