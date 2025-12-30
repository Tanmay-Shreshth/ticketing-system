package com.project.ticket_service.service;

import java.util.UUID;

import com.project.ticket_service.dto.request.CreateTicketRequest;
import com.project.ticket_service.dto.response.TicketResponse;

public interface TicketService {

    TicketResponse createTicket(CreateTicketRequest request);

    TicketResponse getTicketById(UUID ticketId);

}
