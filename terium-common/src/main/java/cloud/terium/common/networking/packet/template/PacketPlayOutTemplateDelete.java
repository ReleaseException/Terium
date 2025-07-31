package cloud.terium.common.networking.packet.template;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.Packet;
import cloud.terium.common.templates.ITemplate;

import java.util.Optional;

public record PacketPlayOutTemplateDelete(String template) implements Packet {

    public Optional<ITemplate> parsedTemplate() {
        return TeriumCommon.getTeriumFramework().getProvider().getTemplateProvider().getTemplateByName(template);
    }
}