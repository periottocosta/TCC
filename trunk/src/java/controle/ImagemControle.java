package controle;

import dao.ImagensDao;
import dao.ImagensDaoImp;
import entidade.Imagens;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class ImagemControle {

    private static final long serialVersionUID = 1L;
    private Imagens imagen = new Imagens();

    private List<Imagens> imagens = new ArrayList<Imagens>();
    @SuppressWarnings("unused")
    private StreamedContent file;


    public void fileUploadAction(FileUploadEvent event) throws IOException {
        
        try {
            imagen.setNome(event.getFile().getFileName());
            byte[] conteudo = event.getFile().getContents();
            String caminho = "C:\\Users\\Gustavo\\Documents\\imagens\\" + event.getFile().getFileName();
            FileOutputStream fos = new FileOutputStream(caminho);
            fos.write(conteudo);
            fos.close();
            ImagensDao adao = new ImagensDaoImp();

            imagen.setCaminho(caminho);
            imagen.setTamanho(conteudo.length);
            String nomeArquivo = imagen.getNome();
            int e = nomeArquivo.lastIndexOf(".");
//            IMC = new ImovelControle();
            imagen.setTipo(nomeArquivo.substring(e));
//            imagen.setIdImovel(id);
            adao.salvar(imagen);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Arquivo Salvo!", imagen.getNome()));
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", " " + e));
        } finally {
            imagen = new Imagens();
            imagens = new ArrayList< Imagens>();
            file = null;
        }
//        return "pesqImovel";
    }

    public List<Imagens> getListArquivos() {
        ImagensDao adao = new ImagensDaoImp();
        try {
            imagens = adao.listar();
        } catch (Exception ex) {
            Logger.getLogger(ImagemControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imagens;
    }

    public StreamedContent getFile() throws FileNotFoundException {
        ImagensDao adao = new ImagensDaoImp();
        Long id = imagen.getId();
        try {
            imagen = adao.pesquisar(id);
        } catch (Exception ex) {
            Logger.getLogger(ImagemControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        String caminho = imagen.getCaminho();
        FileInputStream stream = new FileInputStream(caminho);
        return file = new DefaultStreamedContent(stream, caminho, imagen.getNome());
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public Imagens getImagen() {
        return imagen;
    }

    public void setImagen(Imagens imagen) {
        this.imagen = imagen;
    }

    public List< Imagens> getImagens() {
        return imagens;
    }

    public void setImagens(List<Imagens> imagens) {
        this.imagens = imagens;
    }
}
