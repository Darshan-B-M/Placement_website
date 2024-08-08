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

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest; 
 

@RestController
@RequestMapping(path="/company/JSON", produces="application/json")
@CrossOrigin(origins="*")
public class CompanyRestJsonResponse { 
	
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
    
	@PersistenceContext
    private EntityManager em;
	
	@PostMapping("/dashboard")
	public Hashtable<String,Object>  dashboard(HttpServletRequest request)
	{
		Hashtable<String,Object> arr=new Hashtable<String,Object>();  
		arr.put("success", 1);
		 arr.put("course", courses.count());
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
	
	@PostMapping("/campusdrives")
	public Hashtable<String,Object>  campusdrives(HttpServletRequest request)
	{
		Hashtable<String,Object> arr=new Hashtable<String,Object>();
		Hashtable<String,Object> obj=new Hashtable<String,Object>();
		String action=request.getParameter("action");
		 User user = logins.findByEmail(
	  		      SecurityContextHolder.getContext().getAuthentication().getName());
		arr.put("success", 0);
		Jobpost cobj=null;
		String  title,content,fromdate,todate,location,minsal,maxsal,status,requiredscore;
		int id;
		switch(action)
		{
		case "readall":
			arr.put("success", 1);  
			arr.put("data" ,campusdrives.findByCompanyOrderByIdDesc(user.getId()));
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
		    	status=request.getParameter("status");  
		    	content=request.getParameter("content"); 
		    	fromdate=request.getParameter("fromdate"); 
		    	todate=request.getParameter("todate");  
		    	location=request.getParameter("location");
		    	minsal=request.getParameter("minsal");
		    	maxsal=request.getParameter("maxsal");
		    	requiredscore=request.getParameter("requiredscore");
		    	
			    List<String> errors=new ArrayList<String>();
			    if(title.isBlank())
			    	errors.add("Title");
			     
			    
			    if(location.isBlank())
			    	errors.add("Location");
			    
			    if(status.isBlank())
			    	errors.add("Status");
			    
			    if(requiredscore.isBlank())
			    	errors.add("Required score");
			    
			    
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
			    	  cobj.setCompany(user.getId());
			    	  cobj.setJobtitle(title);
			    	  cobj.setDescription(content);
			    	  cobj.setLocation(location);
			    	  cobj.setFromdate(Date.valueOf(fromdate));
			    	  cobj.setTodate(Date.valueOf(todate)); 
			    	  cobj.setMaximumsalary(CommonFuns.cfloat(maxsal));
			    	  cobj.setMinimumsalary(CommonFuns.cfloat(minsal));
			    	  cobj.setCreator(user.getId());
			    	  cobj.setStatus(CommonFuns.cint(status));
			    	  cobj.setRequiredscore(CommonFuns.cfloat(requiredscore));
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
			    cobj=campusdrives.findById(id);
			    
			    if(cobj==null)
			      errors.add("Not exists!");
			    else if(user.getId()!=cobj.getCreator() && cobj.getCompany()!=user.getId())
			    	errors.add("Invalid Access!");
			    
			    if(minsal.isBlank())
			    	errors.add("Min. salary");			    
			    if(maxsal.isBlank())
			    	errors.add("Max. salary");			
			    		 
			    
			    if(errors.size()==0)
			    {
			    	  cobj=campusdrives.findById(id);
			    	  cobj.setJobtitle(title);
			    	  cobj.setDescription(content);
			    	  cobj.setLocation(location);
			    	  cobj.setFromdate(Date.valueOf(fromdate));
			    	  cobj.setTodate(Date.valueOf(todate)); 
			    	  cobj.setMaximumsalary(CommonFuns.cfloat(maxsal));
			    	  cobj.setMinimumsalary(CommonFuns.cfloat(minsal));
			    	  cobj.setRequiredscore(CommonFuns.cfloat(requiredscore));
			    	  cobj.setStatus(CommonFuns.cint(status));
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
				 else if(user.getId()!=cobj.getCreator() && cobj.getCompany()!=user.getId())
				 {
						arr.put("success", 0);
						arr.put("message", "Invalid Access!");
				 }
				else
				{ 
					
					arr.put("success", 1);
					campusdrives.delete(cobj); 
					jbranch.deleteByJobpost(id);
					  jacademicyear.deleteByJobpost(id);
			    	  jskill.deleteByJobpost(id);
			    	  jplacementrounds.deleteByJobpost(id);
					arr.put("message", "Deleted Successfully!");
				}
		    	break;
		}
		return arr;
	}
	
}
