package cloud.terium.teriumapi.service.group.impl;

import cloud.terium.networking.packet.group.PacketPlayOutGroupUpdate;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.teriumapi.node.INode;
import cloud.terium.teriumapi.service.ServiceType;
import cloud.terium.teriumapi.service.group.ICloudServiceGroup;
import cloud.terium.teriumapi.template.ITemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultProxyGroup implements ICloudServiceGroup {

    private final String name;
    private final String groupTitle;
    private final INode node;
    private final List<INode> fallbackNodes;
    private final List<ITemplate> templates;
    private final ServiceType cloudServiceType = ServiceType.Proxy;
    private String version;
    private final int port;
    private boolean maintenance;
    private boolean isStatic;
    private int maximumPlayers;
    private int memory;
    private int minimalServices;
    private int maximalServices;

    @SneakyThrows
    public DefaultProxyGroup(String name, String groupTitle, INode node, List<INode> fallbackNodes, List<ITemplate> templates, String version, boolean maintenance, boolean isStatic, int port, int maximumPlayers, int memory, int minimalServices, int maximalServices) {
        this.name = name;
        this.groupTitle = groupTitle;
        this.node = node;
        this.fallbackNodes = fallbackNodes;
        this.templates = templates;
        this.version = version;
        this.maintenance = maintenance;
        this.isStatic = isStatic;
        this.port = port;
        this.maximumPlayers = maximumPlayers;
        this.memory = memory;
        this.minimalServices = minimalServices;
        this.maximalServices = maximalServices;
    }

    public ICloudServiceGroup initFile() {
        final JsonObject json = new JsonObject();
        final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        final JsonArray templateArray = new JsonArray();
        final JsonArray fallbackNodesArray = new JsonArray();
        templates.forEach(template -> templateArray.add(template.getName()));
        fallbackNodes.forEach(node -> fallbackNodesArray.add(node.getName()));

        json.addProperty("group_name", name);
        json.addProperty("group_title", groupTitle);
        json.addProperty("node", node.getName());
        json.add("fallback_nodes", fallbackNodesArray);
        json.add("templates", templateArray);
        json.addProperty("servicetype", ServiceType.Proxy.name());
        json.addProperty("port", port);
        json.addProperty("version", version);
        json.addProperty("maintenance", maintenance);
        json.addProperty("static", isStatic);
        json.addProperty("maximum_players", maximumPlayers);
        json.addProperty("memory", memory);
        json.addProperty("minimal_services", minimalServices);
        json.addProperty("maximal_services", maximalServices);

        executorService.execute(() -> {
            try (final OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(new File("groups/" + name + ".json").toPath()), StandardCharsets.UTF_8)) {
                gson.toJson(json, writer);
            } catch (IOException ignored) {
            }
        });

        return this;
    }

    @Override
    public String getGroupName() {
        return name;
    }

    @Override
    public String getGroupTitle() {
        return groupTitle;
    }

    @Override
    public INode getGroupNode() {
        return node;
    }

    @Override
    public List<INode> getGroupFallbackNode() {
        return fallbackNodes;
    }

    @Override
    public List<ITemplate> getTemplates() {
        return templates;
    }

    @Override
    public ServiceType getServiceType() {
        return cloudServiceType;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean isMaintenance() {
        return maintenance;
    }

    @Override
    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    @Override
    public boolean isStatic() {
        return isStatic;
    }

    @Override
    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    @Override
    public boolean hasPort() {
        return true;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public int getMaxPlayers() {
        return maximumPlayers;
    }

    @Override
    public int getMemory() {
        return memory;
    }

    @Override
    public void setMemory(int memory) {
        this.memory = memory;
    }

    @Override
    public int getMinServices() {
        return minimalServices;
    }

    @Override
    public void setMinServices(int services) {
        this.minimalServices = services;
    }

    @Override
    public int getMaxServices() {
        return maximalServices;
    }

    @Override
    public void setMaxServices(int services) {
        this.maximalServices = services;
    }

    @Override
    public void setMaxPlayer(int players) {
        this.maximumPlayers = players;
    }

    @Override
    public void update() {
        TeriumAPI.getTeriumAPI().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutGroupUpdate(this));
    }
}