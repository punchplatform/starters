package io.github.starter.punchline.spark;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.punchplatform.api.punchline.spark.Function;
import io.github.punchplatform.api.punchline.spark.InputDatasets;
import io.github.punchplatform.api.punchline.spark.OutputDatasets;
import org.apache.spark.sql.Column;

/**
 * A custom function selecting the provided columns
 *
 * @author Punch Team
 */
public class CustomFunction extends Function {

    @JsonProperty(value = "select")
    public List<String> select;

    @Override
    public void execute(InputDatasets input, OutputDatasets output) {
        Column[] columns = select.stream().map(Column::new).toArray(Column[]::new);
        output.put(input.getFirst().select(columns));
    }
}
