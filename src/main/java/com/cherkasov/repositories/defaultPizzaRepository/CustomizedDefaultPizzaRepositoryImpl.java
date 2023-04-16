package com.cherkasov.repositories.defaultPizzaRepository;

import com.cherkasov.dto.DefaultPizzaMainInfoDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class CustomizedDefaultPizzaRepositoryImpl implements CustomizedDefaultPizzaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DefaultPizzaMainInfoDto> getDefaultPizzaInfo() {
        return entityManager.createQuery(
                        "SELECT new com.cherkasov.dto.DefaultPizzaMainInfoDto " +
                                "(d.id, d.name, d.image, d.description, " +
                                "d.size, d.price, d.weight, d.availableStatus) " +
                                "FROM DefaultPizza d ORDER BY d.name",
                        DefaultPizzaMainInfoDto.class)
                .getResultList();
    }

    @Override
    public List<DefaultPizzaMainInfoDto> getSortedDefaultPizzaInfoForMenu(String order, String dir) {
        String query = String.format("SELECT new com.cherkasov.dto.DefaultPizzaMainInfoDto " +
        "(d.id, d.name, d.image, d.description, " +
                "d.size, d.price, d.weight, d.availableStatus) " +
                "FROM DefaultPizza d " +
                "WHERE d.availableStatus = true " +
                "ORDER BY d.%s %s", order, dir);
        return entityManager.createQuery(query, DefaultPizzaMainInfoDto.class)
                .getResultList();
    }

    @Override
    public List<DefaultPizzaMainInfoDto> getDefaultPizzaInfoForMenu() {
        return entityManager.createQuery(
                        "SELECT new com.cherkasov.dto.DefaultPizzaMainInfoDto " +
                                "(d.id, d.name, d.image, d.description, " +
                                "d.size, d.price, d.weight, d.availableStatus) " +
                                "FROM DefaultPizza d " +
                                "WHERE d.availableStatus = true " +
                                "ORDER BY d.name", DefaultPizzaMainInfoDto.class)
                .getResultList();
    }

    @Override
    public Optional<DefaultPizzaMainInfoDto> getDefaultPizzaInfoById(String id) {
        return entityManager.createQuery(
                        "SELECT new com.cherkasov.dto.DefaultPizzaMainInfoDto " +
                                "(d.id, d.name, d.image, d.description, " +
                                "d.size, d.price, d.weight, d.availableStatus) " +
                                "FROM DefaultPizza d " +
                                "WHERE d.id = :id",
                        DefaultPizzaMainInfoDto.class)
                .setParameter("id", id)
                .getResultStream().findAny();
    }

}