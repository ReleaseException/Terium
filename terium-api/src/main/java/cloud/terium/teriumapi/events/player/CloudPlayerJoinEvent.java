package cloud.terium.teriumapi.events.player;

import cloud.terium.common.event.Event;
import cloud.terium.common.player.ICloudPlayer;
import lombok.Getter;

@Getter
public class CloudPlayerJoinEvent extends Event {

    private final ICloudPlayer cloudPlayer;

    public CloudPlayerJoinEvent(ICloudPlayer cloudPlayer) {
        this.cloudPlayer = cloudPlayer;
    }
}