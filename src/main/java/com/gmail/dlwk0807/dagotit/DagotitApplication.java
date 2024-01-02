package com.gmail.dlwk0807.dagotit;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DagotitApplication {

	public static void main(String[] args) {
		SpringApplication.run(DagotitApplication.class, args);
//		StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
//		pbeEnc.setAlgorithm("PBEWithMD5AndDES");
//		pbeEnc.setPassword("NzY2NA=="); //2번 설정의 암호화 키를 입력
//
//		String enc = pbeEnc.encrypt("tnedggdhfnnmadze"); //암호화 할 내용
//		System.out.println("enc = " + enc); //암호화 한 내용을 출력
//
////테스트용 복호화
//		String des = pbeEnc.decrypt(enc);
//		System.out.println("des = " + des);
	}

}
