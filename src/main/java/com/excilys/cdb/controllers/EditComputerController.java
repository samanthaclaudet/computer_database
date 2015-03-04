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
 * Controller updating a computer in the database
 * 
 * @author sclaudet
 *
 */
@Controller
@RequestMapping("/edit-computer")
public class EditComputerController {

	private int computerId;

	@Autowired
	private ComputerServiceImpl computerServiceImpl;

	@Autowired
	private CompanyServiceImpl companyServiceImpl;

	/**
	 * Displays all the companies in the drop-down menu Gets the computer to
	 * update id's Puts the computer's data as default values
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String displayComputerData(@RequestParam(value = "id", required = false, defaultValue = "0") int id, ModelMap map) {
	  
	    if (id != 0) {
           computerId = id;
        }
	    
		List<Company> companies = companyServiceImpl.getAll();
		map.addAttribute("companies", companies);
		map.addAttribute("idComputer", computerId);

		Computer c = computerServiceImpl.getById(computerId);
		ComputerDTO cDTO = DTOMapper.computerToDTO(c);
		map.addAttribute("computerDTO", cDTO);
		return "editComputer";
	}

	/**
	 * Updates a computer
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String editComputerInDatabase(@ModelAttribute("computerDTO") @Valid ComputerDTO computerDTO,
			BindingResult bindingResult,
			@RequestParam("companyId") int companyId, ModelMap map) {
	  
		if (bindingResult.hasErrors()) {
			List<Company> companies = companyServiceImpl.getAll();
			map.addAttribute("companies", companies);
			return "editComputer";
		}
		
		Company company =  companyServiceImpl.getById(companyId);
		computerDTO.setCompany(company);
		Computer c = DTOMapper.DTOToComputer(computerDTO);
		computerServiceImpl.update(computerId, c);

		return "redirect: dashboard";
	}

}
