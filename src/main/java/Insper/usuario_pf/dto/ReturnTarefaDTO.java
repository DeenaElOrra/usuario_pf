package Insper.usuario_pf.dto;

import Insper.usuario_pf.model.Tarefa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ReturnTarefaDTO {
    private Integer id;
    private String titulo;
    private String descricao;
    private String projeto;

    public static ReturnTarefaDTO fromEntity(Tarefa tarefa) {
        ReturnTarefaDTO dto = new ReturnTarefaDTO();
        dto.setId(tarefa.getId());
        dto.setTitulo(tarefa.getTitulo());
        dto.setDescricao(tarefa.getDescricao());
        dto.setProjeto(tarefa.getProjeto());
        return dto;
    }
}

