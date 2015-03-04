package com.excilys.cdb.controllers;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.DTOMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.impl.CompanyServiceImpl;
import com.excilys.cdb.service.impl.ComputerServiceImpl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller adding a computer to the database
 * 
 * @author sclaudet
 *
 */
@Controller
@RequestMapping("/add-computer")
public class AddComputerController {

	@Autowired
	private ComputerServiceImpl computerServiceImpl;
	
	@Autowired
	private CompanyServiceImpl companyServiceImpl;
	
	/**
	 * Displays all the companies in the drop-down menu
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String displayCompanies(ModelMap map) {	
		List<Company> companies = companyServiceImpl.getAll();
		map.addAttribute("companies", companies);
		map.addAttribute("computerDTO", new ComputerDTO());
		return "addComputer";
	}

	/**
	 * Adds a computer to the database
	 */
	@RequestMapping(method=RequestMethod.POST)
	public String addComputerToDatabase(@ModelAttribute("computerDTO") @Valid ComputerDTO computerDTO,
			BindingResult bindingResult,
			@RequestParam("companyId") int companyId, ModelMap map) {

		if (bindingResult.hasErrors()) {
			List<Company> companies = companyServiceImpl.getAll();
			map.addAttribute("companies", companies);
			return "addComputer";
		}
		
		Company company =  companyServiceImpl.getById(companyId);
		computerDTO.setCompany(company);
		Computer c = DTOMapper.DTOToComputer(computerDTO);
		computerServiceImpl.set(c);
		return "redirect: dashboard";
	}

}
