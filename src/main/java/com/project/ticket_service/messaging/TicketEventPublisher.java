package com.project.ticket_service.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.project.ticket_service.configuration.RabbitConfig;
import com.project.ticket_service.dto.request.TicketEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketEventPublisher {
    
    private final RabbitTemplate rabbitTemplate;

    public void publishEvent(TicketEvent ticketEvent) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, ticketEvent);
    }
}
