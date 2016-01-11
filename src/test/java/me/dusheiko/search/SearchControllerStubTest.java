package me.dusheiko.search;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import me.dusheiko.MasteringSpringMvcApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MasteringSpringMvcApplication.class, StubTwitterSearchConfiguration.class})
@WebAppConfiguration
@ActiveProfiles("test")
public class SearchControllerStubTest {
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void testShouldSearch() throws Exception {
		mockMvc.perform(get("/search/mixed;keywords=spring"))
		.andExpect(status().isOk())
		.andExpect(view().name("resultPage"))
		.andExpect(model().attribute("tweets", hasSize(2)))
		.andExpect(model().attribute("tweets", hasItems(hasProperty("text", is("tweetText")), hasProperty("text", is("secondTweet")))));
	}

}
