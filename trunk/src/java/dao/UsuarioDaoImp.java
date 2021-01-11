package dao;

import entidade.Funcionario;
import entidade.Usuario;
import java.util.List;
import org.hibernate.Query;

public class UsuarioDaoImp extends BaseDaoImp<Usuario, Long> implements UsuarioDao {

    @Override
    public Usuario pesquisar(Long id) throws Exception {
        session = FabricaConexao.abreSessao();
        Usuario user = (Usuario) session.get(Usuario.class, id);
        session.close();
        return user;
    }

    @Override
    public List<Usuario> listar() throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("FROM Usuario");
        List<Usuario> users = query.list();
        session.close();
        return users;
    }

    @Override
    public List<Usuario> pesquisaPerfil(Long id) throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT u FROM Usuario u WHERE u.perfil.id = :valor");
        query.setLong("valor", id);
        List<Usuario> users = query.list();
        session.close();
        return users;
    }

    @Override
    public Usuario pesquisausuario(String login) throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("FROM Usuario u WHERE u.login = :valor");
        query.setString("valor", login);
        Usuario usuario = (Usuario) query.uniqueResult();
        session.close();
        return usuario;
    }

    @Override
    public List<Funcionario> pesquisaFuncionarioSemUsuario() throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT f FROM Funcionario f WHERE f.usuario = null");
        List<Funcionario> funcionarios = query.list();
        session.close();
        return funcionarios;
    }
}