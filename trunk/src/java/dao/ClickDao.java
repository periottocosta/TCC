package dao;

import entidade.Click;

public interface ClickDao extends BaseDao<Click, Long>{
    
    Click pesquisaClick(Long id) throws Exception;
}