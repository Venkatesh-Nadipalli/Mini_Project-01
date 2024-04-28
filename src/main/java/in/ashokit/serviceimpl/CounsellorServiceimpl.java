package in.ashokit.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.bindingforms.DashboardResponse;
import in.ashokit.entity.Counsellor;
import in.ashokit.entity.StudentEnq;
import in.ashokit.repo.CounsellorRepo;
import in.ashokit.repo.StudentEnqRepo;
import in.ashokit.service.CounsellorService;
import in.ashokit.util.EmailUtils;

@Service
public class CounsellorServiceimpl implements CounsellorService {

	@Autowired
	private CounsellorRepo counsellorrepo;
	
	@Autowired
	private EmailUtils emailutils;
	
	@Autowired
	private StudentEnqRepo studentenqrepo;
	
	@Override
	public String saveCounsellor(Counsellor c) {
		
		Counsellor obj = counsellorrepo.findByEmail(c.getEmail());
		if(obj != null) {
			return "already used this mail id";
		}
		 Counsellor savedobj = counsellorrepo.save(c);
		 
		 if(savedobj.getCid() != null) {
			 return "Registration successful";
		 }
		return "Registration failed";
	}

	@Override
	public Counsellor loginCheck(String email, String pwd) {
		return counsellorrepo.findByEmailAndPwd(email, pwd);
		
	}

	@Override
	public boolean recoverPwd(String email) {
	      Counsellor eml = counsellorrepo.findByEmail(email);
	      
	      if(eml == null) {
	    	  return false;
	      }
	      String sub = "Recover Password from Website";
	      String body = "<h1>Password is  : "+eml.getPwd()+"</h1>";
	     return emailutils.sendEmail(sub, body, email);
	      
	}

	@Override
	public DashboardResponse getDashBoardInfo(Integer cid) {
		
		 List<StudentEnq> allenq = studentenqrepo.findByCid(cid);
		 
		 int enrolledenq = allenq.stream()
				                 .filter(e -> e.getEnqStatus().equals("Enrolled"))
				                 .collect(Collectors.toList())
				                 .size();
		 
		 DashboardResponse resp = new DashboardResponse();
		 
		 resp.setEnrolledEnq(enrolledenq);
		 
		 resp.setLostEnq(allenq.size()-enrolledenq);
		 
		 resp.setTotalEnq(allenq.size());
		 
		return resp;
	}

}
