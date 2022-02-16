package com.shopme.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


// RestController annotation is same as adding Controller and ResponseBody annotations
@RestController
public class UserRestController {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/users/check_email")
	public String checkDuplicateEmail(@Param("email") String email) {
		
		return service.isEmailUnique(email)? "OK" : "Duplicated";
		
		
		
	}
	
	

}
