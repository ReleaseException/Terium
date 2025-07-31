package cloud.terium.common;

import lombok.Getter;

public abstract class TeriumCommon {

    @Getter
    protected static TeriumCommon teriumFramework;

    protected TeriumCommon() {
        teriumFramework = this;
    }

    public abstract ICloudProvider getProvider();

    public abstract ICloudFactory getFactory();

}
