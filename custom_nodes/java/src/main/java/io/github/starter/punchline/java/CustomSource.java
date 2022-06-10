package io.github.starter.punchline.java;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.punchplatform.api.punchline.java.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * This sample source publishes a continuous stream of row in its declared output table.
 *
 * @author Punch Team
 */
public class CustomSource extends Source {
    private static final Logger log = LoggerFactory.getLogger(CustomSource.class);
    private Config config;
    private long count;

    @Override
    public void start() {
        config = new ObjectMapper().convertValue(settings, Config.class);
        insert(out.get(0), Arrays.asList("astring", count, true), count++);
    }

    @Override
    public void ack(Object msgId) {
        if (config.print) {
            log.info("ack {}", msgId);
        }
    }

    @Override
    public void fail(Object msgId) {
        throw new RuntimeException("I want to fail");
    }
}
