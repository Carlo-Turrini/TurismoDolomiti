package com.student.project.TurismoDolomiti.entity;
import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "POSSIEDE")
@SQLDelete(sql = "UPDATE POSSIEDE SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Possiede implements Serializable {
	private static final long serialVersionUID = 1291491393669653817L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column(name = "deleted", nullable = false)
	@ColumnDefault("false")
	private Boolean deleted = false;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utente")
	private Utente proprietario;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_rifugio")
	private Rifugio rifugio;
	
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
	public Utente getProprietario() {
		return this.proprietario;
	}
	public void setProprietario(Utente proprietario) {
		this.proprietario = proprietario;
	}
	public Rifugio getRifugio() {
		return this.rifugio;
	}
	public void setRifugio(Rifugio rifugio) {
		this.rifugio = rifugio;
	}
}
