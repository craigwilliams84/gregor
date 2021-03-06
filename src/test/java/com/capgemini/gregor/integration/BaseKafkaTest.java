/*
* Copyright 2015 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.capgemini.gregor.integration;

import info.batey.kafka.unit.KafkaUnit;
import kafka.producer.KeyedMessage;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Base class for tests that require a kafka instance to be running.
 * 
 * Provides helper, kafka related methods.
 * 
 * @author craigwilliams84
 *
 */
public class BaseKafkaTest {
    
    private static final int WAIT_TIME_MS = 2000;
    
    public static final int BROKER_PORT = 9093;
    
    public static final int ZOOKEEPER_PORT = 2183;
    
    public static final String TEST_TOPIC = "testTopic";
    
    public static final String TEST_TOPIC2 = "testTopic2";
    
    public static final String TEST_TOPIC3 = "testTopic3";
    
    private static KafkaUnit kafka;
    
    @BeforeClass
    public static void startKafka() {
        kafka = new KafkaUnit(ZOOKEEPER_PORT, BROKER_PORT);
        kafka.startup();

        try {
            Thread.sleep(WAIT_TIME_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        kafka.createTopic(TEST_TOPIC);
        kafka.createTopic(TEST_TOPIC2);
        kafka.createTopic(TEST_TOPIC3);
    }
    
    @AfterClass
    public static void shutdownKafka() {
        kafka.shutdown();
        
        sleep();
    }

    @After
    public void drainMessages() {
        //Kafka unit doesn't currently support clearing messages so drain
        //messages so leftover messages from previous tests do not affect results.

        try {
            readMessages(TEST_TOPIC, 10);
        } catch (TimeoutException e) {
            //No need to do anything here
        }

        try {
            readMessages(TEST_TOPIC2, 10);
        } catch (TimeoutException e) {
            //No need to do anything here
        }

        try {
            readMessages(TEST_TOPIC3, 10);
        } catch (TimeoutException e) {
            //No need to do anything here
        }
    }
    
    protected void createTopic(String topicName) {
        kafka.createTopic(topicName);
    }
    
    protected void sendMessage(String topic, String value) {
        final KeyedMessage<String, String> message = new KeyedMessage<String, String>(topic, value);

        kafka.sendMessages(message);

        sleep();
    }

    protected List<String> readMessages(String topic, int expectedMessages) throws TimeoutException {
        return kafka.readMessages(topic, expectedMessages);
    }
    
    protected void waitForMessage() {
        sleep();
    }

    private static void sleep() {
        try {
            Thread.sleep(WAIT_TIME_MS);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
