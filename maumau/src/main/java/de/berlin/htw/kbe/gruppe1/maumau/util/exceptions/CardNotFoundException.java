package de.berlin.htw.kbe.gruppe1.maumau.util.exceptions;

public class CardNotFoundException extends RuntimeException implements ICustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8359031480827145320L;

	@Override
	public void printFailureMessage() {
		// TODO Auto-generated method stub
		System.out.println("Error ID: " + serialVersionUID);
		System.out.println("Unknown Card played");
		System.out.println("The played card is unknown, can't process this card.");
		this.printStackTrace();
	}

	@Override
	public void printCustomFailureMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
	}

	@Override
	public void printCustomFailureMessages(String... messages) {
		// TODO Auto-generated method stub
		for(String message : messages) {
			System.out.println(message);
		}
	}
}
