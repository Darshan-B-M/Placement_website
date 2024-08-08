package com.project.pts.controller;
    
import com.project.pts.CommonFuns; 
import com.project.pts.LoginTypes;
import com.project.pts.SFStatus;
import com.project.pts.repository.*; 
import com.project.pts.entity.*;
import com.project.pts.service.UserloginsService;
 
import java.sql.Date; 
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest; 
 

@RestController
@RequestMapping(path="/admin/JSON", produces="application/json")
@CrossOrigin(origins="*")
public class AdminRestJsonResponse { 
	
	@Resource	private UserloginsService logins;    
	@Resource   private UserRepository users;  
	@Resource   private AcademicyearRepository academicyear;
	@Resource   private CourseRepository courses;   
	@Resource   private BranchRepository branches;   
	@Resource   private CompanyRepository companies;   
	@Resource   private JobpostapplyRepository japply;   
	@Resource   private JobpostbranchRepository jbranch;    
	@Resource   private JobpostplacedRepository jplaced;   
	@Resource   private JobpostplacementroundsRepository jplacementrounds;   
	@Resource   private JobpostacademicyearRepository jacademicyear;   
	@Resource   private JobpostRepository campusdrives;   
	@Resource   private JobpostskillRepository jskill;    
	@Resource   private NoticeRepository notices;   
	@Resource   private PlacementroundsRepository placementrounds;   
	@Resource   private SkillRepository skills;   
	@Resource   private StudentRepository students;    
	@Resource   private TrainingbranchRepository tbranch;   
	@Resource   private TrainingacademicyearRepository tacademicyear;   
	@Resource   private TrainingRepository trainings; 
    @Resource  private StudentfinalstatusRepository sfstatus;
	
	@Resource
	private ContactusRepository contactuses;
	
	@PersistenceContext
    private EntityManager em;
	
	@PostMapping("/dashboard")
	public Hashtable<String,Object>  dashboard(HttpServletRequest request)
	{
		Hashtable<String,Object> arr=new Hashtable<String,Object>();  
		arr.put("success", 1);
		 arr.put("course", courses.count());
		 arr.put("branch", branches.count());
		 arr.put("student", users.findByTypeOrderByIdDesc(LoginTypes.STUDENT.toString()).size()); 
		 arr.put("company", users.findByTypeOrderByIdDesc(LoginTypes.COMPANY.toString()).size());
		 arr.put("campusdrive", campusdrives.count());
		 return arr;
	}
	
	@PostMapping("/academicyear")
	public Hashtable<String,Object>  academicyear(HttpServletRequest request)
	{
		Hashtable<String,Object> arr=new Hashtable<String,Object>();
		Hashtable<String,Object> obj=new Hashtable<String,Object>();
		String action=request.getParameter("action");
		arr.put("success", 0);
		Academicyear cobj=null;
		String name;
		int id;
		switch(action)
		{
		case "readall":
			arr.put("success", 1);  
			arr.put("data" ,academicyear.findAll());
			break; 
		 
		case "read":			
			 id=Integer.parseInt(request.getParameter("id"));
			cobj= academicyear.findById(id);
			if(cobj==null)
			{
				arr.put("success", 0);
				arr.put("message", "Not Exists!");
			}
			else
			{
				arr.put("success", 1);
			   arr.put("data",cobj);
			}
			break;
		    case "add":
		    	  name=request.getParameter("name"); 
			      
			    List<String> errors=new ArrayList<String>();
			    if(name.isBlank())
			    	errors.add("Name");
			    else
			    	if(academicyear.findByName(name)!=null)
			    	     errors.add("Name exists!");
			    		 
			    
			    if(errors.size()==0)
			    {
			    	  cobj=new Academicyear();
			    	  cobj.setName(name);  
			    	  academicyear.save(cobj); 
			    	arr.put("success", 1);
			        arr.put("message","Academicyear Created successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
			break;
		    case "edit":
		    	id=Integer.parseInt(request.getParameter("id")); 
		    	 name=request.getParameter("name"); 
			      
			  errors=new ArrayList<String>(); 
				if(academicyear.findById(id)==null)
					errors.add("Data Not exists!");
				
			    if(name.isBlank())
			    	errors.add("Name");
			    else
			    	cobj=academicyear.findByName(name);
			    	if(cobj!=null)
			    		if(cobj.getId()!=id)
			    	     errors.add("Name exists!");
			    		 
			    
			    if(errors.size()==0)
			    {
			    	  cobj=academicyear.findById(id);
			    	  cobj.setName(name);  
			    	  academicyear.save(cobj); 
			    	arr.put("success", 1);
			        arr.put("message","Academicyear Updated successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
		    	break;
		    case "delete":
		    	id=Integer.parseInt(request.getParameter("id"));
		    	cobj= academicyear.findById(id);
				if(cobj==null)
				{
					arr.put("success", 0);
					arr.put("message", "Not Exists!");
				}
				else
				{ 
					arr.put("success", 1);
					academicyear.delete(cobj); 
					arr.put("message", "Deleted Successfully!");
				}
		    	break;
		}
		return arr;
	}
	
	@PostMapping("/courses")
	public Hashtable<String,Object>  courses(HttpServletRequest request)
	{
		Hashtable<String,Object> arr=new Hashtable<String,Object>();
		Hashtable<String,Object> obj=new Hashtable<String,Object>();
		String action=request.getParameter("action");
		arr.put("success", 0);
		Course cobj=null;
		String name;
		int id;
		switch(action)
		{
		case "readall":
			arr.put("success", 1);  
			arr.put("data" ,courses.findAll());
			break; 
		 
		case "read":			
			 id=Integer.parseInt(request.getParameter("id"));
			cobj= courses.findById(id);
			if(cobj==null)
			{
				arr.put("success", 0);
				arr.put("message", "Not Exists!");
			}
			else
			{
				arr.put("success", 1);
			   arr.put("data",cobj);
			}
			break;
		    case "add":
		    	  name=request.getParameter("name"); 
			      
			    List<String> errors=new ArrayList<String>();
			    if(name.isBlank())
			    	errors.add("Name");
			    else
			    	if(courses.findByName(name)!=null)
			    	     errors.add("Name exists!");
			    		 
			    
			    if(errors.size()==0)
			    {
			    	  cobj=new Course();
			    	  cobj.setName(name);  
			    	  courses.save(cobj); 
			    	arr.put("success", 1);
			        arr.put("message","Course Created successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
			break;
		    case "edit":
		    	id=Integer.parseInt(request.getParameter("id")); 
		    	 name=request.getParameter("name"); 
			      
			  errors=new ArrayList<String>(); 
				if(courses.findById(id)==null)
					errors.add("Data Not exists!");
				
			    if(name.isBlank())
			    	errors.add("Name");
			    else
			    	cobj=courses.findByName(name);
			    	if(cobj!=null)
			    		if(cobj.getId()!=id)
			    	     errors.add("Name exists!");
			    		 
			    
			    if(errors.size()==0)
			    {
			    	  cobj=courses.findById(id);
			    	  cobj.setName(name);  
			    	  courses.save(cobj); 
			    	arr.put("success", 1);
			        arr.put("message","Course Updated successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
		    	break;
		    case "delete":
		    	id=Integer.parseInt(request.getParameter("id"));
		    	cobj= courses.findById(id);
				if(cobj==null)
				{
					arr.put("success", 0);
					arr.put("message", "Not Exists!");
				}
				else
				{ 
					arr.put("success", 1);
					courses.delete(cobj); 
					arr.put("message", "Deleted Successfully!");
				}
		    	break;
		}
		return arr;
	}
	
	@PostMapping("/branches")
	public Hashtable<String,Object>  branches(HttpServletRequest request)
	{
		Hashtable<String,Object> arr=new Hashtable<String,Object>();
		Hashtable<String,Object> obj=new Hashtable<String,Object>();
		String action=request.getParameter("action");
		arr.put("success", 0);
		Branch cobj=null;
		String name,course;
		int id;
		switch(action)
		{
		case "readall":
			arr.put("success", 1);  
			arr.put("data" ,branches.findAll());
			break; 
		 
		case "read":			
			 id=Integer.parseInt(request.getParameter("id"));
			cobj= branches.findById(id);
			if(cobj==null)
			{
				arr.put("success", 0);
				arr.put("message", "Not Exists!");
			}
			else
			{
				arr.put("success", 1);
			   arr.put("data",cobj);
			}
			break;
		    case "add":
		    	  name=request.getParameter("name"); 
			      course=request.getParameter("course");
			    List<String> errors=new ArrayList<String>();
			    
			    if(name.isBlank())
			    	errors.add("Name");
			    else
			    	if(branches.findByCourseAndName(CommonFuns.cint(course),name)!=null)
			    	     errors.add("Name exists!");
			    		 
			    
			    if(errors.size()==0)
			    {
			    	  cobj=new Branch();
			    	  cobj.setName(name);  
			    	  cobj.setCourse(CommonFuns.cint(course));
			    	  branches.save(cobj); 
			    	arr.put("success", 1);
			        arr.put("message","Branch Created successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
			break;
		    case "edit":
		    	id=Integer.parseInt(request.getParameter("id")); 
		    	 name=request.getParameter("name"); 
		    	 course=request.getParameter("course");
			  errors=new ArrayList<String>(); 
				if(branches.findById(id)==null)
					errors.add("Data Not exists!");
				
			    if(name.isBlank())
			    	errors.add("Name");
			    else
			    	cobj=branches.findByCourseAndName(CommonFuns.cint(course),name);
			    	if(cobj!=null)
			    		if(cobj.getId()!=id)
			    	     errors.add("Name exists!");
			    		 
			    
			    if(errors.size()==0)
			    {
			    	  cobj=branches.findById(id);
			    	  cobj.setName(name);  
			    	  cobj.setCourse(CommonFuns.cint(course));
			    	  branches.save(cobj); 
			    	arr.put("success", 1);
			        arr.put("message","Branch Updated successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
		    	break;
		    case "delete":
		    	id=Integer.parseInt(request.getParameter("id"));
		    	cobj= branches.findById(id);
				if(cobj==null)
				{
					arr.put("success", 0);
					arr.put("message", "Not Exists!");
				}
				else
				{ 
					arr.put("success", 1);
					branches.delete(cobj); 
					arr.put("message", "Deleted Successfully!");
				}
		    	break;
		}
		return arr;
	}

	@PostMapping("/skills")
	public Hashtable<String,Object>  skills(HttpServletRequest request)
	{
		Hashtable<String,Object> arr=new Hashtable<String,Object>();
		Hashtable<String,Object> obj=new Hashtable<String,Object>();
		String action=request.getParameter("action");
		arr.put("success", 0);
		Skill cobj=null;
		String name;
		int id;
		switch(action)
		{
		case "readall":
			arr.put("success", 1);  
			arr.put("data" ,skills.findAll());
			break; 
		 
		case "read":			
			 id=Integer.parseInt(request.getParameter("id"));
			cobj= skills.findById(id);
			if(cobj==null)
			{
				arr.put("success", 0);
				arr.put("message", "Not Exists!");
			}
			else
			{
				arr.put("success", 1);
			   arr.put("data",cobj);
			}
			break;
		    case "add":
		    	  name=request.getParameter("name"); 
			      
			    List<String> errors=new ArrayList<String>();
			    if(name.isBlank())
			    	errors.add("Name");
			    else
			    	if(skills.findByName(name)!=null)
			    	     errors.add("Name exists!");
			    		 
			    
			    if(errors.size()==0)
			    {
			    	  cobj=new Skill();
			    	  cobj.setName(name);  
			    	  skills.save(cobj); 
			    	arr.put("success", 1);
			        arr.put("message","Skill Created successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
			break;
		    case "edit":
		    	id=Integer.parseInt(request.getParameter("id")); 
		    	 name=request.getParameter("name"); 
			      
			  errors=new ArrayList<String>(); 
				if(skills.findById(id)==null)
					errors.add("Data Not exists!");
				
			    if(name.isBlank())
			    	errors.add("Name");
			    else
			    	cobj=skills.findByName(name);
			    	if(cobj!=null)
			    		if(cobj.getId()!=id)
			    	     errors.add("Name exists!");
			    		 
			    
			    if(errors.size()==0)
			    {
			    	  cobj=skills.findById(id);
			    	  cobj.setName(name);  
			    	  skills.save(cobj); 
			    	arr.put("success", 1);
			        arr.put("message","Skill Updated successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
		    	break;
		    case "delete":
		    	id=Integer.parseInt(request.getParameter("id"));
		    	cobj= skills.findById(id);
				if(cobj==null)
				{
					arr.put("success", 0);
					arr.put("message", "Not Exists!");
				}
				else
				{ 
					arr.put("success", 1);
					skills.delete(cobj); 
					arr.put("message", "Deleted Successfully!");
				}
		    	break;
		}
		return arr;
	}

	@PostMapping("/placementrounds")
	public Hashtable<String,Object>  placementrounds(HttpServletRequest request)
	{
		Hashtable<String,Object> arr=new Hashtable<String,Object>();
		Hashtable<String,Object> obj=new Hashtable<String,Object>();
		String action=request.getParameter("action");
		arr.put("success", 0);
		Placementrounds cobj=null;
		String name;
		int id;
		switch(action)
		{
		case "readall":
			arr.put("success", 1);  
			arr.put("data" ,placementrounds.findAll());
			break; 
		 
		case "read":			
			 id=Integer.parseInt(request.getParameter("id"));
			cobj= placementrounds.findById(id);
			if(cobj==null)
			{
				arr.put("success", 0);
				arr.put("message", "Not Exists!");
			}
			else
			{
				arr.put("success", 1);
			   arr.put("data",cobj);
			}
			break;
		    case "add":
		    	  name=request.getParameter("name"); 
			      
			    List<String> errors=new ArrayList<String>();
			    if(name.isBlank())
			    	errors.add("Name");
			    else
			    	if(placementrounds.findByName(name)!=null)
			    	     errors.add("Name exists!");
			    		 
			    
			    if(errors.size()==0)
			    {
			    	  cobj=new Placementrounds();
			    	  cobj.setName(name);  
			    	  placementrounds.save(cobj); 
			    	arr.put("success", 1);
			        arr.put("message","Placement round Created successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
			break;
		    case "edit":
		    	id=Integer.parseInt(request.getParameter("id")); 
		    	 name=request.getParameter("name"); 
			      
			  errors=new ArrayList<String>(); 
				if(placementrounds.findById(id)==null)
					errors.add("Data Not exists!");
				
			    if(name.isBlank())
			    	errors.add("Name");
			    else
			    	cobj=placementrounds.findByName(name);
			    	if(cobj!=null)
			    		if(cobj.getId()!=id)
			    	     errors.add("Name exists!");
			    		 
			    
			    if(errors.size()==0)
			    {
			    	  cobj=placementrounds.findById(id);
			    	  cobj.setName(name);  
			    	  placementrounds.save(cobj); 
			    	arr.put("success", 1);
			        arr.put("message","Placement round Updated successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
		    	break;
		    case "delete":
		    	id=Integer.parseInt(request.getParameter("id"));
		    	cobj= placementrounds.findById(id);
				if(cobj==null)
				{
					arr.put("success", 0);
					arr.put("message", "Not Exists!");
				}
				else
				{ 
					arr.put("success", 1);
					placementrounds.delete(cobj); 
					arr.put("message", "Deleted Successfully!");
				}
		    	break;
		}
		return arr;
	}

	@PostMapping("/notices")
	public Hashtable<String,Object>  notices(HttpServletRequest request)
	{
		Hashtable<String,Object> arr=new Hashtable<String,Object>();
		Hashtable<String,Object> obj=new Hashtable<String,Object>();
		String action=request.getParameter("action");
		arr.put("success", 0);
		Notice cobj=null;
		String title,content,fromdate,todate,audience;
		int id;
		switch(action)
		{
		case "readall":
			arr.put("success", 1);  
			arr.put("data" ,notices.findAll());
			break; 
		 
		case "read":			
			 id=Integer.parseInt(request.getParameter("id"));
			cobj= notices.findById(id);
			if(cobj==null)
			{
				arr.put("success", 0);
				arr.put("message", "Not Exists!");
			}
			else
			{
				arr.put("success", 1);
			   arr.put("data",cobj);
			}
			break;
		    case "add":
		    	title=request.getParameter("title"); 
		    	content=request.getParameter("content"); 
		    	fromdate=request.getParameter("fromdate"); 
		    	todate=request.getParameter("todate"); 
		    	audience=request.getParameter("audience"); 
		    	
		    	
			    List<String> errors=new ArrayList<String>();
			    if(title.isBlank())
			    	errors.add("Title");
			    
			    if(fromdate.isBlank())
			    	errors.add("Fromdate");
			    if(todate.isBlank())
			    	errors.add("Todate");
			    else
			    {
			    	if(Date.valueOf(fromdate).compareTo(Date.valueOf(todate))>0)
			   			 errors.add("Invalid Dates"); 
			    }
			    if(errors.size()==0)
			    {
			    	  cobj=new Notice();
			    	  cobj.setTitle(title);
			    	  cobj.setNotice(content);
			    	  cobj.setAudience(audience);
			    	  cobj.setFromdate(Date.valueOf(fromdate));
			    	  cobj.setTodate(Date.valueOf(todate));
			    	  notices.save(cobj); 
			    	arr.put("success", 1);
			        arr.put("message","Notice Created successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
			break;
		    case "edit":
		    	id=Integer.parseInt(request.getParameter("id")); 
		    	title=request.getParameter("title"); 
		    	content=request.getParameter("content"); 
		    	fromdate=request.getParameter("fromdate"); 
		    	todate=request.getParameter("todate"); 
		    	audience=request.getParameter("audience"); 
		    	
		    	
			   errors=new ArrayList<String>();
			    if(title.isBlank())
			    	errors.add("Title");
			    
			    if(fromdate.isBlank())
			    	errors.add("Fromdate");
			    if(todate.isBlank())
			    	errors.add("Todate");
			    else
			    {
			    	if(Date.valueOf(fromdate).compareTo(Date.valueOf(todate))>0)
			   			 errors.add("Invalid Dates"); 
			    }
			  
			    		 
			    
			    if(errors.size()==0)
			    {
			    	  cobj=notices.findById(id);
			    	  cobj.setTitle(title);
			    	  cobj.setNotice(content);
			    	  cobj.setAudience(audience);
			    	  cobj.setFromdate(Date.valueOf(fromdate));
			    	  cobj.setTodate(Date.valueOf(todate));
			    	  notices.save(cobj); 
			    	arr.put("success", 1);
			        arr.put("message","Notice Updated successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
		    	break;
		    case "delete":
		    	id=Integer.parseInt(request.getParameter("id"));
		    	cobj= notices.findById(id);
				if(cobj==null)
				{
					arr.put("success", 0);
					arr.put("message", "Not Exists!");
				}
				else
				{ 
					arr.put("success", 1);
					notices.delete(cobj); 
					arr.put("message", "Deleted Successfully!");
				}
		    	break;
		}
		return arr;
	}
	
	@PostMapping("/students")
	public Hashtable<String,Object>  students(HttpServletRequest request)
	{
		Hashtable<String,Object> arr=new Hashtable<String,Object>();
		Hashtable<String,Object> obj=new Hashtable<String,Object>();
		String action=request.getParameter("action");
		arr.put("success", 0);
		User cobj;
		String name,contact,email, password,type,sts,sfst;
		  type=LoginTypes.STUDENT.toString();
		int id;
		switch(action)
		{
		case "readall":
			arr.put("success", 1);    
			arr.put("data",new ArrayList<Hashtable<String,Object>>());
	         obj=new Hashtable<String,Object>(); 
	        for(User c:users.findByTypeOrderByIdDesc(type))
	        {
	        	obj=new Hashtable<String,Object>(); 
	        	obj.put("id",c.getId());
	        	obj.put("name", c.getName());
	        	obj.put("email", c.getEmail());
	        	obj.put("contact", c.getContact()); 
	        	obj.put("status",c.getStatus());
	        	Studentfinalstatus sf=sfstatus.findByStudent(c.getId());
	        	obj.put("sfstatus","");
	        	if(sf!=null)
	        	  obj.put("sfstatus",sf.getStatus());
				((List<Hashtable<String,Object>>)arr.get("data")).add(obj); 
	        }
			
			//arr.put("data" ,users.findByTypeOrderByIdDesc(type));
			
			break; 
		case "search":
			String key=request.getParameter("key"); 
			List<User> objs;
		 
				objs=users.findByStatusAndTypeAndNameContaining(1,type,key);
			
			
			arr.put("data" ,new ArrayList<Hashtable<String,Object>>());
			for(User c:objs)
			{
				obj=new Hashtable<String,Object>(); 
	        	obj.put("id",c.getId());
	        	obj.put("label", c.getName()+" - "+c.getType()); 
	        	obj.put("value", c.getName()+" - "+c.getType()); 
				((List<Hashtable<String,Object>>)arr.get("data")).add(obj);
			} 
			break;
		case "read":			
			 id=Integer.parseInt(request.getParameter("id"));
			cobj= users.findById(id);
			if(cobj==null)
			{
				arr.put("success", 0);
				arr.put("message", "Not Exists!");
			}
			else
			{
				arr.put("success", 1);
			   arr.put("data",cobj); 
	        	Studentfinalstatus sf=sfstatus.findByStudent(cobj.getId());
	        	if(sf!=null)
	        	  arr.put("sfstatus",sf.getStatus());
	        	else
	        		  arr.put("sfstatus","");
			}
			break;
		    case "add":
		    	  name=request.getParameter("name");
			      email=request.getParameter("email"); 
			    
			      contact=request.getParameter("contact");  
		   	      password=request.getParameter("password"); 
		   	      sts=request.getParameter("status");
		   	      sfst=request.getParameter("sfstatus");
			      
			    List<String> errors=new ArrayList<String>(); 
			    
			    if(email.isBlank())
			    	errors.add("Email");
			    else
			    	if(users.findByEmail(email)!=null)
			    	     errors.add("Email exists!");
			    
			    if(contact.isBlank())
			    	errors.add("Contact");
			    else
			    	if(users.findByContact(contact)!=null)
			    	     errors.add("Contact exists!");
			     
			    
			    if(!logins.validation_Password(password))   errors.add("A valid Password");
			  
		    		
			    if(name.isBlank()) errors.add("Full name");
			    if(type.isBlank()) errors.add("type");
			    
			    if(errors.size()==0)
			    {
			    	  cobj=new User();
			    	  cobj.setName(name);
			    	  cobj.setEmail(email);
			    	  cobj.setContact(contact); 
			    	  cobj.setType(type);
			    	  cobj.setStatus(CommonFuns.cint(sts));
			    	  cobj.setPassword("Test@123"); 
			    	users.save(cobj);
			    	if(!sfst.isBlank())
			    	{
			    		 Studentfinalstatus sf=sfstatus.findByStudent(cobj.getId());
	    				  if(sf==null) sf=new Studentfinalstatus();
	    				  sf.setStudent(cobj.getId());
	    				  sf.setStatus(sfst);
	    				  sfstatus.save(sf);
			    	}
			    	logins.changeUserPassword(cobj, password);
			    	arr.put("success", 1);
			        arr.put("message","Student Created successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
			break;
		    case "edit":
		    	id=Integer.parseInt(request.getParameter("id")); 
		    	name=request.getParameter("name");
			      email=request.getParameter("email");  
			      contact=request.getParameter("contact");  		   	      password=request.getParameter("password");     
		   	   sts=request.getParameter("status");
		   	 sfst=request.getParameter("sfstatus");
			     errors=new ArrayList<String>();
			      
			     errors=new ArrayList<String>();
			    
			    User user =users.findById(id);
			    
			    if(user==null) errors.add("Not exists!");
			    if(name.isBlank()) errors.add("Full name");  
			    
			    if(email.isBlank()) errors.add("Email"); 
			    else
			    {
			    	User user1=users.findByEmail(email);
			    	if(user1!=null) 
			    	if(user.getId()!=user1.getId())
			    		errors.add("Email already exists!");
			    }
			    
			    if(contact.isBlank()) errors.add("Contact"); 
			    else
			    {
			    	User user1=users.findByContact(contact);
			    	if(user1!=null) 
			    	if(user.getId()!=user1.getId())
			    		errors.add("Contact already exists!");
			    }
			     
			    
			    if(!password.isBlank())
			    {
			    	 if(!logins.validation_Password(password))   errors.add("A valid Password");
			    	 
			    }
			    
			    if(errors.size()==0)
			    { 
			    	cobj =users.findById(id);
			    	 cobj.setName(name);
			    	  cobj.setEmail(email);
			    	  cobj.setContact(contact); 
			    	  cobj.setStatus(CommonFuns.cint(sts));
			    	  cobj.setType(type);
			    	users.save(cobj); 
			    	if(!sfst.isBlank())
			    	{
			    		 Studentfinalstatus sf=sfstatus.findByStudent(cobj.getId());
	    				  if(sf==null) sf=new Studentfinalstatus();
	    				  sf.setStudent(cobj.getId());
	    				  sf.setStatus(sfst);
	    				  sfstatus.save(sf);
			    	}
			    	 if(!password.isBlank())
			    		 logins.changeUserPassword(user, password);
			    	arr.put("success", 1);
			        arr.put("message","Saved!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));

		    	break;
		    
		}
		return arr;
	}
	
	@PostMapping("/companies")
	public Hashtable<String,Object>  companies(HttpServletRequest request)
	{
		Hashtable<String,Object> arr=new Hashtable<String,Object>();
		Hashtable<String,Object> obj=new Hashtable<String,Object>();
		String action=request.getParameter("action");
		arr.put("success", 0);
		User cobj;
		String name,contact,email, password,type,sts;
		  type=LoginTypes.COMPANY.toString();
		int id;
		switch(action)
		{
		case "readall":
			arr.put("success", 1);    
			arr.put("data" ,users.findByTypeOrderByIdDesc(type));
			break; 
		case "search":
			String key=request.getParameter("key"); 
			List<User> objs;
		 
				objs=users.findByStatusAndTypeAndNameContaining(1,type,key);
			
			
			arr.put("data" ,new ArrayList<Hashtable<String,Object>>());
			for(User c:objs)
			{
				obj=new Hashtable<String,Object>(); 
	        	obj.put("id",c.getId());
	        	obj.put("label", c.getName()+" - "+c.getType()); 
	        	obj.put("value", c.getName()+" - "+c.getType()); 
				((List<Hashtable<String,Object>>)arr.get("data")).add(obj);
			} 
			break;
		case "read":			
			 id=Integer.parseInt(request.getParameter("id"));
			cobj= users.findById(id);
			if(cobj==null)
			{
				arr.put("success", 0);
				arr.put("message", "Not Exists!");
			}
			else
			{
				arr.put("success", 1);
			   arr.put("data",cobj);
			}
			break;
		    case "add":
		    	  name=request.getParameter("name");
			      email=request.getParameter("email"); 
			    
			      contact=request.getParameter("contact");  
		   	      password=request.getParameter("password"); 
		   	      sts=request.getParameter("status");
			      
			    List<String> errors=new ArrayList<String>(); 
			    
			    if(email.isBlank())
			    	errors.add("Email");
			    else
			    	if(users.findByEmail(email)!=null)
			    	     errors.add("Email exists!");
			    
			    if(contact.isBlank())
			    	errors.add("Contact");
			    else
			    	if(users.findByContact(contact)!=null)
			    	     errors.add("Contact exists!");
			     
			    
			    if(!logins.validation_Password(password))   errors.add("A valid Password");
			  
		    		
			    if(name.isBlank()) errors.add("Full name");
			    if(type.isBlank()) errors.add("type");
			    
			    if(errors.size()==0)
			    {
			    	  cobj=new User();
			    	  cobj.setName(name);
			    	  cobj.setEmail(email);
			    	  cobj.setContact(contact); 
			    	  cobj.setType(type);
			    	  cobj.setStatus(CommonFuns.cint(sts));
			    	  cobj.setPassword("Test@123"); 
			    	users.save(cobj);
			    	logins.changeUserPassword(cobj, password);
			    	arr.put("success", 1);
			        arr.put("message","Student Created successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
			break;
		    case "edit":
		    	id=Integer.parseInt(request.getParameter("id")); 
		    	name=request.getParameter("name");
			      email=request.getParameter("email");  
			      contact=request.getParameter("contact");  		   	      password=request.getParameter("password");     
		   	   sts=request.getParameter("status");
			     errors=new ArrayList<String>();
			      
			     errors=new ArrayList<String>();
			    
			    User user =users.findById(id);
			    
			    if(user==null) errors.add("Not exists!");
			    if(name.isBlank()) errors.add("Full name");  
			    
			    if(email.isBlank()) errors.add("Email"); 
			    else
			    {
			    	User user1=users.findByEmail(email);
			    	if(user1!=null) 
			    	if(user.getId()!=user1.getId())
			    		errors.add("Email already exists!");
			    }
			    
			    if(contact.isBlank()) errors.add("Contact"); 
			    else
			    {
			    	User user1=users.findByContact(contact);
			    	if(user1!=null) 
			    	if(user.getId()!=user1.getId())
			    		errors.add("Contact already exists!");
			    }
			     
			    
			    if(!password.isBlank())
			    {
			    	 if(!logins.validation_Password(password))   errors.add("A valid Password");
			    	 
			    }
			    
			    if(errors.size()==0)
			    { 
			    	cobj =users.findById(id);
			    	 cobj.setName(name);
			    	  cobj.setEmail(email);
			    	  cobj.setContact(contact); 
			    	  cobj.setStatus(CommonFuns.cint(sts));
			    	  cobj.setType(type);
			    	users.save(cobj); 
			    	 if(!password.isBlank())
			    		 logins.changeUserPassword(user, password);
			    	arr.put("success", 1);
			        arr.put("message","Saved!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));

		    	break;
		    
		}
		return arr;
	}
	@PostMapping("/trainings")
	public Hashtable<String,Object>  trainings(HttpServletRequest request)
	{
		Hashtable<String,Object> arr=new Hashtable<String,Object>();
		Hashtable<String,Object> obj=new Hashtable<String,Object>();
		String action=request.getParameter("action");
		arr.put("success", 0);
		Training cobj=null;
		String title,content,fromdate,todate,location;
		int id;
		switch(action)
		{
		case "readall":
			arr.put("success", 1);  
			arr.put("data" ,trainings.findAllByOrderByIdDesc());
			break; 
		 
		case "read":			
			 id=Integer.parseInt(request.getParameter("id"));
			cobj= trainings.findById(id);
			if(cobj==null)
			{
				arr.put("success", 0);
				arr.put("message", "Not Exists!");
			}
			else
			{
				arr.put("success", 1);
			   arr.put("data",cobj);
			   arr.put("branches",tbranch.findByTraining(id));
			   arr.put("academicyear",tacademicyear.findByTraining(id));
			}
			break;
		    case "add":
		    	title=request.getParameter("title"); 
		    	content=request.getParameter("content"); 
		    	fromdate=request.getParameter("fromdate"); 
		    	todate=request.getParameter("todate");  
		    	location=request.getParameter("location"); 
		    	
			    List<String> errors=new ArrayList<String>();
			    if(title.isBlank())
			    	errors.add("Title");
			    
			    if(location.isBlank())
			    	errors.add("Location");
			    
			    if(fromdate.isBlank())
			    	errors.add("Fromdate");
			    if(todate.isBlank())
			    	errors.add("Todate");
			    else
			    {
			    	if(Date.valueOf(fromdate).compareTo(Date.valueOf(todate))>0)
			   			 errors.add("Invalid Dates"); 
			    }
			    
			    
			    if(errors.size()==0)
			    {
			    	  cobj=new Training();
			    	  cobj.setTitle(title);
			    	  cobj.setDescription(content);
			    	  cobj.setLocation(location);
			    	  cobj.setFromdate(Date.valueOf(fromdate));
			    	  cobj.setTodate(Date.valueOf(todate));
			    	  trainings.save(cobj); 
			    	  if(request.getParameterValues("branch[]")!=null)
			    	  for(String s:request.getParameterValues("branch[]"))
			    	  {
			    		  Trainingbranch tb=new Trainingbranch();
			    		  tb.setTraining(cobj.getId());
			    		  tb.setBranch(CommonFuns.cint(s));
			    		  tbranch.save(tb);
			    	  }
			    	  
			    	  if(request.getParameterValues("academicyear[]")!=null)
				    	  for(String s:request.getParameterValues("academicyear[]"))
				    	  {
				    		  Trainingacademicyear tb=new Trainingacademicyear();
				    		  tb.setTraining(cobj.getId());
				    		  tb.setAcademicyear(CommonFuns.cint(s));
				    		  tacademicyear.save(tb);
				    	  }
			    	arr.put("success", 1);
			        arr.put("message","Training Created successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
			break;
		    case "edit":
		    	id=Integer.parseInt(request.getParameter("id")); 
		    	title=request.getParameter("title"); 
		    	content=request.getParameter("content"); 
		    	fromdate=request.getParameter("fromdate"); 
		    	todate=request.getParameter("todate");  
		    	location=request.getParameter("location"); 
		    	
			   errors=new ArrayList<String>();
			    if(title.isBlank())
			    	errors.add("Title");

			    if(location.isBlank())
			    	errors.add("Location");
			    
			    if(fromdate.isBlank())
			    	errors.add("Fromdate");
			    if(todate.isBlank())
			    	errors.add("Todate");
			    else
			    {
			    	if(Date.valueOf(fromdate).compareTo(Date.valueOf(todate))>0)
			   			 errors.add("Invalid Dates"); 
			    }
			  
			    		 
			    
			    if(errors.size()==0)
			    {
			    	  cobj=trainings.findById(id);
			    	  cobj.setTitle(title);
			    	  cobj.setDescription(content);
			    	  cobj.setLocation(location);
			    	  cobj.setFromdate(Date.valueOf(fromdate));
			    	  cobj.setTodate(Date.valueOf(todate));
			    	  trainings.save(cobj); 
			    	  tbranch.deleteByTraining(id);
						tacademicyear.deleteByTraining(id);
			    	  if(request.getParameterValues("branch[]")!=null)
				    	  for(String s:request.getParameterValues("branch[]"))
				    	  {
				    		  Trainingbranch tb=new Trainingbranch();
				    		  tb.setTraining(cobj.getId());
				    		  tb.setBranch(CommonFuns.cint(s));
				    		  tbranch.save(tb);
				    	  }
				    	  
				    	  if(request.getParameterValues("academicyear[]")!=null)
					    	  for(String s:request.getParameterValues("academicyear[]"))
					    	  {
					    		  Trainingacademicyear tb=new Trainingacademicyear();
					    		  tb.setTraining(cobj.getId());
					    		  tb.setAcademicyear(CommonFuns.cint(s));
					    		  tacademicyear.save(tb);
					    	  }
			    	arr.put("success", 1);
			        arr.put("message","Training Updated successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
		    	break;
		    case "delete":
		    	id=Integer.parseInt(request.getParameter("id"));
		    	cobj= trainings.findById(id);
				if(cobj==null)
				{
					arr.put("success", 0);
					arr.put("message", "Not Exists!");
				}
				else
				{ 
					arr.put("success", 1);
					trainings.delete(cobj); 
					tbranch.deleteByTraining(id);
					tacademicyear.deleteByTraining(id);
					arr.put("message", "Deleted Successfully!");
				}
		    	break;
		}
		return arr;
	}
	
	@PostMapping("/campusdrives")
	public Hashtable<String,Object>  campusdrives(HttpServletRequest request)
	{
		Hashtable<String,Object> arr=new Hashtable<String,Object>();
		Hashtable<String,Object> obj=new Hashtable<String,Object>();
		String action=request.getParameter("action");
		arr.put("success", 0);
		User user = logins.findByEmail(
	  		      SecurityContextHolder.getContext().getAuthentication().getName());
		Jobpost cobj=null;
		String company,title,content,fromdate,todate,location,minsal,maxsal,status,requiredscore;
		int id;
		switch(action)
		{
		case "readall":
			arr.put("success", 1);  
			arr.put("data" ,campusdrives.findAllByOrderByIdDesc());
			break; 
		 
		case "read":			
			 id=Integer.parseInt(request.getParameter("id"));
			cobj= campusdrives.findById(id);
			if(cobj==null)
			{
				arr.put("success", 0);
				arr.put("message", "Not Exists!");
			}
			else
			{
				arr.put("success", 1);
			   arr.put("data",cobj);
			   arr.put("branches",jbranch.findByJobpost(id));
			   arr.put("academicyear",jacademicyear.findByJobpost(id));
			   arr.put("skills",jskill.findByJobpost(id));
			   arr.put("rounds",jplacementrounds.findByJobpost(id));
			}
			break;
		    case "add":
		    	title=request.getParameter("title"); 
		    	company=request.getParameter("company"); 
		    	content=request.getParameter("content"); 
		    	fromdate=request.getParameter("fromdate"); 
		    	todate=request.getParameter("todate");  
		    	location=request.getParameter("location");
		    	minsal=request.getParameter("minsal");
		    	maxsal=request.getParameter("maxsal"); 
		    	status=request.getParameter("status");  
		    	requiredscore=request.getParameter("requiredscore");  
		    	
			    List<String> errors=new ArrayList<String>();
			    if(title.isBlank())
			    	errors.add("Title");
			    
			    if(company.isBlank())
			    	errors.add("Company");
			    
			    if(requiredscore.isBlank())
			    	errors.add("Required score");
			    
			    if(location.isBlank())
			    	errors.add("Location");

			    if(status.isBlank())
			    	errors.add("Status");
			    
			    if(fromdate.isBlank())
			    	errors.add("Fromdate");
			    if(todate.isBlank())
			    	errors.add("Todate");
			    else
			    {
			    	if(Date.valueOf(fromdate).compareTo(Date.valueOf(todate))>0)
			   			 errors.add("Invalid Dates"); 
			    }
			    if(minsal.isBlank())
			    	errors.add("Min. salary");			    
			    if(maxsal.isBlank())
			    	errors.add("Max. salary");				    
			    if(errors.size()==0)
			    {
			    	  cobj=new Jobpost();
			    	  cobj.setJobtitle(title);
			    	  cobj.setDescription(content);
			    	  cobj.setLocation(location);
			    	  cobj.setFromdate(Date.valueOf(fromdate));
			    	  cobj.setTodate(Date.valueOf(todate));
			    	  cobj.setCompany(CommonFuns.cint(company));
			    	  cobj.setMaximumsalary(CommonFuns.cfloat(maxsal));
			    	  cobj.setMinimumsalary(CommonFuns.cfloat(minsal));
			    	  cobj.setStatus(CommonFuns.cint(status));
			    	  cobj.setRequiredscore(CommonFuns.cfloat(requiredscore));
			    	  cobj.setCreator(user.getId());
			    	  campusdrives.save(cobj); 
			    	  if(request.getParameterValues("branch[]")!=null)
			    	  for(String s:request.getParameterValues("branch[]"))
			    	  {
			    		  Jobpostbranch tb=new Jobpostbranch();
			    		  tb.setJobpost(cobj.getId());
			    		  tb.setBranch(CommonFuns.cint(s));
			    		  jbranch.save(tb);
			    	  }
			    	  
			    	  if(request.getParameterValues("academicyear[]")!=null)
				    	  for(String s:request.getParameterValues("academicyear[]"))
				    	  {
				    		  Jobpostacademicyear tb=new Jobpostacademicyear();
				    		  tb.setJobpost(cobj.getId());
				    		  tb.setAcademicyear(CommonFuns.cint(s));
				    		  jacademicyear.save(tb);
				    	  }
			    	  if(request.getParameterValues("placementrounds[]")!=null)
				    	  for(String s:request.getParameterValues("placementrounds[]"))
				    	  {
				    		  Jobpostplacementrounds tb=new Jobpostplacementrounds();
				    		  tb.setJobpost(cobj.getId());
				    		  tb.setPlacementround(CommonFuns.cint(s));
				    		  jplacementrounds.save(tb);
				    	  }
			    	  if(request.getParameterValues("skill[]")!=null)
				    	  for(String s:request.getParameterValues("skill[]"))
				    	  {
				    		  Jobpostskill tb=new Jobpostskill();
				    		  tb.setJobpost(cobj.getId());
				    		  tb.setSkill(CommonFuns.cint(s));
				    		  jskill.save(tb);
				    	  }
			    	  
			    	  
			    	  
			    	arr.put("success", 1);
			        arr.put("message","Created successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
			break;
		    case "edit":
		    	id=Integer.parseInt(request.getParameter("id")); 
		    	title=request.getParameter("title"); 
		    	company=request.getParameter("company"); 
		    	content=request.getParameter("content"); 
		    	fromdate=request.getParameter("fromdate"); 
		    	todate=request.getParameter("todate");  
		    	location=request.getParameter("location");
		    	minsal=request.getParameter("minsal");
		    	maxsal=request.getParameter("maxsal");
		    	status=request.getParameter("status");  
		    	requiredscore=request.getParameter("requiredscore");  
		    	
			    errors=new ArrayList<String>();
			    if(title.isBlank())
			    	errors.add("Title");
			    
			    if(company.isBlank())
			    	errors.add("Company");
			    
			    if(location.isBlank())
			    	errors.add("Location");
			    
			    if(requiredscore.isBlank())
			    	errors.add("Required score");
			    
			    if(status.isBlank())
			    	errors.add("Status");
			    
			    if(fromdate.isBlank())
			    	errors.add("Fromdate");
			    if(todate.isBlank())
			    	errors.add("Todate");
			    else
			    {
			    	if(Date.valueOf(fromdate).compareTo(Date.valueOf(todate))>0)
			   			 errors.add("Invalid Dates"); 
			    }
			    if(minsal.isBlank())
			    	errors.add("Min. salary");			    
			    if(maxsal.isBlank())
			    	errors.add("Max. salary");			
                cobj=campusdrives.findById(id);
			    
			    if(cobj==null)
			      errors.add("Not exists!"); 
			    if(errors.size()==0)
			    {
			    	  cobj=campusdrives.findById(id);
			    	  cobj.setJobtitle(title);
			    	  cobj.setDescription(content);
			    	  cobj.setLocation(location);
			    	  cobj.setFromdate(Date.valueOf(fromdate));
			    	  cobj.setTodate(Date.valueOf(todate));
			    	  cobj.setCompany(CommonFuns.cint(company));
			    	  cobj.setMaximumsalary(CommonFuns.cfloat(maxsal));
			    	  cobj.setMinimumsalary(CommonFuns.cfloat(minsal));
			    	  cobj.setStatus(CommonFuns.cint(status));
			    	  cobj.setRequiredscore(CommonFuns.cfloat(requiredscore));
			    	  campusdrives.save(cobj); 
			    	  jbranch.deleteByJobpost(id);
					  jacademicyear.deleteByJobpost(id);
			    	  jskill.deleteByJobpost(id);
			    	  jplacementrounds.deleteByJobpost(id);
					 if(request.getParameterValues("branch[]")!=null)
				    	  for(String s:request.getParameterValues("branch[]"))
				    	  {
				    		  Jobpostbranch tb=new Jobpostbranch();
				    		  tb.setJobpost(cobj.getId());
				    		  tb.setBranch(CommonFuns.cint(s));
				    		  jbranch.save(tb);
				    	  }
				    	  
				    	  if(request.getParameterValues("academicyear[]")!=null)
					    	  for(String s:request.getParameterValues("academicyear[]"))
					    	  {
					    		  Jobpostacademicyear tb=new Jobpostacademicyear();
					    		  tb.setJobpost(cobj.getId());
					    		  tb.setAcademicyear(CommonFuns.cint(s));
					    		  jacademicyear.save(tb);
					    	  }
				    	  if(request.getParameterValues("placementrounds[]")!=null)
					    	  for(String s:request.getParameterValues("placementrounds[]"))
					    	  {
					    		  Jobpostplacementrounds tb=new Jobpostplacementrounds();
					    		  tb.setJobpost(cobj.getId());
					    		  tb.setPlacementround(CommonFuns.cint(s));
					    		  jplacementrounds.save(tb);
					    	  }
				    	  if(request.getParameterValues("skill[]")!=null)
					    	  for(String s:request.getParameterValues("skill[]"))
					    	  {
					    		  Jobpostskill tb=new Jobpostskill();
					    		  tb.setJobpost(cobj.getId());
					    		  tb.setSkill(CommonFuns.cint(s));
					    		  jskill.save(tb);
					    	  } 
			    	arr.put("success", 1);
			        arr.put("message","Updated successfully!");
			    }
			    else
			        arr.put("message","The following fields contains errors: "+errors.stream().collect(Collectors.joining("<br/> ")));
		    	break;
		    case "readcampusselections":
		    	id=Integer.parseInt(request.getParameter("id"));
		    	arr.put("success", 1);
		    	arr.put("applications" ,new ArrayList<Hashtable<String,Object>>());
		    	for(Jobpostapply j:japply.findByJobpost(id))
		    	{
		    		 obj=new Hashtable<String,Object>();
		       	     obj.put("id", j.getId()); 
		       	     obj.put("placed", jplaced.findByJobpostapply(j.getId()));
		    		((List<Hashtable<String,Object>>)arr.get("applications")).add(obj);
		    	}
		    	
		    	break;
		    case "savecampusselections":
		    	id=Integer.parseInt(request.getParameter("id"));
		    	arr.put("success", 1);
		    	arr.put("applications" ,new ArrayList<Hashtable<String,Object>>());
		    	 Studentfinalstatus sf ;
		    	for(Jobpostapply j:japply.findByJobpost(id))
		    	{
		    		sf =sfstatus.findByStudent(j.getStudent());
					  if(sf==null) sf=new Studentfinalstatus();
		    		  for(Jobpostplacementrounds r:jplacementrounds.findByJobpost(id))
		    		  {
		    			  Jobpostplaced ja=jplaced.findByJobpostapplyAndPlacementround(j.getId(),r.getPlacementround());
		    			  if(ja==null)
		    				  ja=new Jobpostplaced();
		    			  ja.setJobpost(id);
		    			  ja.setJobpostapply(j.getId());
		    			
		    			  String placed=request.getParameter("placed["+j.getId()+"]["+r.getPlacementround()+"]");
		    			  if(placed==null)
		    			  {
		    				  if(r.getPlacementround()==6 && ja.getStatus()==1)
		    				  { 
			    				  sfstatus.delete(sf);
		    				  }
 
		    				  ja.setStatus(0); 
		    			  }
		    			  else if(placed.compareTo("1")==0)
		    			  {
		    				  ja.setStatus(1);
		    			  if(placed.compareTo("1")==0 && r.getPlacementround()==6)
		    			  { 
		    				  sf.setStudent(j.getStudent());
		    				  sf.setStatus(SFStatus.PLACED.toString());
		    				  sfstatus.save(sf);
		    			  }  
		    			  
		    			  }
		    			  ja.setPlacementround(r.getPlacementround()); 
		    			  jplaced.save(ja);
		    		  }
		    	}
		    	arr.put("message","Updated successfully!");
		    	break;		    	
		    case "delete":
		    	id=Integer.parseInt(request.getParameter("id"));
		    	cobj= campusdrives.findById(id);
				if(cobj==null)
				{
					arr.put("success", 0);
					arr.put("message", "Not Exists!");
				}
				else
				{ 
					arr.put("success", 1);
					campusdrives.delete(cobj); 
					jbranch.deleteByJobpost(id);
					  jacademicyear.deleteByJobpost(id);
			    	  jskill.deleteByJobpost(id);
			    	  jplacementrounds.deleteByJobpost(id);
			    	  jplaced.deleteByJobpost(id);
					arr.put("message", "Deleted Successfully!");
				}
		    	break;
		}
		return arr;
	}
	
	@PostMapping("/readuser")
	public Hashtable<String,Object>  readuser(HttpServletRequest request)
	{
		Hashtable<String,Object> arr=new Hashtable<String,Object>();  
		arr.put("success", 1);
		int id=Integer.parseInt(request.getParameter("id"));
		arr.put("user", users.findById(id).getName());
	    return arr;
	}
	
	@GetMapping("/loadcontactues/{page}")
	public Hashtable<String,Object>  loadcontactues(@PathVariable("page")int page)
	{
		Hashtable<String,Object> arr=new Hashtable<String,Object>();
		arr.put("success", 1);
        Pageable p=PageRequest.of(page, 5);
        int i=(page*5)+1;
        arr.put("data",new ArrayList<Hashtable<String,Object>>());
        Hashtable<String,Object> obj=new Hashtable<String,Object>(); 
        for(Contactus c:contactuses.findAllByOrderByIdDesc(p))
        {
        	obj=new Hashtable<String,Object>(); 
        	obj.put("id",c.getId());
        	obj.put("name", c.getName());
        	obj.put("email", c.getEmail());
        	obj.put("message", c.getMessage());
        	obj.put("ipaddress",c.getIpaddress());
        	obj.put("creationtime", c.getCreationtime());
			((List<Hashtable<String,Object>>)arr.get("data")).add(obj);
			i++;
        }
		return arr;
	} 
	
	
}
