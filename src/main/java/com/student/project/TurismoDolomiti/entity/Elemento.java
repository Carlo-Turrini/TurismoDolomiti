package com.student.project.TurismoDolomiti.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import com.student.project.TurismoDolomiti.constants.Constants;

@Entity
@Table(name = "ELEMENTO", uniqueConstraints = @UniqueConstraint(columnNames = {"nome", "deletion_token_el"}))
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_el")
@Where(clause = "deletion_token_el = 0x00000000000000000000000000000000")
public abstract class Elemento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "deletion_token_el")
	@ColumnDefault("0")
	private UUID deletionTokenEl = Constants.nilUUID;
	
	@Column(name = "massiccio_montuoso", length = 64, nullable = false)
	private String massiccioMontuoso;
	
	@Column(length = 2048)
	private String descrizione;
	
	@Column(length = 128, nullable = false)
	private String nome;
	
	@Column(name = "latitude", nullable = false)
	private Double latitude;
	
	@Column(name = "longitude", nullable = false)
	private Double longitude;
	
	@Column(name = "icon_path", length = 256)
	@ColumnDefault("'setICONPATH'")
	private String iconPath;
	
	@OneToMany(mappedBy = "elemento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Commento> commenti = new LinkedList<Commento>();
	
	@OneToMany(mappedBy = "elemento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Foto> foto = new LinkedList<Foto>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public UUID getDeletionTokenEl() {
		return deletionTokenEl;
	}
	public void setDeletionTokenEl(UUID deletionTokenEl) {
		this.deletionTokenEl = deletionTokenEl;
	}
	public String getMassiccioMontuoso() {
		return massiccioMontuoso;
	}
	public void setMassiccioMontuoso(String massiccioMontuoso) {
		this.massiccioMontuoso = massiccioMontuoso;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public List<Commento> getCommenti() {
		return commenti;
	}
	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}
	public List<Foto> getFoto() {
		return foto;
	}
	public void setFoto(List<Foto> foto) {
		this.foto = foto;
	}
	
	
}
