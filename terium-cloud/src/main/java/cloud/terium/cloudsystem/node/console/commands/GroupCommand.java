package cloud.terium.cloudsystem.node.console.commands;

import cloud.terium.cloudsystem.node.utils.Logger;
import cloud.terium.cloudsystem.node.NodeStartup;
import cloud.terium.teriumapi.console.LogType;
import cloud.terium.teriumapi.console.command.Command;
import cloud.terium.teriumapi.node.INode;
import cloud.terium.teriumapi.service.ServiceType;
import cloud.terium.teriumapi.service.group.ICloudServiceGroup;
import cloud.terium.teriumapi.template.ITemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GroupCommand extends Command {

    public GroupCommand() {
        super("group", "Manage service groups.");
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("create")) {
                if (args.length > 1) {
                    if (args[1].equalsIgnoreCase("lobby") || args[1].equalsIgnoreCase("server")) {
                        if (args.length == 6) {
                            switch (args[1]) {
                                case "lobby" ->
                                        NodeStartup.getNode().getServiceGroupProvider().registerServiceGroup(NodeStartup.getNode().getServiceGroupFactory().createLobbyGroup(args[2], "Default lobby group", NodeStartup.getNode().getThisNode(), List.of(), List.of(NodeStartup.getNode().getTemplateFactory().createTemplate(args[2])), args[3], false, Boolean.parseBoolean(args[4]), 20, Integer.parseInt(args[5]), 1, 1));
                                case "server" ->
                                        NodeStartup.getNode().getServiceGroupProvider().registerServiceGroup(NodeStartup.getNode().getServiceGroupFactory().createServerGroup(args[2], "Default server group", NodeStartup.getNode().getThisNode(), List.of(), List.of(NodeStartup.getNode().getTemplateFactory().createTemplate(args[2])), args[3], false, Boolean.parseBoolean(args[4]), 20, Integer.parseInt(args[5]), 1, 1));
                            }
                        } else Logger.log("group create lobby/server [name] [version] [static] [memory]", LogType.INFO);
                    } else {
                        if (args.length == 7) {
                            NodeStartup.getNode().getServiceGroupProvider().registerServiceGroup(NodeStartup.getNode().getServiceGroupFactory().createProxyGroup(args[2], "Default lobby group", NodeStartup.getNode().getThisNode(), List.of(), List.of(NodeStartup.getNode().getTemplateFactory().createTemplate(args[2])), args[3], true, Boolean.parseBoolean(args[4]), Integer.parseInt(args[6]), 20, Integer.parseInt(args[5]), 1, 1));
                        } else Logger.log("group create proxy [name] [version] [static] [memory] [port]", LogType.INFO);
                    }
                }
                return;
            }

            if (args[0].equalsIgnoreCase("delete")) {
                if (args.length > 1) {
                    NodeStartup.getNode().getServiceGroupProvider().getServiceGroupByName(args[1]).ifPresentOrElse(cloudServiceGroup -> {
                        NodeStartup.getNode().getServiceGroupFactory().deleteServiceGroup(cloudServiceGroup);
                        NodeStartup.getNode().getTemplateFactory().deleteTemplate(args[1]);
                        Logger.log("Successfully deleted service group '" + args[1] + "'.", LogType.INFO);
                    }, () -> Logger.log("A service group with that name isn't registered.", LogType.ERROR));
                } else Logger.log("group delete [name]", LogType.INFO);
                return;
            }

            if (args[0].equalsIgnoreCase("info")) {
                if (args.length > 1) {
                    if (args.length == 3)
                        NodeStartup.getNode().getServiceGroupProvider().getServiceGroupByName(args[1]).ifPresentOrElse(cloudServiceGroup -> Logger.log(cloudServiceGroup.getInformationsFromJson(), LogType.INFO), () -> Logger.log("A service group with that name isn't registered.", LogType.ERROR));
                    else
                        NodeStartup.getNode().getServiceGroupProvider().getServiceGroupByName(args[1]).ifPresentOrElse(cloudServiceGroup -> Logger.log(cloudServiceGroup.getInformations(), LogType.INFO), () -> Logger.log("A service group with that name isn't registered.", LogType.ERROR));
                } else Logger.log("group info [name]", LogType.INFO);
                return;
            }

            if (args[0].equalsIgnoreCase("update")) {
                if (args.length > 1) {
                    if (args.length == 4) {
                        Optional<ICloudServiceGroup> cloudServiceGroup = NodeStartup.getNode().getServiceGroupProvider().getServiceGroupByName(args[1]);
                        cloudServiceGroup.ifPresentOrElse(serviceGroup -> {
                            switch (args[2]) {
                                case "maintenance" -> {
                                    if (args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("false"))
                                        serviceGroup.setMaintenance(Boolean.parseBoolean(args[3]));
                                }
                                case "static" -> {
                                    if (args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("false"))
                                        serviceGroup.setStatic(Boolean.parseBoolean(args[3]));
                                }
                                case "version" -> {
                                    if (!serviceGroup.getServiceType().equals(ServiceType.Proxy)) {
                                        if (Arrays.asList("paper-1.19.3", "paper-1.19.2", "paper-1.18.2", "paper-1.17.1", "paper-1.16.5", "paper-1.15.2", "paper-1.14.4", "paper-1.13.2", "paper-1.12.2", "minestom").contains(args[3]))
                                            serviceGroup.setVersion(args[3]);
                                    } else {
                                        if (Arrays.asList("bungeecord", "waterfall", "velocity", "velocity-3").contains(args[3]))
                                            serviceGroup.setVersion(args[3]);
                                    }
                                }
                                case "memory" -> {
                                    try {
                                        serviceGroup.setMemory(Integer.parseInt(args[3]));
                                    } catch (Exception exception) {
                                        Logger.log("Please type a int as value.", LogType.ERROR);
                                    }
                                }
                                case "maxplayers" -> {
                                    try {
                                        serviceGroup.setMaxPlayer(Integer.parseInt(args[3]));
                                    } catch (Exception exception) {
                                        Logger.log("Please type a int as value.", LogType.ERROR);
                                    }
                                }
                                case "minservices" -> {
                                    try {
                                        serviceGroup.setMinServices(Integer.parseInt(args[3]));
                                    } catch (Exception exception) {
                                        Logger.log("Please type a int as value.", LogType.ERROR);
                                    }
                                }
                                case "maxservices" -> {
                                    try {
                                        serviceGroup.setMaxServices(Integer.parseInt(args[3]));
                                    } catch (Exception exception) {
                                        Logger.log("Please type a int as value.", LogType.ERROR);
                                    }
                                }
                            }
                            NodeStartup.getNode().getServiceGroupProvider().updateServiceGroup(serviceGroup);
                            Logger.log("Successfully updated service group '" + args[1] + "'.", LogType.INFO);
                        }, () -> Logger.log("A service group with that name isn't registered.", LogType.ERROR));

                    }
                    return;
                }
                Logger.log("group update [name] [maintenance/static/version/memory/maxplayers/minservices/maxservices] [value] (--shutdown) | update a service group", LogType.INFO);
                return;
            }

            if (args[0].equalsIgnoreCase("add")) {
                if (args.length > 1) {
                    if (args.length == 4) {
                        Optional<ICloudServiceGroup> cloudServiceGroup = NodeStartup.getNode().getServiceGroupProvider().getServiceGroupByName(args[1]);
                        cloudServiceGroup.ifPresent(serviceGroup -> {
                            try {
                                if (args[2].equalsIgnoreCase("fallback-node"))
                                    serviceGroup.addFallbackNode(NodeStartup.getNode().getNodeProvider().getNodeByName(args[3]).orElseGet(null));
                                else if (args[2].equalsIgnoreCase("template"))
                                    serviceGroup.addTemplate(NodeStartup.getNode().getTemplateProvider().getTemplateByName(args[3]).orElseGet(null));
                                NodeStartup.getNode().getServiceGroupProvider().updateServiceGroup(serviceGroup);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                if (exception.getMessage() == null) {
                                    if (args[2].equalsIgnoreCase("fallback-node"))
                                        Logger.log("A node with that name isn't registered.", LogType.ERROR);
                                    else if (args[2].equalsIgnoreCase("template"))
                                        Logger.log("A template with that name isn't registered.", LogType.ERROR);
                                } else Logger.log("A service group with that name isn't registered.", LogType.ERROR);
                            }
                        });
                    }
                }
            }

            if (args[0].equalsIgnoreCase("remove")) {
                if (args.length > 1) {
                    if (args.length == 4) {
                        Optional<ICloudServiceGroup> cloudServiceGroup = NodeStartup.getNode().getServiceGroupProvider().getServiceGroupByName(args[1]);
                        cloudServiceGroup.ifPresent(serviceGroup -> {
                            try {
                                if (args[2].equalsIgnoreCase("fallback-node"))
                                    serviceGroup.removeFallbackNode(NodeStartup.getNode().getNodeProvider().getNodeByName(args[3]).orElseGet(null));
                                else if (args[2].equalsIgnoreCase("template"))
                                    serviceGroup.removeTemplate(NodeStartup.getNode().getTemplateProvider().getTemplateByName(args[3]).orElseGet(null));
                                NodeStartup.getNode().getServiceGroupProvider().updateServiceGroup(serviceGroup);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                if (exception.getMessage() == null) {
                                    if (args[2].equalsIgnoreCase("fallback-node"))
                                        Logger.log("A node with that name isn't added to the service group.", LogType.ERROR);
                                    else if (args[2].equalsIgnoreCase("template"))
                                        Logger.log("A template with that name isn't added to the service group.", LogType.ERROR);
                                } else Logger.log("A service group with that name isn't registered.", LogType.ERROR);
                            }
                        });
                    }
                }
            }

            if (args[0].equalsIgnoreCase("list")) {
                // TODO: Add online services count (after implement cloud services)
                if (NodeStartup.getNode().getServiceGroupProvider().getAllServiceGroups().size() > 0)
                    NodeStartup.getNode().getServiceGroupProvider().getAllServiceGroups().forEach(serviceGroup -> {
                        Logger.log("Name: " + serviceGroup.getGroupName() + "(" + serviceGroup.getServiceType().toString().toUpperCase() + ") - Online services: " + NodeStartup.getNode().getServiceGroupProvider().getOnlineServicesFromServiceGroup(serviceGroup.getGroupName()) + " - Templates: " + serviceGroup.getTemplates().stream().map(ITemplate::getName).toList(), LogType.INFO);
                    });
                else Logger.log("There are no loaded service groups.", LogType.ERROR);
            }

            return;
        }

        Logger.log("group create lobby/server [name] [version] [static] [memory] | create a lobby/server service group", LogType.INFO);
        Logger.log("group create proxy [name] [version] [static] [memory] [port] | create a proxy service group", LogType.INFO);
        Logger.log("group delete [name] | delete a service group", LogType.INFO);
        Logger.log("group info [name] (--json) | see all informations about a service group", LogType.INFO);
        Logger.log("group update [name] [maintenance/version/static/memory/maxplayers/minservices/maxservices] [value] (--shutdown) | update a service group", LogType.INFO);
        Logger.log("group add/remove [name] [fallback-node/template] [value] | add or remove fallback node or template to a service group", LogType.INFO);
        Logger.log("group list | a list of all loaded service groups with informations", LogType.INFO);
    }

    @Override
    public List<String> tabComplete(String[] args) {
        switch (args.length) {
            case 1 -> {
                return Arrays.asList("create", "delete", "add", "remove", "info", "update", "list");
            }
            case 2 -> {
                if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("update") || args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove"))
                    return NodeStartup.getNode().getServiceGroupProvider().getAllServiceGroups().stream().map(ICloudServiceGroup::getGroupName).toList();
                if (args[0].equalsIgnoreCase("create"))
                    return Arrays.asList("lobby", "server", "proxy");
            }
            case 3 -> {
                if (args[0].equalsIgnoreCase("update"))
                    return Arrays.asList("maintenance", "version", "static", "memory", "maxplayers", "minservices", "maxservices");
                if (args[0].equalsIgnoreCase("info"))
                    return List.of("--json");
                if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove"))
                    return Arrays.asList("fallback-node", "template");
            }
            case 4 -> {
                if (args[1].equalsIgnoreCase("lobby") || args[1].equalsIgnoreCase("server"))
                    return Arrays.asList("paper-1.19.3", "paper-1.19.2", "paper-1.18.2", "paper-1.17.1", "paper-1.16.5", "paper-1.15.2", "paper-1.14.4", "paper-1.13.2", "paper-1.12.2", "minestom");
                if (args[1].equalsIgnoreCase("proxy"))
                    return Arrays.asList("velocity-3", "velocity", "waterfall", "bungeecord");

                if (args[0].equalsIgnoreCase("update")) {
                    if (args[2].equalsIgnoreCase("maintenance") || args[2].equalsIgnoreCase("static"))
                        return Arrays.asList("true", "false");
                    else if (args[2].equalsIgnoreCase("version"))
                        return Arrays.asList("paper-1.19.3", "paper-1.19.2", "paper-1.18.2", "paper-1.17.1", "paper-1.16.5", "paper-1.15.2", "paper-1.14.4", "paper-1.13.2", "paper-1.12.2", "minestom");
                }

                if (args[0].equalsIgnoreCase("add")) {
                    if (args[2].equalsIgnoreCase("fallback-node"))
                        return NodeStartup.getNode().getNodeProvider().getAllNodes().stream().filter(node -> node != NodeStartup.getNode().getThisNode()).filter(node -> !NodeStartup.getNode().getServiceGroupProvider().getServiceGroupByName(args[1]).orElseGet(null).getGroupFallbackNode().contains(node)).map(INode::getName).toList();
                    if (args[2].equalsIgnoreCase("template"))
                        return NodeStartup.getNode().getTemplateProvider().getAllTemplates().stream().filter(template -> !NodeStartup.getNode().getServiceGroupProvider().getServiceGroupByName(args[1]).orElseGet(null).getTemplates().contains(template)).map(ITemplate::getName).toList();
                }

                if (args[0].equalsIgnoreCase("remove")) {
                    if (args[2].equalsIgnoreCase("fallback-node"))
                        return NodeStartup.getNode().getServiceGroupProvider().getServiceGroupByName(args[1]).orElseGet(null).getGroupFallbackNode().stream().map(INode::getName).toList();
                    if (args[2].equalsIgnoreCase("template"))
                        return NodeStartup.getNode().getServiceGroupProvider().getServiceGroupByName(args[1]).orElseGet(null).getTemplates().stream().map(ITemplate::getName).toList();
                }
            }
            case 5 -> {
                if (args[0].equalsIgnoreCase("update")) {
                    return List.of("--shutdown");
                }
            }
        }
        return super.tabComplete(args);
    }
}