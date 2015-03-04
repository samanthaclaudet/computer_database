package com.excilys.cdb.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * Mapping Computer <--> ComputerDTO
 * 
 * @author sclaudet
 *
 */
public class DTOMapper {
  
    public static DateTimeFormatter dateTimeFormatter;
    public static Pattern pattern;
  
    static {
      // Date validation
      AbstractApplicationContext context = new ClassPathXmlApplicationContext("/application-context.xml");
      MessageSource message = context.getBean(MessageSource.class);
      String regex = message.getMessage("label.regex", null, LocaleContextHolder.getLocale());
      String formatter = message.getMessage("label.formatter", null, LocaleContextHolder.getLocale());
      pattern = Pattern.compile(regex);
      dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
      context.close();
    }
  
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
		    introduced = computer.getIntroduced().format(dateTimeFormatter);
		}
		String discontinued = null;
		if (computer.getDiscontinued() != null) {
		  discontinued = computer.getDiscontinued().format(dateTimeFormatter);
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
		
		LocalDateTime dateIn = null;
		String date = computerDTO.getIntroduced().replace('T', ' ');
		if (pattern.matcher(date).find()) {
          dateIn = LocalDateTime.parse(date, dateTimeFormatter);
        }
		

		LocalDateTime dateDis = null;
		date = computerDTO.getDiscontinued().replace('T', ' ');
		if (pattern.matcher(date).find()) {
          dateDis = LocalDateTime.parse(date, dateTimeFormatter);
        }
		
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
