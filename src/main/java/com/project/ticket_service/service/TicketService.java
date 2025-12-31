package com.project.ticket_service.service;

import java.util.UUID;

import com.project.ticket_service.dto.request.CreateTicketRequest;
import com.project.ticket_service.dto.response.TicketResponse;
import com.project.ticket_service.model.enums.TicketStatus;

public interface TicketService {

    TicketResponse createTicket(CreateTicketRequest request);

    TicketResponse getTicketById(UUID ticketId);

    TicketResponse assignTicket(UUID ticketId, String agent);

    TicketResponse updateTicketStatus(UUID ticketId, TicketStatus newStatus, String updatedBy);

}
