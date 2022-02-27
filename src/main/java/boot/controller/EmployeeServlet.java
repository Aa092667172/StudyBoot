package boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.EmployeeVO;
import com.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeServlet {
	
	@Autowired
	EmployeeService service;
	
	@RequestMapping("/getOne")
	public List<EmployeeVO> test() {
		return service.findAll();
	}
}
