package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class BaseDaoImp<T, ID> implements BaseDao<T, ID> {
    protected Session session;
    protected Transaction tx;

    @Override
    public T salvar(T entidade) throws Exception {
        abreConexao();
        session.save(entidade);
        fechaConecao();
        return entidade;
    }

    @Override
    public void excluir(T entidade) throws Exception {
        abreConexao();
        session.delete(entidade);
        fechaConecao();
    }

    @Override
    public void alterar(T entidade) throws Exception {
          abreConexao();
        session.update(entidade);
        fechaConecao();
    }

    protected void abreConexao() {
        session = FabricaConexao.abreSessao();
        tx = session.beginTransaction();
    }

    protected void fechaConecao() {
        tx.commit();
        session.close();
    }
}