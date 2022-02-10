package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	Random random = new Random();

	@Autowired
	private UserRepository repo;
	@Autowired
	private RoleRepository roleRepo;
	
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUserWithOneRole() {
		int rand = random.nextInt(10000);
		
		Role roleAdmin = entityManager.find(Role.class, 1);
		
		User testUser = new User("user"+rand+"@email.com", "password", "user"+rand+"First", "user"+rand+"Last");
		testUser.addRole(roleAdmin);
		
		User savedUser = repo.save(testUser);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserWithTwoRoles() {
	
		int rand = random.nextInt(10000);
		
		User testUser = new User("user"+rand+"@email.com", "password", "user"+rand+"First", "user"+rand+"Last");
		
		
		Random rand1 = new Random();
		Random rand2 = new Random();
		int low = 2;
		int high = 5;
		int roleRand1 = rand1.nextInt(high-low) + low;
		int roleRand2 = rand2.nextInt(high-low) + low;
		
		Role roleEditor = new Role(roleRand1);
		Role roleAssistant = new Role(roleRand2);
		
		testUser.addRole(roleEditor);
		testUser.addRole(roleAssistant);
		
		
		User savedUser = repo.save(testUser);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	
	
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user->System.out.println(user));
		
	}
	
	@Test
	public void testGetUserbyId() {
		User user1 = repo.findById(1).get();
		assertThat(user1).isNotNull();
		
	}
	
	@Test
	public void testUpdateUserDetails() {
		int rand = random.nextInt(10000);
		
		User user1 = repo.findById(1).get();
		user1.setEnabled(true);
		user1.setEmail(rand + "user1@useremail.com");
		
		
		
	}
	
	@Test
	public void testUpdateUserRoles() {
//		Iterable<User> users = repo.findAll();
//		List<Integer> ids = new ArrayList<>();
//		
//		for(User user: users) {
//			ids.add(user.getId());
//			
//		}
//		int randomId = (int)(Math.random() * ids.size());
//		
//		
//		
//		User user = repo.findById(randomId).get();
//		
//		Set<Role> roles = user.getRoles();
//		List<String> rolesList = new ArrayList<>();
//		
//		for(Role role: roles) {
//			rolesList.add(role.getName());
//		}
//		System.out.println("===================");
//		System.out.println(user.getRoles().toString());
//		System.out.println(user.getRoles().getClass());
//		
//		for(String role: rolesList) {
//		System.out.println(role);
//		}
//		String chosenRole =rolesList.get(0);
//		int chosenRoleId = 0;
//		int newRole 
//		
//		Iterable<Role> allRoles = roleRepo.findAll();
//		for(Role role: allRoles) {
//			if(role.getName().equals(chosenRole)) {
//				chosenRoleId = role.getId();
//			}
//			
//		}
//		
//		
//		chosenRole = new Role(chosenRoleId);
//
//		user.addRole(roleEditor);
//		user.getRoles().remove(roleEditor);
//		user.addRole(roleSalesperson);
//		
//		repo.save(user);
	}
	
	@Test
	public void testDeleteUser() {
		
		
		Iterable<User> users = repo.findAll();
		List<Integer> ids = new ArrayList<>();
		
		for(User user: users) {
			ids.add(user.getId());
			
		}
		int randomId = 0;
		int count = 0;
		while(randomId ==0 || randomId ==1) {
			
			randomId = (int)(Math.random() * ids.size());
			if(randomId != 1 && randomId != 0) break;
			if(count >= 5) break;
			count++;
		}

		
		User user = repo.findById(randomId).get();
		repo.deleteById(user.getId());
		
		
	}
	
}





















