package com.stalary.book;

import com.stalary.book.filter.CrossOriginFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

@SpringBootApplication
@EnableAutoConfiguration
@ServletComponentScan
@EnableCaching
public class BookApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean crossOriginFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(crossOriginFilter());
		registration.addUrlPatterns("*");
		registration.setName("crossOriginFilter");
		registration.setOrder(1);
		return registration;
	}

	@Bean(name = "crossOriginFilter")
	public Filter crossOriginFilter() {
		return new CrossOriginFilter();
	}
}
