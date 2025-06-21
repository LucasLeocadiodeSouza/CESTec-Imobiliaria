package com.cestec.cestec.repository.custom;


import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.sp_aplicacoes;

import org.hibernate.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class prjCadastroAplicacaoRepository {
    private final EntityManager em;

    @PersistenceContext
    private EntityManager em2;

    public prjCadastroAplicacaoRepository(EntityManager em){
        this.em = em;
    }

    public List<sp_aplicacoes> buscarAplicacoes(Integer codapl, Integer codmodel, String ideusu){
        String query = "SELECT apl FROM sp_aplicacoes apl WHERE apl.ideusu = :ideusu ORDER BY apl.modulo.id";

        if(codapl   != 0 && codapl   != null) query += " AND apl.id = :codapl ";
        if(codmodel != 0 && codmodel != null) query += " AND apl.modulo.id = :codmodel";

        var q = em.createQuery(query, sp_aplicacoes.class);
        q.setParameter("ideusu", ideusu);
        
        if(codapl != null && codapl != 0){
            q.setParameter("codapl", codapl);
        }
        if(codmodel != null && codmodel != 0){
            q.setParameter("codmodel", codmodel);
        }

        return q.getResultList();
    }

    // public List<String> buscarTabelas(Integer codapl, Integer codmodel, Integer codtable){ <- ChatGPT
    //     List<String> tabelas = new ArrayList<>();
        
    //     // Obter a sessão do Hibernate a partir do EntityManager
    //     Session session = em.unwrap(Session.class);

    //     session.doWork(new Work() {
    //         @Override
    //         public void execute(Connection connection) throws SQLException {
    //             DatabaseMetaData metaData = connection.getMetaData();
    //             String catalog = connection.getCatalog(); // Obtém o banco de dados atual
    //             String schema = connection.getSchema();  // Obtém o schema atual

    //             try (ResultSet rs = metaData.getTables(catalog, schema, "%", new String[]{"TABLE"})) {
    //                 while (rs.next()) {
    //                     tabelas.add(rs.getString("TABLE_NAME"));
    //                 }
    //             }
    //         }
    //     });
        
    //     return tabelas;
    // }

    public List<String> buscarTabelas(String nomeTabela){
        String query = "SHOW TABLES ";

        if(nomeTabela != null && !nomeTabela.isEmpty()){
            query += " LIKE :nome";
        }

        var q = em.createNativeQuery(query);
    
        if(nomeTabela != null && !nomeTabela.isEmpty()) {
            q.setParameter("nome", nomeTabela + "%");
        }

        return q.getResultList();
    }

    //Para tabelas que usem mais de uma coluna na PK ele mostra so a ultima coluna. Achei muito complexo entao vou evitar usar multiplas PK
    public Map<String, Object[]> buscarIndexs(String nomeTabela) throws SQLException {
        Map<String, Object[]> metadados = new LinkedHashMap<>();
        
        Session session = em.unwrap(Session.class);
        session.doWork(connection -> {
            DatabaseMetaData metaData = connection.getMetaData();
            
            try (ResultSet rs = metaData.getIndexInfo(null, null, nomeTabela, false, false)) {
                while (rs.next()) {
                    Object[] dados = {
                        rs.getString("COLUMN_NAME"),
                        rs.getShort("TYPE"),
                        rs.getShort("ORDINAL_POSITION")
                    };
                    metadados.put(rs.getString("INDEX_NAME"), dados);
                }
            }
        });
        
        return metadados;
    }

    public Map<String, Object[]> buscarColunas(String nomeTabela) throws SQLException {
        Map<String, Object[]> metadados = new LinkedHashMap<>();
        
        Session session = em.unwrap(Session.class);
        session.doWork(connection -> {
            DatabaseMetaData metaData = connection.getMetaData();
            
            try (ResultSet rs = metaData.getColumns(null, null, nomeTabela, null)) {
                while (rs.next()) {
                    Object[] dados = {
                        rs.getString("TYPE_NAME"),
                        rs.getInt("COLUMN_SIZE"),
                        "DECIMAL".equalsIgnoreCase(rs.getString("TYPE_NAME"))?rs.getInt("DECIMAL_DIGITS"):"-",
                        rs.getInt("ORDINAL_POSITION")
                    };
                    metadados.put(rs.getString("COLUMN_NAME"), dados);
                }
            }
        });
        
        return metadados;
    }
}
