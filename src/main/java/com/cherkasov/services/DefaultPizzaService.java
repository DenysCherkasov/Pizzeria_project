package com.cherkasov.services;


import com.cherkasov.dto.DefaultPizzaMainInfoDto;
import com.cherkasov.models.dish.DefaultPizza;
import com.cherkasov.repositories.defaultPizzaRepository.DefaultPizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultPizzaService {
    private final DefaultPizzaRepository defaultPizzaRepository;

    @Autowired
    public DefaultPizzaService(DefaultPizzaRepository defaultPizzaRepository) {
        this.defaultPizzaRepository = defaultPizzaRepository;
    }

    public void save(final DefaultPizza defaultPizza) {
        defaultPizzaRepository.save(defaultPizza);
    }

    public Iterable<DefaultPizza> getAll() {
        return defaultPizzaRepository.findAll();
    }

    public Optional<DefaultPizza> getById(final String id) {
        return defaultPizzaRepository.findById(id);
    }

    public List<DefaultPizzaMainInfoDto> getDefaultPizzaInfo() {
        return defaultPizzaRepository.getDefaultPizzaInfo();
    }

    public List<DefaultPizzaMainInfoDto> getDefaultPizzaInfoForMenu() {
        return defaultPizzaRepository.getDefaultPizzaInfoForMenu();
    }

    public List<DefaultPizzaMainInfoDto> getSortedDefaultPizzaInfoForMenu(final String order, final String dir) {
        return defaultPizzaRepository.getSortedDefaultPizzaInfoForMenu(order, dir);
    }

    public Optional<DefaultPizzaMainInfoDto> getDefaultPizzaInfoById(final String id) {
        return defaultPizzaRepository.getDefaultPizzaInfoById(id);
    }

    public void deleteById(final String id) {
        defaultPizzaRepository.deleteById(id);

    }

}
