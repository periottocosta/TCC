package dao;

import entidade.Funcionario;
import entidade.Usuario;
import java.util.List;

public interface UsuarioDao extends BaseDao<Usuario, Long> {

    public List<Usuario> pesquisaPerfil(Long id) throws Exception;

    public Usuario pesquisausuario(String login) throws Exception;
    // public Usuario login(String login, String senha) throws Exception;

    List<Funcionario> pesquisaFuncionarioSemUsuario() throws Exception;
}