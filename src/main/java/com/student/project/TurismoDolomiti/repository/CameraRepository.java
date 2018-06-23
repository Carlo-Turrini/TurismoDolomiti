package com.student.project.TurismoDolomiti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.student.project.TurismoDolomiti.dto.CameraPrenInfoDTO;
import com.student.project.TurismoDolomiti.entity.Camera;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Long> {
	@Query("SELECT COUNT(c) FROM Camera c WHERE c.rifugio.id = :id_rif AND c.numCamera = :num_camera")
	Integer verificaNumeroCamera(@Param("id_rif")Long idRif, @Param("num_camera")Integer numCamera);
	
	@Query("SELECT c FROM Camera c WHERE c.rifugio.id = :id_rif")
	List<Camera> findCamereByRifugioId(@Param("id_rif")Long idRif);
	
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.CameraPrenInfoDTO(c.numCamera, c.tipologia, COUNT(pl)) FROM Prenotazione p JOIN PeriodoPrenotato pp ON p.id = pp.prenotazione.id JOIN PostoLetto pl ON pp.postoLetto.id = pl.id JOIN Camera c ON pl.camera.id = c.id WHERE p.id = :id_pren GROUP BY c.numCamera")
	List<CameraPrenInfoDTO> findCamerePrenInfo(@Param("id_pren")Long idPren);
	
	@Query("SELECT COUNT(c) FROM Camera c WHERE c.rifugio.id = :id_rif AND c.id = :id_camera")
	Integer verificaCameraRifugio(@Param("id_rif")Long idRif, @Param("id_camera")Long idCamera);
}

