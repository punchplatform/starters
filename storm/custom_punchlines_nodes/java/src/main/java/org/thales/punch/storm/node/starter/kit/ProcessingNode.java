package org.thales.punch.storm.node.starter.kit;

import java.util.Collections;
import org.apache.storm.tuple.Tuple;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.punch.api.node.PunchNode;
import com.github.punch.api.node.StreamDeclaration;
import com.github.punch.api.storm.PunchProcessingNode;

/**
 * 
 * An example of Storm processing node
 * 
 * @author Punch Team
 *
 */
@PunchNode(name = "processing_node", version = "1.0.0")
public class ProcessingNode extends PunchProcessingNode {

  private static final long serialVersionUID = 1L;

  /**
    Enable or disable processing.
    default: false
   */
  @JsonProperty(value = "can_process")
  public Boolean canProcess = false;
  
  @Override
  public void process(Tuple input) {
    for (StreamDeclaration stream: getPublishStreams()) {
      if (canProcess.booleanValue()) {
        getCollector().emit(stream.getStreamId(), Collections.singleton(input), input.getValues());
      } 
    }
    getCollector().ack(input);
  }

}
