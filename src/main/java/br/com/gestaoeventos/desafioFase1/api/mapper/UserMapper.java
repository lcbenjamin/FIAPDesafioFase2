package br.com.gestaoeventos.desafioFase1.api.mapper;

import br.com.gestaoeventos.desafioFase1.api.dto.UserDto;
import br.com.gestaoeventos.desafioFase1.api.dto.UserUpdateDto;
import br.com.gestaoeventos.desafioFase1.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserDto toDto(User usuario) {
        UserDto dto = new UserDto();
        dto.nome = usuario.getNome();
        dto.email = usuario.getEmail();
        dto.login = usuario.getLogin();
        dto.endereco = usuario.getEndereco();
        dto.tipo = usuario.getTipo();
        return dto;
    }

    public static User toEntity(UserDto dto) {
        User user = new User();
        user.setNome(dto.nome);
        user.setEmail(dto.email);
        user.setLogin(dto.login);
        user.setSenha(dto.senha);
        user.setEndereco(dto.endereco);
        user.setTipo(dto.tipo);
        return user;
    }

    public static User updateDtoToEntity(UserUpdateDto dto) {
        User user = new User();
        user.setNome(dto.nome);
        user.setEndereco(dto.endereco);
        user.setTipo(dto.tipo);
        return user;
    }

}
