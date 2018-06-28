package com.student.project.TurismoDolomiti.entity;
import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "PASSA_PER")
@SQLDelete(sql = "UPDATE PASSA_PER SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class PassaPer implements Serializable {
	private static final long serialVersionUID = 1491591348669647817L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column(name = "deleted", nullable = false)
	@ColumnDefault("false")
	private Boolean deleted = false;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_escursione")
	private Escursione escursione;
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
	public Escursione getEscursione() {
		return this.escursione;
	}
	public void setEscursione(Escursione escursione) {
		this.escursione = escursione;
	}
	public Rifugio getRifugio() {
		return this.rifugio;
	}
	public void setRifugio(Rifugio rifugio) {
		this.rifugio = rifugio;
	}
	//Override metodi equals e hashcode?
}
