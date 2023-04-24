package com.cherkasov.dto;


import com.cherkasov.models.user.Role;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserMainInfoDto {
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private Role role;

}

