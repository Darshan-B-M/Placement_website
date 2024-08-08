package com.project.pts.controller;
   
import com.project.pts.LoginTypes;

import com.project.pts.entity.*;
import com.project.pts.repository.*; 
import com.project.pts.service.UserloginsService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;  
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody; 

@Controller
public class CompanyController { 
	
 
	 
	@Autowired 
	private UserloginsService companyService;  
	
	@Autowired 
	private UserRepository users;
	 
	@Autowired
	private StudentRepository students;
	   
	@Autowired
	private CompanyRepository companies;
	
	@Autowired
	private CourseRepository courses;
	
	@Autowired
	private BranchRepository branches;
	
	@Autowired
	private AcademicyearRepository academicyear;
	
	@Autowired
	private SkillRepository skills;
	
	@Autowired
	private PlacementroundsRepository rounds;
	
	@Autowired   
	private JobpostplacementroundsRepository jplacementrounds;   
	
	@Autowired   
	private JobpostapplyRepository japply;   
	
	@Autowired   
	private JobpostplacedRepository jplaced; 	
     
	@Autowired   
	private StudentfinalstatusRepository sfstatus; 	
	
    @GetMapping("/company")
    public String user(Model model) {  
        return "redirect:/company/campusdrives";
    } 
    
     
 
    @GetMapping("/company/dashboard") 
    public String dashboard(Model model){
        //model.addAttribute("users", users.findByTypeOrderByIdDesc(LoginTypes.EMPLOYEE.toString()));
    	  return "redirect:/company/campusdrives";
    } 
   
     
    
    @GetMapping("/company/changepassword") 
    public String changepassword(Model model){ 
        return "company/changepassword";
    } 
     
    
    @PostMapping("/company/updatepassword")
    public String updatepassword(HttpServletRequest request,Model m)
    {
    	String password=request.getParameter("password");
    	String newpassword=request.getParameter("newpassword");
    	String confirmpassword=request.getParameter("confirmpassword");
    	
    	User user = companyService.findByEmail(
    		      SecurityContextHolder.getContext().getAuthentication().getName());
    		    if(newpassword.compareTo(confirmpassword)>0)
    		    {
    		    	m.addAttribute("updatepassword_errors", "New Password and Confirm Password Field do not match  !!");
    		    }
    		    else if (!companyService.checkIfValidOldPassword(user, password)) {
    		        m.addAttribute("updatepassword_errors", "Inavlid Current Password");
    		    }
    		    else if(!companyService.validation_Password(newpassword))
    		    {
    		    	  m.addAttribute("updatepassword_errors", "Inavlid New Password.Please follow specified policies.");
    		    }
    		    else
    		    {
    		    	companyService.changeUserPassword(user, newpassword);
    		      m.addAttribute("updatepassword_success", "Password updated successfully");
    		    }
    	return changepassword(m);
    } 
    
    
    @GetMapping("/company/profile") 
    public String profile(Model model){
    	User obj = companyService.findByEmail(
  		      SecurityContextHolder.getContext().getAuthentication().getName());
    	model.addAttribute("obj", obj);
    	Company cobj=companies.findByUser(obj.getId());
    	model.addAttribute("cobj",cobj==null?new Company():cobj ); 
        return "company/profile";
    } 
    
    @PostMapping("/company/updateprofile")
    @ResponseBody
    public Map<String,Object> updateprofile(HttpServletRequest request)
    {
    	Map<String,Object> arr=new HashMap<>();
    	arr.put("success", 0);  
   	    String email=request.getParameter("email");
	    String fullname=request.getParameter("fullname");     
	    String contact=request.getParameter("contact");
	    
	    String about=request.getParameter("about");
	    String startedyear=request.getParameter("startedyear");
	    String recruitmenthead=request.getParameter("recruitmenthead");
	    String website=request.getParameter("website");
	    
	    List<String> errors=new ArrayList<String>();
	    
	    User user = companyService.findByEmail(
  		      SecurityContextHolder.getContext().getAuthentication().getName());
	    
	    
	    if(fullname.isBlank()) errors.add("Full name");  
	    if(email.isBlank()) errors.add("Email");  
	    if(contact.isBlank()) errors.add("Contact"); 
	    
	    if(about.isBlank()) errors.add("About"); 
	    if(startedyear.isBlank()) errors.add("Started year"); 
	    if(recruitmenthead.isBlank()) errors.add("Recruitment head"); 
	    if(website.isBlank()) errors.add("Website"); 
	    
	    User cobj; 
	    
	    cobj=users.findByEmail(email);
	    if(cobj!=null)
	    	if(cobj.getId()!=user.getId()) errors.add("Email already Exists!");
	    
	     
	    
	    cobj=users.findByContact(contact);
	    if(cobj!=null)
	    	if(cobj.getId()!=user.getId()) errors.add("Contact already Exists!");
	    
	    if(errors.size()==0)
	    { 
	    	arr.put("success", 1);
	        arr.put("message","Saved!"); 
	    	if(user.getEmail().compareTo(email)!=0)
	    	{
	    		arr.put("success", 2);
		        arr.put("message","Saved!,as username changed please login again"); 
	    	}
	    	user.setName(fullname); 
	    	user.setEmail(email);
	    	user.setContact(contact); 
	    	users.save(user); 
	    	Company eobj=companies.findByUser(user.getId());
	    	if(eobj==null) eobj=new Company();
	    	eobj.setUser(user.getId());
	    	eobj.setAbout(about);
	    	eobj.setWebsite(website);
	    	eobj.setStartedyear(startedyear);
	    	eobj.setRecruitmenthead(recruitmenthead);
	    	companies.save(eobj);
	    }
	    else
	        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining(", ")));
	    	
	    return   arr; 	
    }
     
    
    
    
    
    @GetMapping("/company/campusdrives") 
    public String campusdrives(Model model){  
        return "company/campusdrives";
    } 
    
    
    @GetMapping("/company/newcampusdrive") 
    public String newcampusdrive(Model model){
    	model.addAttribute("courses" ,new ArrayList<Hashtable<String,Object>>()); 
    	model.addAttribute("academicyear" ,academicyear.findAll()); 
    	model.addAttribute("skills" ,skills.findAll()); 
    	model.addAttribute("rounds" ,rounds.findAll()); 
		Hashtable<String,Object> obj=new Hashtable<String,Object>();
       for(Course c:courses.findAll())
       {
    	  obj=new Hashtable<String,Object>();
    	  obj.put("id", c.getId());
    	  obj.put("name", c.getName());
    	  List<Branch> br= branches.findByCourse(c.getId());
    	  obj.put("branches",br);    	
    	  if(br.size()>0)((List<Hashtable<String,Object>>)model.getAttribute("courses")).add(obj);
       }
    	return "company/newcampusdrive";
    } 
    
    
    @GetMapping("/company/editcampusdrive/{id}") 
    public String newcampusdrive(@PathVariable("id")int id,Model model){
    	model.addAttribute("courses" ,new ArrayList<Hashtable<String,Object>>()); 
    	model.addAttribute("academicyear" ,academicyear.findAll());  
    	model.addAttribute("skills" ,skills.findAll()); 
    	model.addAttribute("rounds" ,rounds.findAll()); 
    	model.addAttribute("id" ,id); 
		Hashtable<String,Object> obj=new Hashtable<String,Object>();
       for(Course c:courses.findAll())
       {
    	  obj=new Hashtable<String,Object>();
    	  obj.put("id", c.getId());
    	  obj.put("name", c.getName());
    	  List<Branch> br= branches.findByCourse(c.getId());
    	  obj.put("branches",br);    	
    	  if(br.size()>0)((List<Hashtable<String,Object>>)model.getAttribute("courses")).add(obj);
       }
    	return "company/editcampusdrive";
    }
    
    @GetMapping("/company/campusselections/{id}") 
    public String campusselections(@PathVariable("id")int id,Model model){ 
    	model.addAttribute("id" ,id); 
    	Hashtable<String,Object> obj=new Hashtable<String,Object>();
    	model.addAttribute("rounds" ,new ArrayList<Hashtable<String,Object>>());
    	model.addAttribute("applications" ,new ArrayList<Hashtable<String,Object>>());
    	
    	
    	for(Jobpostplacementrounds j:jplacementrounds.findByJobpost(id))
    	{
    		 obj=new Hashtable<String,Object>();
       	     obj.put("id", j.getPlacementround());
       	     obj.put("round", rounds.findById(j.getPlacementround()).getName());
       	     
    		((List<Hashtable<String,Object>>)model.getAttribute("rounds")).add(obj);
    	}
    	
    	for(Jobpostapply j:japply.findByJobpost(id))
    	{
    		 obj=new Hashtable<String,Object>();
       	     obj.put("id", j.getId());
       	     obj.put("name", users.findById(j.getStudent()).getName());
       	     obj.put("score", students.findByUser(j.getStudent()).getAvgscore());
       	     //obj.put("placed", jplaced.findByJobpostapply(j.getId()));
    		((List<Hashtable<String,Object>>)model.getAttribute("applications")).add(obj);
    	}
    	return "company/campusselections";
    }
    
}