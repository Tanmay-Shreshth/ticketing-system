package com.project.ticket_service.dto.request;

import java.util.UUID;

import com.project.ticket_service.model.enums.TicketEventType;
import com.project.ticket_service.model.enums.TicketStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketEvent {
    private UUID ticketId;
    private TicketEventType eventType;
    private TicketStatus status;
    private String actor;
}
