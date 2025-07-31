package cloud.terium.common;

import cloud.terium.common.command.ICommandFactory;
import cloud.terium.common.services.ICloudServiceFactory;
import cloud.terium.common.services.groups.ICloudServiceGroupFactory;
import cloud.terium.common.templates.ITemplateFactory;

public abstract class ICloudFactory {

    /*
     * Use this methode to get the ICloudServiceFactory
     * The ICloudServiceFactory is usefull to create or start services.
     */
    public abstract ICloudServiceFactory getServiceFactory();

    /*
     * Use this methode to get the ICloudServiceGroupFactory
     * The ICloudServiceFactory is usefull to create or delete service groups
     */
    public abstract ICloudServiceGroupFactory getServiceGroupFactory();

    /*
     * Use this methode to get the ITemplateFactory
     * The ITemplateFactory is usefull to create or delete templates
     */
    public abstract ITemplateFactory getTemplateFactory();

    /*
     * Use this methode to get the ICommandFactory
     * The ICommandFactory is usefull to register self-written commands.
     */
    public abstract ICommandFactory getCommandFactory();
}