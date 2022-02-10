package io.github.starter.punchline.java;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.punchplatform.api.exceptions.ConfigurationException;
import io.github.punchplatform.api.punchline.java.Function;
import io.github.punchplatform.api.punchline.java.Row;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
/**
 * The generator publishes fake data in configurable streams. It is extra simple and can be used to design
 *
 * @author Punch Team
 */
public class CustomSink extends Function implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(CustomSink.class);

    @JsonProperty
    public Config config;

    @Override
    public void execute(Row row) {
        if (config.print) {
            logger.info("execute row " + row.getValues().toString());
        }
    }

    @Override
    public void open() throws IOException, ConfigurationException {
        config = new ObjectMapper().convertValue(settings, Config.class);
        if (this.out.size() > 0) {
            throw new ConfigurationException("the sample sink cannot output any row");
        }
    }
}