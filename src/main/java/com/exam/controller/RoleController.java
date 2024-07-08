//package com.exam.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.exam.model.Role;
//import com.exam.entity.Trainee;
//import com.exam.services.RoleService;
//
//@RestController
//@RequestMapping("/user")
//@CrossOrigin("*")
//public class RoleController {
//	
//		@Autowired
//		RoleService roleService;
//	
//	    @GetMapping("/{name}")
//	    public Role getTrainee(@PathVariable("name") String name)
//	    {
//	    	System.out.println("get single Role");
//	        return this.roleService.findByRoleName(name);
//	    }
//
//}
