package com.example.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import com.example.entity.User;
import com.example.repository.UserRespority;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRespority userrespority;
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public User saveUser(User user,String url) {
		
	String password=passwordEncoder.encode(user.getPassword());
		
	user.setRole("ROLE_USER");
	user.setPassword(password);
	
	user.setEnable(false);
	user.setVarificationcode(UUID.randomUUID().toString());
	
		User newuser=userrespority.save(user);
		if(newuser!=null)
		{
			
			sendEmail(newuser,url);
		}
		
		return newuser;
	}

	
	


	@Override
	public void sendEmail(User user, String url) {
		String from="ravinchakrawarti@gamil.com";
		String to=user.getEmail();
		String subject="Account Verification";
		String content="Dear [[name]],<br>"+"Please click the link below to verify your registraion:<br>"
		               +"<br> <a href=\"[[URL]]\"target=\"_self\">VERIFY</a></h3>"+"  thank you,<br>"+"Becoder";
		
		
		
			
	try {
			MimeMessage message=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message);
	        
			helper.setFrom(from,"Becoder");
			helper.setTo(to);
			helper.setSubject(subject);
			
			content=content.replace("[[name]]", user.getName());
			String siteUrl=url+"/verify?code="+user.getVarificationcode();
			
			content=content.replace("[[URL]]", siteUrl);
			
			helper.setText(content,true);
			
			mailSender.send(message);
			
			
	}
	catch(Exception e){
		e.printStackTrace();
		
		
	}
		
	}





	@Override
	public boolean verifyAccount(String verificationCode) {
		User user=userrespority.findByVarificationcode(verificationCode);
		if(user==null)
		{
			return false;
		}
		else {
			
			user.setEnable(true);
			user.setVarificationcode(null);
			userrespority.save(user);
			
		}
		return true;
	}





	@Override
	public void removeSessionMessage() {
		
		HttpSession session=((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		
		
		session.removeAttribute("msg");
	}
	
	

}
