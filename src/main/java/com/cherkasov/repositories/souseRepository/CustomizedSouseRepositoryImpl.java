package com.cherkasov.repositories.souseRepository;

import com.cherkasov.dto.SouseMainInfoDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CustomizedSouseRepositoryImpl implements CustomizedSouseRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List <SouseMainInfoDto> getSouseInfo() {
        return entityManager.createQuery(
                        "SELECT new com.cherkasov.dto.SouseMainInfoDto " +
                                "(s.id, s.name, s.price, s.weight, s.spice) " +
                                "FROM Souse s ORDER BY s.name",
                        SouseMainInfoDto.class)
                .getResultList();

    }

}
