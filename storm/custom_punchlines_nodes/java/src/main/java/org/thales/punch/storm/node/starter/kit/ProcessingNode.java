/*
 *  This code is licensed under the outer restricted Tiss license:
 *
 *  Copyright [2014]-[2020] Thales Services under the Thales Inner Source Software License
 *  (Version 1.0, InnerPublic - OuterRestricted the "License");
 *
 *  You may not use this file except in compliance with the License.
 *
 *  The complete license agreement can be requested at contact@punchplatform.com.
 *
 *  Refer to the License for the specific language governing permissions and limitations
 *  under the License.
 */

package org.thales.punch.storm.node.starter.kit;

import org.apache.storm.tuple.Tuple;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.punch.api.node.PunchNode;
import com.github.punch.api.storm.impl.PunchProcessingNode;
import com.github.punch.api.storm.impl.StormNodePubSub;

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

  @Override
  public void declare(StormNodePubSub declarer) {
    declarer.subscribeMap(new TypeReference<String>() {});
    declarer.publishMap(new TypeReference<String>() {});
  }

}
