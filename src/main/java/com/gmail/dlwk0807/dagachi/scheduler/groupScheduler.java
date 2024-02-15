package com.gmail.dlwk0807.dagachi.scheduler;

import com.gmail.dlwk0807.dagachi.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class groupScheduler {

    private final GroupService groupService;

    @Scheduled(cron = "0 00 20 * * *")
    public void proceedGroupBatch() {
        log.info("20시 모임 종료일로 부터 1주일 지난 진행중인 모임 실패처리 & 모임개설 점수 감산");
        groupService.setFailAndMinusScore();
    }
}
