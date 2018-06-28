package com.student.project.TurismoDolomiti.entity;
import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "PERIODO_PRENOTATO")
public class PeriodoPrenotato implements Serializable {
	private static final long serialVersionUID = 1591992348664653817L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column(name = "deleted", nullable = false)
	@ColumnDefault("false")
	private Boolean deleted = false;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_prenotazione")
	private Prenotazione prenotazione;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_posto_letto")
	private PostoLetto postoLetto;
	
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
	public Prenotazione getPrenotazione() {
		return this.prenotazione;
	}
	public void setPrenotazione(Prenotazione prenotazione) {
		this.prenotazione = prenotazione;
	}
	public PostoLetto getPostoLetto() {
		return this.postoLetto;
	}
	public void setPostoLetto(PostoLetto postoLetto) {
		this.postoLetto = postoLetto;
	}
	//Override metodi equals e hashcode?

}
