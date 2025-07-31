package cloud.terium.common.networking.packet.template;

import cloud.terium.common.networking.Packet;

import java.nio.file.Path;

public record PacketPlayOutTemplateCreate(String name) implements Packet {

    public Path path() {
        return Path.of("templates\\" + name);
    }
}