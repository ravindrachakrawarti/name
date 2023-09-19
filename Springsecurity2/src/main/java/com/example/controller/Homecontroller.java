package com.example.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.entity.User;
import com.example.repository.UserRespority;
import com.example.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class Homecontroller {
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private UserRespority userrespority;
	
	@ModelAttribute
	public void commonUser(Principal principal,Model model)
	{
		
  if(principal!=null)
  {
	    String email=principal.getName();
			
	    User user=userrespority.findByEmail(email);
			
		   model.addAttribute("user", user);
  }
	}
	
	
	@GetMapping("/")
	public String index()
  {
		
		return "home1";
	}
	
	
	@GetMapping("/Registration")
	public String Registration()
  {
		
		return "Registration";
	}
	
	
	@GetMapping("/signin")
	public String login()
  {
		
		return "login";
	}
	
	@GetMapping("/user/profile")
	public String profile(Principal principal,Model model)
  {
		
    String email=principal.getName();
		
    User user=userrespority.findByEmail(email);
		
	   model.addAttribute("user", user);	
    
    
		return "index";
	}
	
	
	
	
	@GetMapping("/user/home")
	public String home()
  {
		
		return "home";
	}
	
	@PostMapping("/saveuser")
	public String saveuser(@ModelAttribute User user, HttpSession session, HttpServletRequest request)
	{
		//System.out.println(user);
		
		String url=request.getRequestURL().toString();
		//System.out.println(url);
		url=url.replace(request.getServletPath(), "");
		//System.out.println(url);
		
		
		User u=userservice.saveUser(user,url);
		
	        if(u!=null)
		{
			session.setAttribute("msg", "Register successfully");
			
		}
		else
		{
			
			session.setAttribute("msg", "something wrong !!");
			
	 }
		
		
		return "redirect:/Registration";
	}
	
	@GetMapping("/verify")
	public String verifycount(@Param("code") String code,Model m)
	{
		
		boolean f=userservice.verifyAccount(code);
		if(f)
		{
			m.addAttribute("msg","Successfully your account is verified");
		}
		else {
		m.addAttribute("msg","may be Your verification code is incorrect or already verified");
		
		}
		return "Message";
	}
	
	@GetMapping("/category")
	public String category()
  {
		
		return "category";
	}
	@GetMapping("/contact")
	public String contact()
  {
		
		return "contact";
	}
	
	@GetMapping("/index")
	public String home1()
  {
		
		return "index";
	}
	@GetMapping("/clients")
	public String client()
  {
		
		return "clients";
	}
	@GetMapping("/products")
	public String products()
  {
		
		return "products";
	}
	
	
}
