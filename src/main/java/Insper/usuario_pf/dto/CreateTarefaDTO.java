package Insper.usuario_pf.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTarefaDTO {
    @NotEmpty
    private String titulo;
    @NotEmpty
    private String descricao;
    @NotEmpty
    private String projeto;

}
