package me.dusheiko.profile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@Autowired
	private UserProfileSession userProfileSession;

	@RequestMapping("/")
	public String home() {
		List<String> tastes = userProfileSession.getTastes();
		if (tastes.isEmpty()) {
			return "redirect:/profile";
		}
		return "redirect:/search/mixed;keywords=" + String.join(",", tastes);
	}
}
