package io.github.starter.punchline.spark;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.punchplatform.api.punchline.spark.datasets.InputDataset;
import io.github.punchplatform.api.punchline.spark.datasets.OutputDataset;
import io.github.punchplatform.api.punchline.spark.nodes.PunchProcessingNode;
import org.apache.spark.sql.Column;

/**
 * A custom function selecting the provided columns
 *
 * @author Punch Team
 */
public class CustomFunction extends PunchProcessingNode {

    @JsonProperty(value = "select")
    public List<String> select;

    @Override
    public void execute(InputDataset input, OutputDataset output) {
        Column[] columns = select.stream().map(Column::new).toArray(Column[]::new);
        input.getSingletonDataframe().ifPresent(ds -> output.put(ds.select(columns)));
    }
}
