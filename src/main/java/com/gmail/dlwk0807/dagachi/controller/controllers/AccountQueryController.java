//package com.gmail.dlwk0807.dagachi.controller.controllers;
//
//import com.gmail.dlwk0807.dagachi.eventsourcing.entities.AccountQueryEntity;
//import com.gmail.dlwk0807.dagachi.eventsourcing.services.queries.AccountQueryService;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping(value = "/bank-accounts")
//@Tag(name = "Account 컨트롤 [CQRS eventsourcing 테스트]")
//public class AccountQueryController {
//
//    private final AccountQueryService accountQueryService;
//
//    public AccountQueryController(AccountQueryService accountQueryService) {
//        this.accountQueryService = accountQueryService;
//    }
//
//    @GetMapping("/{accountNumber}")
//    public AccountQueryEntity getAccount(@PathVariable(value = "accountNumber") String accountNumber){
//        return accountQueryService.getAccount(accountNumber);
//    }
//
//    @GetMapping("/{accountNumber}/events")
//    public List<Object> listEventsForAccount(@PathVariable(value = "accountNumber") String accountNumber){
//        return accountQueryService.listEventsForAccount(accountNumber);
//    }
//
//}
