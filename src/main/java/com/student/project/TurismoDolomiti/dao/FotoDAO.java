package com.student.project.TurismoDolomiti.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.student.project.TurismoDolomiti.dto.FotoCardDto;
import com.student.project.TurismoDolomiti.dto.FotoSequenceDTO;
import com.student.project.TurismoDolomiti.entity.Foto;

@Repository
public interface FotoDAO extends JpaRepository<Foto, Long> {
	
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.FotoCardDto(f.id, f.photoPath, f.label, f.timestamp, f.utente.id, f.utente.nome, f.utente.cognome, f.utente.profilePhotoPath) FROM Foto f WHERE f.elemento.id = :id_elemento ORDER BY f.timestamp DESC")
	List<FotoCardDto> findPhotoesByElemento(@Param("id_elemento") Long idElemento);
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.FotoSequenceDTO(f.photoPath, f.timestamp) FROM Foto f WHERE f.elemento.id = :id_elemento ORDER BY f.timestamp DESC")
	List<FotoSequenceDTO> findPhotoesForSequence(@Param("id_elemento") Long idElemento, Pageable pageable);
	@Query("SELECT COUNT(f) FROM Foto f WHERE f.id = :id_foto AND f.elemento.id = :id_el")
	Integer verificaEsistenzaFotoEl(@Param("id_foto")Long idFoto, @Param("id_el")Long idEl);
	@Query("SELECT COUNT(f) FROM Foto f WHERE f.id = :id_foto AND f.utente.id = :id_utente")
	Integer verificaUtenteFoto(@Param("id_foto")Long idFoto, @Param("id_utente") Long idUtente);
}
