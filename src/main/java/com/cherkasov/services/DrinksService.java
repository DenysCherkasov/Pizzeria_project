package com.cherkasov.services;

import com.cherkasov.dto.DrinksMainInfoDto;
import com.cherkasov.dto.DrinksMainInfoForMenuDto;
import com.cherkasov.models.dish.Drinks;
import com.cherkasov.repositories.drinksRepository.DrinksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrinksService {
    private final DrinksRepository drinksRepository;

    @Autowired
    public DrinksService(DrinksRepository drinksRepository) {
        this.drinksRepository = drinksRepository;
    }

    public void save(final Drinks drinks) {
        drinksRepository.save(drinks);
    }

    public Iterable<Drinks> getAll() {
        return drinksRepository.findAll();
    }

    public Optional<Drinks> getById(final String id) {
        return drinksRepository.findById(id);
    }

    public List<DrinksMainInfoDto> getDrinksInfo() {
        return drinksRepository.getDrinksInfo();
    }

    public List<DrinksMainInfoForMenuDto> getSortedAlcoholDrinksInfoForMenu(final String order, final String dir) {
        return drinksRepository.getSortedAlcoholDrinksInfoForMenu(order, dir);
    }

    public List<DrinksMainInfoForMenuDto> getSortedNonAlcoholDrinksInfoForMenu(final String order, final String dir) {
        return drinksRepository.getSortedNonAlcoholDrinksInfoForMenu(order, dir);
    }

    public Optional<DrinksMainInfoForMenuDto> getDrinksInfoById(final String id) {
        return drinksRepository.getDrinksInfoById(id);
    }

    public void deleteById(final String id) {
        drinksRepository.deleteById(id);
    }

}
