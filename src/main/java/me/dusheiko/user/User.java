package me.dusheiko.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {
	private String twitterHande;
	private String email;
	private LocalDate birthDate;
	private List<String> tastes = new ArrayList<String>();
	
	public String getTwitterHande() {
		return twitterHande;
	}
	public void setTwitterHande(String twitterHande) {
		this.twitterHande = twitterHande;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public List<String> getTastes() {
		return tastes;
	}
	public void setTastes(List<String> tastes) {
		this.tastes = tastes;
	}
}
