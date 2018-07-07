package com.student.project.TurismoDolomiti.dao;

import java.util.List;

//import java.util.List;



import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.student.project.TurismoDolomiti.dto.CommentoCardDto;
import com.student.project.TurismoDolomiti.entity.Commento;

@Repository
public interface CommentoDAO extends JpaRepository<Commento, Long> {
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.CommentoCardDto(c.id, c.testo, c.timestamp, c.utente.id, c.utente.nome, c.utente.cognome, c.utente.profilePhotoPath) FROM Commento c  WHERE c.elemento.id = :id_elemento ORDER BY c.timestamp DESC")
	List<CommentoCardDto> findCommentiByElemento(@Param("id_elemento")Long idElemento, Pageable pageable); //Altrimenti page
	@Query("SELECT COUNT(c) FROM Commento c WHERE c.id = :id_com AND c.elemento.id = :id_el")
	Integer verificaEsistenzaCommentoRifugio(@Param("id_com")Long idCom, @Param("id_el")Long idEl);
	@Query("SELECT COUNT(c) FROM Commento c WHERE c.id = :id_com AND c.utente.id = :id_utente")
	Integer verificaUtenteCommento(@Param("id_com")Long idCom, @Param("id_utente")Long idUtente);
}
