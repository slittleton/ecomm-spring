package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Service
@Transactional
public class UserService {

	public static final int USERS_PER_PAGE = 4;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> listAll() {
		return (List<User>) userRepo.findAll();

	}

	public List<Role> listRoles() {

		return (List<Role>) roleRepo.findAll();
	}

	// --- Pagination ---
	public Page<User> listByPage(int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE);

		return userRepo.findAll(pageable);
	}

	// --- Save User ---
	public User save(User user) {
		boolean isUpdatingUser = (user.getId() != null);

		// if user id exists then get user info so it can be updated
		if (isUpdatingUser) {
			User existingUser = userRepo.findById(user.getId()).get();

			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				encodePassword(user);
			}

		} else {

			encodePassword(user);
		}

		return userRepo.save(user);

	}

	// --- encodePassword ---
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}

	// --- isEmailUnique ---
	public boolean isEmailUnique(Integer id, String email) {

		User userByEmail = userRepo.getUserByEmail(email);

		if (userByEmail == null) {
			return true;
		}

		// if no id for that email but email exists then it has already been used by
		// another user
		boolean isCreatingNew = (id == null);

		if (isCreatingNew) {
			if (userByEmail != null)
				return false;
		} else {
			// if user id does not match expected user id then email is not unique
			if (userByEmail.getId() != id) {
				return false;
			}
		}

		// otherwise email is unique
		return true;
	}

	// --- Get User ID ---
	public User get(Integer id) throws UserNotFoundException {

		try {
			// returns an Optional<T> so you need to use .get() at the end
			return userRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}

	}

	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any user with ID " + id);

		}

		userRepo.deleteById(id);

	}

	public void updateUserEnabledStatus(Integer id, boolean enabled) {
		userRepo.updateEnabledStatus(id, enabled);

	}

}
