package org.thales.punch.spark.node.starter.kit;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.punch.api.spark.OutputDataset;
import com.github.punch.api.spark.SparkNodePubSub;
import com.github.punch.api.spark.nodes.PunchInputNode;
import com.github.punch.api.node.PunchNode;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An example of a custom INPUT_NODE
 * 
 * 
 * @author Punch Team
 *
 */
@PunchNode(name = "input_node_example", version = "1.0.0")
public class InputNode extends PunchInputNode {

  private static final long serialVersionUID = 1L;

  @JsonProperty(value = "title")
  public String title = "a_title";

  @JsonProperty(value = "input_data")
  public List<String> inputData = new LinkedList<>();

  @Override
  public void execute(OutputDataset output) {
    SparkSession sparkSession = SparkSession.builder().getOrCreate();
    List<Row> rows = inputData.stream().map(data -> RowFactory.create(data)).collect(Collectors.toList());
    StructType schema = new StructType(new StructField[] {
        new StructField(title, DataTypes.StringType, false, Metadata.empty())
    });
    Dataset<Row> documentDataset = sparkSession.createDataFrame(rows, schema);
    output.put(documentDataset);
  }

  @Override
  public void declare(SparkNodePubSub declarer) {
    declarer.publishMap(new TypeReference<Dataset<Row>>() {});
  }

}
