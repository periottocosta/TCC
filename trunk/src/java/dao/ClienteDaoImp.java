package dao;

import entidade.Cliente;
import entidade.Imovel;
import java.util.List;
import org.hibernate.Query;

public class ClienteDaoImp extends BaseDaoImp<Cliente, Long> implements ClienteDao {

    @Override
    public Cliente pesquisar(Long id) throws Exception {
        session = FabricaConexao.abreSessao();
        Cliente cli = (Cliente) session.get(Cliente.class, id);
        session.close();
        return cli;
    }

    @Override
    public List<Cliente> listar() throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("FROM Cliente");
        List<Cliente> clientes = query.list();
        session.close();
        return clientes;
    }

    @Override
    public List<Cliente> pesquisaClienteCpf(String cpf) {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("FROM Cliente c WHERE c.cpf = :valor");
        query.setString("valor", cpf);
        List<Cliente> clientes = query.list();
        session.close();
        return clientes;
    }

    @Override
    public List<Cliente> pesquisaNome(String nome) throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT c FROM Cliente c WHERE c.nome LIKE :valor");
        query.setString("valor", "%" + nome + "%");
        List<Cliente> clientes = query.list();
        session.close();
        return clientes;
    }

    @Override
    public List<Imovel> pesquisaImovel(Long id) throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT i FROM Imovel i WHERE i.id = :valor");
        query.setLong("valor", id);
        List<Imovel> imoveis = query.list();
        session.close();
        return imoveis;
    }

    @Override
    public Cliente pesquisaNome2(String nome) throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT c FROM Cliente c WHERE c.nome= :valor");
        query.setString("valor", nome);
        Cliente cliente = (Cliente) query.uniqueResult();
        session.close();
        return  cliente;
    }
}
