package controle;

import dao.ClienteDao;
import dao.ClienteDaoImp;
import entidade.Cliente;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "propietarioConverter")
public class PropietarioConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String string) {
        ClienteDao cliDao = new ClienteDaoImp();

        try {
            Cliente cliente = cliDao.pesquisaNome2(string);
            return cliente;
        } catch (Exception ex) {
            Logger.getLogger(PropietarioConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object obj) {
        Cliente cliente = new Cliente();
        cliente = (Cliente) obj;
        return cliente.getNome();
    }
}
