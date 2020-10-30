package org.thales.punch.storm.node.starter.kit;

import org.apache.storm.tuple.Tuple;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.punch.api.node.PunchNode;
import com.github.punch.api.storm.impl.PunchProcessingNode;

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
  public void prepare() {
    super.prepare();
    getLogger().info("message=\"Processing Node ready. Can process data tuples ? {}\"", canProcess);
  }
  
  @Override
  public void process(Tuple input) {
    // Metric Tuple
    if(isMetricTuple(input)){
      enrichAndForwardMetricTuple(input); // enrich and forward on _ppf_metrics.
      return;
    }

    // Error Tuple
    if(isErrorTuple(input)){
      forwardErrorTuple(input); // processing. Here there is no processing. Incoming tuple is forwarded on _ppf_errors.
      return;
    }

    // Data Tuple
    if(isDataTuple(input)){
      if(canProcess.booleanValue()){
        forwardDataTuple(input); // processing. Here there is no processing. Incoming tuple is forwarded on user defined streams.
      }
    }
  }

}
