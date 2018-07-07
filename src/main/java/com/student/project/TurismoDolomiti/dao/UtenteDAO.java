package com.student.project.TurismoDolomiti.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.student.project.TurismoDolomiti.entity.Utente;

@Repository
public interface UtenteDAO extends JpaRepository<Utente, Long> {
	Optional<Utente> findById(Long id);
	Utente findByEmail(String email);
	@Query("SELECT COUNT(u) FROM Utente u WHERE u.id = :id_utente")
	Integer verificaEsistenzaUtente(@Param("id_utente")Long idUtente);
	
	@Query("SELECT u.profilePhotoPath FROM Utente u WHERE u.id = :id_utente")
	String findProfilePhotoPath(@Param("id_utente")Long idUtente);
}
