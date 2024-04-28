package in.ashokit.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.bindingforms.SearchCriteria;
import in.ashokit.entity.StudentEnq;
import in.ashokit.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	
	@Autowired
	private StudentService studentservice;
	
	@GetMapping("/add-enquiry")
	public String enqPage(HttpServletRequest req,Model model) {
		
		HttpSession	session = req.getSession(false);
		  Integer cid=(Integer) session.getAttribute("CID");
		  
		  if(cid == null) {
				
				return "redirect:/logout";
			}
		model.addAttribute("enq",new StudentEnq());
		return "addenquiry";
	}
	
	@PostMapping("/add-enquiry")
	public String addEnquiry(@ModelAttribute("enq") StudentEnq enq,HttpServletRequest req,Model model) {
		
	  HttpSession	session = req.getSession(false);
	  Integer cid=(Integer) session.getAttribute("CID");
	  
	  if(cid == null) {
			
			return "redirect:/logout";
		}
	  enq.setCid(cid);
	
		boolean enq1= studentservice.addEnq(enq);
		if(enq1) {
			model.addAttribute("sMsg", "enquiry added");
		}else {
			model.addAttribute("eMsg", "enquiry fail to add");
		}
		return "addenquiry";
	}
	
	@GetMapping("/view-enquiry")
	public String viewEnqPage(HttpServletRequest req,Model model) {
		
		HttpSession	session = req.getSession(false);
		Integer cid=(Integer) session.getAttribute("CID");
		
		if(cid == null) {
			
			return "redirect:/logout";
		}
		
		model.addAttribute("sc", new SearchCriteria());
		  
	List<StudentEnq> allenquiries= studentservice.getEnquiries(cid, new SearchCriteria());
	model.addAttribute("enquiries", allenquiries);
		return "viewEnquries";
	}
	
	@PostMapping("/filter-enquiry")
	public String filterEnquiries(@ModelAttribute("sc") SearchCriteria sc,HttpServletRequest req,Model model) {
		
		HttpSession	session = req.getSession(false);
		Integer cid=(Integer) session.getAttribute("CID");
		

		if(cid == null) {
			
			return "redirect:/logout";
		}
		
		List<StudentEnq> allenquiries= studentservice.getEnquiries(cid, sc);
		
		model.addAttribute("enquiries", allenquiries);
		return "filterEnqView";
	}
}
