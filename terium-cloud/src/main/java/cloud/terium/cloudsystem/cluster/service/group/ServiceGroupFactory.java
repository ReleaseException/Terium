package cloud.terium.cloudsystem.cluster.service.group;

import cloud.terium.cloudsystem.cluster.ClusterStartup;
import cloud.terium.common.services.groups.ICloudServiceGroup;
import cloud.terium.common.services.groups.ICloudServiceGroupFactory;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class ServiceGroupFactory implements ICloudServiceGroupFactory {

    @SneakyThrows
    @Override
    public void deleteServiceGroup(ICloudServiceGroup iCloudServiceGroup) {
        // stop all services
        ClusterStartup.getCluster().getServiceGroupProvider().getAllServiceGroups().remove(iCloudServiceGroup);
        FileUtils.delete(new File("groups//" + iCloudServiceGroup.getGroupName() + ".json"));
    }
}