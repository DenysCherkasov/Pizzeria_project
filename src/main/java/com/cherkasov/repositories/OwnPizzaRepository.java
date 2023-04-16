package com.cherkasov.repositories;

import com.cherkasov.models.dish.OwnPizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnPizzaRepository extends JpaRepository<OwnPizza, String> {

    @Override
    @NonNull
    <S extends OwnPizza> S saveAndFlush(@NonNull S entity);

}
