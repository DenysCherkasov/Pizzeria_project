package com.cherkasov.repositories.userRepository;

import com.cherkasov.dto.UserMainInfoDto;
import com.cherkasov.models.user.Role;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class CustomizedUserRepositoryImpl implements CustomizedUserRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    @Override
    public void setTypeById(String id, Role role) {
        Query query = entityManager.createQuery("update User u set u.role = :role where u.id = :id ");
        query.setParameter("role", role);
        query.setParameter("id", id);
        query.executeUpdate();
    }


    @Override
    public List<UserMainInfoDto> getUsersInfo() {
        return entityManager.createQuery(
                        "SELECT new com.cherkasov.dto.UserMainInfoDto " +
                                "(u.id, u.name, u.phoneNumber, u.email, u.role) " +
                                "FROM User u ORDER BY u.name",
                        UserMainInfoDto.class)
                .getResultList();
    }

    public Optional<UserMainInfoDto> findUserInfoByEmail(String email) {
        return entityManager.createQuery(
                        "SELECT new com.cherkasov.dto.UserMainInfoDto " +
                                "(u.id, u.name, u.phoneNumber, u.email, u.role) " +
                                "FROM User u WHERE u.email = " + email,
                        UserMainInfoDto.class)
                .getResultStream().findAny();
    }


}
