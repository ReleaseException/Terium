package cloud.terium.common.networking.packet.group;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.Packet;
import cloud.terium.common.node.INode;
import cloud.terium.common.templates.ITemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record PacketPlayOutCreateServerGroup(String name, String groupTitle, String node,
                                             List<String> templates,
                                             String version, boolean maintenance, boolean isStatic, int maximumPlayers,
                                             int memory, int minimalServices, int maximalServices) implements Packet {

    public Optional<INode> parsedNode() {
        return TeriumCommon.getTeriumFramework().getProvider().getNodeProvider().getNodeByName(node);
    }

    public List<ITemplate> parsedTemplates() {
        List<ITemplate> templateList = new ArrayList<>();
        templates.forEach(template -> {
            templateList.add(TeriumCommon.getTeriumFramework().getProvider().getTemplateProvider().getTemplateByName(template).orElseGet(null));
        });
        return templateList;
    }
}