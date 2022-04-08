package io.github.starter.punchline.java;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.punchplatform.api.exceptions.ConfigurationException;
import io.github.punchplatform.api.punchline.java.Function;
import io.github.punchplatform.api.punchline.java.Row;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sample function printing row
 *
 * @author Punch Team
 */
public class CustomSink extends Function {
    private static final Logger log = LoggerFactory.getLogger(CustomSink.class);
    private Config config;

    @Override
    public void execute(Row row) {
        if (config.print) {
            log.info("execute row {}", row.getValues().toString());
        }
        ack(row);
    }

    @Override
    public void open() throws ConfigurationException {
        config = new ObjectMapper().convertValue(settings, Config.class);
        if (!out.isEmpty()) {
            throw new ConfigurationException("the sample sink cannot output any row");
        }
    }
}
