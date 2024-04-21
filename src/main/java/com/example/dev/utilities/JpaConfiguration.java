package com.example.dev.utilities;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "com.example.dev.model")
public class JpaConfiguration {

}
