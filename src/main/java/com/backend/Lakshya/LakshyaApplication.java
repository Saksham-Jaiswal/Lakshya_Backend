package com.backend.Lakshya;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LakshyaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LakshyaApplication.class, args);
		System.out.println("Hello world!");
	}



}
