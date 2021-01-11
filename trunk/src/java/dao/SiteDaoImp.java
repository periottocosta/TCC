package dao;

import entidade.Imagens;
import entidade.Imovel;
import java.util.List;
import org.hibernate.Query;

public class SiteDaoImp extends BaseDaoImp<Imovel, Long> implements SiteDao {

    @Override
    public Imovel pesquisar(Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Imovel> listar() throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT i FROM Imovel i ORDER BY i.id DESC LIMIT 8");
        List<Imovel> imoveis = query.list();
        session.close();
        return imoveis;
    }

    @Override
    public Imagens pesqeImg(Long id) throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT i FROM Imagens i WHERE i.idImovel.id = :valor and i.status = :valorStatus");
        //SELECT i FROM Imagens i JWHERE i.idImovel.id = :valor and i.status = :valorStatus
        String status = "capa";
        query.setLong("valor", id);
        query.setString("valorStatus", status);
        Imagens img = (Imagens) query.uniqueResult();
        session.close();
        return img;
    }
    private List<Imagens> imgs;

    @Override
    public List<Imagens> pesquisaImovelSite(Long Bairro, Long tipoImovel, int nDormitorios, double valor) throws Exception {
        session = FabricaConexao.abreSessao();
        //
        if (tipoImovel == 1 || tipoImovel == 4) {
            if (valor == 0) {
                Query query = session.createQuery("SELECT i FROM Imagens i JOIN i.idImovel JOIN i.idImovel.tipoImovel JOIN i.idImovel.endereco.cep.bairro WHERE i.idImovel.endereco.cep.bairro.id= :bairro and i.idImovel.tipoImovel.id = :tipoImovel and i.status = 'capa' and i.idImovel.statusImovel= 'a venda'");
                query.setLong("bairro", Bairro);
                query.setLong("tipoImovel", tipoImovel);
                query.setDouble("valor", valor);
                imgs = query.list();
            } else {
                Query query = session.createQuery("SELECT i FROM Imagens i JOIN i.idImovel JOIN i.idImovel.tipoImovel JOIN i.idImovel.endereco.cep.bairro WHERE i.idImovel.endereco.cep.bairro.id= :bairro and i.idImovel.tipoImovel.id = :tipoImovel  and i.idImovel.valorImovel <= :valor and i.status = 'capa' and i.idImovel.statusImovel= 'a venda'");
                query.setLong("bairro", Bairro);
                query.setLong("tipoImovel", tipoImovel);
                query.setDouble("valor", valor);
                imgs = query.list();

            }
        } else {
            if (nDormitorios == 0) {
                Query query = session.createQuery("SELECT i FROM Imagens i JOIN i.idImovel JOIN i.idImovel.tipoImovel JOIN i.idImovel.endereco.cep.bairro WHERE i.idImovel.endereco.cep.bairro.id= :bairro and i.idImovel.tipoImovel.id = :tipoImovel and i.idImovel.valorImovel <= :valor and i.status = 'capa' and i.idImovel.statusImovel= 'a venda'");
                query.setLong("bairro", Bairro);
                query.setLong("tipoImovel", tipoImovel);
                query.setInteger("NQuartos", nDormitorios);
                query.setDouble("valor", valor);
                imgs = query.list();
            } else {
                Query query = session.createQuery("SELECT i FROM Imagens i JOIN i.idImovel JOIN i.idImovel.tipoImovel JOIN i.idImovel.endereco.cep.bairro WHERE i.idImovel.endereco.cep.bairro.id= :bairro and i.idImovel.tipoImovel.id = :tipoImovel and i.idImovel.Ndormitorios= :NQuartos and i.idImovel.valorImovel <= :valor and i.status = 'capa' and i.idImovel.statusImovel= 'a venda'");
                query.setLong("bairro", Bairro);
                query.setLong("tipoImovel", tipoImovel);
                query.setInteger("NQuartos", nDormitorios);
                query.setDouble("valor", valor);
                imgs = query.list();
            }
        }
        session.close();
        return imgs;
    }

    @Override
    public Imagens alteraImagenStatus(Long id, Long idImovel) throws Exception {
        session = FabricaConexao.abreSessao();
        Query query1 = session.createQuery("SELECT i FROM Imagens i WHERE i.idImovel = :valorid");
        query1.setLong("valorid", idImovel);
        List<Imagens> imgs2 = query1.list();
        for (Imagens im : imgs2) {
            if (im.getStatus() != null) {
                ImagensDao imgDAo = new ImagensDaoImp();
                Imagens img = new Imagens();
                img.setId(im.getId());
                img.setCaminho(im.getCaminho());
                img.setIdImovel(im.getIdImovel());
                img.setNome(im.getNome());
                img.setTamanho(im.getTamanho());
                img.setTipo(im.getTipo());
                imgDAo.alterar(img);
            }
        }

        Query query = session.createQuery("select i from Imagens i WHERE i.id = :valor");
        query.setLong("valor", id);

        Imagens imag = (Imagens) query.uniqueResult();
        imag.setStatus("capa");
        ImagensDao idao = new ImagensDaoImp();
        idao.alterar(imag);
        session.close();

        return null;
    }

    @Override
    public Imovel pesquisaImovelSelecionado(Long id) throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT DISTINCT i FROM Imovel i JOIN FETCH i.imagens WHERE i.id = :valorID");
        query.setLong("valorID", id);
        Imovel imovel = (Imovel) query.uniqueResult();
        session.close();
        return imovel;
    }

    @Override
    public Imovel pesquidaCodigoImovel(String codigo) throws Exception {
        session = FabricaConexao.abreSessao();
//        String status = "a venda";
        Query query = session.createQuery("SELECT DISTINCT i FROM Imovel i JOIN FETCH i.imagens WHERE i.codigo = :valorCodigo ");
        query.setString("valorCodigo", codigo);
//        query.setString("status", status);
        Imovel imovel = (Imovel) query.uniqueResult();
        session.close();
        return imovel;
    }

    @Override
    public List<Imagens> pesquisaImovelGaleriaValida() throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT i FROM Imagens i WHERE i.statusGaleria = 'galeria' ");
        List<Imagens> imagens = query.list();
        session.close();
        return imagens;
    }

    @Override
    public Imagens salvaStatusGaleria(Long idImovel, Long idImagen) throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT i FROM Imagens i WHERE i.idImovel = :valorIdImovel");
        query.setLong("valorIdImovel", idImovel);
        List<Imagens> imgs = query.list();
        for (Imagens image : imgs) {
            if (image.getStatusGaleria() != null) {
                ImagensDao imgDAo = new ImagensDaoImp();
                Imagens img = new Imagens();
                img.setId(image.getId());
                img.setCaminho(image.getCaminho());
                img.setIdImovel(image.getIdImovel());
                img.setNome(image.getNome());
                img.setTamanho(image.getTamanho());
                img.setTipo(image.getTipo());
                img.setStatus(image.getStatus());
                imgDAo.alterar(img);
            }
        }
//        Query query2 = session.createQuery("select i from Imagens i WHERE i.id = :valor");
//        query2.setLong("valor", idImagen);
//
//        Imagens imag = (Imagens) query.uniqueResult();
//        imag.setStatus("galeria");
//        ImagensDao idao = new ImagensDaoImp();
//        idao.alterar(imag);
//        session.close();
        return null;
    }

    @Override
    public List<Imagens> pesquisaGaleria() throws Exception {
        session = FabricaConexao.abreSessao();
        Query query = session.createQuery("SELECT i FROM Imagens i WHERE i.statusGaleria = 'galeria' and i.idImovel.statusImovel= 'a venda'");
        List<Imagens> imagens = query.list();
        session.close();
        return imagens;
    }
}
