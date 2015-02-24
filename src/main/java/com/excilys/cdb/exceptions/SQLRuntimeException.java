package com.excilys.cdb.exceptions;

/**
 * Exception returned when a problem occurs during the deletion of a company
 * 
 * @author sclaudet
 *
 */
public class SQLRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SQLRuntimeException() {
		System.out.println("An error occurred, the transaction is being rolled back");
	}
	
}
