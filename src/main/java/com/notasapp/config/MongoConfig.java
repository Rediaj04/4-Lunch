package com.notasapp.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Clase de configuración para establecer la conexión con MongoDB.
 * Define los beans necesarios para la integración con la base de datos.
 */
@Configuration
public class MongoConfig {

    /**
     * Crea y configura el cliente de MongoDB.
     * @return Una instancia del cliente MongoDB configurado
     */
    @Bean
    MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    /**
     * Crea la plantilla MongoDB que se usará para operaciones con la base de datos.
     * @return Una instancia de MongoTemplate configurada
     */
    @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "notesdb");
    }
}