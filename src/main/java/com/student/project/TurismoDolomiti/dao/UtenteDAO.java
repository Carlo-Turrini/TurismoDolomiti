package com.student.project.TurismoDolomiti.dao;



import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.student.project.TurismoDolomiti.entity.Utente;

@Repository
public interface UtenteDAO extends JpaRepository<Utente, Long> {
	public Utente findByEmail(String email);
	
	@Query("SELECT COUNT(u) FROM Utente u WHERE u.id = :id_utente")
	public Integer verificaEsistenzaUtente(@Param("id_utente")Long idUtente);
	
	@Query("SELECT u.profilePhotoPath FROM Utente u WHERE u.id = :id_utente")
	public String findProfilePhotoPath(@Param("id_utente")Long idUtente);
	
	@Modifying
	@Query("UPDATE Utente u SET u.deletionToken = :del_token WHERE u.id = :id_utente")
	public Integer setDeletionTokenUtente(@Param("del_token")UUID delToken, @Param("id_utente")Long idUtente);	
	
}
