package com.cherkasov.services;

import com.cherkasov.dto.SouseMainInfoDto;
import com.cherkasov.models.pizzaParts.Souse;
import com.cherkasov.repositories.souseRepository.SouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SouseService {
    private final SouseRepository souseRepository;

    @Autowired
    public SouseService(SouseRepository souseRepository) {
        this.souseRepository = souseRepository;
    }

    public void save(final Souse souse) {
        souseRepository.save(souse);
    }

    public Iterable<Souse> getAll() {
        return souseRepository.findAll();
    }

    public Optional<Souse> getById(final String id) {
        return souseRepository.findById(id);
    }

    public List<SouseMainInfoDto> getSouseInfo() {
        return souseRepository.getSouseInfo();
    }

    public void deleteById(final String id) {
        souseRepository.deleteById(id);

    }


}
