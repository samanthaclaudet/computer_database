package com.excilys.cdb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.service.impl.ComputerServiceImpl;

/**
 * Remove one or several computers from the database
 * 
 * @author sclaudet
 *
 */
@Controller
@RequestMapping("/delete-computer")
public class DeleteComputerController {

  @Autowired
  private ComputerServiceImpl computerServiceImpl;
  
  @RequestMapping(method=RequestMethod.POST)
  public String deleteComputers(@RequestParam("selection") String toDelete) {
    String[] computersId = toDelete.split(",");
    for (String id : computersId) {
        int computerId = Integer.parseInt(id);
        computerServiceImpl.delete(computerId);
    }
    return "redirect: dashboard";
  }

}
