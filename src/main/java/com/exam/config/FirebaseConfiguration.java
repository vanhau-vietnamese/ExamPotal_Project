package com.exam.config;

import ch.qos.logback.core.model.IncludeModel;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Objects;

@Configuration
public class FirebaseConfiguration {
    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void initializeFirebase() throws FileNotFoundException {
        try {
            Resource resource = resourceLoader.getResource("classpath:serviceAccountKey.json");
            InputStream serviceAccount = resource.getInputStream();

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
}
