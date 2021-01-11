package controle;

import dao.FuncionarioDao;
import dao.FuncionarioDaoImp;
import dao.PerfilDao;
import dao.PerfilDaoImp;
import dao.UsuarioDao;
import dao.UsuarioDaoImp;
import entidade.Funcionario;
import entidade.Perfil;
import entidade.Usuario;
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
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@ManagedBean
@SessionScoped
public class UsuarioControle {
    
    private Perfil perfil;
    private Usuario userLoign;
    private Usuario userCadastro;
    private Funcionario func;
    private DataModel modelUser;
    private DataModel modelPerfil;
    private boolean pesquisa = false;
    private String msn = null;
    private FacesContext contexto;
    private UsuarioDao userDao;
    
    public UsuarioControle() {
        userLoign = new Usuario();
        userDao = new UsuarioDaoImp();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext) {
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication) {
                userLoign.setLogin(((User) authentication.getPrincipal()).getUsername());
            }
            try {
                userLoign = userDao.pesquisausuario(userLoign.getLogin());
            } catch (Exception ex) {
                Logger.getLogger(UsuarioControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public Usuario getUserCadastro() {
        if (userCadastro == null) {
            userCadastro = new Usuario();
        }
        return userCadastro;
    }
    
    public void setUserCadastro(Usuario userCadastro) {
        this.userCadastro = userCadastro;
    }
    
    public Perfil getPerfil() {
        if (perfil == null) {
            perfil = new Perfil();
        }
        return perfil;
    }
    
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
    public Usuario getUser() {
        return userLoign;
    }
    
    public void setUser(Usuario userLoign) {
        this.userLoign = userLoign;
    }
    
    public DataModel getModelUser() {
        return modelUser;
    }
    
    public DataModel getModelPerfil() {
        return modelPerfil;
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
    
    public String salvar() {
        
        userDao = new UsuarioDaoImp();
        userCadastro.setPerfil(perfil);
        userCadastro.setFunc(func);
        
        String login = userCadastro.getLogin();
        String senha = userCadastro.getSenha();
        userCadastro.setSenha(encriptar(senha));
        userCadastro.setEnable(true);
        contexto = FacesContext.getCurrentInstance();
        try {
            if (userCadastro.getId() == null) {
                FuncionarioDao fdao = new FuncionarioDaoImp();
                Funcionario f = fdao.pesquisar(userCadastro.getFunc().getId());
                String nome = f.getNome();
                String email = f.getEmail();
                String mensg = "Olá " + nome + "\n\n Seguem seus dados de usuários do sistema da Trust Imobiliaria "
                        + "\n\n Senha :" + senha + "\n Login :" + login
                        + "\n\n Atenciosamente Adiministrador da Trust Imobiliaria \n\nNão responder a esse e-mail";
                JavaMailApp email2 = new JavaMailApp();
                email2.Email(email, mensg);
                
                userDao.salvar(userCadastro);
                f.setUsuario(userCadastro);
                fdao.alterar(f);
                msn = "Usuário salvo com sucesso!";
            } else {
                userDao.alterar(userCadastro);
                msn = "Usuário alterado com sucesso!";
            }
        } catch (Exception ex) {
            System.out.println("ERRO ao SALVAR ou ALTERAR USER \n" + ex.getMessage());
            msn = "Erro ao tentar Salvar ou Alterar User !";
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
        return "menu";
    }
    
    public static String encriptar(String senha) {
        PasswordEncoder encoder = new Md5PasswordEncoder();
        senha = encoder.encodePassword(senha, null);
        return senha;
    }
    
    public String excluir() {
        userCadastro = (Usuario) modelUser.getRowData();
        userDao = new UsuarioDaoImp();
        try {
            userDao.excluir(userCadastro);
            msn = "Usuario Excluido com Sucesso !";
        } catch (Exception ex) {
            System.out.println("ERRO ao EXCLUIR USUARIO\n" + ex.getMessage());
            msn = "Erro ao Excluir Usuario";
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
        return "menu";
    }
    
    public String alterar() {
        userCadastro = (Usuario) modelUser.getRowData();
        return "cadUsuario";
    }
    
    private void limpa() {
        userCadastro = null;
        perfil = null;
        func = null;
    }
    
    public String novoUser() {
        userCadastro = new Usuario();
        limpa();
        pesquisa = false;
        return "cadUsuario";
    }
    
    public String pesquisa() {
        if (userCadastro != null) {
            limpa();
            modelUser = null;
        }
        pesquisa = false;
        return "pesqUsuario";
    }
    
    public List<SelectItem> getTodosPerfis() {
        PerfilDao udao = new PerfilDaoImp();
        try {
            List<Perfil> perfis;
            perfis = udao.listar();
            List<SelectItem> listaPerfil = new ArrayList<SelectItem>();
            for (Perfil perf : perfis) {
                listaPerfil.add(new SelectItem(perf.getId(), perf.getDescricao()));
            }
            return listaPerfil;
        } catch (Exception ex) {
            System.out.println("Erro a fazer lista da Combo perfil\n" + ex.getMessage());
        }
        return null;
    }
    
    public List<SelectItem> getTodosFunc() {
        userDao = new UsuarioDaoImp();
        try {
            List<Funcionario> funcs;
            funcs = userDao.pesquisaFuncionarioSemUsuario();
            List<SelectItem> listaFunc = new ArrayList<SelectItem>();
            for (Funcionario func : funcs) {
                listaFunc.add(new SelectItem(func.getId(), func.getNome()));
            }
            return listaFunc;
        } catch (Exception ex) {
            System.out.println("Erro a fazer lista da Combo \n" + ex.getMessage());
        }
        return null;
    }
    
    public void pesquisaUsuario() {
        userDao = new UsuarioDaoImp();
        try {
            if (perfil.getId() != null) {
                List<Usuario> users = userDao.pesquisaPerfil(perfil.getId());
                modelUser = new ListDataModel(users);
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioControle.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERRO ao pesquisar User\n" + ex.getMessage());
        }
    }

    /*public static void main(String[] args) {
     String senha = "123";
     PasswordEncoder encoder = new Md5PasswordEncoder();
     senha = encoder.encodePassword(senha, null);        
     String senha2 = senha;
     System.out.println(senha2);
     }*/
}