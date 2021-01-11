package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class FabricaConexao {

    private static SessionFactory fabrica;

    public static SessionFactory fabricaSessao() {

        Configuration cfg = new AnnotationConfiguration();

        cfg.configure("/br/com/imobiliaria/dao/hibernate.cfg.xml");

        fabrica = cfg.buildSessionFactory();

        return fabrica;
    }
    
    public static Session abreSessao(){
     if(fabrica == null){
      fabricaSessao();     
     }
     Session session = fabrica.openSession();
     return  session;
    }
}


