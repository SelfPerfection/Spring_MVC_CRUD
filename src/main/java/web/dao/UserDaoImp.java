package web.dao;

import web.model.User;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.List;

@Repository
@Transactional
public class UserDaoImp implements UserDao {
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User getUserById(int id) {
        return getEntityManager().find(User.class, id);
    }

    @Override
    public void createUser(User user) {
        getEntityManager().persist(user);
    }

    @Override
    public void updateUser(User user) {
        getEntityManager().merge(user);
    }

    @Override
    public void deleteUser(int id) {
        String jpql = "DELETE FROM User u WHERE u.id=:id";
        int isSuccessful = getEntityManager().createQuery(jpql)
                .setParameter("id", id)
                .executeUpdate();

        /* Exception example
         *
        if (isSuccessful == 0) {
            throw new SQLException("Can not delete User with id = " + id);
        }
        */
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        String jpql = "SELECT u FROM User u";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        return query.getResultList();
    }
}
