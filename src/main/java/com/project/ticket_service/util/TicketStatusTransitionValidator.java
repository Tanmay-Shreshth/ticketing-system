package com.project.ticket_service.util;

import com.project.ticket_service.model.enums.TicketStatus;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class TicketStatusTransitionValidator {

    private static final Map<TicketStatus, Set<TicketStatus>> ALLOWED_TRANSITIONS =
            new EnumMap<>(TicketStatus.class);

    static {
        ALLOWED_TRANSITIONS.put(TicketStatus.OPEN, EnumSet.of(TicketStatus.ASSIGNED));

        ALLOWED_TRANSITIONS.put(TicketStatus.ASSIGNED, EnumSet.of(TicketStatus.IN_PROGRESS));

        ALLOWED_TRANSITIONS.put(TicketStatus.IN_PROGRESS, EnumSet.of(TicketStatus.ON_HOLD, TicketStatus.RESOLVED));

        ALLOWED_TRANSITIONS.put(TicketStatus.ON_HOLD, EnumSet.of(TicketStatus.IN_PROGRESS));

        ALLOWED_TRANSITIONS.put(TicketStatus.RESOLVED, EnumSet.of(TicketStatus.CLOSED));
    }

    public static boolean isValidTransition(TicketStatus from, TicketStatus to) {
        return ALLOWED_TRANSITIONS
                .getOrDefault(from, EnumSet.noneOf(TicketStatus.class))
                .contains(to);
    }
}
