package me.dusheiko.config;

import java.time.LocalDate;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import me.dusheiko.date.USLocalDateFormater;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter{
	
	@Override
	public void addFormatters(FormatterRegistry formatterRegistry) {
		formatterRegistry.addFormatterForFieldType(LocalDate.class, new USLocalDateFormater());
	}
}
