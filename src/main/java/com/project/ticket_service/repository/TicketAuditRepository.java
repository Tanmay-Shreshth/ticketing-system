package com.project.ticket_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.ticket_service.model.entity.TicketAudit;

@Repository
public interface TicketAuditRepository extends JpaRepository<TicketAudit, UUID> {
    
}
