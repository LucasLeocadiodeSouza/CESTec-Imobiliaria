package com.cestec.cestec.model.cri;

import java.sql.Time;
import java.time.LocalDate;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "pcp_imovel_img")
public class pcp_imovel_img {
    @EmbeddedId
    private pcp_imovel_imgId id;

    @ManyToOne
    @MapsId("codimovel")
    @JoinColumn(name = "codimovel")
    private pcp_imovel pcp_imovel;

    private String    imgpath;
    private LocalDate criado_em;
    private String    ideusu;
}
