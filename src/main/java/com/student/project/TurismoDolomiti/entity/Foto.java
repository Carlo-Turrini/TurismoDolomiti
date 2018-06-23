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
@Table(name = "FOTO")
@SQLDelete(sql = "UPDATE FOTO SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Foto implements Serializable {
	private static final long serialVersionUID = 6974718924837417215L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column(length = 256, nullable = false)
	private String photoPath;
	@Column(length = 128)
	private String label;
	@Column(nullable = false, updatable = false)
	@CreatedDate
	//@Temporal(TemporalType.TIMESTAMP)
	private Timestamp timestamp;
	@Column(name = "deleted", nullable = false)
	@ColumnDefault("false")
	private Boolean deleted;
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
	public String getPhotoPath() {
		return this.photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public String getLabel() {
		return this.label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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
	

	//Override metodi equals e hashcode?
}
