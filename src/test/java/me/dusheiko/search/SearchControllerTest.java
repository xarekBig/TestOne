package me.dusheiko.search;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import me.dusheiko.MasteringSpringMvcApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MasteringSpringMvcApplication.class)
@WebAppConfiguration
public class SearchControllerTest {
	@Mock
	private SearchService searchService;
	
	@InjectMocks
	private SearchController searchController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(searchController)
				.setRemoveSemicolonContent(false)
				.build();
	}

	@Test
	public void testShouldSearch() throws Exception {
		when(searchService.search(anyString(), anyListOf(String.class)))
		.thenReturn(Arrays.asList(new LightTweet("tweetText")));
		
		mockMvc.perform(get("/search/mixed;keywords=spring"))
		.andExpect(status().isOk())
		.andExpect(view().name("resultPage"))
		.andExpect(model().attribute("tweets", everyItem(hasProperty("text", is("tweetText")))));
		
		verify(searchService, times(1)).search(anyString(), anyListOf(String.class));
	}

}
