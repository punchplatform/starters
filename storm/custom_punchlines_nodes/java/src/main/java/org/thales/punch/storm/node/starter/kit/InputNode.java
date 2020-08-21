package org.thales.punch.storm.node.starter.kit;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.punch.api.node.PunchNode;
import com.github.punch.api.storm.PunchInputNode;

/**
 * An example of a custom storm Input Node
 * 
 * @author Punch Team
 *
 */
@PunchNode(name = "input_node", version = "1.0.0")
public class InputNode extends PunchInputNode {
  
  private static final long serialVersionUID = 1L;

  /**
  Specific name to display.
  */
  @JsonProperty(required = true)
  String username;

  String message;

  @Override
  public void onOpen() {
      super.onOpen();
      message = "Hello "+username;
      getLogger().info("open node with message={}", message);  
  }

  @Override
  public void nextTuple() {
      super.nextTuple();
      getPublishStreams().forEach(stream -> 
          getCollector().emit(stream.getStreamId(), Arrays.asList(message)));
  }

  @Override
  public void onClose() {
      super.onClose();
  }

}
