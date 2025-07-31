package cloud.terium.teriumapi.events.service;

import cloud.terium.common.event.Event;
import cloud.terium.common.services.ICloudService;
import lombok.Getter;

@Getter
public class CloudServiceStartingEvent extends Event {

    private final ICloudService cloudService;

    public CloudServiceStartingEvent(ICloudService cloudService) {
        this.cloudService = cloudService;
    }
}