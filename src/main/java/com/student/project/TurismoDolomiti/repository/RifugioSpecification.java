package com.student.project.TurismoDolomiti.repository;

import com.student.project.TurismoDolomiti.entity.Rifugio;
import com.student.project.TurismoDolomiti.entity.Rifugio_;

import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RifugioSpecification implements Specification<Rifugio> {
	private RifugioSearch criteria;
	
	public RifugioSpecification(RifugioSearch rifugioSearch) {
		this.criteria = rifugioSearch;
	}
	
	@Override
	public Predicate toPredicate(Root<Rifugio> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

		Path<Date> dataApertura = root.get(Rifugio_.dataApertura);
		Path<Date> dataChiusura = root.get(Rifugio_.dataChiusura);
		Path<String> nome = root.get(Rifugio_.nome);
		Path<String> massiccioMontuoso = root.get(Rifugio_.massiccioMontuoso);
		
		final List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(criteria.isAperto()) {
			Date dataOdierna = Date.valueOf(LocalDate.now());
			predicates.add(cb.lessThanOrEqualTo(dataApertura, dataOdierna));
			predicates.add(cb.greaterThanOrEqualTo(dataChiusura, dataOdierna));
		}
		if(criteria.getNome() != null) {
			predicates.add(cb.like(nome, "%" + criteria.getNome() + "%"));
		}
		if(criteria.getMassiccioMontuoso() != null) {
			predicates.add(cb.like(massiccioMontuoso, "%" + criteria.getMassiccioMontuoso() + "%"));
		}
		
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}
}
