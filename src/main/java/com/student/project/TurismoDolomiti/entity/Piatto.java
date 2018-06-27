package com.student.project.TurismoDolomiti.entity;
import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "PIATTO")
@SQLDelete(sql = "UPDATE PIATTO SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Piatto implements Serializable {
	private static final long serialVersionUID = -7271845831766487137L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column(length = 64, nullable = false) 
	private String nome;
	@Column(nullable = false)
	private Integer prezzo; //In euro
	@Column(length = 516, nullable = false)
	private String descrizione;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CategoriaMenu categoria;
	@Column(name = "deleted", nullable = false)
	@ColumnDefault("false")
	private Boolean deleted;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_rifugio")
	private Rifugio rifugio;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(Integer prezzo) {
		this.prezzo = prezzo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public CategoriaMenu getCategoria() {
		return categoria;
	}
	public void setCategoria(CategoriaMenu categoria) {
		this.categoria = categoria;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Rifugio getRifugio() {
		return rifugio;
	}
	public void setRifugio(Rifugio rifugio) {
		this.rifugio = rifugio;
	}
	
	
}
