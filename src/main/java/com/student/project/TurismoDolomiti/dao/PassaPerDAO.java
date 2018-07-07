package com.student.project.TurismoDolomiti.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.student.project.TurismoDolomiti.entity.PassaPer;


@Repository
public interface PassaPerDAO extends JpaRepository<PassaPer, Long> {
	@Query("SELECT pp.rifugio.id FROM PassaPer pp WHERE pp.escursione.id = :id_esc")
	List<Long> findPuntiRistoroEsc(@Param("id_esc")Long idEsc);
	
	@Query("SELECT pp FROM PassaPer pp WHERE pp.escursione.id = :id_esc AND pp.rifugio.id = :id_rif")
	PassaPer findPassaPerByRifAndEsc(@Param("id_esc")Long idEsc, @Param("id_rif")Long idRif);
}
