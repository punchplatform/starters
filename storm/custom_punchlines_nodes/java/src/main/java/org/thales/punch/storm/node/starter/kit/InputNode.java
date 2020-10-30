package org.thales.punch.storm.node.starter.kit;

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
  @JsonProperty(value = "user_name", required = true)
  public String username;

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
      emitDataTuple(message); // emit only on user defined streams
  }

  @Override
  public void onClose() {
      super.onClose();
      // do something before the node exit
  }

}
