package controle;

import dao.FuncaoDao;
import dao.FuncaoDaoImp;
import entidade.Funcao;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;

@ManagedBean
@SessionScoped
public class FuncaoControle {
    private Funcao funcao;
    private FuncaoDao funcaoDao;
    private DataModel model;
    private boolean pesquisa = false;
    private FacesContext contexto;
    private String msn = null;

    public Funcao getFuncao() {
        if (funcao == null) {
            funcao = new Funcao();
        }
        return funcao;
    }
    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public FuncaoDao getFuncaoDao() {
        return funcaoDao;
    }
    public void setFuncaoDao(FuncaoDao funcaoDao) {
        this.funcaoDao = funcaoDao;
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
        funcaoDao = new FuncaoDaoImp();
        contexto = FacesContext.getCurrentInstance();
        msn = null;
        try {
            if (funcao.getId() == null) {
                funcaoDao.salvar(funcao);
                msn = "Função Salva com Sucesso !";
            } else {
                funcaoDao.alterar(funcao);
                msn = "Função Alterada com Sucesso !";
            }
        } catch (Exception ex) {
            System.out.println("Erro ao Salvar OU Alterar Função \n" + ex.getMessage());
            msn = "Erro ao excuter a Operação";
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
        return "menu";
    }

    public void pesquisaFuncao() {
        funcaoDao = new FuncaoDaoImp();
        if (funcao.getId() == null) {
            try {
                funcao = funcaoDao.pesquisar(funcao.getId());
            } catch (Exception ex) {
                System.out.println("Erro ao pesquisar Função .......::::::" + ex.getMessage());
            }
        }
    }

    public String alterar() {
        funcao = (Funcao) model.getRowData();
        return "pagina de direcionamento";
    }

    public String excluir() {
        contexto = FacesContext.getCurrentInstance();
        funcao = (Funcao) model.getRowData();
        funcaoDao = new FuncaoDaoImp();
        msn = null;
        try {
            funcaoDao.excluir(funcao);
            msn = "Função Excluida com Sucesso !!!!!";
        } catch (Exception ex) {
            msn = "Erro ao Excluir Função";
            System.out.println("Erro ao Excluir Função .....::::" + ex.getMessage());
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
        return "pagina de direcionamento";
    }

    public String pesquisa() {
        if (funcao != null) {
            limpa();
            model = null;
        }
        pesquisa = false;
        return "pagina de direcionamento";
    }

    private void limpa() {
        funcao = null;
    }

    public String novaFuncao() {
        funcao = new Funcao();
        limpa();
        pesquisa = false;
        return "cadFuncao";
    }
}