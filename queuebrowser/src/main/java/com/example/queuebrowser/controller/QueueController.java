package com.example.queuebrowser.controller;

import java.net.URISyntaxException;
import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.queuebrowser.config.JmsConfig;

@RestController
public class QueueController {

	@Autowired
	private JmsConfig jmsConfig;

	@Autowired
	private ActiveMQConnectionFactory conn;


	@PostMapping(value = "/greet")
	public String simplePrint(@RequestBody String message) {
		System.out.println("Message : "+message);
		return message;
	}

	@GetMapping(value = "/getContent")
	public void getContent() throws URISyntaxException, Exception {	
		Connection connection = null;
		Session session = null;
		QueueBrowser browser = null;
		try {
			connection = conn.createConnection();
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("OUTBOUNDQROUTER");
			MessageConsumer consumer = session.createConsumer(queue);
			connection.start();

			System.out.println("Browse through the elements in queue");
			browser = session.createBrowser(queue);
			Enumeration e = browser.getEnumeration();
			while (e.hasMoreElements()) {
				TextMessage message = (TextMessage) e.nextElement();
				System.out.println("Browse [" + message.getText() + "]");
			}
			System.out.println("Done");
		} finally {
			session.close();
			browser.close();
			connection.close();
		}
	}
}
