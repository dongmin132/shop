package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Bean   //메소드를 호출하여 Bean 객체를 생성
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {    //put, delete 처리
		//filter로 어떤 Mapping인지를 처리 해줌
		return new HiddenHttpMethodFilter();
	}

}
