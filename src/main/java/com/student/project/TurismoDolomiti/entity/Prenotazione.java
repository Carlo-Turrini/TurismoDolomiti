package com.student.project.TurismoDolomiti.entity;
import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Date;

@Entity
@Table(name = "PRENOTAZIONE")
@SQLDelete(sql = "UPDATE PRENOTAZIONE SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Prenotazione implements Serializable{
	private static final long serialVersionUID = -1245878631294328166L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column(name = "num_persone", nullable = false) 
	private Integer numPersone;
	@Column(nullable = false)
	private Date arrivo;
	@Column(nullable = false)
	private Date partenza;
	@Column(nullable = false)
	private Integer costo;
	@Column(name = "descrizione_gruppo", length = 2048) 
	private String descrizioneGruppo;
	@Column(name = "deleted", nullable = false)
	@ColumnDefault("false")
	private Boolean deleted = false;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente")
	private Utente cliente;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_rifugio")
	private Rifugio rifugio;
	@OneToMany(mappedBy = "prenotazione", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PeriodoPrenotato> periodiPrenotati = new LinkedList<PeriodoPrenotato>();
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getNumPersone() {
		return this.numPersone;
	}
	public void setNumPersone(Integer numPersone) {
		this.numPersone = numPersone;
	}
	public Date getArrivo() {
		return this.arrivo;
	}
	public void setArrivo(Date arrivo) {
		this.arrivo = arrivo;
	}
	public Date getPartenza() {
		return this.partenza;
	}
	public void setPartenza(Date partenza) {
		this.partenza = partenza;
	}
	public Integer getCosto() {
		return costo;
	}
	public void setCosto(Integer costo) {
		this.costo = costo;
	}
	
	public String getDescrizioneGruppo() {
		return descrizioneGruppo;
	}
	public void setDescrizioneGruppo(String descrizioneGruppo) {
		this.descrizioneGruppo = descrizioneGruppo;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Utente getCliente() {
		return this.cliente;
	}
	public void setCliente(Utente cliente) {
		this.cliente = cliente;
	}
	public Rifugio getRifugio() {
		return this.rifugio;
	}
	public void setRifugio(Rifugio rifugio) {
		this.rifugio = rifugio;
	}
	public List<PeriodoPrenotato> getPeriodiPrenotati() {
		return periodiPrenotati;
	}
	public void setPeriodiPrenotati(List<PeriodoPrenotato> periodiPrenotati) {
		this.periodiPrenotati = periodiPrenotati;
	}
}
