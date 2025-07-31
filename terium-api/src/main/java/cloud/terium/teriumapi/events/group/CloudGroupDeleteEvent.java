package cloud.terium.teriumapi.events.group;

import cloud.terium.common.event.Event;
import cloud.terium.common.services.groups.ICloudServiceGroup;
import lombok.Getter;

@Getter
public class CloudGroupDeleteEvent extends Event {

    private final ICloudServiceGroup cloudServiceGroup;

    public CloudGroupDeleteEvent(ICloudServiceGroup cloudServiceGroup) {
        this.cloudServiceGroup = cloudServiceGroup;
    }
}
