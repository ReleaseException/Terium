package cloud.terium.teriumapi.events.group;

import cloud.terium.common.event.Event;
import cloud.terium.common.services.groups.ICloudServiceGroup;
import lombok.Getter;

@Getter
public class CloudGroupCreatedEvent extends Event {

    private final ICloudServiceGroup cloudServiceGroup;

    public CloudGroupCreatedEvent(ICloudServiceGroup cloudServiceGroup) {
        this.cloudServiceGroup = cloudServiceGroup;
    }
}
