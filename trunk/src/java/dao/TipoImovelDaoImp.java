package dao;

import entidade.TipoImovel;
import java.util.List;
import org.hibernate.Query;

public class TipoImovelDaoImp extends BaseDaoImp<TipoImovel, Long> implements TipoImovelDao{

    @Override
    public TipoImovel pesquisar(Long id) throws Exception {
      session = FabricaConexao.abreSessao();
      TipoImovel tipoImovel = (TipoImovel) session.get(TipoImovel.class, id);
      session.close();
      return tipoImovel;
    }

    @Override
    public List<TipoImovel> listar() throws Exception {
      session = FabricaConexao.abreSessao();
      Query query = session.createQuery("From TipoImovel");
      List<TipoImovel> tipoImoveis = query.list();
      session.close();
      return tipoImoveis;
    }
}