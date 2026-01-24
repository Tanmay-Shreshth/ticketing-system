package com.project.ticket_service.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.project.ticket_service.configuration.rabbitmq.RabbitConfig;
import com.project.ticket_service.dto.request.TicketEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketEventPublisher {
    
    private final RabbitTemplate rabbitTemplate;

    public void publishEvent(TicketEvent ticketEvent) {

        try{
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, ticketEvent);
        }
        catch(AmqpException ex){
            log.error("RabbitMQ Publish faisl", ex);
        }
        
    }
}
