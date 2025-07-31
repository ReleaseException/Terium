package cloud.terium.common.networking.packet.module;

import cloud.terium.common.module.ModuleType;
import cloud.terium.common.networking.Packet;

public record PacketPlayOutAddLoadedModule(String name, String fileName, String author, String version, String description, String mainClass, boolean reloadable, ModuleType moduleType) implements Packet {
}
