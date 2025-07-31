package cloud.terium.plugin.impl.template;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.template.PacketPlayOutTemplateCreate;
import cloud.terium.common.networking.packet.template.PacketPlayOutTemplateDelete;
import cloud.terium.common.templates.ITemplate;
import cloud.terium.common.templates.ITemplateFactory;
import cloud.terium.teriumapi.template.impl.Template;

import java.nio.file.Path;

public class TemplateFactory implements ITemplateFactory {

    @Override
    public ITemplate createTemplate(String name) {
        ITemplate template = new Template(name, Path.of("templates\\" + name));
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutTemplateCreate(name));
        return template;
    }

    @Override
    public void deleteTemplate(String name) {
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutTemplateDelete(name));
    }
}
