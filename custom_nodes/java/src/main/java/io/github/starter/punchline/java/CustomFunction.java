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

package io.github.starter.punchline.java;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.punchplatform.api.exceptions.ConfigurationException;
import io.github.punchplatform.api.punchline.java.Function;
import io.github.punchplatform.api.punchline.java.Row;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The generator publishes fake data in configurable streams. It is extra simple and can be used to design
 *
 * @author Punch Team
 */
public class CustomFunction extends Function {

    private static Logger logger = LoggerFactory.getLogger(CustomFunction.class);
    Config config;

    @Override
    public void execute(Row row) {
        if (config.print) {
            logger.info("execute row " + row);
        }
        insert(out.get(0), row, row.getValues());
        ack(row);
    }

    @Override
    public void open() throws IOException, ConfigurationException {
        config = new ObjectMapper().convertValue(settings, Config.class);
        if (this.out.size() != 1) {
            throw new ConfigurationException("the sample function must output one and exactly one table");
        }
    }
}
