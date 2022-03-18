package com.shopme.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public class UtilsTest {
	@Autowired
	private TestEntityManager entityManager;
	
	
	@Test
	public void getOperatingSystem() {
	    String os = System.getProperty("os.name");
	     System.out.println("Using System Property: " + os);
	     
	     
	     
//	    return os;
	}
	


}
