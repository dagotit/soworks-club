package com.gmail.dlwk0807.dagachi.eventsourcing.events;

import com.gmail.dlwk0807.dagachi.eventsourcing.aggregates.Status;

public class AccountActivatedEvent extends BaseEvent<String> {

    public final Status status;

    public AccountActivatedEvent(String id, Status status) {
        super(id);
        this.status = status;
    }
}
