package cloud.terium.teriumapi.service;

import java.util.List;
import java.util.Optional;

public interface ICloudServiceProvider {

    /**
     * Get a ICloudService by serviceName
     *
     * @param serviceName The service name of the service you want.
     * @return ICloudService The cloud service you wanted by name.
     */
    Optional<ICloudService> getServiceByName(String serviceName);

    /**
     * Get a List of all ICloudServices by serviceGroup
     *
     * @param serviceGroup The service group of the services you want.
     * @return List<ICloudService> This returns a list of all cloud services from the group.
     */
    List<ICloudService> getServicesByGroupName(String serviceGroup);

    /**
     * Get a List of all ICloudServices by group title
     *
     * @param groupTitle The service group of the services you want.
     * @return List<ICloudService> This returns a list of all cloud services from the group title.
     */
    List<ICloudService> getServicesByGroupTitle(String groupTitle);

    /**
     * Get all ICloudService of type lobby they are connected with the cloud.
     *
     * @return List<ICloudService> This returns a list of all lobby cloud services there are connected with the cloud.
     */
    List<ICloudService> getAllLobbyServices();

    /**
     * Get all ICloudService they are connected with the cloud.
     *
     * @return List<ICloudService> This returns a list of all cloud services there are connected with the cloud.
     */
    List<ICloudService> getAllServices();
}
