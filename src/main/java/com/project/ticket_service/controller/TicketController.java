package com.project.ticket_service.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.project.ticket_service.dto.request.CreateTicketRequest;
import com.project.ticket_service.dto.request.UpdateTicketStatusRequest;
import com.project.ticket_service.dto.response.TicketResponse;
import com.project.ticket_service.service.TicketService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    
    @Autowired
    private TicketService ticketService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse createTicket(@Valid @RequestBody CreateTicketRequest request) {
        return ticketService.createTicket(request);
    }

    @GetMapping("/{ticketId}")
    public TicketResponse getTicket(@PathVariable UUID ticketId) {
        return ticketService.getTicketById(ticketId);
    }

    @PutMapping("/{ticketId}/assign/{agent}")
    public TicketResponse assignTicket(@PathVariable UUID ticketId, @PathVariable String agent) {
        return ticketService.assignTicket(ticketId, agent);
    }

    @PutMapping("/{ticketId}/status")
    public TicketResponse updateTicketStatus(@PathVariable UUID ticketId,  @RequestBody @Valid UpdateTicketStatusRequest request) {
        return ticketService.updateTicketStatus(ticketId, request.getNewStatus(), request.getUpdatedBy());
    }

    @GetMapping
    public String healthCheck() {
        return "Ticket Service is running.";
    }
}
