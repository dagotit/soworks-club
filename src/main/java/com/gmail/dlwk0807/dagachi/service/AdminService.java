package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.dto.admin.AdminSendAlarmDTO;
import com.gmail.dlwk0807.dagachi.dto.member.MemberExcelRequestDTO;
import com.gmail.dlwk0807.dagachi.entity.Company;
import com.gmail.dlwk0807.dagachi.entity.Member;
import com.gmail.dlwk0807.dagachi.repository.MemberRepository;
import com.gmail.dlwk0807.dagachi.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;

    public String sendAlarm(AdminSendAlarmDTO adminSendAlarmDto) {

        return null;

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
}
