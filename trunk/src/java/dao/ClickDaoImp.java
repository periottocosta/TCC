package dao;

import entidade.Click;
import java.util.List;
import org.hibernate.Query;

public class ClickDaoImp extends BaseDaoImp<Click, Long> implements ClickDao {

    @Override
    public Click pesquisar(Long id) throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT c FROM Click c WHERE c.imovel.id= :valor ");
        query.setLong("valor", id);
        Click click = (Click) query.uniqueResult();
        session.close();
        return click;
    }

    @Override
    public List<Click> listar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Click pesquisaClick(Long id) throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT c FROM Click c WHERE c.imovel.id= :valor ");
        query.setLong("valor", id);
        Click click = (Click) query.uniqueResult();
        session.close();
        return click;
    }
}
