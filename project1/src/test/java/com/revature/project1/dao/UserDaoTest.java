package com.revature.project1.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.revature.project1.model.User;

class UserDaoTest {

	static UserDao dao;
	String[] usernames = {"hlarsen", "nselfridge", "jhorn", "chatita", "edgrrr"};
	String[] passwords = {"pass1", "pass2", "pass3", "pass4", "pass5"};		
	String[] fullNames = {"Holly Larsen", "Nancy Selfridge", "Jessica Horn", "Chatita", "Edgar"};
	String[] positions = {"employee", "employee", "employee", "employee", "manager"};
	
	@BeforeAll
	static void setup() {
		dao = UserDao.getUserDao();
	}
	
	@Test
	void testFindAll() {
		List<User> users = dao.findAll();
        for (int i = 0; i < users.size(); i++) {
            Assertions.assertTrue(users.get(i).getUserName().equals(usernames[i]));
            Assertions.assertTrue(users.get(i).getPassword().equals(passwords[i]));
            Assertions.assertTrue(users.get(i).getFullName().equals(fullNames[i]));
            Assertions.assertTrue(users.get(i).getPosition().equals(positions[i]));
        }
	}

	@Test
	void testFindById() {
        for (int i = 0; i < usernames.length; i++) {
        	User u = dao.findById(i + 1);
        	Assertions.assertTrue(u.getUserName().equals(usernames[i]));
        	Assertions.assertTrue(u.getPassword().equals(passwords[i]));
        	Assertions.assertTrue(u.getFullName().equals(fullNames[i]));
        	Assertions.assertTrue(u.getPosition().equals(positions[i]));
        }
	}

}
