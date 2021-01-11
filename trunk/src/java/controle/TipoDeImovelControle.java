package controle;

import dao.TipoImovelDao;
import dao.TipoImovelDaoImp;
import entidade.TipoImovel;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;

@ManagedBean
@SessionScoped
public class TipoDeImovelControle {
    private TipoImovel tipoImovel;
    private TipoImovelDao tipoImovelDao;
    private DataModel model;
    private boolean pesquisa = false;
    private FacesContext contexto;
    private String msn = null;

    public TipoImovel getTipoImovel() {
        if (tipoImovel == null) {
            tipoImovel = new TipoImovel();

        }
        return tipoImovel;
    }
    public void setTipoImovel(TipoImovel tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public DataModel getModel() {
        return model;
    }
    public void setModel(DataModel model) {
        this.model = model;
    }

    public boolean isPesquisa() {
        return pesquisa;
    }
    public void setPesquisa(boolean pesquisa) {
        this.pesquisa = pesquisa;
    }

    public String salvar() {
        tipoImovelDao = new TipoImovelDaoImp();
        contexto = FacesContext.getCurrentInstance();
        try {
            if (tipoImovel.getId() == null) {
                tipoImovelDao.salvar(tipoImovel);
                msn = "Tipo de Imovel salvo com Sucesso !";
            } else {
                tipoImovelDao.alterar(tipoImovel);
                msn = "Tipo de Imovel alterado com Sucesso !";
            }
        } catch (Exception ex) {
            System.out.println("Erro ao Salvar ou Alterar Tipo de Imovel \n" + ex.getMessage());
            msn = "Erro ao Salvar ou Alterar Tipo de Imovel !!";
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
        return "menu";
    }

    public void pesquisaTipoImovel() {
        tipoImovelDao = new TipoImovelDaoImp();
        try {
            if (tipoImovel.getId() == null) {
                tipoImovel = tipoImovelDao.pesquisar(tipoImovel.getId());
            }
        } catch (Exception ex) {
            System.out.println("Erro ao pesquisar Tipo de Imovel .....::::::" + ex.getMessage());
        }
    }

    public String alterar() {
        tipoImovel = (TipoImovel) model.getRowData();
        return "pagina";
    }

    public String excluir() {
        contexto = FacesContext.getCurrentInstance();
        tipoImovel = (TipoImovel) model.getRowData();
        tipoImovelDao = new TipoImovelDaoImp();
        msn = null;
        try {
            tipoImovelDao.excluir(tipoImovel);
            msn = "Tipo de Imovel Excluido com Sucesso !!!!!";
        } catch (Exception ex) {
            System.out.println("Erro ao Excluir Tipo de Imovel ......::::::" + ex.getMessage());
            msn = "Erro ao Excluir Tipo de Imovel";
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
        return "pagina";
    }

    public String pesquisa() {
        if (tipoImovel != null) {
            limpa();
            model = null;
        }
        pesquisa = false;
        return "pasgina";
    }

    private void limpa() {
        tipoImovel = null;
    }

    public String novoTipoImovel() {
        tipoImovel = new TipoImovel();
        limpa();
        pesquisa = false;
        return "cadTipoImovel";
    }
}