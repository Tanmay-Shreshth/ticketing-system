package com.project.ticket_service.dto.request;

import com.project.ticket_service.model.enums.TicketStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTicketStatusRequest {
    
    @NotNull
    private TicketStatus newStatus;

    @NotNull
    private String updatedBy;
}
