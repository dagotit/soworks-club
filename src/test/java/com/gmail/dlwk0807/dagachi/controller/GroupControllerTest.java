package com.gmail.dlwk0807.dagachi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.dlwk0807.dagachi.dto.group.GroupResponseDTO;
import com.gmail.dlwk0807.dagachi.dto.group.GroupSaveRequestDTO;
import com.gmail.dlwk0807.dagachi.entity.Group;
import com.gmail.dlwk0807.dagachi.service.GroupService;
import org.apache.catalina.core.ApplicationPart;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_MSG;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GroupService groupService;

    @Test
    @WithMockUser(username = "1", password = "1234", roles = "USER")
    void testInfo() throws Exception {
        Long groupId = 1L;
        GroupResponseDTO responseDTO = GroupResponseDTO.builder()
                .groupId(1L)
                .memberId(1L)
                .name("모임이름")
                .strStartDate("20201212")
                .strEndDate("20201212")
                .build();
        when(groupService.info(groupId)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/groups/info")
                        .param("groupId", groupId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respMsg").value(OK_RESP_MSG))
                .andExpect(jsonPath("$.respCode").value(OK_RESP_CODE))
                .andExpect(jsonPath("$.respBody").exists());
    }

    @Test
    @WithMockUser(username = "1", password = "1234", roles = "USER")
    public void testCreateGroup() throws Exception {
        // Mock 데이터 생성
        GroupSaveRequestDTO groupSaveRequestDTO = new GroupSaveRequestDTO(List.of(1L, 2L, 3L), "모임명", "설명", "202012120505", "202012120505", 3L);
        // 설정...

        GroupResponseDTO responseDTO = GroupResponseDTO.builder()
                .groupId(1L)
                .memberId(1L)
                .name("모임이름")
                .strStartDate("202012121010")
                .strEndDate("202012121010")
                .build();
        // Mock Service 메소드의 반환값 설정
        when(groupService.saveGroup(any(GroupSaveRequestDTO.class), any(MockMultipartFile.class)))
                .thenReturn(responseDTO); // 예제 결과값으로 Mocked result를 사용

        // MockMultipartFile을 생성하여 파일 업로드 테스트
        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        // MockMvc를 사용하여 엔드포인트 호출
        ResultActions perform = mockMvc.perform(multipart("/api/v1/groups/save")
                .file(file)
//                .part(new MockPart("group", objectMapper.writeValueAsString(groupSaveRequestDTO).getBytes())
//                , new MockPart("file", file.getBytes()))
                .param("group", objectMapper.writeValueAsString(groupSaveRequestDTO))
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));

                perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.respMsg").value(OK_RESP_MSG))
                .andExpect(jsonPath("$.respCode").value(OK_RESP_CODE))
                .andExpect(jsonPath("$.respBody").exists());

        // 추가적인 검증 로직이 있다면 이곳에 작성
    }

}