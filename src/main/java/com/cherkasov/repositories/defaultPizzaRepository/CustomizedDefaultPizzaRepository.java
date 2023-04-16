package com.cherkasov.repositories.defaultPizzaRepository;

import com.cherkasov.dto.DefaultPizzaMainInfoDto;

import java.util.List;
import java.util.Optional;

public interface CustomizedDefaultPizzaRepository {
    List<DefaultPizzaMainInfoDto> getDefaultPizzaInfo();

    List<DefaultPizzaMainInfoDto> getDefaultPizzaInfoForMenu();

    List<DefaultPizzaMainInfoDto> getSortedDefaultPizzaInfoForMenu(String order, String dir);

    Optional<DefaultPizzaMainInfoDto> getDefaultPizzaInfoById(String id);


}
