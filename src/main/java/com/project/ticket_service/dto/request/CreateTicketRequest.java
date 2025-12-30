package com.project.ticket_service.dto.request;

import com.project.ticket_service.model.enums.TicketPriority;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTicketRequest {
    @NotBlank
    private String title;
    private String description;

    private TicketPriority ticketPriority;

    @NotBlank
    private String createdBy;
}
