package io.github.starter.punchline.java;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.punchplatform.api.exceptions.ConfigurationException;
import io.github.punchplatform.api.punchline.java.Function;
import io.github.punchplatform.api.punchline.java.Row;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sample function forwarding and printing row
 *
 * @author Punch Team
 */
public class CustomFunction extends Function {
    private static final Logger log = LoggerFactory.getLogger(CustomFunction.class);
    private Config config;

    @Override
    public void execute(Row row) {
        if (config.print) {
            log.info("execute row {}", row);
        }
        insert(out.get(0), row, row.getValues());
        ack(row);
    }

    @Override
    public void open() throws ConfigurationException {
        config = new ObjectMapper().convertValue(settings, Config.class);
        if (out.size() != 1) {
            throw new ConfigurationException(
                    "the sample function must output one and exactly one table");
        }
    }
}
