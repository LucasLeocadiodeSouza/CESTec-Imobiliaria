package com.cestec.cestec.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class modelUtilForm {
    private Integer id;
    private String  descricao;
    private String  paramName;

    public modelUtilForm(Integer id, String descricao) {
        this.id        = id;
        this.descricao = descricao;
    }

    public modelUtilForm(String paramName, String descricao) {
        this.paramName = paramName;
        this.descricao = descricao;
    }
}
