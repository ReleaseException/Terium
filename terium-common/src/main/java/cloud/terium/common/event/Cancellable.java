package cloud.terium.common.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Cancellable extends Event {


    /**
     * Indicates whether the current event has been cancelled.
     * If set to true, further processing of the event may be stopped
     * depending on the event handling logic.
     */
    private boolean isCancelled = false;

}