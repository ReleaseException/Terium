package cloud.terium.teriumapi.events.group;

import cloud.terium.common.event.Event;
import cloud.terium.common.services.groups.ICloudServiceGroup;
import lombok.Getter;

@Getter
public class CloudGroupUpdatedEvent extends Event {

    private final ICloudServiceGroup cloudServiceGroup;

    public CloudGroupUpdatedEvent(ICloudServiceGroup cloudServiceGroup) {
        this.cloudServiceGroup = cloudServiceGroup;
    }
}
