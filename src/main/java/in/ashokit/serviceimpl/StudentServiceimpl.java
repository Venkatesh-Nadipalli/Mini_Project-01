package in.ashokit.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.ashokit.bindingforms.SearchCriteria;
import in.ashokit.entity.StudentEnq;
import in.ashokit.repo.StudentEnqRepo;
import in.ashokit.service.StudentService;

@Service
public class StudentServiceimpl implements StudentService {

	@Autowired
	private StudentEnqRepo studentrepo;
	
	@Override
	public boolean addEnq(StudentEnq se) {
		
      StudentEnq savedenq = studentrepo.save(se);
      
		return savedenq.getEnqId()!= null;
	}

	@Override
	public List<StudentEnq> getEnquiries(Integer Cid, SearchCriteria sc) {
		
		StudentEnq enq = new StudentEnq();
		
		enq.setCid(Cid);
		
		if(sc.getClassMode() != null && sc.getClassMode()!= ("")) {
			enq.setClassMode(sc.getClassMode());
		}
		if(sc.getCourseName() != null && sc.getCourseName()!= ("")) {
			enq.setCourseName(sc.getCourseName());
		}
		if(sc.getEnqStatus() != null && sc.getEnqStatus()!= ("")) {
			enq.setEnqStatus(sc.getEnqStatus());
		}
		
		Example<StudentEnq> of = Example.of(enq);
		List<StudentEnq> enquiries = studentrepo.findAll(of);
		return enquiries;
	}

}
