package com.example.demo.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class SavingAccount extends Account{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column (name="interest")
	private int interest;
	
	@OneToOne
	@JoinColumn(name = "user_ID")
	private User user;

	public int getInterest() {
		return interest;
	}

	public void setInterest(int interest) {
		this.interest = interest;
	}

	public SavingAccount(int interest) {
		super();
		this.interest = interest;
	}

	public SavingAccount() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
