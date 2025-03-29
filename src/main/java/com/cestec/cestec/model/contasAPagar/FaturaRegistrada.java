package com.cestec.cestec.model.contasAPagar;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="fatura_registrada")
@Getter
@Setter
public class FaturaRegistrada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime criadoEm;
    
    @UpdateTimestamp
    private LocalDateTime atualizadoEm;

    private String linhaDigitavel;
    private String qrcodeUrl;
    private String qrcodeEmv;

    @ManyToOne
    @JoinColumn(name="fatura_id")
    private Fatura fatura;

    public FaturaRegistrada criar(Fatura fatura, String linhaDigitavel, String qrcodeUrl, String qrcodeEmv){
        this.fatura         = fatura;
        this.linhaDigitavel = linhaDigitavel;
        this.qrcodeUrl      = qrcodeUrl;
        this.qrcodeEmv      = qrcodeEmv;
        return this;
    }

}
