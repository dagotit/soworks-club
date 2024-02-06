package com.gmail.dlwk0807.dagachi.controller;

import com.gmail.dlwk0807.dagachi.service.AlarmService;
import com.gmail.dlwk0807.dagachi.vo.ApiMessageVO;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_MSG;
import static com.gmail.dlwk0807.dagachi.util.SecurityUtils.getCurrentMemberId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alarm")
public class AlarmController {

    private final FirebaseMessaging firebaseMessaging;
    private final AlarmService alarmService;

//    @PostMapping("/send-message")
//    public void sendMessage(@RequestBody MessageDTO message) throws FirebaseMessagingException {
//        String topic = "my-topic";
//        String notificationTitle = "FCM Message";
//        String notificationBody = message.getContent(); message.
//
//        FirebaseMessaging.Message fcmMessage = FirebaseMessaging.Message.builder()
//                .setTopic(topic)
//                .setNotification(new Notification(notificationTitle, notificationBody))
//                .build();
//
//        firebaseMessaging.send(fcmMessage);
//    }

    @GetMapping("/list")
    public ApiMessageVO listAlarm() {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(alarmService.listAlarm(getCurrentMemberId()))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @GetMapping("/{id}")
    public ApiMessageVO findAlarm(@PathVariable Long id) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(alarmService.findAlarm(id))
                .respCode(OK_RESP_CODE)
                .build();
    }
}
