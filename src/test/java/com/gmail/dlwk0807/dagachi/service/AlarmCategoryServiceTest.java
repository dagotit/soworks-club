package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.entity.*;
import com.gmail.dlwk0807.dagachi.repository.AlarmCategoryRepository;
import com.gmail.dlwk0807.dagachi.repository.AlarmRepository;
import com.gmail.dlwk0807.dagachi.repository.CategoryRepository;
import com.gmail.dlwk0807.dagachi.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

//@SpringBootTest
//@ActiveProfiles(profiles = {"local"})
//@Transactional
public class AlarmCategoryServiceTest {

//    @Autowired
//    AlarmCategoryRepository alarmCategoryRepository;
//    @Autowired
//    AlarmRepository alarmRepository;
//    @Autowired
//    CompanyRepository companyRepository;



//    @BeforeEach
//    void listTest() throws Exception {
//        //given
//        Alarm alarm = Alarm.builder()
//                .readYn("N")
//                .regDateTime(LocalDateTime.now())
//                .content("내용 테스트")
//                .title("타이틀")
//                .receiveId(2L)
//                .sendId(1L)
//                .build();
//        Alarm alarm2 = Alarm.builder()
//                .readYn("Y")
//                .regDateTime(LocalDateTime.now())
//                .content("내용 테스트22")
//                .title("타이틀22")
//                .receiveId(3L)
//                .sendId(1L)
//                .build();
//        alarmRepository.save(alarm);
//        alarmRepository.save(alarm2);
//
//        Company company = Company.builder()
//                .address("test")
//                .bizNo("1234")
//                .build();
//
//        Company company2 = Company.builder()
//                .address("test22")
//                .bizNo("123445")
//                .build();
//
//        companyRepository.save(company);
//        companyRepository.save(company2);
//        //when
//        AlarmCategory alarmCategory = AlarmCategory.builder()
//                .id(new AlarmCompanyId(alarm.getId(), company.getId()))
//                .alarm(alarm)
//                .company(company)
//                .build();
//
//        AlarmCategory alarmCategory2 = AlarmCategory.builder()
//                .id(new AlarmCompanyId(alarm2.getId(), company2.getId()))
//                .alarm(alarm2)
//                .company(company2)
//                .build();
//
//        alarmCategoryRepository.save(alarmCategory);
//        alarmCategoryRepository.save(alarmCategory2);
//        //then
//    }
//
//    @Test
//    @DisplayName("test")
//    void test() throws Exception {
//        //given
//        List<AlarmCategory> all = alarmCategoryRepository.findAll();
//        for (AlarmCategory alarmCategory : all) {
//            String bizNo = alarmCategory.getCompany().getBizNo();
//            String readYn = alarmCategory.getAlarm().getReadYn();
//        }
//        //when
//
//        //then
//    }
}
