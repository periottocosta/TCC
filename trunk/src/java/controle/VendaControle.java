package controle;

import dao.FuncionarioDao;
import dao.FuncionarioDaoImp;
import dao.ImovelDao;
import dao.ImovelDaoImp;
import dao.VendaDao;
import dao.VendaDaoImp;
import entidade.Funcionario;
import entidade.Imovel;
import entidade.Venda;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

@ManagedBean
@SessionScoped
public class VendaControle {

    private Venda venda;
    private VendaDao vdao;
    private Funcionario func;
    private Imovel imovel;
    private DataModel modelVenda;
    private DataModel modelImovel;
    private DataModel modelFunc;
    private boolean pesquisa = false;
    private String msn = null;
    private FacesContext contexto;

    public Venda getVenda() {
        if (venda == null) {
            venda = new Venda();
        }
        return venda;
    }
    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Funcionario getFunc() {
        if (func == null) {
            func = new Funcionario();
        }
        return func;
    }
    public void setFunc(Funcionario func) {
        this.func = func;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }
    public Imovel getImovel() {
        if (imovel == null) {
            imovel = new Imovel();
        }
        return imovel;
    }

    public DataModel getModelVenda() {
        return modelVenda;
    }

    public DataModel getModelImovel() {
        return modelImovel;
    }

    public DataModel getModelFunc() {
        return modelFunc;
    }

    public String salvar() {
        vdao = new VendaDaoImp();
        venda.setFunc(func);
        venda.setImovel(imovel);
        contexto = FacesContext.getCurrentInstance();
        msn = null;
        try {
            if (venda.getId() == null) {
                vdao.salvar(venda);
                msn = "Venda realizada com Secesso!";
            } else {
                vdao.alterar(venda);
                msn = "Venda alterada com Secesso!";
            }
        } catch (Exception ex) {
            System.out.println("Erro ao salvar/alterar cliente\n" + ex.getMessage());
            msn = "Erro ao  Alterado ou Salvar cliente";
            return "menu";
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
        return "menu";
    }

    private void limpa() {
        venda = null;
        func = null;
        imovel = null;
    }

    public String novaVenda() {
        venda = new Venda();
        limpa();
        pesquisa = false;
        return "cadVenda";
    }

    public String pesquisaVenda() {
        vdao = new VendaDaoImp();
        try {
            List<Venda> vendas = vdao.listar();
            modelVenda = new ListDataModel(vendas);
        } catch (Exception ex) {
            Logger.getLogger(VendaControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "pesqVenda";
    }
    
    public void pesquisaImovel() {
        ImovelDao idao = new ImovelDaoImp();
        if (func.getNome() != null) {
            List<Imovel> imoveis = null;
            try {
                imoveis = idao.pesquisaImovelPorCodigo(imovel.getCodigo());
            } catch (Exception ex) {
                Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
            }
            modelImovel = new ListDataModel(imoveis);
        }
    }
    
    public void pesquisaFunc() {
        FuncionarioDao fdao = new FuncionarioDaoImp();
        if (func.getNome() != null) {
            List<Funcionario> funcs = null;
            try {
                funcs = fdao.pesqFunc(func.getNome());
            } catch (Exception ex) {
                Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
            }
            modelFunc = new ListDataModel(funcs);
        }
    }
    
    public void carregaImovel() {
        imovel = (Imovel) modelImovel.getRowData();
        pesquisa = false;
    }
    
    public void carregaFunc() {
        func = (Funcionario) modelFunc.getRowData();
        pesquisa = false;
    }
}