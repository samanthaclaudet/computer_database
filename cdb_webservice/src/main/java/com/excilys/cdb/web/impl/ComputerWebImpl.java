package com.excilys.cdb.web.impl;

import java.util.List;

import javax.jws.WebService;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.DTOMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.interfaces.ComputerService;
import com.excilys.cdb.web.interfaces.ComputerWeb;
import com.excilys.cdb.web.wrapper.ListWrapper;

@WebService(endpointInterface = "com.excilys.cdb.web.interfaces.ComputerWeb")
public class ComputerWebImpl implements ComputerWeb {

  private ComputerService computerService;
  private DTOMapper       dtoMapper;

  public ComputerWebImpl(ComputerService computerService, DTOMapper dtoMapper) {
    this.computerService = computerService;
    this.dtoMapper = dtoMapper;
  }

  @Override
  public String getHelloWorldAsString(String name) {
    return "Hello World JAX-WS " + name;
  }

  @Override
  public int getNbComputers() {
    return computerService.getNbComputers();
  }

  @Override
  public ListWrapper<ComputerDTO> getAll() {
    List<ComputerDTO> c = dtoMapper.listToDto(computerService.getAll());
    ListWrapper<ComputerDTO> cw = new ListWrapper<>();
    cw.setList(c);
    return cw;
  }

  @Override
  public ComputerDTO getById(int id) {
    Computer c = computerService.getById(id);
    if (c == null) {
      return null;
    }
    return dtoMapper.computerToDTO(c);
  }

  @Override
  public Page getPage(String name, int idx, int size, String orderBy) {
    return computerService.getPage(name, idx, size, orderBy);
  }

  @Override
  public void set(ComputerDTO c) {
    computerService.set(dtoMapper.DTOToComputer(c));
  }

  @Override
  public void update(ComputerDTO c) {
    computerService.update(dtoMapper.DTOToComputer(c));
  }

  @Override
  public void delete(int id) {
    computerService.delete(id);
  }

}
