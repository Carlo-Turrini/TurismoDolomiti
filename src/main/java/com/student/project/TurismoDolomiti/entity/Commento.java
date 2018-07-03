package com.student.project.TurismoDolomiti.entity;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class) 
@Table(name = "COMMENTO")
@SQLDelete(sql = "UPDATE COMMENTO SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Commento implements Serializable {
	private static final long serialVersionUID = -2112646333836327280L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column(length = 516, nullable = false)
	private String testo;
	@Column(nullable = false, updatable = false)
	@CreatedDate
	private Timestamp timestamp;
	@Column(name = "deleted", nullable = false)
	@ColumnDefault("false")
	private Boolean deleted = false;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utente")
	private Utente utente;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_elemento")
	private Elemento elemento;
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public String getTesto() {
		return this.testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	public Utente getUtente() {
		return this.utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Elemento getElemento() {
		return elemento;
	}
	public void setElemento(Elemento elemento) {
		this.elemento = elemento;
	}

	
}
