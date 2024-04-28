package in.ashokit.service;

import in.ashokit.bindingforms.DashboardResponse;
import in.ashokit.entity.Counsellor;


public interface CounsellorService {

	public String saveCounsellor(Counsellor c);
	
	public Counsellor loginCheck(String email, String pwd);
	
	public boolean recoverPwd(String email);
	
	public DashboardResponse getDashBoardInfo(Integer Cid);
}
