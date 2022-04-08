package io.github.starter.punchline.spark;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.punchplatform.api.punchline.spark.datasets.OutputDataset;
import io.github.punchplatform.api.punchline.spark.nodes.PunchInputNode;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

/**
 * A custom source creating a single column dataset with input data
 *
 * @author Punch Team
 */
public class CustomSource extends PunchInputNode {

  @JsonProperty(value = "title")
  public String title = "a_title";

  @JsonProperty(value = "input_data")
  public List<Integer> inputData = new LinkedList<>();

  @Override
  public void execute(OutputDataset output) {
    SparkSession sparkSession = SparkSession.builder().getOrCreate();
    List<Row> rows = inputData.stream().map(RowFactory::create).collect(Collectors.toList());
    StructType schema =
        new StructType(
            new StructField[] {
              new StructField(title, DataTypes.IntegerType, false, Metadata.empty())
            });
    output.put(sparkSession.createDataFrame(rows, schema));
  }
}
