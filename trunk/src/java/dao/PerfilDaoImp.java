package dao;

import entidade.Perfil;
import java.util.List;
import org.hibernate.Query;

public class PerfilDaoImp extends BaseDaoImp<Perfil, Long> implements PerfilDao {

    @Override
    public Perfil pesquisar(Long id) throws Exception {
        session = FabricaConexao.abreSessao();
        Perfil perfil = (Perfil) session.get(Perfil.class, id);
        session.close();
        return perfil;
    }

    @Override
    public List<Perfil> listar() throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("FROM Perfil");
        List<Perfil> perfis = query.list();
        session.close();
        return perfis;
    }
}