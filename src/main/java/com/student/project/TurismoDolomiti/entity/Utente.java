package com.student.project.TurismoDolomiti.entity;
import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import com.student.project.TurismoDolomiti.constants.Constants;
import com.student.project.TurismoDolomiti.enums.CredenzialiUtente;
import com.student.project.TurismoDolomiti.enums.Sesso;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "UTENTE", uniqueConstraints = @UniqueConstraint(columnNames = {"email", "deletion_token"}))
@Where(clause = "deletion_token = 0x00000000000000000000000000000000")
public class Utente implements Serializable {
	private static final long serialVersionUID = -4271845821706887137L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	
	@ColumnDefault("0")
	@Column(name = "deletion_token")
	private UUID deletionToken = new UUID(0L, 0L);
	
	@Column(length = 48, nullable = false)
	private String nome;
	
	@Column(length = 48, nullable = false)
	private String cognome;
	
	@Email
	@Column(length = 128, nullable = false)
	private String email;
	
	@Column (length = 64, nullable = false)
	private String password;
	
	@Column(nullable = false, name = "data_nascita")
	private Date dataNascita;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Sesso sesso;
	
	@Column(length = 10)
	private String tel;
	
	@Enumerated(EnumType.STRING)
	@ColumnDefault("'Normale'")
	@Column(name = "credenziali")
	private CredenzialiUtente credenziali = CredenzialiUtente.Normale;
	
	
	@ColumnDefault("'/profiledefault.png'")
	@Column(length = 128, name = "profile_photo_path")
	private String profilePhotoPath = Constants.profileDef;
	
	@Column(length = 2048)
	private String descrizione;
	
	@OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Commento> commenti = new LinkedList<Commento>();
	@OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Foto> foto = new LinkedList<Foto>();
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Prenotazione> prenotazioni = new LinkedList<Prenotazione>();
	@OneToMany(mappedBy = "proprietario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Possiede> rifGestiti  = new LinkedList<Possiede>();
	
	public UUID getDeletionToken() {
		return deletionToken;
	}
	public void setDeletionToken(UUID deletionToken) {
		this.deletionToken = deletionToken;
	}
	public List<Possiede> getRifGestiti() {
		return rifGestiti;
	}
	public void setRifGestiti(List<Possiede> rifGestiti) {
		this.rifGestiti = rifGestiti;
	}
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome=nome;
	}
	public String getCognome() {
		return this.cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public Sesso getSesso() {
		return this.sesso;
	}
	public void setSesso(Sesso sesso) {
		this.sesso=sesso;
	}
	public String getTel() {
		return this.tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getDescrizione() {
		return this.descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione=descrizione;
	}
	public CredenzialiUtente getCredenziali() {
		return this.credenziali;
	}
	public void setCredenziali(CredenzialiUtente credenziali) {
		this.credenziali = credenziali;
	}
	public String getProfilePhotoPath() {
		return profilePhotoPath;
	}
	public void setProfilePhotoPath(String profilePhotoPath) {
		this.profilePhotoPath = profilePhotoPath;
	}
	
	public List<Commento> getCommenti() {
		return this.commenti;
	}
	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}

	public List<Foto> getFoto() {
		return this.foto;
	}
	public void setFoto(List<Foto> foto) {
		this.foto = foto;
	}

	public List<Prenotazione> getPrenotazioni() {
		return this.prenotazioni;
	}
	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

	
}
