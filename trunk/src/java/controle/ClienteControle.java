package controle;

import dao.CepDao;
import dao.CepDaoImp;
import dao.ClienteDao;
import dao.ClienteDaoImp;
import entidade.Cep;
import entidade.Cliente;
import entidade.Endereco;
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
public class ClienteControle {

    private Cliente cliente;
    private ClienteDao clienteDao;
    private Endereco end;
    private Cep cep;
    private DataModel modelCli;
    private DataModel modelCep;
    private boolean pesquisa = false;
    private String msn = null;
    private FacesContext contexto;
    private List<Imovel> imoveis;

    public List<Imovel> getImoveis() {
        return imoveis;
    }

    public void setImoveis(List<Imovel> imoveis) {
        this.imoveis = imoveis;
    }

    public DataModel getModelCli() {
        return modelCli;
    }

    public DataModel getModelCep() {
        return modelCep;
    }

    public Cliente getCliente() {
        if (cliente == null) {
            cliente = new Cliente();
        }
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public String salvar() {
        clienteDao = new ClienteDaoImp();
        end.setCep(cep);
        cliente.setEndereco(end);
        contexto = FacesContext.getCurrentInstance();
        msn = null;
        try {
            if (cliente.getId() == null) {
              
                clienteDao.salvar(cliente);
                msn = "Cliente Salvo com Secesso!";
            } else {
                clienteDao.alterar(cliente);
                msn = "Cliente Alterado com Secesso!";
                return "menu";
            }
        } catch (Exception ex) {
            System.out.println("Erro ao salvar/alterar cliente\n" + ex.getMessage());
            msn = "Erro ao  Alterado ou Salvar cliente";
            return "menu";
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
        return "menu";
    }

    public String excluir() {
        cliente = (Cliente) modelCli.getRowData();
        clienteDao = new ClienteDaoImp();
        try {
            clienteDao.excluir(cliente);
            msn = "Cliente Excluido com Sucesso!";
        } catch (Exception ex) {
            System.out.println("Erro ao excluir cliente\n" + ex.getMessage());
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
        return "menu";
    }

    public String alterar() {
        cliente = (Cliente) modelCli.getRowData();
        end = cliente.getEndereco();
        cep = end.getCep();
        return "cadCliente";
    }

    private void limpa() {
        cliente = null;
        end = null;
        cep = null;
    }

    public String novoCliente() {
        cliente = new Cliente();
        limpa();
        pesquisa = false;
        return "cadCliente";
    }

    public void pesquisaCep() {
        CepDao cepDao = new CepDaoImp();
        try {
            if (cep.getCep() != null) {
                cep = cepDao.procuraCep(cep.getCep());
            }
        } catch (Exception ex) {
            Logger.getLogger(ClienteControle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String pesquisa() {
        if (cliente != null) {
            limpa();
            modelCli = null;
        }
        pesquisa = false;
        return "pesqCliente";
    }

//    public void pesquisaCpfCliente() {
//        clienteDao = new ClienteDaoImp();
//        try {
//            if (cliente.getCpf() != null) {
//                modelCli = new ListDataModel(clienteDao.pesquisaClienteCpf(cliente.getCpf()));
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(ClienteControle.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void pesquisaNomeCliente() {
        clienteDao = new ClienteDaoImp();
        try {
            if (cliente.getNome() != null) {
                modelCli = new ListDataModel(clienteDao.pesquisaNome(cliente.getNome()));
            }
        } catch (Exception ex) {
            Logger.getLogger(ClienteControle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pesuisaEndereco() {
        cliente = null;
        cliente = (Cliente) modelCli.getRowData();
    }

    public void carregaListaImovel() {
        clienteDao = new ClienteDaoImp();
        cliente = null;
        cliente = (Cliente) modelCli.getRowData();
        try {
            if (cliente.getId() != null) {
                imoveis = clienteDao.pesquisaImovel(cliente.getId());
            }
        } catch (Exception ex) {
            Logger.getLogger(ClienteControle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<SelectItem> getTodosCliente() {
        clienteDao = new ClienteDaoImp();
        try {
            List<Cliente> clientes;
            clientes = clienteDao.listar();
            List<SelectItem> listaCombo = new ArrayList<SelectItem>();
            for (Cliente cli : clientes) {
                listaCombo.add(new SelectItem(cli.getId(), cli.getNome()));
            }
            return listaCombo;
        } catch (Exception ex) {
            System.out.println("Erro a fazer lista da Combo \n" + ex.getMessage());
        }
        return null;
    }
    /////EM FAZE DE TESTE
}