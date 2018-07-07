package com.student.project.TurismoDolomiti.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.student.project.TurismoDolomiti.entity.PeriodoPrenotato;

@Repository
public interface PeriodoPrenotatoDAO extends JpaRepository<PeriodoPrenotato, Long> {
	@Query("SELECT DISTINCT pp.prenotazione.id FROM PeriodoPrenotato pp JOIN pp.postoLetto pl WHERE pl.camera.id = :id_camera")
	List<Long> findPrenotazioniByCamera(@Param("id_camera")Long idCamera);
}
