package me.dusheiko.profile;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



import me.dusheiko.MasteringSpringMvcApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(MasteringSpringMvcApplication.class)
@WebAppConfiguration
public class HomeControllerTest {
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testShouldRedirectToProfile() throws Exception {
		mockMvc.perform(get("/"))
		.andDo(print())
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/profile"));
	}
	
	@Test
	public void testShouldRedirectToTastes() throws Exception {
		MockHttpSession session = new MockHttpSession();
		UserProfileSession sessionBean = new UserProfileSession();
		sessionBean.setTastes(Arrays.asList("spring", "groovy"));
		session.setAttribute("scopedTarget.userProfileSession", sessionBean);
		
		mockMvc.perform(get("/").session(session))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/search/mixed;keywords=spring,groovy"));
	}

}
