package cz.reservation.app;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {

    RESERVABLE_SCHEDULE_NOT_AVAILABLE("Termín rezervace je nedostupný"),
    RESERVABLE_SCHEDULE_NOT_FOUND("Termín rezervace nenalezen"),
    RESERVATION_NOT_FOUND("Rezervace nenalezena"),
    UNKNOWN_WS_ACTION("Nepodporovaná operace");

    private final String key;

    ErrorCode(String key) {
        this.key = key;
    }

    @JsonValue
    public String getKey() {
        return key;
    }
}
