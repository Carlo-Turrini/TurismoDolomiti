package com.student.project.TurismoDolomiti.entity;

import javax.persistence.metamodel.StaticMetamodel;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.ListAttribute;
import java.sql.Date;
import java.util.UUID;

@StaticMetamodel(Rifugio.class)
public class Rifugio_ extends Elemento_ {
	
	public static volatile SingularAttribute<Rifugio, UUID> deletionToken;
	public static volatile SingularAttribute<Rifugio, Integer> altitudine;
	public static volatile SingularAttribute<Rifugio, Date> dataApertura;
	public static volatile SingularAttribute<Rifugio, Date> dataChiusura;
	public static volatile SingularAttribute<Rifugio, String> email;
	public static volatile SingularAttribute<Rifugio, String> tel;
	public static volatile SingularAttribute<Rifugio, Integer> prezzoPostoLetto;
	public static volatile ListAttribute<Rifugio, Possiede> possessori;
	public static volatile ListAttribute<Rifugio, PassaPer> passaPer;
	public static volatile ListAttribute<Rifugio, Camera> camere;
	public static volatile ListAttribute<Rifugio, Prenotazione> prenotazioni;
	public static volatile ListAttribute<Rifugio, Piatto> piatti;
	
		
}
