package com.cherkasov.repositories.drinksRepository;

import com.cherkasov.dto.DrinksMainInfoDto;
import com.cherkasov.dto.DrinksMainInfoForMenuDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class CustomizedDrinksRepositoryImpl implements CustomizedDrinksRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<DrinksMainInfoDto> getDrinksInfo() {
        return entityManager.createQuery(
                        "SELECT new com.cherkasov.dto.DrinksMainInfoDto " +
                                "(d.id, d.name, d.image, d.price, d.volume, d.availableStatus, d.drinksType) " +
                                "FROM Drinks d ORDER BY d.name",
                        DrinksMainInfoDto.class)
                .getResultList();
    }

    @Override
    public List<DrinksMainInfoForMenuDto> getSortedAlcoholDrinksInfoForMenu(String order, String dir) {
        String query = String.format("SELECT new com.cherkasov.dto.DrinksMainInfoForMenuDto " +
                "(d.id, d.name, d.image, d.price, d.volume) " +
                "FROM Drinks d " +
                "WHERE d.availableStatus = true AND d.drinksType = 'ALCOHOL' " +
                "ORDER BY d.%s %s", order, dir);
        return entityManager.createQuery(query, DrinksMainInfoForMenuDto.class)
                .getResultList();
    }

    @Override
    public List<DrinksMainInfoForMenuDto> getSortedNonAlcoholDrinksInfoForMenu(String order, String dir) {
        String query = String.format("SELECT new com.cherkasov.dto.DrinksMainInfoForMenuDto " +
                "(d.id, d.name, d.image, d.price, d.volume) " +
                "FROM Drinks d " +
                "WHERE d.availableStatus = true AND d.drinksType = 'NON_ALCOHOL' " +
                "ORDER BY d.%s %s", order, dir);
        return entityManager.createQuery(query, DrinksMainInfoForMenuDto.class)
                .getResultList();
    }

    public Optional<DrinksMainInfoForMenuDto> getDrinksInfoById(String id) {
        return entityManager.createQuery(
                        "SELECT new com.cherkasov.dto.DrinksMainInfoForMenuDto " +
                                "(d.id, d.name, d.image, d.price, d.volume) " +
                                "FROM Drinks d " +
                                "WHERE d.id = :id",
                        DrinksMainInfoForMenuDto.class)
                .setParameter("id", id)
                .getResultStream().findAny();
    }

}
