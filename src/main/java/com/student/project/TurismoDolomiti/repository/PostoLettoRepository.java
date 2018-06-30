package com.student.project.TurismoDolomiti.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.student.project.TurismoDolomiti.entity.PostoLetto;
import com.student.project.TurismoDolomiti.dto.PostiDisponibiliCameraRifugioDto;
import com.student.project.TurismoDolomiti.dto.PostiLettoPrenotatiCameraDTO;

@Repository
public interface PostoLettoRepository extends JpaRepository<PostoLetto, Long> {
	@Query("SELECT COUNT(pl) FROM PostoLetto pl JOIN pl.camera c WHERE c.rifugio.id = :id_rifugio AND pl.id NOT IN (SELECT DISTINCT pp.postoLetto.id FROM PeriodoPrenotato pp JOIN pp.prenotazione p  WHERE p.rifugio.id = :id_rifugio AND (p.arrivo <= :check_out OR p.partenza > :check_in))")
	Integer findNumPostiLettoRifugioDisponibiliInPeriodo(@Param("id_rifugio")Long idRifugio, @Param("check_in") Date checkIn, @Param("check_out") Date checkOut);
	
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.PostiDisponibiliCameraRifugioDto(c.id, c.capienza, c.numCamera, c.tipologia, COUNT(pl)) FROM PostoLetto pl JOIN Camera c ON pl.camera.id = c.id WHERE c.rifugio.id = :id_rifugio AND pl.id NOT IN (SELECT DISTINCT pp.postoLetto.id FROM PeriodoPrenotato pp JOIN Prenotazione p ON pp.prenotazione.id = p.id WHERE p.rifugio.id = :id_rifugio AND (p.arrivo <= :check_out OR p.partenza > :check_in)) GROUP BY c.id")
	List<PostiDisponibiliCameraRifugioDto> findPostiLettoRifugioDisponibiliGroupByCamera(@Param("id_rifugio")Long idRifugio, @Param("check_in") Date checkIn, @Param("check_out") Date checkOut);
	
	@Query("SELECT COUNT(pl) FROM PostoLetto pl WHERE pl.camera.id = :id_camera AND pl.id NOT IN (SELECT DISTINCT pp.postoLetto.id FROM PeriodoPrenotato pp JOIN Prenotazione p ON pp.prenotazione.id = p.id WHERE p.rifugio.id = :id_rifugio AND (p.arrivo <= :check_out OR p.partenza > :check_in))")
	Integer findNumPostiLettoRifugioDisponibiliInPeriodoByCamera(@Param("check_in") Date checkIn, @Param("check_out")Date checkOut, @Param("id_rifugio")Long idRifugio, @Param("id_camera") Long idCamera);
	
	@Query("SELECT pl FROM PostoLetto pl WHERE pl.camera.id = :id_camera AND pl.id NOT IN (SELECT DISTINCT pp.postoLetto.id FROM PeriodoPrenotato pp JOIN Prenotazione p ON pp.prenotazione.id = p.id WHERE p.rifugio.id = :id_rifugio AND (p.arrivo <= :check_out OR p.partenza > :check_in))")
	List<PostoLetto> findByCameraIdFreeInPeriodo(@Param("id_camera")Long idCamera, @Param("check_in") Date checkIn, @Param("check_out") Date checkOut, @Param("id_rifugio")Long idRifugio, Pageable pageable);
	
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.PostiLettoPrenotatiCameraDTO(c.id, c.numCamera, COUNT(pl), c.capienza, c.tipologia) FROM PeriodoPrenotato pp JOIN PostoLetto pl ON pp.postoLetto.id = pl.id JOIN Camera c ON pl.camera.id = c.id WHERE pp.prenotazione.id = :id_pren GROUP BY c.id")
	List<PostiLettoPrenotatiCameraDTO> findPrenotatiByCamera(@Param("id_pren")Long idPren);
	

}
