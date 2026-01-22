package com.project.ticket_service.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ticket_service.model.entity.TicketAudit;
import com.project.ticket_service.model.enums.TicketStatus;
import com.project.ticket_service.repository.TicketAuditRepository;
import com.project.ticket_service.service.TicketAuditService;

@Service
public class TicketAuditServiceImpl implements TicketAuditService {

    @Autowired
    private TicketAuditRepository ticketAuditRepository;

    @Override
    public void auditStatusChange(UUID ticketId, TicketStatus oldStatus, TicketStatus newStatus, String updatedBy) {
        TicketAudit audit = TicketAudit.builder()
                .ticketId(ticketId)
                .oldStatus(oldStatus)
                .newStatus(newStatus)
                .updatedBy(updatedBy)
                .updatedAt(LocalDateTime.now())
                .build();

        ticketAuditRepository.save(audit);
    }
    
}
