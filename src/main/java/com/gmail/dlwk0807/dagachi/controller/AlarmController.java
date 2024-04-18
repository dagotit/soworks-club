package com.gmail.dlwk0807.dagachi.controller;

import com.gmail.dlwk0807.dagachi.service.AlarmService;
import com.gmail.dlwk0807.dagachi.vo.ApiMessageVO;
import com.google.firebase.messaging.FirebaseMessaging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "ALARM API", description = "알람 API")
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

    @Operation(summary = "알람리스트")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @GetMapping("/list")
    public ApiMessageVO listAlarm() {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(alarmService.listAlarm(getCurrentMemberId()))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "알람ID로 찾기")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @GetMapping("/{id}")
    public ApiMessageVO findAlarm(@PathVariable Long id) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(alarmService.findAlarm(id))
                .respCode(OK_RESP_CODE)
                .build();
    }
}
