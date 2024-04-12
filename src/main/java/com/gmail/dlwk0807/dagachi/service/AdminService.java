package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.dto.admin.AdminSendAlarmDTO;
import com.gmail.dlwk0807.dagachi.dto.member.MemberExcelRequestDTO;
import com.gmail.dlwk0807.dagachi.dto.member.MemberResponseDTO;
import com.gmail.dlwk0807.dagachi.entity.Alarm;
import com.gmail.dlwk0807.dagachi.entity.Company;
import com.gmail.dlwk0807.dagachi.entity.Member;
import com.gmail.dlwk0807.dagachi.repository.AlarmRepository;
import com.gmail.dlwk0807.dagachi.repository.MemberCustomRepository;
import com.gmail.dlwk0807.dagachi.repository.MemberRepository;
import com.gmail.dlwk0807.dagachi.util.AuthUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final MemberCustomRepository memberCustomRepository;
    private final MemberRepository memberRepository;
    private final AlarmRepository alarmRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;

    public String sendAlarm(List<AdminSendAlarmDTO> adminSendAlarmDtoList) {
        for (AdminSendAlarmDTO adminSendAlarmDTO : adminSendAlarmDtoList) {
            Alarm alarm = Alarm.builder()
                    .sendId(adminSendAlarmDTO.getMemberId())
                    .receiveId(adminSendAlarmDTO.getReceiveId())
                    .title(adminSendAlarmDTO.getTitle())
                    .content(adminSendAlarmDTO.getContent())
                    .regDateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                    .readYn("N")
                    .build();
            alarmRepository.save(alarm);
        }

        return "알림발송 완료";

    }

    public String memberUpload(MultipartFile file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {

            DataFormatter formatter = new DataFormatter();
            XSSFRow row = worksheet.getRow(i);

            String email = formatter.formatCellValue(row.getCell(0));
            String name = formatter.formatCellValue(row.getCell(1));
            String nickname = formatter.formatCellValue(row.getCell(2));
            String birth = formatter.formatCellValue(row.getCell(3));

            MemberExcelRequestDTO memberDTO = MemberExcelRequestDTO.builder()
                    .email(email)
                    .name(name)
                    .nickname(nickname)
                    .birth(birth)
                    .build();

            //현재 로그인한 관리자의 소속 회사
            Company currentCompany = authUtils.getCurrentCompany();
            Member member = memberDTO.toMember(passwordEncoder, currentCompany);
            memberRepository.save(member);
        }

        return "일괄업로드 성공";
    }

    public List<MemberResponseDTO> memberList(String name, String email) {
        return memberCustomRepository.findAllByNameAndEmailContaining(name, email).stream()
                .map(MemberResponseDTO::of).collect(Collectors.toList());
    }

    public String template(HttpServletResponse res) throws IOException {
        /**
         * excel sheet 생성
         */
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("회원목록"); // 엑셀 sheet 이름
        sheet.setDefaultColumnWidth(28); // 디폴트 너비 설정

        /**
         * header font style
         */
        XSSFFont headerXSSFFont = (XSSFFont) workbook.createFont();
        headerXSSFFont.setColor(IndexedColors.WHITE.getIndex());

        /**
         * header cell style
         */
        XSSFCellStyle headerXssfCellStyle = (XSSFCellStyle) workbook.createCellStyle();

        // 테두리 설정
        headerXssfCellStyle.setBorderLeft(BorderStyle.THIN);
        headerXssfCellStyle.setBorderRight(BorderStyle.THIN);
        headerXssfCellStyle.setBorderTop(BorderStyle.THIN);
        headerXssfCellStyle.setBorderBottom(BorderStyle.THIN);

        // 배경 설정
        headerXssfCellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
        headerXssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerXssfCellStyle.setFont(headerXSSFFont);

        /**
         * body cell style
         */
        XSSFCellStyle bodyXssfCellStyle = (XSSFCellStyle) workbook.createCellStyle();

        // 테두리 설정
        bodyXssfCellStyle.setBorderLeft(BorderStyle.THIN);
        bodyXssfCellStyle.setBorderRight(BorderStyle.THIN);
        bodyXssfCellStyle.setBorderTop(BorderStyle.THIN);
        bodyXssfCellStyle.setBorderBottom(BorderStyle.THIN);

        /**
         * header data
         */
        int rowCount = 0; // 데이터가 저장될 행
        String headerNames[] = new String[]{"이메일", "이름", "닉네임", "생년월일(8자리)"};

        Row headerRow = null;
        Cell headerCell = null;

        headerRow = sheet.createRow(rowCount++);
        for(int i=0; i<headerNames.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headerNames[i]); // 데이터 추가
            headerCell.setCellStyle(headerXssfCellStyle); // 스타일 추가
        }

        /**
         * body data
         */
        String bodyDatass[][] = new String[][]{
                {"user1@company.co.kr", "mike", "바이오더마", "20001111"},
                {"user2@company.co.kr", "nike", "먹는샘물", "20201212"},
                {"user3@company.co.kr", "john", "항균제", "19990909"}
        };

        Row bodyRow = null;
        Cell bodyCell = null;

        for(String[] bodyDatas : bodyDatass) {
            bodyRow = sheet.createRow(rowCount++);

            for(int i=0; i<bodyDatas.length; i++) {
                bodyCell = bodyRow.createCell(i);
                bodyCell.setCellValue(bodyDatas[i]); // 데이터 추가
                bodyCell.setCellStyle(bodyXssfCellStyle); // 스타일 추가
            }
        }

        /**
         * download
         */
        String fileName = "dagachi-upload-template";

        res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream servletOutputStream = res.getOutputStream();

        workbook.write(servletOutputStream);
        workbook.close();
        servletOutputStream.flush();
        servletOutputStream.close();

        return "ok";
    }

}
