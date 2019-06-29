package de.berlin.htw.kbe.gruppe1.maumau.persistence.management;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Persistence;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.springframework.stereotype.Component;

import de.berlin.htw.kbe.gruppe1.maumau.cardGame.modell.MauMau;
import de.berlin.htw.kbe.gruppe1.maumau.persistence.PersistenceService;
import de.berlin.htw.kbe.gruppe1.maumau.persistence.modell.JPAHandler;
import de.berlin.htw.kbe.gruppe1.maumau.util.exceptions.DbConnectionException;
import de.berlin.htw.kbe.gruppe1.maumau.util.exceptions.LoadGameException;
import de.berlin.htw.kbe.gruppe1.maumau.util.exceptions.SaveOrUpdateException;

@Component
public class PersistenceMgmt implements PersistenceService {

	@Override
	public void persistGame(MauMau maumau, JPAHandler handler) {
		// TODO Auto-generated method stub
		try {
		handler.getEm().getTransaction().begin();
		
		handler.getEm().persist(maumau);
		
		handler.getEm().getTransaction().commit();
		}catch(JDBCException e) {
			throw new SaveOrUpdateException();
		}
		
	}

	@Override
	public JPAHandler establishConnection(String instanceName, JPAHandler handler) {
		// TODO Auto-generated method stub
		/*try {
		handler.setEm(Persistence.createEntityManagerFactory(instanceName).createEntityManager());
		}catch(HibernateException e) {
			throw new DbConnectionException();
		}
		return handler;*/
		return null;
	}

	@Override
	public List<MauMau> loadGames(JPAHandler handler, List<String> userNames) {
		// TODO Auto-generated method stub
		List<MauMau> unfinishedGames = null;
		String query = "SELECT DISTINCT g.id FROM Games g ";
		 //AND u.username = 'one' OR u.username = 'two'
		String innerJoin = " INNER JOIN Users ";
		String on = " ON ";
		String gameId = " gameId ";
		String gid = " id ";
		String gameTablePrefix = "g";
		String userTablePrefix = "u";
		String and = " AND ";
		String or = " OR ";
		String whereUserName = " username = '";
		String whereClause = " WHERE winnerId is NULL ";
		
		int userIndex = 0;
		
		for(String username : userNames) {
			//INNER JOIN User {u##} ON u##.gameId = g.Id
			query += innerJoin + userTablePrefix + userIndex + on + userTablePrefix + userIndex + "." + gameId + " = " + gameTablePrefix + "." + gid;
			//AND u##.username = '{username}'
			whereClause += and + userTablePrefix + userIndex + "." + whereUserName + username + "'";
			userIndex++;
		}
		
		query += whereClause;
		
		try {
		handler.getEm().getTransaction().begin();
		
		List<Integer> unfinishedGameIds = handler.getEm().createNativeQuery(query).getResultList();
		
		for(Integer id : unfinishedGameIds) {
			if(unfinishedGames == null) {
				unfinishedGames = new LinkedList<MauMau>();	
			}
			unfinishedGames.add(handler.getEm().find(MauMau.class, id));
		}
		handler.getEm().getTransaction().commit();
		}catch(JDBCException e) {
			throw new LoadGameException();
		}
		
		return unfinishedGames;
	}
}
