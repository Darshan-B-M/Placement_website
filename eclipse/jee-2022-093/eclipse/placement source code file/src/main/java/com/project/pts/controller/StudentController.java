package com.project.pts.controller;
    
import com.project.pts.CommonFuns;
import com.project.pts.LoginTypes;

import com.project.pts.entity.*;
import com.project.pts.repository.*; 
import com.project.pts.service.UserloginsService;
 
import jakarta.servlet.http.HttpServletRequest; 
import java.sql.Date;
import java.util.ArrayList;  
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired; 
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
public class StudentController { 
	
 
	 
	@Autowired 
	private UserloginsService studentService;  
	
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
	private JobpostRepository campusdrives;
	
	@Autowired
	private TrainingRepository trainings;
	
	@Autowired
	private TrainingacademicyearRepository tacademicyear;
	
	@Autowired
	private TrainingbranchRepository tbranch;
	
	@Autowired
	private ContactusRepository contactuses;

	@Autowired   private JobpostapplyRepository japply;   
	@Autowired   private JobpostbranchRepository jbranch;    
	@Autowired   private JobpostplacedRepository jplaced;   
	@Autowired   private JobpostplacementroundsRepository jplacementrounds;   
	@Autowired   private JobpostacademicyearRepository jacademicyear;   
	@Autowired   private JobpostskillRepository jskill;  
	
	
	@GetMapping("/")
    public String index(Authentication auth)
    { 
		   
    		return "index";
    }
	
	@GetMapping("/faq")
    public String faq()
    { 
		   
    		return "faq";
    }
	
	
	@GetMapping("/about")
    public String about()
    { 
		   
    		return "about";
    }
	
	
	@GetMapping("/contact")
    public String contact()
    { 
		   
    		return "contact";
    }
	
	
	@GetMapping("/student-register")
    public String studentregister()
    { 
		   
    		return "studentregister";
    }
	
	@GetMapping("/company-register")
    public String companyregister()
    { 
		   
    		return "companyregister";
    }
	
	 @GetMapping("/login")
	    public String login(Authentication auth){  
		//adminService.changeUserPassword(adminService.findByEmail("admin@placement.com"), "Test@123");
		   if(auth!=null)
		    if(auth.isAuthenticated())
		    {
		    	if(!auth.getAuthorities().isEmpty())
		    	{
		    		for(LoginTypes l:LoginTypes.values())
		    		if(auth.getAuthorities().contains(new SimpleGrantedAuthority(l.toString())))
		              	return "redirect:/"+l.toString().toLowerCase()+"/dashboard"; 
		    	}
		    }
	        return "login";
	    }  
    
	  @PostMapping("/savecontactus")
	    public @ResponseBody Map<String,Object>  savecontactus(HttpServletRequest request)
		{
	    	Map<String,Object> arr=new HashMap<String,Object>();
			arr.put("success",0);
			String name=request.getParameter("name");
			String email=request.getParameter("email"); 
			String message=request.getParameter("message");
			
			List<String> errors=new ArrayList<String>();
			if(name.trim()=="" || name==null) 	errors.add("Name");  
 
	    	if(message==null) 	errors.add("Message"); 
	    	else if(message.trim().length()<5 ) errors.add("Message with not less than 5 letters");
			
	    	if(errors.size()==0)
	    	{
	    		Contactus cobj=new Contactus();
	    		cobj.setName(name);
	    		cobj.setEmail(email); 
	    		cobj.setMessage(message);
	    		cobj.setIpaddress(request.getRemoteAddr());
	    		contactuses.save(cobj);
	    		arr.put("message","Send successfully"); 
	    		arr.put("success",1);
	    	}
	    	else
				arr.put("message", "Following fields errors : "+errors.stream().collect(Collectors.joining(", ")));
	    	
			return arr;
		}
	
 
	
	 @PostMapping("/registeruser")
	    @ResponseBody
	    public Map<String,Object> farmerregisterdb(HttpServletRequest request)
	    {
	    	Map<String,Object> arr=new HashMap<>();
	    	arr.put("success", 0); 
	   	    String password=request.getParameter("password");   
	   	    String contact=request.getParameter("contact");
		    String fullname=request.getParameter("name"); 
	    	String email=request.getParameter("email"); 
	    	String type=request.getParameter("type"); 
		    List<String> errors=new ArrayList<String>(); 
		    
		    if(!studentService.validation_Password(password))   errors.add("A valid Password");  
		    
		    if(contact.trim()=="" || contact==null) 
		    	errors.add("Contact");
		    else
		    	if(users.findByContact(contact)!=null)
		    		 errors.add("Contact exists!");
		    
		    if(email.trim()=="" || email==null) 
		    	errors.add("Email");
		    else
		    	if(users.findByEmail(email)!=null)
		    		 errors.add("Email exists!");
	    		
		    if(fullname.trim()=="" || fullname==null) errors.add("Full name");
		    
		    if(errors.size()==0)
		    {
		    	User obj=new User(); 
		        obj.setEmail(email);
		        obj.setContact(contact); 
		        obj.setName(fullname);
		        obj.setType(type.toUpperCase());
		        obj.setPassword("Test@123");
		        obj.setStatus(0);
		    	users.save(obj);
		    	studentService.changeUserPassword(obj, password);
		    	arr.put("success", 1);
		        arr.put("message"," Registered successfully!<br>You can login once Admin activate your login.");
		    }
		    else
		        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining(", ")));
		    	
		    return   arr; 	
	    }
	 
    @GetMapping("/student")
    public String user(Model model) {  
        return "redirect:student/dashboard";
    } 
    
     
 
    @GetMapping("/student/dashboard") 
    public String dashboard(Model model){
        //model.addAttribute("users", users.findByTypeOrderByIdDesc(LoginTypes.EMPLOYEE.toString()));
        return "student/index";
    } 
   
     
    
    @GetMapping("/student/changepassword") 
    public String changepassword(Model model){ 
        return "student/changepassword";
    } 
     
    
    @PostMapping("/student/updatepassword")
    public String updatepassword(HttpServletRequest request,Model m)
    {
    	String password=request.getParameter("password");
    	String newpassword=request.getParameter("newpassword");
    	String confirmpassword=request.getParameter("confirmpassword");
    	
    	User user = studentService.findByEmail(
    		      SecurityContextHolder.getContext().getAuthentication().getName());
    		    if(newpassword.compareTo(confirmpassword)>0)
    		    {
    		    	m.addAttribute("updatepassword_errors", "New Password and Confirm Password Field do not match  !!");
    		    }
    		    else if (!studentService.checkIfValidOldPassword(user, password)) {
    		        m.addAttribute("updatepassword_errors", "Inavlid Current Password");
    		    }
    		    else if(!studentService.validation_Password(newpassword))
    		    {
    		    	  m.addAttribute("updatepassword_errors", "Inavlid New Password.Please follow specified policies.");
    		    }
    		    else
    		    {
    		    	studentService.changeUserPassword(user, newpassword);
    		      m.addAttribute("updatepassword_success", "Password updated successfully");
    		    }
    	return changepassword(m);
    } 
    
    
    @GetMapping("/student/profile") 
    public String profile(Model model){
    	User obj = studentService.findByEmail(
  		      SecurityContextHolder.getContext().getAuthentication().getName());
    	model.addAttribute("obj", obj);
    	model.addAttribute("courses", courses.findAll());
    	model.addAttribute("academicyear", academicyear.findAll());
    	Student cobj=students.findByUser(obj.getId());
    	model.addAttribute("sobj",cobj==null?new Student():cobj ); 
        return "student/profile";
    } 
    
    @PostMapping("/student/updateprofile")
    @ResponseBody
    public Map<String,Object> updateprofile(HttpServletRequest request)
    {
    	Map<String,Object> arr=new HashMap<>();
    	arr.put("success", 0);  
   	    String email=request.getParameter("email");
	    String fullname=request.getParameter("fullname");     
	    String contact=request.getParameter("contact");
	    
	    String about=request.getParameter("about");
	    String address=request.getParameter("address");
	    String joiningyear=request.getParameter("joiningyear");
	    String regno=request.getParameter("regno");
	    String course=request.getParameter("course");
	    String branch=request.getParameter("branch");
	    String academicyear=request.getParameter("academicyear");
	    String score=request.getParameter("score"); 
	    String dob=request.getParameter("dob"); 
	    
	    List<String> errors=new ArrayList<String>();
	    
	    User user = studentService.findByEmail(
  		      SecurityContextHolder.getContext().getAuthentication().getName());
	    
	    
	    if(fullname.isBlank()) errors.add("Full name");  
	    if(email.isBlank()) errors.add("Email");  
	    if(contact.isBlank()) errors.add("Contact"); 
	    
	    if(about.isBlank()) errors.add("About"); 
	    if(address.isBlank()) errors.add("Address"); 
	    if(joiningyear.isBlank()) errors.add("Joining year"); 
	    if(regno.isBlank()) errors.add("Regno"); 
	    if(course.isBlank()) errors.add("Course"); 
	    if(branch.isBlank()) errors.add("Branch"); 
	    if(academicyear.isBlank()) errors.add("Academic year"); 
	    if(score.isBlank()) errors.add("Score"); 
	    if(dob.isBlank()) errors.add("Birth Date"); 
	    
	    User cobj; 
	    
	    cobj=users.findByEmail(email);
	    if(cobj!=null)
	    	if(cobj.getId()!=user.getId()) errors.add("Email already Exists!");
	    
	     
	    
	    cobj=users.findByContact(contact);
	    if(cobj!=null)
	    	if(cobj.getId()!=user.getId()) errors.add("Contact already Exists!");
	    
	    Student eobj=students.findByRegno(regno);
	    if(eobj!=null)
	    	if(eobj.getUser()!=cobj.getId())
                         errors.add("Regno already Exists!");
	    
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
	         eobj=students.findByUser(user.getId());
	    	if(eobj==null) eobj=new Student();
	    	eobj.setUser(user.getId());
	    	eobj.setAboutme(about); 
	    	eobj.setAddress(address);
	    	eobj.setAvgscore(CommonFuns.cfloat(score));
	    	eobj.setAyear(CommonFuns.cint(academicyear));
	    	eobj.setBranch(CommonFuns.cint(branch));
	    	eobj.setCourse(CommonFuns.cint(course));
	        eobj.setDob(Date.valueOf(dob));
	        eobj.setJoiningyear(CommonFuns.cint(joiningyear));
	        eobj.setRegno(regno);
	    	students.save(eobj);
	    }
	    else
	        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining(", ")));
	    	
	    return   arr; 	
    }
     
    
    @GetMapping("/student/loadbranches/{id}")
    @ResponseBody
    public Map<String,Object> loadbranches(@PathVariable("id") int id)
    {
    	Map<String,Object> arr=new HashMap<>();
    	arr.put("success", 1);  
    	arr.put("data",branches.findByCourse(id));
    	return arr;
    }
     
	
    @GetMapping("/student/campusdrives") 
    public String campusdrives(Model model){  
        User user = studentService.findByEmail(
	  		      SecurityContextHolder.getContext().getAuthentication().getName());
Student eobj=students.findByUser(user.getId());
	if(eobj==null)
		 return "student/noprofile";
	else
	{
		 int branch=eobj.getBranch();
         int ayear=eobj.getAyear(); 
         float avgscrore=eobj.getAvgscore(); 
         
         List<Jobpostacademicyear> tac=jacademicyear.findByAcademicyear(ayear);
         List<Jobpostbranch>  tb=jbranch.findByBranch(branch);
         List<Integer> t1=new ArrayList<Integer>();
         for(Jobpostacademicyear t:tac)
       	  t1.add(t.getJobpost());
         
         List<Integer> t2=new ArrayList<Integer>();
         for(Jobpostbranch t:tb)
       	  t2.add(t.getJobpost());
         
         List<Integer> intersect = t1.stream()
       		    .filter(t1::contains)
       		    .collect(Collectors.toList());
         
         
         List<Jobpost> jobposts=campusdrives.findByStatusAndIdIn(1,intersect);  
         model.addAttribute("jobposts" ,new ArrayList<Hashtable<String,Object>>());
 		Hashtable<String,Object> obj=new Hashtable<String,Object>();
         for(Jobpost j:jobposts)
         {
        	 obj=new Hashtable<String,Object>(); 
        	 if(avgscrore>=j.getRequiredscore())
        	 {
        	 Jobpostapply jp=japply.findByStudentAndJobpost(user.getId(),j.getId());
        	    obj.put("applied", jp==null?"":jp.getDate_added());
        	    obj.put("result", jp==null?"":(jp.getStatus()==1?"Selected":""));
	        	obj.put("id",j.getId());
	        	obj.put("jobtitle", j.getJobtitle());  
	        	obj.put("fromdate", j.getFromdate());  
	        	obj.put("todate", j.getTodate());
	        	obj.put("company",users.findById(j.getCompany()).getName()); 
        	 ((List<Hashtable<String,Object>>)model.getAttribute("jobposts")).add(obj);
        	 }
         } 
        return "student/campusdrives";
	}
    } 
    
    @GetMapping("/student/trainings") 
    public String trainings(Model model){  
       User user = studentService.findByEmail(
    	  		      SecurityContextHolder.getContext().getAuthentication().getName());
      Student eobj=students.findByUser(user.getId());
     	if(eobj==null)
     		 return "student/noprofile";
     	else
     	{
          int branch=eobj.getBranch();
          int ayear=eobj.getAyear(); 
          List<Trainingacademicyear> tac=tacademicyear.findByAcademicyear(ayear);
          List<Trainingbranch>  tb=tbranch.findByBranch(branch);
          List<Integer> t1=new ArrayList<Integer>();
          for(Trainingacademicyear t:tac)
        	  t1.add(t.getTraining());
          
          List<Integer> t2=new ArrayList<Integer>();
          for(Trainingbranch t:tb)
        	  t2.add(t.getTraining());
          
          List<Integer> intersect = t1.stream()
        		    .filter(t1::contains)
        		    .collect(Collectors.toList());
          
          
          List<Training> ptrainings=trainings.findByStatusAndIdIn(1,intersect);
          model.addAttribute("trainings", ptrainings);
          return "student/trainings";
     	}
    } 
    
    @GetMapping("/student/viewtraining/{id}") 
    public String newtraining(@PathVariable("id")int id,Model model){ 
    	model.addAttribute("tobj" ,trainings.findById(id));
    	   List<String> ayear=new ArrayList<String>();
    	
    	  for(Trainingacademicyear ta: tacademicyear.findByTraining(id))
                   ayear.add(academicyear.findById(ta.getAcademicyear()).getName());
    	model.addAttribute("academicyear", ayear);
    	
    	 List<String> tb=new ArrayList<String>();
     	
   	  for(Trainingbranch ta: tbranch.findByTraining(id))
   	  {
   		          Branch br=branches.findById(ta.getBranch()); 
   		       tb.add(br.getName()+" - "+courses.findById(br.getCourse()).getName());
   	  }
     	model.addAttribute("academicyear", ayear);
     	model.addAttribute("branch", tb);
    	return "student/viewtraining";
    } 
    
    
    @GetMapping("/student/viewjobpost/{id}") 
    public String viewjobpost(@PathVariable("id")int id,Model model){ 
    	 User user = studentService.findByEmail(
	  		      SecurityContextHolder.getContext().getAuthentication().getName());
    	Student eobj=students.findByUser(user.getId());
    	if(eobj==null)
    		 return "student/noprofile";
    	else
    	{ 
    	model.addAttribute("tobj" ,campusdrives.findById(id));
    	   List<String> ayear=new ArrayList<String>();
    	
    	  for(Jobpostacademicyear ta: jacademicyear.findByJobpost(id))
                   ayear.add(academicyear.findById(ta.getAcademicyear()).getName());
    	model.addAttribute("academicyear", ayear);
    	
    	 List<String> tb=new ArrayList<String>();
     	
   	  for(Jobpostbranch  ta: jbranch.findByJobpost(id))
   	  {
   		          Branch br=branches.findById(ta.getBranch()); 
   		       tb.add(br.getName()+" - "+courses.findById(br.getCourse()).getName());
   	  }
   	  
   	List<String> ts=new ArrayList<String>();
 	
 	  for(Jobpostskill  ta: jskill.findByJobpost(id))
 	  {
 		          Skill br=skills.findById(ta.getSkill()); 
 		       ts.add(br.getName());
 	  }
   	  
 	  
 	 List<String> tr=new ArrayList<String>();
  	
	  for(Jobpostplacementrounds  ta: jplacementrounds.findByJobpost(id))
	  {
		  Placementrounds br=rounds.findById(ta.getPlacementround()); 
		       tr.add(br.getName());
	  }
	    Jobpostapply jp=japply.findByStudentAndJobpost(user.getId(),id);
	    model.addAttribute("applied", jp==null?"":jp.getDate_added());
 	  
     	model.addAttribute("academicyear", ayear);
     	model.addAttribute("branch", tb);
    	model.addAttribute("skill", ts);
    	model.addAttribute("placementrounds", tr);
    	return "student/viewjobpost";
    	}
    	
    } 
    
    
    @PostMapping("/student/applyjobpost")
    @ResponseBody
    public Map<String,Object> applyjobpost(HttpServletRequest request)
    {
    	Map<String,Object> arr=new HashMap<>();
    	arr.put("success", 0);  
    	int id=CommonFuns.cint(request.getParameter("id")); 
       	 User user = studentService.findByEmail(
   	  		      SecurityContextHolder.getContext().getAuthentication().getName());
       	Student eobj=students.findByUser(user.getId());
       	if(eobj==null)
       		arr.put("message", "Please Update your profile!");	
       	else
       	{
       		int branch=eobj.getBranch();
            int ayear=eobj.getAyear(); 
            float avgscrore=eobj.getAvgscore(); 
            Jobpostacademicyear ta=jacademicyear.findByJobpostAndAcademicyear(id,ayear );
            if(ta==null)
           		arr.put("message", "Invalid Application!");	
            else
            {
            	Jobpostbranch  tb=jbranch.findByJobpostAndBranch(id,branch);
            	if(tb==null)
               		arr.put("message", "Invalid Application!");	
            	else
            	{ 
            		Jobpost tobj=campusdrives.findById(id);
            	    if(tobj.getRequiredscore()<=avgscrore)
            	    { 
            	    	 Jobpostapply jp=japply.findByStudentAndJobpost(user.getId(),id);
            	    	 if(jp==null)
            	    	 {
            	    		 jp=new  Jobpostapply();
            	    		 jp.setJobpost(id);
            	    		 jp.setStudent(user.getId());
            	    		 jp.setStatus(0);
            	             japply.save(jp);
            	             arr.put("success", 1);
            	             arr.put("message","Applied Successfully");
            	    	 }
            	    }
            	    else
            	    	arr.put("message", "Invalid Application!");	
            	}
            }
       	}
    	return arr;
    }
    
}