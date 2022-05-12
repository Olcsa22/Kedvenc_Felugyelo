package com.example.vizsga_kedvenc_felugyelo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.Timestamp;
import java.util.Arrays;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;


@EnableSwagger2
@SpringBootApplication
public class VizsgaKedvencFelugyeloApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(VizsgaKedvencFelugyeloApplication.class, args);
    }


}
