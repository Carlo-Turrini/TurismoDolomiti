package com.student.project.TurismoDolomiti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.student.project.TurismoDolomiti.entity.PeriodoPrenotato;

@Repository
public interface PeriodoPrenotatoRepository extends JpaRepository<PeriodoPrenotato, Long> {

}
