package io.github.starter.punchline.spark;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.punchplatform.api.punchline.spark.Function;
import io.github.punchplatform.api.punchline.spark.InputDatasets;
import io.github.punchplatform.api.punchline.spark.OutputDatasets;

/**
 * A custom sink showing a dataset with a truncate setting
 *
 * @author Punch Team
 */
public class CustomSink extends Function {

    @JsonProperty(value = "truncate")
    public int truncate = 10;

    @Override
    public void execute(InputDatasets input, OutputDatasets output) {
        input.getFirst().show(truncate);
    }
}
