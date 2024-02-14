package com.gmail.dlwk0807.dagachi.eventsourcing.events;

public class BaseEvent<T> {

    public final T id;

    public BaseEvent(T id) {
        this.id = id;
    }
}
