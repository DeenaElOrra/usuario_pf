package Insper.usuario_pf.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {
    private String nome;
    private String cpf;
    private String email;
    private String papel;
    private String password;

}
