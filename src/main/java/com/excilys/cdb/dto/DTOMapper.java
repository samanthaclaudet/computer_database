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
import com.excilys.cdb.model.Page;

/**
 * Mapping Computer <--> ComputerDTO
 *     and Page     <--> PageDTO
 * 
 * @author sclaudet
 *
 */
public class DTOMapper {
  
    private static MessageSource message;
    private static DateTimeFormatter dateTimeFormatter;
    private static Pattern pattern;
  
    static {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("/application-context.xml");
        message = context.getBean(MessageSource.class);
        context.close();
    }
    
    public static void getFormat() {
      // Date validation
      String regex = message.getMessage("label.regex", null, LocaleContextHolder.getLocale());
      String formatter = message.getMessage("label.formatter", null, LocaleContextHolder.getLocale());
      pattern = Pattern.compile(regex);
      dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
    }
  
    /**
     * Maps a page into a pageDTO
     * 
     * @param Page
     * @return PageDTO
     */
    public static PageDTO pageToDTO(Page page) {
        int nbComputers = page.getNbComputers();
        int nbComputerPerPage = page.getNbComputerPerPage();
        int nbPages = page.getNbPages();
        int idx = page.getIdx();
        int[] range = page.getRange();
        
        List<ComputerDTO> computers = listToDto(page.getComputers());
      
        return new PageDTO(nbComputers, nbComputerPerPage, nbPages, computers, idx, range);
    }

    /**
     * Maps a pageDTO into a page
     * 
     * @param PageDTO
     * @return Page
     */
    public static Page DTOToPage(PageDTO pageDTO) {
        int nbComputers = pageDTO.getNbComputers();
        int nbComputerPerPage = pageDTO.getNbComputerPerPage();
        int nbPages = pageDTO.getNbPages();
        int idx = pageDTO.getIdx();
        int[] range = pageDTO.getRange();
        
        List<Computer> computers = listFromDto(pageDTO.getComputers());

        return new Page(nbComputers, nbComputerPerPage, nbPages, computers, idx, range);
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
		getFormat();

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
		getFormat();
		
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
