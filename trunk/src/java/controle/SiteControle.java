package controle;

import dao.ClickDao;
import dao.ClickDaoImp;
import dao.SiteDao;
import dao.SiteDaoImp;
import dao.TipoImovelDao;
import dao.TipoImovelDaoImp;
import entidade.Bairro;
import entidade.Click;
import entidade.Imagens;
import entidade.Imovel;
import entidade.TipoImovel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class SiteControle {

    private Imovel imovel;
    private Imagens imagen;
    private String msn = null;
    private FacesContext contexto;
    private SiteDao siteDao;
//    private DataModel listaImagensGaleria;
    private List<Imagens> imagens;
    private TipoImovel tipoImovel;
    private Bairro bairro;
    private List<Imagens> imagensFiltro;
    private Click click;
    private ClickDao clickDao;
    private List<Imagens> imagensGaleria;
    ///Entidades para o filtro///
    private Imovel imovelFiltro;
    private TipoImovel tipoImovelFiltro;
    private Bairro bairroFiltro;

    public Imovel getImovelFiltro() {
        if (imovelFiltro == null) {
            imovelFiltro = new Imovel();

        }
        return imovelFiltro;
    }

    public void setImovelFiltro(Imovel imovelFiltro) {
        this.imovelFiltro = imovelFiltro;
    }

    public TipoImovel getTipoImovelFiltro() {
        if (tipoImovelFiltro == null) {
            tipoImovelFiltro = new TipoImovel();
        }
        return tipoImovelFiltro;
    }

    public void setTipoImovelFiltro(TipoImovel tipoImovelFiltro) {
        this.tipoImovelFiltro = tipoImovelFiltro;
    }

    public Bairro getBairroFiltro() {
        if (bairroFiltro == null) {
            bairroFiltro = new Bairro();
        }
        return bairroFiltro;
    }

    public void setBairroFiltro(Bairro bairroFiltro) {
        this.bairroFiltro = bairroFiltro;
    }

    //Fim
    public List<Imagens> getImagensGaleria() {
        if (imagensGaleria == null) {
            carregaGaleria();
        }
        return imagensGaleria;
    }

    public void setImagensGaleria(List<Imagens> imagensGaleria) {
        this.imagensGaleria = imagensGaleria;
    }

    public Click getClick() {
        if (click == null) {
            click = new Click();
        }
        return click;
    }

    public void setClick(Click click) {
        this.click = click;
    }

    public List<Imagens> getImagensFiltro() {
        return imagensFiltro;
    }

    public void setImagensFiltro(List<Imagens> imagensFiltro) {
        this.imagensFiltro = imagensFiltro;
    }

    public TipoImovel getTipoImovel() {
        if (tipoImovel == null) {
            tipoImovel = new TipoImovel();
        }
        return tipoImovel;
    }

    public void setTipoImovel(TipoImovel tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public List<Imagens> getImagens() {
        if (imagens == null) {
            carregaNoidades();
        }
        return imagens;
    }

    public void setImagens(List<Imagens> imagens) {
        this.imagens = imagens;
    }

    public Imovel getImovel() {
        if (imovel == null) {
            imovel = new Imovel();
        }
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public Imagens getImagen() {
        if (imagen == null) {
            imagen = new Imagens();
        }
        return imagen;
    }

    public void setImagen(Imagens imagen) {
        this.imagen = imagen;
    }

    public void criaStatusImagenCapa(Long idImagen, Long idImovel) {
        SiteDao sdao = new SiteDaoImp();
        contexto = FacesContext.getCurrentInstance();
        try {
            sdao.alteraImagenStatus(idImagen, idImovel);
            msn = "Estatos salvo com sucesso!";
        } catch (Exception ex) {
            Logger.getLogger(SiteControle.class.getName()).log(Level.SEVERE, null, ex);
            msn = "Erro ao salvar estatos";
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
    }

    public String carregaNoidades() {
        siteDao = new SiteDaoImp();
        imagens = null;
        List<Imovel> imov = new ArrayList();
        imagens = new ArrayList();
        try {
            imov = siteDao.listar();
            for (Imovel imo : imov) {
                imagens.add(siteDao.pesqeImg(imo.getId()));
            }
        } catch (Exception ex) {
            Logger.getLogger(SiteControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "novidades";
    }

    public String carregaImovelSelecionado(Long id) {
        siteDao = new SiteDaoImp();
        clickDao = new ClickDaoImp();
        imovel = null;
        try {
            imovel = siteDao.pesquisaImovelSelecionado(id);
            click = clickDao.pesquisaClick(id);

            if (click.getId() == null) {
                click.setImovel(imovel);
                click.setContador(1);
                clickDao.salvar(click);
            } else {
                click.setContador(click.getContador() + 1);
                click.setImovel(click.getImovel());
                clickDao.alterar(click);
            }
        } catch (Exception ex) {
            Logger.getLogger(SiteControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "imovelSelecionado";
    }

    public List<SelectItem> getTodosTipoImovel() {
        TipoImovelDao tipoImovelDao = new TipoImovelDaoImp();
        try {
            List<TipoImovel> tipoImovels;
            tipoImovels = tipoImovelDao.listar();
            List<SelectItem> listaComboTipo = new ArrayList<SelectItem>();
            for (TipoImovel tiposIm : tipoImovels) {
                listaComboTipo.add(new SelectItem(tiposIm.getId(), tiposIm.getNome()));
            }
            return listaComboTipo;
        } catch (Exception ex) {
            System.out.println("Erro a fazer lista da Combo .....:::::" + ex.getMessage());
        }
        return null;
    }

    public String pesquisaFiltro() {
        siteDao = new SiteDaoImp();
        try {

            imagensFiltro = siteDao.pesquisaImovelSite(bairroFiltro.getId(), tipoImovelFiltro.getId(), imovelFiltro.getNdormitorios(), imovelFiltro.getValorImovel());
            //bairroFiltro.getNome(), tipoImovelFiltro.getId(), imovelFiltro.getNdormitorios(), imovelFiltro.getValorImovel()
        } catch (Exception ex) {
            Logger.getLogger(SiteControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "resultadoFiltro";
    }

    public String pesquisaCodigoImovel() {
        siteDao = new SiteDaoImp();
        imovel = null;
        try {
            imovel = siteDao.pesquidaCodigoImovel(imovelFiltro.getCodigo());
        } catch (Exception ex) {
            Logger.getLogger(SiteControle.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "imovelSelecionado";
    }

    /// FUNÇÕES PARA ESCOLHA DA IMAGEN PARA A GALERIA
    public String adicionadaImagenGaleria() {
        siteDao = new SiteDaoImp();
        contexto = FacesContext.getCurrentInstance();
        List<Imagens> imgs;
        try {
            imgs = siteDao.pesquisaImovelGaleriaValida();
            if (imgs.size() == 4) {
                msn = "Ja exedeu o limite de imagens na galeria";
            } else {
            }
        } catch (Exception ex) {
            Logger.getLogger(SiteControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
        return "galeria";
    }

    public void carregaGaleria() {
        siteDao = new SiteDaoImp();
        try {
            imagensGaleria = siteDao.pesquisaGaleria();
        } catch (Exception ex) {
            Logger.getLogger(SiteControle.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//    public static void main(String[] args) throws Exception {
//        SiteDao sdao = new SiteDaoImp();
//        List<Imagens> imagens = new ArrayList();
//        for (Imovel imo : sdao.listar()) {
//
//            System.out.println("id  " + imo.getId());
//            imagens.add(sdao.pesqeImg(imo.getId()));
//        }
//        for (Imagens imag : imagens) {
//            System.out.println("id imovel " + imag.getIdImovel().getId());
//            System.out.println("nome dai magens " + imag.getNome());
//        }
//
////        String bairro2 = "Barra da Lagoa";
////        String tipo = "Casa";
////        String nDormotorios = "";
////        List<Imagens> img = sdao.pesquisaImovelSite(bairro2, tipo, nDormotorios, "");
////
////        for (Imagens imagens : img) {
////            System.out.println("imagens " + imagens.getNome());
////            System.out.println("imagens " + imagens.getStatus());
////            System.out.println("imovel " + imagens.getIdImovel().getNdormitorios());
////            System.out.println("Endereco " + imagens.getIdImovel().getEndereco().getCep().getLogradouro());
////        }
//    }
}
