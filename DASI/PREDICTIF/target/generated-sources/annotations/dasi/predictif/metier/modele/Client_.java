package dasi.predictif.metier.modele;

import dasi.predictif.metier.modele.Consultation;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-05-01T22:57:24")
@StaticMetamodel(Client.class)
public class Client_ extends Personne_ {

    public static volatile SingularAttribute<Client, String> signeZodiaque;
    public static volatile SingularAttribute<Client, Date> dateNaissance;
    public static volatile SingularAttribute<Client, String> adresse;
    public static volatile SingularAttribute<Client, String> couleur;
    public static volatile SingularAttribute<Client, String> animal;
    public static volatile SingularAttribute<Client, String> signeChinois;
    public static volatile ListAttribute<Client, Consultation> consultations;

}