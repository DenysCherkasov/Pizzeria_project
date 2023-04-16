package com.cherkasov.repositories.drinksRepository;

import com.cherkasov.dto.DrinksMainInfoDto;
import com.cherkasov.dto.DrinksMainInfoForMenuDto;

import java.util.List;
import java.util.Optional;

public interface CustomizedDrinksRepository {

    List<DrinksMainInfoDto> getDrinksInfo();

    List<DrinksMainInfoForMenuDto> getSortedNonAlcoholDrinksInfoForMenu(String order, String dir);

    List<DrinksMainInfoForMenuDto> getSortedAlcoholDrinksInfoForMenu(String order, String dir);

    Optional<DrinksMainInfoForMenuDto> getDrinksInfoById(String id);


}
