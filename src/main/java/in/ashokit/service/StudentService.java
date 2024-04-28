package in.ashokit.service;

import java.util.List;

import in.ashokit.bindingforms.SearchCriteria;
import in.ashokit.entity.StudentEnq;


public interface StudentService {

	public boolean addEnq(StudentEnq se);
	
	public List<StudentEnq> getEnquiries(Integer Cid, SearchCriteria s);
}
