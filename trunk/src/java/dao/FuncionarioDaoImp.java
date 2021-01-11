package dao;

import entidade.Funcionario;
import java.util.List;
import org.hibernate.Query;

public class FuncionarioDaoImp extends BaseDaoImp<Funcionario, Long> implements FuncionarioDao {

    @Override
    public Funcionario pesquisar(Long id) throws Exception {
        session = FabricaConexao.abreSessao();
        Funcionario func = (Funcionario) session.get(Funcionario.class, id);
        session.close();
        return func;
    }

    @Override
    public List<Funcionario> listar() throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("Select f from Funcionario f join f.funcao WHERE f.funcao.id = 1");
        List<Funcionario> funcs = query.list();
        session.close();
        return funcs;
    }
    
    @Override
    public List<Funcionario> listaFuncionario() throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("from Funcionario");
        List<Funcionario> funcs = query.list();
        session.close();
        return funcs;
    }

    @Override
    public Funcionario pesquisaFuncionarioNome(Long id) throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT DISTINCT func FROM Funcionario func JOIN fetch func.imoveis WHERE func.id = :valor");
        query.setDouble("valor", id);
        Funcionario funcionario = (Funcionario) query.uniqueResult();
        session.close();
        return funcionario;
    }

    @Override
    public List<Funcionario> relatorio() throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT DISTINCT func FROM Funcionario func JOIN fetch func.imoveis");
        List<Funcionario> funcs = query.list();        
        session.close();
        return funcs;
    }
    
    @Override
    public List<Funcionario> pesqFunc(String nome) throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT c FROM Funcionario c WHERE c.nome LIKE :valor");
        query.setString("valor", "%" + nome + "%");
        List<Funcionario> funcs = query.list();
        session.close();
        return funcs;
    }
}