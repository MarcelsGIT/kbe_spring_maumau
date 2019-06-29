package de.berlin.htw.kbe.gruppe1.maumau.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import de.berlin.htw.kbe.gruppe1.maumau.cardGame.modell.MauMau;
import de.berlin.htw.kbe.gruppe1.maumau.persistence.modell.JPAHandler;

public interface PersistenceService {
	
	public void persistGame(MauMau maumau, JPAHandler handler);
	public JPAHandler establishConnection(String instanceName, JPAHandler handler);
	public List<MauMau> loadGames(JPAHandler handler, List<String> userNames);
}
