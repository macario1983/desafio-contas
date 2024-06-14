package br.com.desafio.payableaccount.core.mapstruct;

import br.com.desafio.payableaccount.api.v1.model.input.UserInput;
import br.com.desafio.payableaccount.core.security.EncryptConfig;
import br.com.desafio.payableaccount.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = EncryptConfig.class)
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "username", source = "username"),
            @Mapping(target = "password", source = "password", qualifiedByName = "encode"),
    })
    User toModel(UserInput userInput);

}
