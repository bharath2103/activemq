package com.example.queuebrowser.config;

import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.queuebrowser.controller.QueueController;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JmsRouter extends RouteBuilder {
	@Autowired
	private QueueController queueController;

	@Override
	public void configure() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		from("{{input.queue}}")
		.process(exchange -> {
			Message message = exchange.getMessage();
			Object body = message.getBody();
			String convertedMessage = "";
			if (body instanceof byte[]) {
				byte[] bytes = (byte[]) body;
				convertedMessage =  new String(bytes);

			} else {
				convertedMessage =  body.toString();
			}
			exchange.getContext().getDataFormatParameterJsonSchema(convertedMessage);
			message.setBody(mapper.writeValueAsString(queueController.simplePrint(convertedMessage)));
		})
		.to("{{output.queue}}")
		//        .log(LoggingLevel.DEBUG, log, "Message sent to the other queue")
		.end();
	}
}
