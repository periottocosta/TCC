package dao;

import entidade.Bairro;
import entidade.Imagens;
import entidade.Imovel;
import java.util.List;
import org.hibernate.Query;

public class ImovelDaoImp extends BaseDaoImp<Imovel, Long> implements ImovelDao {

    @Override
    public Imovel pesquisar(Long id) throws Exception {
        session = FabricaConexao.abreSessao();
        Imovel imovel = (Imovel) session.get(Imovel.class, id);
        session.close();
        return imovel;
    }

    @Override
    public List<Imovel> listar() throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT DISTINCT i FROM Imovel i join fetch i.funcionario");
        List<Imovel> imoveis = query.list();
        session.close();
        return imoveis;
    }

    @Override
    public List<Bairro> pesquisaBairro(String municipio) throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT b FROM Bairro b join b.municipio join  b.municipio.estado WHERE b.municipio.nome = :valor and b.municipio.estado.id = 24");

        query.setString("valor", municipio);
        List<Bairro> bairros = (List<Bairro>) query.list();
        session.close();
        return bairros;
    }

    @Override
    public List<Imovel> pesquisaImovel(Long bairro) throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT i FROM Imovel i WHERE i.endereco.cep.bairro.id = :valor");
        query.setLong("valor", bairro);
        List<Imovel> imoveis = query.list();
        session.close();
        return imoveis;
    }
     @Override
    public List<Imagens> pesquisaImagens(Long id) throws Exception {
       session = FabricaConexao.abreSessao();
       Query query = session.createQuery("SELECT i FROM Imagens i Join i.idImovel WHERE i.idImovel.id = :valor");
       query.setLong("valor", id);
       List<Imagens> imagens = query.list();
       session.close();
       return imagens;
    }

    @Override
    public Imovel pesquisaImovelFuncionario(Long id) throws Exception {
       session = FabricaConexao.abreSessao();
       Query query = session.createQuery("SELECT i FROM Imovel i WHERE i.funcionario = :valor");
       query.setLong("valor", id);
       Imovel imovel = (Imovel) query.uniqueResult();
       session.close();
       return imovel;
    }

    @Override
    public List<Imovel> pesquisaCodigo() throws Exception {
       session = FabricaConexao.abreSessao();
       Query query = session.createQuery(" FROM Imovel i ");
       List<Imovel> imovelCodigo = query.list();
       session.close();
       return imovelCodigo;
    }

    @Override
    public List<Imovel> pesquisaImovelPorCodigo(String codigo) throws Exception {
       session = FabricaConexao.abreSessao();
       Query query = session.createQuery("SELECT i FROM Imovel i WHERE i.codigo = :valorCodigo and i.statusImovel= 'a venda'");
       query.setString("valorCodigo", codigo);
       List<Imovel> imoveis =  query.list();
       session.close();
       return imoveis;
    }
}
