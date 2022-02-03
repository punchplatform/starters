/*
 *  This code is licensed under the outer restricted Tiss license:
 *
 *  Copyright [2014]-[2020] Thales Services under the Thales Inner Source Software License
 *  (Version 1.0, InnerPublic - OuterRestricted the "License");
 *
 *  You may not use this file except in compliance with the License.
 *
 *  The complete license agreement can be requested at contact@punchplatform.com.
 *
 *  Refer to the License for the specific language governing permissions and limitations
 *  under the License.
 */

package com.github.starter.punchline.java;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.punchplatform.api.exceptions.ConfigurationException;
import com.github.punchplatform.api.punchline.java.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This sample source publishes  a continuous stream of row in its declared output table.
 * 
 * @author Punch Team
 * 
 */

public class CustomSource extends Source {
    private static Logger logger = LoggerFactory.getLogger(CustomSource.class);

    private long count;
    Config config;

    @Override
    public void nextRows() {
        insert(out.get(0),
                Arrays.asList("astring", count, true), count++);
    }

    @Override
    public void ack(Object msgId) {
        if (config.print) {
            logger.info("ack {}", msgId);
        }
    }

    @Override
    public void fail(Object msgId) {
        throw new RuntimeException("I want to fail");
    }

    @Override
    public void open() throws IOException, ConfigurationException {
        config = new ObjectMapper().convertValue(settings, Config.class);
    }
}
