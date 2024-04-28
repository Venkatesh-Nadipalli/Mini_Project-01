package in.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.bindingforms.DashboardResponse;
import in.ashokit.entity.Counsellor;
import in.ashokit.service.CounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {
	
	@Autowired
	private CounsellorService counsellorsvc;


	@GetMapping("/logout")
	public String logout(HttpServletRequest req,Model model) {
		HttpSession session = req.getSession(false);
		session.invalidate();
		return "redirect:/index";
	}
	
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("counsellor",new Counsellor());
		return "login";
	}
	
	@PostMapping("/login")
	public String handleLogin(Counsellor c,HttpServletRequest req, Model model) {
		
		Counsellor crd = counsellorsvc.loginCheck(c.getEmail(),c.getPwd());
		
		if (crd == null) {
			model.addAttribute("msg", "invalid credintials");
			return "login";
		}
		
		HttpSession session = req.getSession(true);
		session.setAttribute("CID", crd.getCid());
		
		return "redirect:dashboard";
	}
	
	@GetMapping("/register-view")
	public String regPage(Model model) {
		model.addAttribute("counsellor", new Counsellor());
		return "registration";
	}
	
	@PostMapping("/register-view")
	public String handleRegistration(Counsellor c,Model model) {
	String	msg = counsellorsvc.saveCounsellor(c);
	model.addAttribute("msg",msg);
		return "registration";
	}
	
	@GetMapping("/forgot-pwd")
	public String recoverPwdPage(Model model) {
		return "recoverpwd";
	}
	
	
	@GetMapping("/recover-pwd")
	public String handleForgotPwd(@RequestParam String email,Model model) {
		boolean eml = counsellorsvc.recoverPwd(email);
		if(eml) {
			model.addAttribute("sucMsg","your pwd is sent to your email");
		}else {
			model.addAttribute("errMsg","invalid email");
		}
		return "recoverpwd";
	}
	
	@GetMapping("/dashboard")
	public String buildDashboard(HttpServletRequest req,Model model) {
	
		HttpSession session = req.getSession(false);
		Object obj =session.getAttribute("CID");
		
         
		Integer cid = (Integer)obj;
		
        if(cid == null) {
			
			return "redirect:/logout";
		}
		DashboardResponse dashboardinfo = counsellorsvc.getDashBoardInfo(cid);
		model.addAttribute("db",dashboardinfo);
		
		return "dashboard";
	}
	
}
