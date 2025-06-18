package com.cestec.cestec.repository.generico;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cestec.cestec.model.LibAcesso;
import com.cestec.cestec.model.LibAcessoId;

public interface LibAcessoRepository extends JpaRepository<LibAcesso, LibAcessoId> {
}
