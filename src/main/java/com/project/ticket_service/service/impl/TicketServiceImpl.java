package com.project.ticket_service.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.project.ticket_service.dto.request.CreateTicketRequest;
import com.project.ticket_service.dto.request.TicketEvent;
import com.project.ticket_service.dto.response.TicketResponse;
import com.project.ticket_service.exception.ResourceNotFoundException;
import com.project.ticket_service.messaging.TicketEventPublisher;
import com.project.ticket_service.model.entity.Ticket;
import com.project.ticket_service.model.enums.TicketEventType;
import com.project.ticket_service.model.enums.TicketStatus;
import com.project.ticket_service.repository.TicketRepository;
import com.project.ticket_service.service.TicketAuditService;
import com.project.ticket_service.service.TicketService;
import com.project.ticket_service.util.TicketStatusTransitionValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private final TicketAuditService ticketAuditService;

    private final TicketEventPublisher ticketEventPublisher;

    @Override
    public TicketResponse createTicket(CreateTicketRequest request) {
        
        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setTicketPriority(request.getTicketPriority());
        ticket.setCreatedBy(request.getCreatedBy());

        Ticket savedTicket = ticketRepository.save(ticket);

        publishTicketEvent(savedTicket, TicketEventType.CREATED, request.getCreatedBy());

        return ticketResponse(savedTicket);
    }

    @Override
    public TicketResponse getTicketById(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        return ticketResponse(ticket);
    }

    @Override
    public TicketResponse assignTicket(UUID ticketId, String agent) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        if(ticket.getTicketStatus() != TicketStatus.OPEN) {
            throw new IllegalStateException("Only open tickets can be assigned");
        }

        TicketStatus oldStatus = ticket.getTicketStatus();

        ticket.setAssignedTo(agent);
        ticket.setTicketStatus(TicketStatus.ASSIGNED);

        Ticket updatedTicket = ticketRepository.save(ticket);

        ticketAuditService.auditStatusChange(ticketId, oldStatus, TicketStatus.ASSIGNED, agent);

        publishTicketEvent(updatedTicket, TicketEventType.ASSIGNED, agent);

        return ticketResponse(updatedTicket);

    }

    @Override
    public TicketResponse updateTicketStatus(UUID ticketId, TicketStatus newStatus, String updatedBy) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        TicketStatus currentStatus = ticket.getTicketStatus();

        if (!TicketStatusTransitionValidator.isValidTransition(currentStatus, newStatus)) {
            throw new IllegalStateException("Invalid status transition from " + currentStatus + " to " + newStatus);
        }

        ticket.setTicketStatus(newStatus);
        Ticket updatedTicket = ticketRepository.save(ticket);

        ticketAuditService.auditStatusChange(ticketId, currentStatus, newStatus, updatedBy);

        publishTicketEvent(updatedTicket, TicketEventType.STATUS_CHANGED, updatedBy);

        return ticketResponse(updatedTicket);
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

    private void publishTicketEvent(Ticket ticket, TicketEventType eventType, String actor) {
        ticketEventPublisher.publishEvent(
            TicketEvent.builder()
                .ticketId(ticket.getId())
                .status(ticket.getTicketStatus())
                .eventType(eventType)
                .actor(actor)
                .build()
        );
    }

}
