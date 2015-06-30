package com.tocea.corolla.ui;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.domain.User;

public class JaversApplicationTests extends AbstractSpringTest {

	@Autowired
	private Javers javers;
	
	@Autowired
	private IUserDAO userDAO;
	
	@Test
	public void test() {
		
		User user = new User();
		user.setLogin("jsnow");
		user.setFirstName("Jon");
		user.setLastName("Snow");
		
		userDAO.save(user);
		javers.commit("me", user);
		
		user.setLastName("Snoooow");
		
		userDAO.save(user);
		javers.commit("me", user);
		
		List<CdoSnapshot> snapshots = javers.findSnapshots(
				QueryBuilder.byInstanceId(user.getId(), User.class).build()
		);
				
		assertEquals(2, snapshots.size()); 	
		
		assertEquals("Snow", snapshots.get(1).getPropertyValue("lastName"));
		assertEquals("Jon", snapshots.get(1).getPropertyValue("firstName"));
		
		assertEquals("Snoooow", snapshots.get(0).getPropertyValue("lastName"));
		assertEquals("Jon", snapshots.get(0).getPropertyValue("firstName"));		
		
		List<String> changes = snapshots.get(0).getChangedPropertyNames();
		
		assertEquals(1, changes.size());
		assertEquals("lastName", changes.get(0));
		
	}

}
