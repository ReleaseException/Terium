package cloud.terium.cloudsystem.common.event.events.service.template;

import cloud.terium.cloudsystem.cluster.ClusterStartup;
import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.template.PacketPlayOutTemplateDelete;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.event.Event;
import lombok.Getter;

@Getter
public class TemplateDeleteEvent extends Event {

    private final String template;

    public TemplateDeleteEvent(String template) {
        this.template = template;
        if (ClusterStartup.getCluster() != null)
            TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutTemplateDelete(template));
    }
}