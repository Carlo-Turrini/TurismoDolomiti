package com.student.project.TurismoDolomiti.entity;
import java.io.Serializable;


import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import com.student.project.TurismoDolomiti.constants.Constants;
import com.student.project.TurismoDolomiti.enums.DifficoltaEscursione;
import com.student.project.TurismoDolomiti.enums.TipologiaEscursione;

import java.util.*;

@Entity
@Table(name = "ESCURSIONE")
@DiscriminatorValue("Escursione")
@PrimaryKeyJoinColumn(name = "id")
@Where(clause = "deletion_token = 0")
public class Escursione extends Elemento implements Serializable {
	private static final long serialVersionUID = 1791991398669653817L;
	
	@Column(name = "deletion_token")
	@ColumnDefault("0")
	private UUID deletionToken = Constants.nilUUID;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipologiaEscursione tipologia;
	@Column(nullable = false)
	private Float durata;
	@Column(nullable = false)
	private Integer lunghezza; 
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DifficoltaEscursione difficolta;
	@Column(name = "dislivello_salita", nullable = false)
	private Integer dislivelloSalita;
	@Column(name = "dislivello_discesa", nullable = false)
	private Integer dislivelloDiscesa;
	@Column(name = "completo", nullable = false)
	@ColumnDefault("false")
	private Boolean completo = false;
	@Column(name = "gpx_path",length = 256) 
	private String gpxPath;
	@Column(name = "altimetria_path", length = 256)
	private String altimetriaPath;
	@Column(name = "label", length = 256)
	private String label;
	@OneToMany(mappedBy = "escursione", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PassaPer> passaPer = new LinkedList<PassaPer>();
	
	
	public UUID getDeletionToken() {
		return this.deletionToken;
	}
	public void setDeletionToken(UUID deletionToken) {
		this.deletionToken = deletionToken;
	}
	public TipologiaEscursione getTipologia() {
		return this.tipologia;
	}
	public void setTipologia(TipologiaEscursione tipologia) {
		this.tipologia = tipologia;
	}
	public Float getDurata() {
		return this.durata;
	}
	public void setDurata(Float durata) {
		this.durata = durata;
	}
	public Integer getLunghezza() {
		return this.lunghezza;
	}
	public void setLunghezza(Integer lunghezza) {
		this.lunghezza = lunghezza;
	}
	public DifficoltaEscursione getDifficolta() {
		return this.difficolta;
	}
	public void setDifficolta(DifficoltaEscursione difficolta) {
		this.difficolta = difficolta;
	}
	public Integer getDislivelloSalita() {
		return this.dislivelloSalita;
	}
	public void setDislivelloSalita(Integer dislivelloSalita) {
		this.dislivelloSalita = dislivelloSalita;
	}
	public Integer getDislivelloDiscesa() {
		return this.dislivelloDiscesa;
	}
	public void setDislivelloDiscesa(Integer dislivelloDiscesa) {
		this.dislivelloDiscesa = dislivelloDiscesa;
	}
	public String getGpxPath() {
		return this.gpxPath;
	}
	public void setGpxPath(String gpxPath) {
		this.gpxPath = gpxPath;
	}
	public String getAltimetriaPath() {
		return this.altimetriaPath;
	}
	public void setAltimetriaPath(String altimetriaPath) {
		this.altimetriaPath = altimetriaPath;
	}
	
	public Boolean getCompleto() {
		return completo;
	}
	public void setCompleto(Boolean completo) {
		this.completo = completo;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<PassaPer> getPassaPer() {
		return passaPer;
	}
	public void setPassaPer(List<PassaPer> passaPer) {
		this.passaPer = passaPer;
	}
}
