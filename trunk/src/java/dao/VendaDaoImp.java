package dao;

import entidade.Venda;
import java.util.List;
import org.hibernate.Query;

public class VendaDaoImp extends BaseDaoImp<Venda, Long> implements VendaDao{
    
    @Override
    public Venda pesquisar(Long id) throws Exception {
        session = FabricaConexao.abreSessao();
        Venda venda = (Venda) session.get(Venda.class, id);
        session.close();
        return venda;
    }

    @Override
    public List<Venda> listar() throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT v.func.nome,v.imovel.tipoImovel,v.imovel.statusImovel FROM Venda v JOIN v.func JOIN v.imovel ORDER BY v.func.nome");
        List<Venda> vendas = query.list();
        session.close();
        return vendas;
    }
}