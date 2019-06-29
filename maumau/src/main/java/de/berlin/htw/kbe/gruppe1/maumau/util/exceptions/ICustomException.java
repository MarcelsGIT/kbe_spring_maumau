package de.berlin.htw.kbe.gruppe1.maumau.util.exceptions;

public interface ICustomException {

	public void printFailureMessage();
	
	public void printCustomFailureMessage(String message);
	
	public void printCustomFailureMessages(String ... messages);
}
