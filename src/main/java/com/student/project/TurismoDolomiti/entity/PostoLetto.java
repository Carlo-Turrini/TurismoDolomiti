package com.student.project.TurismoDolomiti.entity;
import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.*;

@Entity
@Table(name = "POSTO_LETTO")
@SQLDelete(sql = "UPDATE POSTO_LETTO SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class PostoLetto implements Serializable {
	private static final long serialVersionUID = -1845978631214328166L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(name = "deleted", nullable = false)
	@ColumnDefault("false")
	private Boolean deleted = false;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_camera")
	private Camera camera;
	
	@OneToMany(mappedBy = "postoLetto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PeriodoPrenotato> periodiPrenotati = new LinkedList<PeriodoPrenotato>();
	
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
	public Camera getCamera() {
		return this.camera;
	}
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	public List<PeriodoPrenotato> getPeriodiPrenotati() {
		return periodiPrenotati;
	}
	public void setPeriodiPrenotati(List<PeriodoPrenotato> periodiPrenotati) {
		this.periodiPrenotati = periodiPrenotati;
	}
	
}
