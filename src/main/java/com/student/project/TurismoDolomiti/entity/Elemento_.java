package com.student.project.TurismoDolomiti.entity;

import java.util.UUID;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Elemento.class)
public class Elemento_ {
	public static volatile SingularAttribute<Elemento, Long> id;
	public static volatile SingularAttribute<Elemento, UUID> deletionTokeEl;
	public static volatile SingularAttribute<Elemento, String> massiccioMontuoso;
	public static volatile SingularAttribute<Elemento, String> descrizione;
	public static volatile SingularAttribute<Elemento, String> nome;
	public static volatile SingularAttribute<Elemento, Double> latitude;
	public static volatile SingularAttribute<Elemento, Double> longitude;
	public static volatile SingularAttribute<Elemento, String> iconPath;
	public static volatile ListAttribute<Elemento, Commento> commenti;
	public static volatile ListAttribute<Elemento, Foto> foto;
	
}
