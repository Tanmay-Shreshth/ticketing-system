package com.project.ticket_service.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.project.ticket_service.model.enums.TicketPriority;
import com.project.ticket_service.model.enums.TicketStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketResponse {
    
    private UUID id;
    private String title;
    private String description;
    private TicketStatus ticketStatus;
    private TicketPriority ticketPriority;
    private String createdBy;
    private String assignedTo;
    private LocalDateTime createdAt;
}
