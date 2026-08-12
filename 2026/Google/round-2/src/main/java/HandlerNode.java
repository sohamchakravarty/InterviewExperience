import java.util.HashMap;
import java.util.Map;

public class HandlerNode {
    String nodeValue;
    String nodeParameter;
    Map<String, HandlerNode> childNodes;

    public HandlerNode(String nodeValue) {
        this.nodeValue = nodeValue;
        this.nodeParameter = null;
        this.childNodes = new HashMap<>();
    }

    public void addChildHandler(String handlerValue) {
        if(handlerValue.startsWith("{")) {
            this.nodeParameter = handlerValue;
        } else {
            this.childNodes.put(handlerValue, new HandlerNode(handlerValue));
        }
    }

    public HandlerNode getChildHandler(String handlerValue) {
        
    }
}
