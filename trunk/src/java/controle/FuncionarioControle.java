package controle;

import dao.CepDao;
import dao.CepDaoImp;
import dao.FuncaoDao;
import dao.FuncaoDaoImp;
import dao.FuncionarioDao;
import dao.FuncionarioDaoImp;
import dao.ImovelDao;
import dao.ImovelDaoImp;
import entidade.Cep;
import entidade.Endereco;
import entidade.Funcao;
import entidade.Funcionario;
import entidade.Imovel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class FuncionarioControle {

    private Funcionario func;
    private Funcao funcao;
    private FuncionarioDao funcDao;
    private Endereco end;
    private Cep cep;
    private DataModel modelFunc;
    private DataModel modelRelatorio;
    private DataModel modelFuncao;
    private DataModel modelCep;
    private boolean pesquisa = false;
    private String msn = null;
    private FacesContext contexto;
    private boolean cresci = false;
    private Imovel imovel;
    private boolean carrega = false;

    public boolean isCarrega() {
        return carrega;
    }

    public void setCarrega(boolean carrega) {
        this.carrega = carrega;
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

    public boolean isCresci() {
        return cresci;
    }

    public DataModel getModelFunc() {
        return modelFunc;
    }

    public DataModel getModelFuncao() {
        return modelFuncao;
    }

    public DataModel getModelCep() {
        return modelCep;
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

    public Funcao getFuncao() {
        if (funcao == null) {
            funcao = new Funcao();
        }
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public Endereco getEnd() {
        if (end == null) {
            end = new Endereco();
        }
        return end;
    }

    public void setEnd(Endereco end) {
        this.end = end;
    }

    public Cep getCep() {
        if (cep == null) {
            cep = new Cep();
        }
        return cep;
    }

    public void setCep(Cep cep) {
        this.cep = cep;
    }

    public boolean isPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(boolean pesquisa) {
        this.pesquisa = pesquisa;
    }

    public DataModel getModelRelatorio() {
        return modelRelatorio;
    }

    public String salvar() {
        funcDao = new FuncionarioDaoImp();
        end.setCep(cep);
        func.setEndereco(end);
        func.setFuncao(funcao);
        contexto = FacesContext.getCurrentInstance();
        msn = null;
        try {
            if (func.getId() == null) {
                funcDao.salvar(func);
                msn = "Funcionario Salvo com Sucesso !";
            } else {
                funcDao.alterar(func);
                msn = "Funcionario Alterado com Sucesso !";
            }
        } catch (Exception ex) {
            System.out.println("Erro ao salvar ou alterar funcionario \n" + ex.getMessage());
            msn = "Erro ao  Alterado ou Salvar Funcionario!";
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
        return "menu";
    }

    public String excluir() {
    //   func = (Funcionario) modelFunc.getRowData();
        funcDao = new FuncionarioDaoImp();
        try {
            if(func.getImoveis().isEmpty()){
            funcDao.excluir(func);
            msn = "Funcionario Excluido com Sucesso !";
              return "menu";
            }else{
            msn= "Existem Imoveis alocados no corretor!!";
            return null;
            }
            
        } catch (Exception ex) {
            System.out.println("Erro ao excluir Funcionario \n" + ex.getMessage());
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
        return null;
    }

    public String alterar() {
        //  func = (Funcionario) modelFunc.getRowData();
        end = func.getEndereco();
        cep = end.getCep();
        funcao = func.getFuncao();
        return "cadFuncionario";
    }

    private void limpa() {
        func = null;
        end = null;
        cep = null;
        funcao = null;
    }

    public String novoFuncionario() {
        func = new Funcionario();
        limpa();
        pesquisa = false;
        return "cadFuncionario";
    }

    public List<SelectItem> getTodasFuncoes() {
        FuncaoDao funcaoDao = new FuncaoDaoImp();
        try {
            List<Funcao> funcoes;
            funcoes = funcaoDao.listar();
            List<SelectItem> listaCombo = new ArrayList<SelectItem>();
            for (Funcao funca : funcoes) {
                listaCombo.add(new SelectItem(funca.getId(), funca.getNome()));
            }
            return listaCombo;
        } catch (Exception ex) {
            Logger.getLogger(FuncionarioControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SelectItem> getTodosFunc() {
        funcDao = new FuncionarioDaoImp();
        try {
            List<Funcionario> funcionarios;
            funcionarios = funcDao.listar();
            List<SelectItem> listaCombo = new ArrayList<SelectItem>();
            for (Funcionario funcs : funcionarios) {
                listaCombo.add(new SelectItem(funcs.getId(), funcs.getNome()));
            }
            return listaCombo;
        } catch (Exception ex) {
            System.out.println("Erro a fazer lista da Combo \n" + ex.getMessage());
        }
        return null;
    }
    

    public void pesquisaCep() {
        CepDao cepDao = new CepDaoImp();
        try {
            if (cep.getCep() != null) {
                cep = cepDao.procuraCep(cep.getCep());
            }
        } catch (Exception ex) {
            Logger.getLogger(FuncionarioControle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String pesquisa() {
        if (func != null) {
            limpa();
            modelFunc = null;
        }
        pesquisa = false;
        return "pesqFuncionario";
    }

    public void pesquisaFncionarioNome() {
        funcDao = new FuncionarioDaoImp();
        try {
            if (func.getId() != null) {
                func = (Funcionario) funcDao.pesquisaFuncionarioNome(func.getId());
            }
        } catch (Exception ex) {
            Logger.getLogger(FuncionarioControle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void abilitaCresci() {
        if (funcao.getId() == 1) {
            cresci = true;
        } else {
            cresci = false;
        }
    }

    public void pesquisaImovelEndereco(Long id) {
        imovel = null;
        ImovelDao imovelDao = new ImovelDaoImp();
        try {
            imovel = imovelDao.pesquisar(id);
        } catch (Exception ex) {
            Logger.getLogger(FuncionarioControle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void abiletaEnd() {
        if (imovel != null) {
            carrega = true;
        } else {
            carrega = false;
        }
    }

    public String relatorio() {
        funcDao = new FuncionarioDaoImp();
        try {
            modelRelatorio = new ListDataModel(funcDao.relatorio());
        } catch (Exception ex) {
            Logger.getLogger(FuncionarioControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "relatorioCorretor";
    }
//  public void realocaImovelCorretor(Long idFunc) {
//        FuncionarioDao fDao = new FuncionarioDaoImp();
//        ImovelDao imovelDao = new ImovelDaoImp();
//        try {
//            func = fDao.pesquisar(idFunc);
//            imovel.setFuncionario(func);
//            imovelDao.alterar(imovel);
//            
//        } catch (Exception ex) {
//            Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    public void criaImovelCorretor(Long idImovel) {
//          funcDao = new FuncionarioDaoImp();
//       ImovelDao imovelDao = new ImovelDaoImp();
//        try {
//            imovel = imovelDao.pesquisar(idImovel);
//             TodosFunc();
//        } catch (Exception ex) {
//            Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//   
//    private  List<SelectItem> listaCombo2;
//
//    public List<SelectItem> getListaCombo2() {
//        return listaCombo2;
//    }
//
//    public void setListaCombo2(List<SelectItem> listaCombo2) {
//        this.listaCombo2 = listaCombo2;
//    }
//    
//    public void TodosFunc() {
//      funcDao = new FuncionarioDaoImp();
//        try {
//            List<Funcionario> funcionarios;
//            funcionarios = funcDao.listar();
//           listaCombo2 = new ArrayList<SelectItem>();
//            for (Funcionario funcs : funcionarios) {
//                listaCombo2.add(new SelectItem(funcs.getId(), funcs.getNome()));
//            }
//          
//        } catch (Exception ex) {
//            System.out.println("Erro a fazer lista da Combo \n" + ex.getMessage());
//        }
//      
//    }
}