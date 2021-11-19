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

import java.util.Collections;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.punch.api.node.PunchNode;
import com.github.punch.api.storm.nodes.PunchInputNode;
import com.github.punch.api.storm.streams.StormNodePubSub;
import com.github.punch.api.node.StreamDeclaration;


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
      for (StreamDeclaration dataStream : getDataStreams()) {
            getCollector().emit(dataStream.getStreamId(), Collections.singletonList(message));
        } 
      }

  @Override
  public void onClose() {
      super.onClose();
      // do something before the node exit
  }

  @Override
  public void declare(StormNodePubSub declarer) {
    declarer.publishMap(new TypeReference<String>() {});
  }

}
