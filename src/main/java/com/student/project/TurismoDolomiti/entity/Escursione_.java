package com.student.project.TurismoDolomiti.entity;



import java.util.UUID;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.student.project.TurismoDolomiti.enums.DifficoltaEscursione;
import com.student.project.TurismoDolomiti.enums.TipologiaEscursione;


@StaticMetamodel(Escursione.class)
public class Escursione_ extends Elemento_{
	
	public static volatile SingularAttribute<Escursione, UUID> deletionToken;
	public static volatile SingularAttribute<Escursione, TipologiaEscursione> tipologia;
	public static volatile SingularAttribute<Escursione, Float> durata;
	public static volatile SingularAttribute<Escursione, Integer> lunghezza;
	public static volatile SingularAttribute<Escursione, DifficoltaEscursione> difficolta;
	public static volatile SingularAttribute<Escursione, Integer> dislivelloSalita;
	public static volatile SingularAttribute<Escursione, Integer> dislivelloDiscesa;
	public static volatile SingularAttribute<Escursione, String> gpxPath;
	public static volatile SingularAttribute<Escursione, String> altimetriaPath;
	public static volatile ListAttribute<Escursione, PassaPer> passaPer;
	public static volatile SingularAttribute<Escursione, Boolean> completo;
	public static volatile SingularAttribute<Escursione, String> label;
}
