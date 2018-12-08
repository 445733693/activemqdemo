package com.ccj;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

import javax.jms.*;

public class TestActivemq {
    @Test
    public void producerQueue() throws Exception {
//        创建工厂连接对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://118.25.70.241:61616");
//        创建一个连接对象
        Connection connection = connectionFactory.createConnection();
//        开启连接
        connection.start();
//        使用连接对象创建会话对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        使用会话对象创建目标对象，有queue和topic（一对一和一对多）
        Queue queue = session.createQueue("test-queue");
//        使用会话创建生产者对象
        MessageProducer producer = session.createProducer(queue);
//        使用会话创建一个消息对象
        TextMessage textMessage = session.createTextMessage("hello:test-queue");
//        发送消息
        producer.send(textMessage);
//        关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void comsumerQueue() throws Exception {
//                                                                                    注意这里是tcp协议，而不是http
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://118.25.70.241:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("test-queue");
//        根据会话创建消费者
        MessageConsumer consumer = session.createConsumer(queue);
//        向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(message instanceof TextMessage){
                    TextMessage tm = (TextMessage) message;
                    try {
                        System.out.println(tm.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
//        程序等待接收用户消息
        System.in.read();
//        关闭资源
        consumer.close();
        session.close();
        connection.close();

    }


    @Test
    public void producerTopic() throws Exception {
//        创建工厂连接对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://118.25.70.241:61616");
//        创建一个连接对象
        Connection connection = connectionFactory.createConnection();
//        开启连接
        connection.start();
//        使用连接对象创建会话对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        使用会话对象创建目标对象，有queue和topic（一对一和一对多）
        Topic topic = session.createTopic("test-topic");
//        使用会话创建生产者对象
        MessageProducer producer = session.createProducer(topic);
//        使用会话创建一个消息对象
        TextMessage textMessage = session.createTextMessage("hello:test-topic");
//        发送消息
        producer.send(textMessage);
//        关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void comsumerTopic() throws Exception {
//                                                                                    注意这里是tcp协议，而不是http
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://118.25.70.241:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test-topic");
//        根据会话创建消费者
        MessageConsumer consumer = session.createConsumer(topic);
//        向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(message instanceof TextMessage){
                    TextMessage tm = (TextMessage) message;
                    try {
                        System.out.println(tm.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
//        程序等待接收用户消息
        System.in.read();
//        关闭资源
        consumer.close();
        session.close();
        connection.close();

    }


}
