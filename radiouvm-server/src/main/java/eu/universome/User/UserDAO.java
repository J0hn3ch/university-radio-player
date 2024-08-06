package eu.universome.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// ATTENZIONE: Utilizza questo oppure UserRepository, sono due implementazioni diverse per recuperare i dati dal DB

@Repository
@Transactional
public class UserDAO {
	
	@Autowired
	private EntityManager entityManager;

	public User findUserByEmail(String email) {
        try {
            String sql = "SELECT e FROM " + User.class.getName() + " e " 
                    + " WHERE e.email = :email ";

            Query query = entityManager.createQuery(sql, User.class);
            query.setParameter("email", email);

            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
