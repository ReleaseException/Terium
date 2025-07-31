package cloud.terium.plugin.impl.module;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.module.ILoadedModule;
import cloud.terium.common.module.IModuleProvider;
import cloud.terium.common.networking.packet.module.PacketPlayOutLoadModule;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ModuleProvider implements IModuleProvider {

    private final List<ILoadedModule> cachedLoadedModules;

    public ModuleProvider() {
        this.cachedLoadedModules = new LinkedList<>();
    }

    @Override
    public void loadModule(String path) {
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutLoadModule(path));
    }

    @Override
    public Optional<ILoadedModule> getModuleByName(String name) {
        return getAllModules().stream().filter(module -> module.getName().equals(name)).findAny();
    }

    @Override
    public List<ILoadedModule> getAllModules() {
        return cachedLoadedModules;
    }
}
