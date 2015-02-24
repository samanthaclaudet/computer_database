package com.excilys.cdb.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.ui.Util;

/**
 * Mapping Computer <--> ComputerDTO
 * 
 * @author sclaudet
 *
 */
public class DTOMapper {

	/**
	 * Maps a computer into a computerDTO
	 * 
	 * @param Computer
	 * @return ComputerDTO
	 */
	public static ComputerDTO computerToDTO(Computer computer) {
		int id = computer.getId();
		String name = computer.getName();
		String introduced = null;
		if (computer.getIntroduced() != null) {
			introduced = computer.getIntroduced().toString();
		}
		String discontinued = null;
		if (computer.getDiscontinued() != null) {
			discontinued = computer.getDiscontinued().toString();
		}
		Company company = computer.getCompany();
		return new ComputerDTO(id, name, introduced, discontinued, company);
	}

	/**
	 * Maps a computerDTO into a computer
	 * 
	 * @param ComputerDTO
	 * @return Computer
	 */
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

	/**
	 * Maps a list of computer into a list of computerDTO
	 * 
	 * @param computers
	 *            a list of Computer
	 * @return computerDTOs a list of ComputerDTO
	 */
	public static List<ComputerDTO> listToDto(List<Computer> computers) {
		return computers.stream().map(c -> computerToDTO(c))
				.collect(Collectors.toList());
	}

	/**
	 * Maps a list of computerDTO into a list of computer
	 * 
	 * @param computerDTOs
	 *            a list of ComputerDTO
	 * @return computers a list of Computer
	 */
	public static List<Computer> listFromDto(List<ComputerDTO> computerDTOs) {
		return computerDTOs.stream().map(c -> DTOToComputer(c))
				.collect(Collectors.toList());
	}

}
