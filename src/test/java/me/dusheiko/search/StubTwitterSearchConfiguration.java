package me.dusheiko.search;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import me.dusheiko.search.LightTweet;
import me.dusheiko.search.TwitterSearch;

@Configuration
public class StubTwitterSearchConfiguration {
	@Profile("test")
	@Primary
	@Bean
	public TwitterSearch twitterSearch() {
		return (search, keywords) -> Arrays.asList(new LightTweet("tweetText"), new LightTweet("secondTweet"));
	}
}
