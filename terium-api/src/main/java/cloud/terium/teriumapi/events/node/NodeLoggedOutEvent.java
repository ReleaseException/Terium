package cloud.terium.teriumapi.events.node;

import cloud.terium.common.event.Event;
import cloud.terium.common.node.INode;
import lombok.Getter;

@Getter
public class NodeLoggedOutEvent extends Event {

    private final INode node;

    public NodeLoggedOutEvent(INode node) {
        this.node = node;
    }
}