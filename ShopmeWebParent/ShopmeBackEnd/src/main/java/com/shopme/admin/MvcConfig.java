package com.shopme.admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String dirName = "user-photos";
		Path userPhotosDir = Paths.get(dirName);

		String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();

		// ** makes all files in this dir available to web-client
		String os = System.getProperty("os.name");

//		if (os.equalsIgnoreCase("Linux")) {
//			registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:" + userPhotosPath + "/");
//		} else {
//			registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + userPhotosPath + "/");
//		}
		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:" + userPhotosPath + "/");
	}


}
