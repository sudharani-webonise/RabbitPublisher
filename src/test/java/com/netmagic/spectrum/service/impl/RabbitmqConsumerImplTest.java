package com.netmagic.spectrum.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import com.netmagic.spectrum.commons.ViewName;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ RabbitmqConsumerImpl.class })
public class RabbitmqConsumerImplTest {

    private static final String QUEUE_NAME = "test";

    private static final String NEW_QUEUE_NAME = "foo";

    private RabbitmqConsumerImpl rabbitmqConsumer;

    private SimpleMessageListenerContainer messageListenerMock;

    @Before
    public void setup() throws Exception {
        rabbitmqConsumer = PowerMockito.spy(new RabbitmqConsumerImpl());
        messageListenerMock = PowerMockito.spy(new SimpleMessageListenerContainer());
        messageListenerMock.addQueueNames(QUEUE_NAME);
        Whitebox.setInternalState(rabbitmqConsumer, "messageListener", messageListenerMock);
    }

    @Test
    public void testAddNewQueueInListener() {
        String viewName = rabbitmqConsumer.listenQueue(NEW_QUEUE_NAME);
        assertEquals(viewName, ViewName.HOME.getViewName());
    }

    @Test
    public void testAddSameQueueInLister() {
        String viewName = rabbitmqConsumer.listenQueue(QUEUE_NAME);
        assertEquals(viewName, ViewName.WARNING.getViewName());
    }

    @Test
    public void testAddNullQueueInLister() {
        String viewName = rabbitmqConsumer.listenQueue(null);
        assertEquals(viewName, ViewName.WARNING.getViewName());
    }

    @Test
    public void testAddEmptyQueueInLister() {
        String viewName = rabbitmqConsumer.listenQueue(Mockito.anyString());
        assertEquals(viewName, ViewName.WARNING.getViewName());
    }
}
