package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Imovel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String descricao;
    private Integer Ndormitorios;
    private String Nsuites;
    private String areaTotal;
    private String arePrivada;
    private String NvagasGaragem;
    private String Nbanheiros;
    private double valorImovel;
    private String codigo;
    private String statusImovel;
    @ManyToOne
    @JoinColumn(name = "idTipoImovel")
    private TipoImovel tipoImovel;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idEndereco")
    private Endereco endereco;
    @OneToMany(mappedBy = "idImovel")
    private List<Imagens> imagens;
    @ManyToOne
    @JoinColumn(name = "idFuncionario")
    private Funcionario funcionario;
    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    public String getStatusImovel() {
        return statusImovel;
    }

    public void setStatusImovel(String statusImovel) {
        this.statusImovel = statusImovel;
    }

    public Integer getNdormitorios() {
        return Ndormitorios;
    }

    public void setNdormitorios(Integer Ndormitorios) {
        this.Ndormitorios = Ndormitorios;
    }

    public double getValorImovel() {
        return valorImovel;
    }

    public void setValorImovel(double valorImovel) {
        this.valorImovel = valorImovel;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Imagens> getImagens() {
        return imagens;
    }

    public void setImagens(List<Imagens> imagens) {
        this.imagens = imagens;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNbanheiros() {
        return Nbanheiros;
    }

    public void setNbanheiros(String Nbanheiros) {
        this.Nbanheiros = Nbanheiros;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNsuites() {
        return Nsuites;
    }

    public void setNsuites(String Nsuites) {
        this.Nsuites = Nsuites;
    }

    public String getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(String areaTotal) {
        this.areaTotal = areaTotal;
    }

    public String getArePrivada() {
        return arePrivada;
    }

    public void setArePrivada(String arePrivada) {
        this.arePrivada = arePrivada;
    }

    public String getNvagasGaragem() {
        return NvagasGaragem;
    }

    public void setNvagasGaragem(String NvagasGaragem) {
        this.NvagasGaragem = NvagasGaragem;
    }

    public TipoImovel getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(TipoImovel tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Imovel)) {
            return false;
        }
        Imovel other = (Imovel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.imobiliaria.entidade.Imovel[ id=" + id + " ]";
    }
}
