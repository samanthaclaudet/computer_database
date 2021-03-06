package com.excilys.cdb.controllers;

import com.excilys.cdb.dto.DTOMapper;
import com.excilys.cdb.dto.PageDTO;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.interfaces.ComputerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Displays all the computer with pagination
 * It is possible to search by computer name or company name
 * 
 * @author sclaudet
 *
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

  public final static String PARAM_LANGUAGE     = "language";
  public final static String PARAM_ORDER        = "order";
  public final static String PARAM_SEARCH       = "search";
  public final static String PARAM_NB_PER_PAGE  = "nbPerPage";
  public final static String PARAM_PAGE         = "page";
  public final static String PARAM_NB_COMPUTERS = "nbcomputers";

  @Autowired
  private ComputerService    computerService;

  @Autowired
  private DTOMapper          dtoMapper;

  /**
   * Displays all the computers with pagination
   */
  @RequestMapping(method = RequestMethod.GET)
  public String displayComputers(
      @RequestParam(value = PARAM_LANGUAGE, required = false, defaultValue = "fr") String language,
      @RequestParam(value = PARAM_ORDER, required = false, defaultValue = "") String orderBy,
      @RequestParam(value = PARAM_SEARCH, required = false, defaultValue = "") String searchName,
      @RequestParam(value = PARAM_NB_PER_PAGE, required = false, defaultValue = "10") int nbComputerPerPage,
      @RequestParam(value = PARAM_PAGE, required = false, defaultValue = "1") int pageNumber,
      ModelMap map) {

    int nbComputers = 0;
    Page p = null;

    p = computerService.getPage(searchName, pageNumber - 1, nbComputerPerPage, orderBy);

    PageDTO pageDTO = dtoMapper.pageToDTO(p);
    nbComputers = pageDTO.getNbComputers();

    map.addAttribute(PARAM_NB_COMPUTERS, nbComputers);
    map.addAttribute(PARAM_PAGE, pageDTO);
    map.addAttribute(PARAM_SEARCH, searchName);
    map.addAttribute(PARAM_ORDER, orderBy);
    map.addAttribute(PARAM_LANGUAGE, language);

    return "dashboard";
  }

}
