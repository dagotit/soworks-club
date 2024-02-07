package com.gmail.dlwk0807.dagachi.core.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseMessaging firebaseMessaging() {
        return FirebaseMessaging.getInstance(firebaseApp());
    }

    @Bean
    public FirebaseApp firebaseApp() {
        return FirebaseApp.initializeApp(firebaseOptions());
    }

    @Bean
    public FirebaseOptions firebaseOptions() {
        try {
            Resource credentialsResource = new ClassPathResource("solid-justice-407909-6826526ca338.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsResource.getInputStream());
            return FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load Firebase credentials from classpath:/solid-justice-407909-6826526ca338.json", e);
        }
    }


}
