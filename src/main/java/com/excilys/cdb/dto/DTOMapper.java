package com.excilys.cdb.dto;

import java.time.LocalDateTime;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.ui.Util;

public class DTOMapper {

    public static ComputerDTO computerToDTO(Computer computer) {
    	int id = computer.getId();
        String name = computer.getName();
        String introduced = null;
        if (computer.getIntroduced()!=null) {
        	introduced = computer.getIntroduced().toString();
        }
        String discontinued = null;
        if (computer.getDiscontinued()!=null) {
        	discontinued = computer.getDiscontinued().toString();
        }
        Company company = computer.getCompany();
        return new ComputerDTO(id, name, introduced, discontinued, company);
    }
    
    public static Computer DTOToComputer(ComputerDTO computerDTO) {
    	Computer computer;
    	int id = computerDTO.getId();
    	String name = computerDTO.getName();
    	LocalDateTime dateIn = Util.checkDate(computerDTO.getIntroduced());
    	LocalDateTime dateDis = Util.checkDate(computerDTO.getDiscontinued());
    	Company company = computerDTO.getCompany();
    	computer = new Computer(id, name, dateIn, dateDis, company);
        return computer;
    }
	
}
