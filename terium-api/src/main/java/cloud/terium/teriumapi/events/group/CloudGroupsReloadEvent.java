package cloud.terium.teriumapi.events.group;

import cloud.terium.common.event.Event;
import cloud.terium.common.services.groups.ICloudServiceGroup;
import lombok.Getter;

import java.util.List;

@Getter
public class CloudGroupsReloadEvent extends Event {

    private final List<ICloudServiceGroup> serviceGroups;

    public CloudGroupsReloadEvent(List<ICloudServiceGroup> serviceGroups) {
        this.serviceGroups = serviceGroups;
    }
}