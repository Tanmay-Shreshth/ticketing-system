package com.project.ticket_service.service;

import java.util.UUID;

import com.project.ticket_service.model.enums.TicketStatus;

public interface TicketAuditService {
    
    void auditStatusChange(UUID ticketId, TicketStatus oldStatus, TicketStatus newStatus, String updatedBy);
}
