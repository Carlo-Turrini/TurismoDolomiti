package com.student.project.TurismoDolomiti.entity;
import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.*;

@Entity
@Table(name = "CAMERA")
@SQLDelete(sql = "UPDATE CAMERA SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Camera implements Serializable {
	private static final long serialVersionUID = -1645978631314329166L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column(length = 128, nullable = false)
	private String tipologia;
	@Column(name = "num_camera", nullable = false)
	private Integer numCamera;
	@Column(nullable = false)
	private Integer capienza;
	@Column(name = "deleted", nullable = false)
	@ColumnDefault("false")
	private Boolean deleted = false;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_rifugio")
	private Rifugio rifugio;
	@OneToMany(mappedBy = "camera", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PostoLetto> postiLetto = new LinkedList<PostoLetto>();
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipologia() {
		return this.tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public Integer getNumCamera() {
		return this.numCamera;
	}
	public void setNumCamera(Integer numCamera) {
		this.numCamera = numCamera;
	}
	public Integer getCapienza() {
		return this.capienza;
	}
	public void setCapienza(Integer capienza) {
		this.capienza = capienza;
	}
	public Rifugio getRifugio() {
		return this.rifugio;
	}
	public void setRifugio(Rifugio rifugio) {
		this.rifugio = rifugio;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	//PostiLetto
	public List<PostoLetto> getPostiLetto() {
		return this.postiLetto;
	}
	public void setPostiLetto(List<PostoLetto> postiLetto) {
		this.postiLetto = postiLetto;
	}
}
