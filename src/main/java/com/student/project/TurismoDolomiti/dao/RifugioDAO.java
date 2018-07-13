package com.student.project.TurismoDolomiti.dao;

import com.student.project.TurismoDolomiti.dto.RifugioCartinaEscursioneCardDto;
import com.student.project.TurismoDolomiti.dto.RifugioNomeIdDTO;
import com.student.project.TurismoDolomiti.dto.RifugioCardDto;
import com.student.project.TurismoDolomiti.entity.Rifugio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface RifugioDAO extends JpaRepository<Rifugio, Long>, JpaSpecificationExecutor<Rifugio>{
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.RifugioCardDto(r.id, r.nome, r.massiccioMontuoso, r.altitudine, r.dataApertura, r.dataChiusura, r.iconPath) FROM Possiede p JOIN p.rifugio r WHERE p.proprietario.id = :id_utente")
	public List<RifugioCardDto> elencoRifugiPosseduti(@Param("id_utente") Long idUtente);
	
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.RifugioCartinaEscursioneCardDto(r.id, r.nome, r.latitude, r.longitude, r.iconPath) FROM Rifugio r JOIN Elemento e ON r.id = e.id JOIN PassaPer pp ON r.id = pp.rifugio.id WHERE pp.escursione.id = :id_escursione")
	public List<RifugioCartinaEscursioneCardDto> findRifugiEscursione(@Param("id_escursione") Long idEscursione);
	
	@Query("SELECT COUNT(r) FROM Rifugio r WHERE r.nome = :nome_rifugio")
	public Integer verificaEsistenzaRifugioByNome(@Param("nome_rifugio")String nomeRifugio);
	
	@Query("SELECT COUNT(r) FROM Rifugio r WHERE r.tel = :tel_rifugio")
	public Integer verificaEsistenzaRifugioByTel(@Param("tel_rifugio")String telRifugio);
	
	@Query("SELECT COUNT(r) FROM Rifugio r WHERE r.email = :email_rifugio")
	public Integer verificaEsistenzaRifugioByEmail(@Param("email_rifugio")String emailRifugio);
	
	@Query("SELECT COUNT(r) FROM Rifugio r WHERE r.id = :id_rifugio")
	public Integer verificaEsistenzaRifugio(@Param("id_rifugio")Long idRif);
	
	@Query("SELECT r.nome FROM Rifugio r WHERE r.id = :id_rifugio")
	public String findNomeRifugio(@Param("id_rifugio")Long idRif);
	
	public Rifugio findByEmail(String email);
	
	public Rifugio findByTel(String tel);

	public Rifugio findByNome(String nome);
	
	@Query("SELECT r.nome FROM Rifugio r")
	public List<String> findRifugiNames();
	
	@Query("SELECT COUNT(r) FROM Rifugio r WHERE r.id = :id_rifugio AND r.dataApertura <= :check_in AND r.dataChiusura >= :check_out")
	public Integer verificaRifugioApertoInPeriodo(@Param("id_rifugio")Long idRifugio, @Param("check_in")Date checkIn, @Param("check_out")Date checkOut);
	
	@Query("SELECT r.iconPath FROM Rifugio r WHERE r.id = :id_rif")
	public String findRifugioIconPath(@Param("id_rif")Long idRif);
	
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.RifugioNomeIdDTO(r.id, r.nome) FROM Rifugio r")
	public List<RifugioNomeIdDTO> findRifugioNomeAndId();
	
	@Query("SELECT r.prezzoPostoLetto FROM Rifugio r WHERE r.id = :id_rif")
	public Integer findPrezzoPostoLetto(@Param("id_rif")Long idRif);
	
	@Modifying
	@Query("UPDATE Rifugio r SET r.deletionToken = :del_token, r.deletionTokenEl = :del_token WHERE r.id = :id_rif")
	public Integer setDeletionTokenRifugio(@Param("del_token")UUID deletionToken, @Param("id_rif")Long idRif);
	
	
}
