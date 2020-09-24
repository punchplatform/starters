package org.thales.punch.udf.starter.kit;

import java.util.ArrayList;
import java.util.List;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class SumAggregate extends UserDefinedAggregateFunction {

  private static final long serialVersionUID = 1L;

  @Override
  public StructType bufferSchema() {
    // buffer that spark will be using to keep track of aggregate result
    List<StructField> bufferFields = new ArrayList<>();
    bufferFields.add(DataTypes.createStructField("current_avg_age", DataTypes.IntegerType, true));
    bufferFields.add(DataTypes.createStructField("num_of_row", DataTypes.IntegerType, true));
    return DataTypes.createStructType(bufferFields);
  }

  @Override
  public DataType dataType() {
    // returned data type
    return DataTypes.IntegerType;
  }

  @Override
  public boolean deterministic() {
    // if order of processed data does not matter, return true
    return true;
  }

  @Override
  public Integer evaluate(Row row) {
    // called when processing is over and only one buffer left, will return the final aggregated result
    return row.getInt(0) / row.getInt(1);
  }

  @Override
  public void initialize(MutableAggregationBuffer mutableAggregationBuffer) {
    // This method is used to initialize the buffer. This method can be 
    // called any number of times of spark during processing.
    
    // initialize buffer at index 0 with value 0
    mutableAggregationBuffer.update(0, 0);
    // initialize buffer at index 1 with value 0
    mutableAggregationBuffer.update(1, 0);
  }

  @Override
  public StructType inputSchema() {
    // what this udaf expect as input schema
    List<StructField> inputFields = new ArrayList<>();
    inputFields.add(DataTypes.createStructField("age", DataTypes.IntegerType, true));
    return DataTypes.createStructType(inputFields);
  }

  @Override
  public void merge(MutableAggregationBuffer mutableAggregationBuffer, Row row) {
    // combine two buffer into one
    // combine buffer index 0
    mutableAggregationBuffer.update(0, mutableAggregationBuffer.getInt(0) + row.getInt(0));
    // combine buffer index 1
    mutableAggregationBuffer.update(1, mutableAggregationBuffer.getInt(1) + row.getInt(1));
  }

  @Override
  public void update(MutableAggregationBuffer mutableAggregationBuffer, Row row) {
    // takes a row and update the buffer
    if (!row.isNullAt(0)) {
      int inputValue = row.getInt(0);
      int bufferAvgAge = mutableAggregationBuffer.getInt(0);
      int bufferRowCount = mutableAggregationBuffer.getInt(1);
      mutableAggregationBuffer.update(0, inputValue + bufferAvgAge);
      mutableAggregationBuffer.update(1, bufferRowCount + 1);
    }
  }

}
