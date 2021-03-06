package cn.e3mall.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class ActiveMqTest {
	/*
	 * 点到点形式发送消息
	 */
	@Test
	public void testQueueProducer() throws Exception {
		// 1.创建一个连接工厂,需要指定服务的ip及端口
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.135:61616");
		// 2.使用工厂对象创建一个Connection
		Connection connection = connectionFactory.createConnection();
		// 3.开启连接,调用Connection的start方法
		connection.start();
		// 4.创建一个Session对象
		// 第一个参数:是否开启事务 一般不开启 如果开启事务 第二个参数无意义
		// 第二个参数:应答模式 一般是 自动应答/手动应答
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5.使用Session对象创建一个Destination对象(目的地)两种形式queue/topic
		Queue queue = session.createQueue("test-queue");
		// 6.使用Session对象创建一个producer对象
		MessageProducer producer = session.createProducer(queue);
		// 7.创建一个Message对象,可以使用TextMessage
		// ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
		TextMessage textMessage = session.createTextMessage("hello active mq");
		// 8.发送消息
		producer.send(textMessage);
		// 9.关闭资源
		producer.close();
		session.close();
		connection.close();
	}

	@Test
	public void testQueueConsumer() throws Exception {
		// 创建一个connectionFactory对象 连接mq服务器
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.135:61616");
		// 创建一个连接对象
		Connection connection = connectionFactory.createConnection();
		// 开启连接
		connection.start();
		// 使用连接对象创建一个session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 创建一个destination queue对象
		Queue queue = session.createQueue("test-queue");
		// 使用session对象创建一个消费者对象
		MessageConsumer consumer = session.createConsumer(queue);
		// 接受消息
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				String text;
				try {
					text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// 等待接受消息
		System.in.read();
		// 关闭资源
		consumer.close();
		session.close();
		connection.close();
	}

	// Topic
	@Test
	public void testTopicProducer() throws Exception {
		// 1.创建一个连接工厂,需要指定服务的ip及端口
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.135:61616");
		// 2.使用工厂对象创建一个Connection
		Connection connection = connectionFactory.createConnection();
		// 3.开启连接,调用Connection的start方法
		connection.start();
		// 4.创建一个Session对象
		// 第一个参数:是否开启事务 一般不开启 如果开启事务 第二个参数无意义
		// 第二个参数:应答模式 一般是 自动应答/手动应答
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5.使用Session对象创建一个Destination对象(目的地)两种形式queue/topic
		Topic topic = session.createTopic("test-topic");
		// 6.使用Session对象创建一个producer对象
		MessageProducer producer = session.createProducer(topic);
		// 7.创建一个Message对象,可以使用TextMessage
		// ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
		TextMessage textMessage = session.createTextMessage("hello active mq topic!!!");
		// 8.发送消息
		producer.send(textMessage);
		// 9.关闭资源
		producer.close();
		session.close();
		connection.close();
	}

	@Test
	public void testTopicConsumer() throws Exception {
		// 创建一个connectionFactory对象 连接mq服务器
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.135:61616");
		// 创建一个连接对象
		Connection connection = connectionFactory.createConnection();
		// 开启连接
		connection.start();
		// 使用连接对象创建一个session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 创建一个destination topic对象
		Topic topic = session.createTopic("test-topic");
		// 使用session对象创建一个消费者对象
		MessageConsumer consumer = session.createConsumer(topic);
		// 接受消息
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				String text;
				try {
					text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// 等待接受消息
		System.in.read();
		// 关闭资源
		consumer.close();
		session.close();
		connection.close();
	}

}
