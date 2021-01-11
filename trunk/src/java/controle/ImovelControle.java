package controle;

import dao.CepDao;
import dao.CepDaoImp;
import dao.ClienteDao;
import dao.ClienteDaoImp;
import dao.FuncionarioDao;
import dao.FuncionarioDaoImp;
import dao.ImagensDao;
import dao.ImagensDaoImp;
import dao.ImovelDao;
import dao.ImovelDaoImp;
import dao.SiteDao;
import dao.SiteDaoImp;
import dao.TipoImovelDao;
import dao.TipoImovelDaoImp;
import entidade.Bairro;
import entidade.Cep;
import entidade.Cliente;
import entidade.Endereco;
import entidade.Funcionario;
import entidade.Imagens;
import entidade.Imovel;
import entidade.Municipio;
import entidade.TipoImovel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class ImovelControle {

    private Funcionario func;
    private Cliente cliente;
    private Imovel imovel;
    private Endereco end;
    private Cep cep;
    private DataModel modelImovel;
    private DataModel modelCep;
    private DataModel modelCliente;
    private boolean pesquisa = false;
    private String msn = null;
    private FacesContext contexto;
    private ImovelDao imovelDao;
    private TipoImovel tipoImovel;
    private Municipio municipio;
    private Bairro bairro;
    private List<Bairro> bairros;
    private List<Imagens> imagens;
    private boolean casa = false;
    private boolean apartamento = false;
    private boolean salac = false;
    private boolean terreno = false;
    private static final long serialVersionUID = 1L;
    private Imagens imagen = new Imagens();
    @SuppressWarnings("unused")
    private StreamedContent file;
    private List<Cliente> clientes = new ArrayList<Cliente>();
    private List<Imagens> imagensGaleria;
    private  List<SelectItem> listBairro;
    private  List<SelectItem> listBairroSistema;

    public List<SelectItem> getListBairroSistema() {
        return listBairroSistema;
    }

    public void setListBairroSistema(List<SelectItem> listBairroSistema) {
        this.listBairroSistema = listBairroSistema;
    }
   
    public List<SelectItem> getListBairro() {
        return listBairro;
    }

    public void setListBairro(List<SelectItem> listBairro) {
        this.listBairro = listBairro;
    }
    public List<Imagens> getImagensGaleria() {
        if (imagensGaleria == null) {
            carregaGaleria();
        }
        return imagensGaleria;
    }

    public void setImagensGaleria(List<Imagens> imagensGaleria) {
        this.imagensGaleria = imagensGaleria;
    }

    public Imagens getImagen() {
        return imagen;
    }

    public void setImagen(Imagens imagen) {
        this.imagen = imagen;
    }

    public List<Imagens> getImagens() {
        return imagens;
    }

    public void setImagens(List<Imagens> imagens) {
        this.imagens = imagens;
    }

    public boolean isCasa() {
        return casa;
    }

    public boolean isApartamento() {
        return apartamento;
    }

    public boolean isSalac() {
        return salac;
    }

    public boolean isTerreno() {
        return terreno;
    }

    public List<Bairro> getBairros() {
        return bairros;
    }

    public void setBairros(List<Bairro> bairros) {
        this.bairros = bairros;
    }

    public Municipio getMunicipio() {
        if (municipio == null) {
            municipio = new Municipio();
        }
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Bairro getBairro() {
        if (bairro == null) {
            bairro = new Bairro();
        }
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
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

    public Funcionario getFunc() {
        if (func == null) {
            func = new Funcionario();
        }
        return func;
    }

    public void setFunc(Funcionario func) {
        this.func = func;
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

    public Imovel getImovel() {
        if (imovel == null) {
            imovel = new Imovel();
        }
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
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

    public DataModel getModelImovel() {
        return modelImovel;
    }

    public DataModel getModelCep() {
        return modelCep;
    }

    public DataModel getModelCliente() {
        return modelCliente;
    }

    public void pegaCliente(Cliente cli) {
        cliente = cli;
    }

    public String salvar() {
        imovelDao = new ImovelDaoImp();
        end.setCep(cep);
        imovel.setEndereco(end);
        imovel.setFuncionario(func);
        imovel.setTipoImovel(tipoImovel);
        imovel.setCliente(cliente);
        contexto = FacesContext.getCurrentInstance();
        try {
            if (imovel.getId() == null) {
                imovel.setStatusImovel("a venda");
                imovel.setCodigo(verificaSenha());
                imovelDao.salvar(imovel);
                msn = "Imovel Salvo com Sucesso !";
            } else {
                imovelDao.alterar(imovel);
                msn = "Imovel Alterado com Sucesso !";
            }
        } catch (Exception ex) {
            System.out.println("ERRO ao SALVAR ou ALTERAR IMOVEL \n" + ex.getMessage());
            msn = "Erro ao tentar Salvar ou Alterar Imovel !";
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
        return "menu";
    }

    public String verificaSenha() {
        try {
            String codigo = geraCodigo();
            List<Imovel> imovis = imovelDao.pesquisaCodigo();
            for (Imovel varImov : imovis) {
                String codImovel = varImov.getCodigo();
                while (codImovel == codigo) {
                    codigo = geraCodigo();
                }
            }
            return codigo;
        } catch (Exception ex) {
            Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String geraCodigo() throws Exception {
        String[] carct = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String codigo = "";
        for (int x = 0; x < 5; x++) {
            int j = (int) (Math.random() * carct.length);
            codigo += carct[j];
        }
        return codigo;
    }

    public String excluir() {
        imovel = (Imovel) modelImovel.getRowData();
        imovelDao = new ImovelDaoImp();
        try {
            imovelDao.excluir(imovel);
            msn = "Imovel Excluido com Sucesso !";
        } catch (Exception ex) {
            System.out.println("ERRO ao EXCLUIR IMOVEL \n" + ex.getMessage());
            msn = "Erro ao Excluir Imovel";
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
        return "menu";
    }

    public String alterar() {
        imovel = (Imovel) modelImovel.getRowData();
        tipoImovel = imovel.getTipoImovel();
        end = imovel.getEndereco();
        cep = end.getCep();
        func = imovel.getFuncionario();
        cliente = imovel.getCliente();
        return "cadImovel";
    }

    private void limpa() {
        imovel = null;
        cliente = null;
        func = null;
        end = null;
        cep = null;
    }

    public String novoImovel() {
        imovel = new Imovel();
        limpa();
        tipoImovel = null;
        pesquisa = false;
        return "cadImovel";
    }

    public void pesquisaCep() {
        CepDao cepDao = new CepDaoImp();
        try {
            if (cep.getCep() != null) {
                cep = cepDao.procuraCep(cep.getCep());
            }
        } catch (Exception ex) {
            Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String pesquisa() {
        if (imovel != null) {
            limpa();
            modelImovel = null;
        }
        pesquisa = false;
        municipio = null;
        bairro = null;
        bairros = null;
        return "pesqImovel";
    }

    public void pesquisaCliente() {
        ClienteDao clienteDao = new ClienteDaoImp();
        if (cliente.getNome() != null) {
            List<Cliente> clientes = null;
            try {
                clientes = clienteDao.pesquisaNome(cliente.getNome());
            } catch (Exception ex) {
                Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
            }
            modelCliente = new ListDataModel(clientes);
        }
    }

    public List<SelectItem> getTodosFunc() {
        FuncionarioDao funcDao = new FuncionarioDaoImp();
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

    public void pesquiBairro() {
        bairros = null;
        bairro = null;
        imovelDao = new ImovelDaoImp();
      //  listBairro = null;
        listBairroSistema = new ArrayList<SelectItem>();
        try {
            if (municipio.getNome() != null) {
                bairros = imovelDao.pesquisaBairro(municipio.getNome());
             //   return bairros;
            }
             for (Bairro b : bairros) {
               listBairroSistema.add(new SelectItem(b.getId(), b.getNome()));
            }
        } catch (Exception ex) {
            Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
        }
      //  return null;
    }

    public void pesquiBairro2(ValueChangeEvent evento) {
        bairros = null;
        String nomeCidade = evento.getNewValue().toString();
        imovelDao = new ImovelDaoImp();
      //  listBairro = null;
        listBairro = new ArrayList<SelectItem>();
        try {
            if (nomeCidade != null) {
                bairros = imovelDao.pesquisaBairro(nomeCidade);
            }
             for (Bairro b : bairros) {
                listBairro.add(new SelectItem(b.getId(), b.getNome()));
            }
        } catch (Exception ex) {
            Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void atualizaCampos() {
        if (tipoImovel.getId() == 2 || tipoImovel.getId() == 3) {
            casa = true;
        } else {
            casa = false;
        }
        if (tipoImovel.getId() == 4) {
            salac = true;
        } else {
            salac = false;
        }
    }

    public void pesquisaImovel() {
        imovelDao = new ImovelDaoImp();
        try {
            if (bairro.getId() != null) {
                List<Imovel> imovis = imovelDao.pesquisaImovel(bairro.getId());
                modelImovel = new ListDataModel(imovis);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fileUploadAction(FileUploadEvent event) throws IOException {

        try {
            imagen.setNome(event.getFile().getFileName());
            byte[] conteudo = event.getFile().getContents();
            String caminho = "E:/gustavo/projeto-PI/trunk/P.I.3/web/imagensDeImoveis/" + event.getFile().getFileName();
            //<!--E:/gustavo/projeto-PI/trunk/P.I.3/web/imagensDeImoveis/-->
            FileOutputStream fos = new FileOutputStream(caminho);
            fos.write(conteudo);
            fos.close();
            ImagensDao adao = new ImagensDaoImp();
            imagen.setCaminho(caminho);
            imagen.setTamanho(conteudo.length);
            String nomeArquivo = imagen.getNome();
            int e = nomeArquivo.lastIndexOf(".");
            imagen.setTipo(nomeArquivo.substring(e));
            imagen.setIdImovel(imovel);
            adao.salvar(imagen);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Arquivo Salvo!", imagen.getNome()));
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", " " + e));
        } finally {

            try {
                imagens = null;
                imagens = imovelDao.pesquisaImagens(imovel.getId());
            } catch (Exception ex) {
                Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
            }
            file = null;
        }
    }

    public void deleteFoto() {
        ImagensDao ImgDao = new ImagensDaoImp();
        contexto = FacesContext.getCurrentInstance();
        try {
            imagen = ImgDao.pesquisar(imagen.getId());
            new File(imagen.getCaminho()).delete();
            ImgDao.excluir(imagen);
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Arquivo Deletado!", imagen.getNome()));
        } catch (Exception ex) {
            Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            imagens = null;
            try {
                imagens = imovelDao.pesquisaImagens(imovel.getId());
            } catch (Exception ex) {
                Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void criaStatusImagenCapa() {
        ImagensDao imgDao = new ImagensDaoImp();
        try {
            imgDao.alterar(imagen);
            msn = "Adicionado com Sucesso!!";
        } catch (Exception ex) {
            Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
            msn = "Erro ao executar operação";
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));

    }

    public String carregaImovel() {
        imovel = null;
        imovel = (Imovel) modelImovel.getRowData();
        try {
            imagens = imovelDao.pesquisaImagens(imovel.getId());

        } catch (Exception ex) {
            Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "cadImagen";
    }

    public void carregaDetalhes() {
        imovel = null;
        imovel = (Imovel) modelImovel.getRowData();


    }

//    public List getGaleria() {
//        imovelDao = new ImovelDaoImp();
//        List imagenss = null;
//        try {
//            imagenss = imovelDao.pesquisaImagens(2l);
//        } catch (Exception ex) {
//            Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return imagenss;
//
//    }
    public void pesquiCodigoImovel() {
        imovelDao = new ImovelDaoImp();
        modelImovel = null;
        if (imovel.getCodigo() != null) {
            try {
                modelImovel = new ListDataModel(imovelDao.pesquisaImovelPorCodigo(imovel.getCodigo()));
            } catch (Exception ex) {
                Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void carregaCliente() {
        cliente = (Cliente) modelCliente.getRowData();
        pesquisa = false;
    }

    public String adicionadaImagenGaleria() {
        SiteDao siteDao = new SiteDaoImp();
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

    public String carregaImovelG() {
        imovel = null;
        imovel = (Imovel) modelImovel.getRowData();

        return "galeria";
    }

    public void carregaGaleria() {
        SiteDao siteDao = new SiteDaoImp();
        try {
            imagensGaleria = siteDao.pesquisaGaleria();
        } catch (Exception ex) {
            Logger.getLogger(SiteControle.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void fileUploadActionGaleria(FileUploadEvent event) throws IOException {
        SiteDao siteDao = new SiteDaoImp();
        contexto = FacesContext.getCurrentInstance();
        List<Imagens> imgs;
        try {
            imgs = siteDao.pesquisaImovelGaleriaValida();
            if (imgs.size() <= 3) {
                imagen.setNome(event.getFile().getFileName());
                byte[] conteudo = event.getFile().getContents();
                String caminho = "E:/gustavo/projeto-PI/trunk/P.I.3/web/imagensDeImoveis/" + event.getFile().getFileName();
                //<!--E:/gustavo/projeto-PI/trunk/P.I.3/web/imagensDeImoveis/-->
                FileOutputStream fos = new FileOutputStream(caminho);
                fos.write(conteudo);
                fos.close();
                ImagensDao adao = new ImagensDaoImp();
                imagen.setCaminho(caminho);
                imagen.setTamanho(conteudo.length);
                String nomeArquivo = imagen.getNome();
                int e = nomeArquivo.lastIndexOf(".");
                imagen.setTipo(nomeArquivo.substring(e));
                imagen.setIdImovel(imovel);
                siteDao.salvaStatusGaleria(imovel.getId(),imagen.getId());
                imagen.setStatusGaleria("galeria");
                adao.salvar(imagen);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Arquivo Salvo!", imagen.getNome()));
              
            } else {
                  msn = "Ja exedeu o limite de imagens na galeria";
            }
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", " " + e));
        } finally {

            try {
                imagensGaleria = null;
                imagensGaleria = siteDao.pesquisaGaleria();
            } catch (Exception ex) {
                Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
            }
            file = null;
        }
    }
     public void deleteFotoGaleria() {
        ImagensDao ImgDao = new ImagensDaoImp();
          SiteDao siteDao = new SiteDaoImp();
        contexto = FacesContext.getCurrentInstance();
        try {
            imagen = ImgDao.pesquisar(imagen.getId());
            new File(imagen.getCaminho()).delete();
            ImgDao.excluir(imagen);
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Arquivo Deletado!", imagen.getNome()));
        } catch (Exception ex) {
            Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            imagens = null;
            try {
                imagensGaleria = null;
                imagensGaleria = siteDao.pesquisaGaleria();
            } catch (Exception ex) {
                Logger.getLogger(ImovelControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
