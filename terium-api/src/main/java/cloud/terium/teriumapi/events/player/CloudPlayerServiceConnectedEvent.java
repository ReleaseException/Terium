package cloud.terium.teriumapi.events.player;

import cloud.terium.common.event.Event;
import cloud.terium.common.player.ICloudPlayer;
import cloud.terium.common.services.ICloudService;
import lombok.Getter;

@Getter
public class CloudPlayerServiceConnectedEvent extends Event {

    private final ICloudPlayer cloudPlayer;
    private final ICloudService cloudService;

    public CloudPlayerServiceConnectedEvent(ICloudPlayer cloudPlayer, ICloudService cloudService) {
        this.cloudPlayer = cloudPlayer;
        this.cloudService = cloudService;
    }
}
