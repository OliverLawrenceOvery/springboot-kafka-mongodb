package com.example;

import io.jaegertracing.Configuration;
import io.opentracing.Tracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

//    @Bean
//    public static Tracer getTracer() {
//        Configuration.SamplerConfiguration samplerConfiguration = Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1);
//        Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
//        Configuration config = new Configuration("jaeger tutorial").withSampler(samplerConfiguration).withReporter(reporterConfiguration);
//        return config.getTracer();
//    }
//
//    @PostConstruct
//    public void setProperty() {
//        System.setProperty("JAEGER_REPORTER_LOG_SPANS", "true");
//    }
}




