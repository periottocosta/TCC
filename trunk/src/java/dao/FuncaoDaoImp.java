package dao;

import entidade.Funcao;
import java.util.List;
import org.hibernate.Query;

public class FuncaoDaoImp extends BaseDaoImp<Funcao, Long> implements FuncaoDao {

    @Override
    public Funcao pesquisar(Long id) throws Exception {
        session = FabricaConexao.abreSessao();
        Funcao funcao = (Funcao) session.get(Funcao.class, id);
        session.close();
        return funcao;
    }

    @Override
    public List<Funcao> listar() throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("FROM Funcao");
        List<Funcao> funcoes = query.list();
        session.close();
        return funcoes;
    }
}
