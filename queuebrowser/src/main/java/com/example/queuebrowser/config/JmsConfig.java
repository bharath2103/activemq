package com.example.queuebrowser.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource("classpath:application.properties")
public class JmsConfig {

	@Value("${spring.activemq.broker-url}")
	private String brokerUrl;
	@Value("${spring.activemq.user}")
	private String user;
	@Value("${spring.activemq.password}")
	private String password;

	@Bean
	@Primary
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}

	/*@Bean
	public ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory(user, password, brokerUrl);
	}*/

	@Bean
	public JmsTransactionManager jmsTransactionManager(final ConnectionFactory connectionFactory) {
		JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
		jmsTransactionManager.setConnectionFactory(connectionFactory);
		return jmsTransactionManager;
	}

	@Bean
	public JmsComponent jmsComponent(final ConnectionFactory connectionFactory,
			final JmsTransactionManager jmsTransactionManager) {
		return JmsComponent.jmsComponentTransacted(connectionFactory, jmsTransactionManager);
	}

	@Bean
	public ActiveMQConnectionFactory activeMqConnectionFactory(){
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerUrl);
		connectionFactory.setPassword(user);
		connectionFactory.setUserName(password);
		return connectionFactory;
	}


}
