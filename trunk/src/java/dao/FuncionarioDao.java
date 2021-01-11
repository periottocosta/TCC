package dao;

import entidade.Funcionario;
import java.util.List;

public interface FuncionarioDao extends BaseDao<Funcionario, Long> {
    public Funcionario pesquisaFuncionarioNome(Long id) throws Exception;
    public List<Funcionario> listaFuncionario() throws Exception;
    public List<Funcionario> relatorio() throws Exception;
    public List<Funcionario> pesqFunc(String nome) throws Exception;
}