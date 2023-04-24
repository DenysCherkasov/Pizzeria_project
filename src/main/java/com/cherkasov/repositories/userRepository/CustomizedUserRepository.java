package com.cherkasov.repositories.userRepository;

import com.cherkasov.dto.UserMainInfoDto;
import com.cherkasov.models.user.Role;

import java.util.List;
import java.util.Optional;

public interface CustomizedUserRepository {
    void setTypeById(String id, Role role);

    List<UserMainInfoDto> getUsersInfo();


}
