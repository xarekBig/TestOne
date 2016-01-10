package me.dusheiko.user.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import me.dusheiko.user.User;
import me.dusheiko.user.UserRepository;

@RestController
@RequestMapping("/api")
public class UserApiController {
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@RequestMapping(value = "/user/{email}", method = RequestMethod.PUT)
	public User updateUser(@PathVariable String email, @RequestBody User user) {
		return userRepository.save(email, user);
	}
	
	@RequestMapping(value = "/user/{email}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable String email) {
		userRepository.delete(email);
	}
	
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
}
