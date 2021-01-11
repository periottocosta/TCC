package dao;

import entidade.Cep;
import java.util.List;

public interface CepDao extends BaseDao<Cep, Long> {
    public Cep procuraCep(String cep) throws Exception;
    public List<Cep> procuraLogradouro(String logradouro) throws Exception;
}