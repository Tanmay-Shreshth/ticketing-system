package com.project.ticket_service.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.project.ticket_service.model.enums.TicketStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ticket_audit")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TicketAudit {

        @Id
        @GeneratedValue
        private UUID id;
        
        @Column(name = "ticket_id", nullable = false)
        private UUID ticketId;

        @Enumerated(EnumType.STRING)
        @Column(name = "old_status", nullable = false)
        private TicketStatus oldStatus;

        @Enumerated(EnumType.STRING)
        @Column(name = "new_status", nullable = false)
        private TicketStatus newStatus;

        @Column(name = "updated_by", nullable = false)
        private String updatedBy;

        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;
}
