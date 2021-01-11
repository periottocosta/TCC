package dao;

import entidade.Cep;
import java.util.List;
import org.hibernate.Query;

public class CepDaoImp extends BaseDaoImp<Cep, Long> implements CepDao{

    @Override
    public Cep pesquisar(Long id) throws Exception {
        session = FabricaConexao.abreSessao();
        Cep cep = (Cep) session.get(Cep.class, id);
        session.close();
        return cep;
    }

    @Override
    public List<Cep> listar() throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("FROM Cep");
        List<Cep> ceps = query.list();
        session.close();
        return ceps;
    }

    @Override
    public Cep procuraCep(String cep) throws Exception {
       session = FabricaConexao.abreSessao();
       Query query = session.createQuery("FROM Cep ce WHERE ce.cep = :valor");
       query.setString("valor", cep);
       Cep cep1 = (Cep) query.uniqueResult();
       session.close();
       return cep1;
    }

    @Override
    public List<Cep> procuraLogradouro(String logradouro) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}