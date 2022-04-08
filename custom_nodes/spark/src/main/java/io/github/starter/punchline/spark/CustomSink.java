package io.github.starter.punchline.spark;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.punchplatform.api.punchline.spark.datasets.InputDataset;
import io.github.punchplatform.api.punchline.spark.datasets.OutputDataset;
import io.github.punchplatform.api.punchline.spark.nodes.PunchProcessingNode;

/**
 * A custom sink showing a dataset with a truncate setting
 *
 * @author Punch Team
 */
public class CustomSink extends PunchProcessingNode {

  @JsonProperty(value = "truncate")
  public int truncate = 10;

  @Override
  public void execute(InputDataset input, OutputDataset output) {
    input.getSingletonDataframe().ifPresent(rowDataset -> rowDataset.show(truncate));
  }
}
