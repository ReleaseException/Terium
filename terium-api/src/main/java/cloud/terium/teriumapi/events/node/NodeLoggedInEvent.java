package cloud.terium.teriumapi.events.node;

import cloud.terium.common.event.Event;
import cloud.terium.common.node.INode;
import lombok.Getter;

@Getter
public class NodeLoggedInEvent extends Event {

    private final INode node;

    public NodeLoggedInEvent(INode node) {
        this.node = node;
    }
}