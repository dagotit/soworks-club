//package com.gmail.dlwk0807.dagachi.eventsourcing.config;
//
//import com.gmail.dlwk0807.dagachi.eventsourcing.aggregates.AccountAggregate;
//import org.axonframework.eventsourcing.EventSourcingRepository;
//import org.axonframework.eventsourcing.eventstore.EventStore;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class AxonConfig {
//
//    @Bean
//    EventSourcingRepository<AccountAggregate> accountAggregateEventSourcingRepository(EventStore eventStore){
//        EventSourcingRepository<AccountAggregate> repository = EventSourcingRepository.builder(AccountAggregate.class).eventStore(eventStore).build();
//        return repository;
//    }
//}