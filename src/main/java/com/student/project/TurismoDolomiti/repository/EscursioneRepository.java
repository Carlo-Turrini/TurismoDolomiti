package com.student.project.TurismoDolomiti.repository;

import com.student.project.TurismoDolomiti.dto.EscursioneCardDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.student.project.TurismoDolomiti.entity.Escursione;


import java.util.*;

@Repository
public interface EscursioneRepository extends JpaRepository<Escursione, Long>, JpaSpecificationExecutor<Escursione> {
	Optional<Escursione> findById(Long id);
	
	List<EscursioneCardDto> findAllDtoedBy();
	
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.EscursioneCardDto(esc.id, esc.nome, esc.iconPath, esc.durata, esc.dislivelloSalita, esc.dislivelloDiscesa, esc.lunghezza, esc.difficolta, esc.tipologia, esc.massiccioMontuoso, esc.label) FROM Escursione esc JOIN PassaPer pp ON esc.id = pp.escursione.id WHERE pp.rifugio.id = :id_rifugio AND esc.completo = TRUE")
	List<EscursioneCardDto> findElencoEscursioniPerRifugio(@Param("id_rifugio")Long rifugioId);
	@Query("SELECT COUNT(e) FROM Escursione e WHERE e.id = :id_escursione") 
	Integer verificaEsistenzaEsc(@Param("id_escursione")Long idEsc);
	@Query("SELECT e.nome FROM Escursione e WHERE e.id = :id_escursione")
	String findNomeEscursione(@Param("id_escursione")Long idEsc);
	Escursione findByNome(String nome);
	List<Escursione> findAll();
	
	@Query("SELECT e.iconPath FROM Escursione e WHERE e.id = :id_escursione")
	String findEscIconPath(@Param("id_escursione")Long idEsc);
	@Query("SELECT e.gpxPath FROM Escursione e WHERE e.id = :id_escursione") 
	String findEscGpxPath(@Param("id_escursione")Long idEsc);
	@Query("SELECT e.altimetriaPath FROM Escursione e WHERE e.id = :id_escursione")
	String findEscAltimetriaPath(@Param("id_escursione")Long idEsc);
	
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.EscursioneCardDto(esc.id, esc.nome, esc.iconPath, esc.durata, esc.dislivelloSalita, esc.dislivelloDiscesa, esc.lunghezza, esc.difficolta, esc.tipologia, esc.massiccioMontuoso, esc.label) FROM Escursione esc WHERE esc.completo = FALSE ")
	List<EscursioneCardDto> findElencoEscursioniDaCompletare();
	
	
}
