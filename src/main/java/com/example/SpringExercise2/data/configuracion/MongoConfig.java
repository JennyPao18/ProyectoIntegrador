package com.example.SpringExercise2.data.configuracion;

import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(MongoClients.create("mongodb+srv://jenniferpaolapirirbor12:18Jenny06@clusterproyectointegrad.ruzahfh.mongodb.net/Users?retryWrites=true&w=majority&appName=ClusterProyectoIntegrador"), "Users");
    }
}


