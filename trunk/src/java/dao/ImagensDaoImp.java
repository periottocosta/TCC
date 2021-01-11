package dao;


import entidade.Imagens;
import java.util.List;
import org.hibernate.Query;

public class ImagensDaoImp extends BaseDaoImp<Imagens, Long> implements ImagensDao {

    @Override
    public List<Imagens> listar() throws Exception {        
        Query query = session.createQuery("from Imagens");
        List<Imagens> arq = query.list();
        session.close();
        return arq;
    }

    @Override
    public Imagens pesquisar(Long id) throws Exception {
        session = FabricaConexao.abreSessao();
        Imagens arq = (Imagens) session.get(Imagens.class, id);
        session.close();
        return arq;
    }
}