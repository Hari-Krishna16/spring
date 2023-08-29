package de.zeroco.spring.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.zeroco.spring.dbutility.Utility;
import de.zeroco.spring.services.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeService service;

	@RequestMapping("/save")
	public ModelAndView save(@RequestParam String name, String number, String email, String dateOfBirth) {
		ModelAndView modelView = new ModelAndView();
		if(!Utility.isBlank(name) & (!Utility.isBlank(number)) & (!Utility.isBlank(email)) & (!Utility.isBlank(dateOfBirth))) {
		int insertionId = service.save(name, number, email, dateOfBirth);
		if(insertionId > 0) {
			modelView.addObject("save", "sucessfully insterted");
			modelView.setViewName("springTest.jsp");
		}else {
			modelView.addObject("save", "user all ready exist");
			modelView.setViewName("springTest.jsp");
		}
	 }else {
		 modelView.addObject("save", "please enter valid data");
			modelView.setViewName("springTest.jsp");
	 }
		return modelView;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam int id) {
		ModelAndView modelView = new ModelAndView();
		if(!Utility.isBlank(id)) {
        int deletedRows = service.delete(id);
        if(deletedRows > 0) {
        	modelView.addObject("delete", "sucessfully Deleted");
        	modelView.setViewName("springTest.jsp");
        }else {
        	modelView.addObject("delete", "no rows deleted");
        	modelView.setViewName("springTest.jsp");
        }
		}else {
			modelView.addObject("delete", "please enter valid details");
        	modelView.setViewName("springTest.jsp");
		}
		return modelView;
	}
	
	@RequestMapping("/get")
	public ModelAndView get(@RequestParam int id) {
		ModelAndView modelView = new ModelAndView();
		if(!Utility.isBlank(id) & id > 0) {
		Map<String, Object> dataSet = service.get(id);
		if(!dataSet.isEmpty()) {
			modelView.addObject("get", dataSet);
			modelView.setViewName("springTest.jsp");
		}else {
			modelView.addObject("getData", "no data found");
			modelView.setViewName("springTest.jsp");
		}
		}
		else {
			modelView.addObject("getValid", "enter data valid data to get data");
			modelView.setViewName("springTest.jsp");
		}
		return modelView;
	}
	
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView modelView = new ModelAndView();
		List<Map<String, Object>> dataSet = service.list();
		if(!dataSet.isEmpty()) {
			modelView.addObject("list", dataSet);
			modelView.setViewName("springTest.jsp");
		}else {
			modelView.addObject("listempty", "no data found");
			modelView.setViewName("springTest.jsp");
		}
		return modelView;
	}
	
	@RequestMapping("/update")
	public ModelAndView update(@RequestParam int id ,String name, String number, String email, String dateOfBirth) {
		ModelAndView modelView = new ModelAndView();
		if(!Utility.isBlank(name) & (!Utility.isBlank(number)) & (!Utility.isBlank(email)) & (!Utility.isBlank(dateOfBirth))) {
		int updatedRows = service.update(id, name, number,  email, dateOfBirth);
		if(updatedRows > 0) {
			modelView.addObject("updateSucess", "updated sucessfullly");
			modelView.setViewName("springTest.jsp");
		}else {
			modelView.addObject("updatefailure", "update failure");
			modelView.setViewName("springTest.jsp");
		}
		}else {
			modelView.addObject("updateValid", "enter valid details");
			modelView.setViewName("springTest.jsp");	
		}
		return modelView;
	}
}
