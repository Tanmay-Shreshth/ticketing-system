package com.project.ticket_service.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ticket_service.dto.request.CreateTicketRequest;
import com.project.ticket_service.dto.response.TicketResponse;
import com.project.ticket_service.exception.ResourceNotFoundException;
import com.project.ticket_service.model.entity.Ticket;
import com.project.ticket_service.repository.TicketRepository;
import com.project.ticket_service.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public TicketResponse createTicket(CreateTicketRequest request) {
        
        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setTicketPriority(request.getTicketPriority());
        ticket.setCreatedBy(request.getCreatedBy());

        Ticket savedTicket = ticketRepository.save(ticket);

        return ticketResponse(savedTicket);
    }

    @SuppressWarnings("null")
    @Override
    public TicketResponse getTicketById(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        return ticketResponse(ticket);
    }

    private TicketResponse ticketResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .ticketStatus(ticket.getTicketStatus())
                .ticketPriority(ticket.getTicketPriority())
                .createdBy(ticket.getCreatedBy())
                .assignedTo(ticket.getAssignedTo())
                .createdAt(ticket.getCreatedAt())
                .build();
    }
}
