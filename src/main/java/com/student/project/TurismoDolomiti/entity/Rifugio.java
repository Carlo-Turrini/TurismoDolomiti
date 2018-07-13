package com.student.project.TurismoDolomiti.entity;
import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.Email;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import com.student.project.TurismoDolomiti.constants.Constants;

import java.util.List;
import java.util.UUID;
import java.util.LinkedList;
import java.sql.Date;


@Entity
@Table(name = "RIFUGIO", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "deletion_token"}), @UniqueConstraint(columnNames = {"tel", "deletion_token"})})
@DiscriminatorValue("Rifugio")
@PrimaryKeyJoinColumn(name = "id")
@Where(clause = "deletion_token = 0x00000000000000000000000000000000")
public class Rifugio extends Elemento implements Serializable{
	private static final long serialVersionUID = -3643975631314389166L;
	
	@Column(name = "deletion_token")
	@ColumnDefault("0")
	private UUID deletionToken = Constants.nilUUID;
	
	@Column(nullable = false) 
	private Integer altitudine;
	
	@Column(nullable = false, name = "data_apertura")
	private Date dataApertura;
	
	@Column(nullable = false, name = "data_chiusura")
	private Date dataChiusura;
	
	@Column(nullable = false, length = 256)
	@Email
	private String email;
	
	@Column(length = 10, nullable = false)
	private String tel;
	
	@Column(name = "prezzo_posto_letto", nullable = false)
	private Integer prezzoPostoLetto;

	@OneToMany(mappedBy = "rifugio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Prenotazione> prenotazioni = new LinkedList<Prenotazione>();
	
	@OneToMany(mappedBy = "rifugio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Piatto> piatti = new LinkedList<Piatto>();
	
	@OneToMany(mappedBy = "rifugio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Camera> camere = new LinkedList<Camera>();
	
	@OneToMany(mappedBy = "rifugio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PassaPer> passaPer = new LinkedList<PassaPer>();
	
	@OneToMany(mappedBy = "rifugio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Possiede> possessori = new LinkedList<Possiede>();

	public UUID getDeletionToken() {
		return deletionToken;
	}
	public void setDeletionToken(UUID deletionToken) {
		this.deletionToken = deletionToken;
	}
	public Integer getAltitudine() {
		return altitudine;
	}
	public void setAltitudine(Integer altitudine) {
		this.altitudine = altitudine;
	}
	public Date getDataApertura() {
		return dataApertura;
	}
	public void setDataApertura(Date dataApertura) {
		this.dataApertura = dataApertura;
	}
	public Date getDataChiusura() {
		return dataChiusura;
	}
	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}
	public Boolean isAperto() {
		Date dataOdierna = Date.valueOf(LocalDate.now());
		if(dataOdierna.after(this.dataApertura) && dataOdierna.before(this.dataChiusura)) {
			return true;
		}
		else return false;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getPrezzoPostoLetto() {
		return prezzoPostoLetto;
	}
	public void setPrezzoPostoLetto(Integer prezzoPostoLetto) {
		this.prezzoPostoLetto = prezzoPostoLetto;
	}
	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}
	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	public List<Piatto> getPiatti() {
		return piatti;
	}
	public void setPiatti(List<Piatto> piatti) {
		this.piatti = piatti;
	}
	public List<Camera> getCamere() {
		return camere;
	}
	public void setCamere(List<Camera> camere) {
		this.camere = camere;
	}
	public List<PassaPer> getPassaPer() {
		return passaPer;
	}
	public void setPassaPer(List<PassaPer> passaPer) {
		this.passaPer = passaPer;
	}
	public List<Possiede> getPossessori() {
		return possessori;
	}
	public void setPossessori(List<Possiede> possessori) {
		this.possessori = possessori;
	}
}
