package com.project.pts.controller;
   
import com.project.pts.LoginTypes;
import com.project.pts.SFStatus;
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
import org.springframework.security.core.context.SecurityContextHolder;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody; 

@Controller
public class AdminController { 
	
 
	 
	@Autowired 
	private UserloginsService adminService;  
	
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
	
	 @GetMapping("/admin") 
	    public String index(Model model){
	        //model.addAttribute("users", users.findByTypeOrderByIdDesc(LoginTypes.EMPLOYEE.toString()));
	        return "redirect:/admin/dashboard";
	    } 
	    
	
    @GetMapping("/admin/dashboard") 
    public String dashboard(Model model){
        //model.addAttribute("users", users.findByTypeOrderByIdDesc(LoginTypes.EMPLOYEE.toString()));
        return "admin/index";
    } 
    
    @GetMapping("/admin/courses") 
    public String courses(Model model){ 
        return "admin/courses";
    } 
     
    @GetMapping("/admin/branches") 
    public String branches(Model model){ 
        return "admin/branches";
    } 
    
    @GetMapping("/admin/skills") 
    public String skills(Model model){ 
        return "admin/skills";
    } 
    
    @GetMapping("/admin/placementrounds") 
    public String placementrounds(Model model){ 
        return "admin/placementrounds";
    } 
    
    @GetMapping("/admin/students") 
    public String employees(Model model){ 
    	model.addAttribute("sfstatus", SFStatus.values());
        return "admin/students";
    }
    @GetMapping("/admin/contactedlist")
    public String contactedlist(Model model) {  
        return "admin/contactedlist";
    }  
    
    
    @GetMapping("/admin/changepassword") 
    public String changepassword(Model model){ 
        return "admin/changepassword";
    } 
     
    
    @PostMapping("/admin/updatepassword")
    public String updatepassword(HttpServletRequest request,Model m)
    {
    	String password=request.getParameter("password");
    	String newpassword=request.getParameter("newpassword");
    	String confirmpassword=request.getParameter("confirmpassword");
    	
    	User user = adminService.findByEmail(
    		      SecurityContextHolder.getContext().getAuthentication().getName());
    		    if(newpassword.compareTo(confirmpassword)>0)
    		    {
    		    	m.addAttribute("updatepassword_errors", "New Password and Confirm Password Field do not match  !!");
    		    }
    		    else if (!adminService.checkIfValidOldPassword(user, password)) {
    		        m.addAttribute("updatepassword_errors", "Inavlid Current Password");
    		    }
    		    else if(!adminService.validation_Password(newpassword))
    		    {
    		    	  m.addAttribute("updatepassword_errors", "Inavlid New Password.Please follow specified policies.");
    		    }
    		    else
    		    {
    		    	adminService.changeUserPassword(user, newpassword);
    		      m.addAttribute("updatepassword_success", "Password updated successfully");
    		    }
    	return changepassword(m);
    } 
    
    
    @GetMapping("/admin/profile") 
    public String profile(Model model){
    	User obj = adminService.findByEmail(
  		      SecurityContextHolder.getContext().getAuthentication().getName());
    	model.addAttribute("obj", obj); 
        return "admin/profile";
    } 
    
    @PostMapping("/admin/updateprofile")
    @ResponseBody
    public Map<String,Object> updateprofile(HttpServletRequest request)
    {
    	Map<String,Object> arr=new HashMap<>();
    	arr.put("success", 0);  
   	    String email=request.getParameter("email");
	    String fullname=request.getParameter("fullname");     
	    String contact=request.getParameter("contact");       
	    List<String> errors=new ArrayList<String>();
	    
	    User user = adminService.findByEmail(
  		      SecurityContextHolder.getContext().getAuthentication().getName());
	    
	    
	    if(fullname.isBlank()) errors.add("Full name");  
	    if(email.isBlank()) errors.add("Email");  
	    if(contact.isBlank()) errors.add("Contact"); 
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
	    }
	    else
	        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining(", ")));
	    	
	    return   arr; 	
    }
    
    @GetMapping("/admin/notices") 
    public String notices(Model model){ 
    	 model.addAttribute("types", LoginTypes.values());
        return "admin/notices";
    } 
    
    
    @GetMapping("/admin/companies") 
    public String companies(Model model){  
        return "admin/companies";
    } 
    
    
    @GetMapping("/admin/viewuser/{id}") 
    public String viewuser(@PathVariable("id")int id,Model model){  
    	
    	User uobj=users.findById(id);
    	model.addAttribute("uobj", uobj);
    	if(uobj.getType().compareTo(LoginTypes.COMPANY.toString())==0)
          model.addAttribute("eobj", companies.findByUser(id));
    	else
    	{
    	   Student eobj=students.findByUser(id);	
    	   model.addAttribute("eobj", eobj);
    	   model.addAttribute("course", eobj!=null?courses.findById(eobj.getCourse()).getName():"");
    	   model.addAttribute("branch", eobj!=null?branches.findById(eobj.getBranch()).getName():"");
    	   model.addAttribute("ayear", eobj!=null?academicyear.findById(eobj.getAyear()).getName():"");
    	   model.addAttribute("sfstatus",sfstatus.findByStudent(id));
    	}
        return "admin/viewuser";
    } 
    
    
    @GetMapping("/admin/academicyear") 
    public String academicyear(Model model){  
        return "admin/academicyear";
    } 
    
    @GetMapping("/admin/newtraining") 
    public String newtraining(Model model){
    	model.addAttribute("courses" ,new ArrayList<Hashtable<String,Object>>()); 
    	model.addAttribute("academicyear" ,academicyear.findAll()); 
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
    	return "admin/newtraining";
    } 
    
    
    @GetMapping("/admin/edittraining/{id}") 
    public String newtraining(@PathVariable("id")int id,Model model){
    	model.addAttribute("courses" ,new ArrayList<Hashtable<String,Object>>()); 
    	model.addAttribute("academicyear" ,academicyear.findAll()); 
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
    	return "admin/edittraining";
    } 
    
    @GetMapping("/admin/trainings") 
    public String trainings(Model model){  
        return "admin/trainings";
    } 
    
    @GetMapping("/admin/campusdrives") 
    public String campusdrives(Model model){  
        return "admin/campusdrives";
    } 
    
    
    @GetMapping("/admin/newcampusdrive") 
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
    	return "admin/newcampusdrive";
    } 
    
    
    @GetMapping("/admin/editcampusdrive/{id}") 
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
    	return "admin/editcampusdrive";
    }
    
    @GetMapping("/admin/campusselections/{id}") 
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
    	return "admin/campusselections";
    }
    
}