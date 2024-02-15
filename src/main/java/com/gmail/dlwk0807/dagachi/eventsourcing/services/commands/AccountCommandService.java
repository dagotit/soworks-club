//package com.gmail.dlwk0807.dagachi.eventsourcing.services.commands;
//
//import com.gmail.dlwk0807.dagachi.eventsourcing.commands.AccountCreateDTO;
//import com.gmail.dlwk0807.dagachi.eventsourcing.commands.MoneyCreditDTO;
//import com.gmail.dlwk0807.dagachi.eventsourcing.commands.MoneyDebitDTO;
//
//import java.util.concurrent.CompletableFuture;
//
//public interface AccountCommandService {
//
//    public CompletableFuture<String> createAccount(AccountCreateDTO accountCreateDTO);
//    public CompletableFuture<String> creditMoneyToAccount(String accountNumber, MoneyCreditDTO moneyCreditDTO);
//    public CompletableFuture<String> debitMoneyFromAccount(String accountNumber, MoneyDebitDTO moneyDebitDTO);
//}
