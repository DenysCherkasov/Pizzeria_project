package com.cherkasov.repositories.souseRepository;

import com.cherkasov.models.pizzaParts.Souse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SouseRepository extends JpaRepository<Souse, String>, CustomizedSouseRepository {

}
