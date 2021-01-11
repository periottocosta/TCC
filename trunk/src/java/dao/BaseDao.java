package dao;

import java.util.List;

public interface BaseDao<T, ID> {
    T salvar(T entidade) throws Exception;
    T pesquisar(ID id) throws Exception;
    void excluir(T entidade) throws Exception;
    void alterar(T entidade) throws Exception;
    List<T> listar() throws Exception;
}