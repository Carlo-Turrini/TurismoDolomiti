package com.student.project.TurismoDolomiti.repository;
import javax.persistence.criteria.*;
import javax.persistence.criteria.Root;

import com.student.project.TurismoDolomiti.entity.DifficoltaEscursione;


import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import com.student.project.TurismoDolomiti.entity.Escursione;
import com.student.project.TurismoDolomiti.entity.Escursione_;
import com.student.project.TurismoDolomiti.entity.TipologiaEscursione;

public class EscursioneSpecification implements Specification<Escursione> {
	private static final long serialVersionUID = -5326872995084852320L;
	private EscursioneSearch criteria;
	
	public EscursioneSpecification(EscursioneSearch escursioneSearch) {
		this.criteria = escursioneSearch;
	}
	
	@Override
	public Predicate toPredicate(Root<Escursione> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		
		Path<String> nome = root.get(Escursione_.nome);
		Path<Float> durata = root.get(Escursione_.durata);
		Path<Integer> dislivelloSalita = root.get(Escursione_.dislivelloSalita);
		Path<Integer> dislivelloDiscesa = root.get(Escursione_.dislivelloDiscesa);
		Path<Integer> lunghezza = root.get(Escursione_.lunghezza);
		Path<DifficoltaEscursione> difficolta = root.get(Escursione_.difficolta);
		Path<TipologiaEscursione> tipologia = root.get(Escursione_.tipologia);
		Path<String> massiccioMontuoso = root.get(Escursione_.massiccioMontuoso);
		Path<Boolean> completo = root.get(Escursione_.completo);
		
		final List<Predicate> predicates = new ArrayList<Predicate>();
		if(criteria.getNome() != null) {
			predicates.add(cb.like(nome, "%" + criteria.getNome() + "%"));
		}
		if(criteria.getDurataMin() != null) {
			predicates.add(cb.greaterThanOrEqualTo(durata, criteria.getDurataMin()));
		}
		if(criteria.getDurataMax() != null) {
			predicates.add(cb.lessThanOrEqualTo(durata, criteria.getDurataMax()));
		}
		if(criteria.getDislivelloSalitaMin() != null) {
			predicates.add(cb.greaterThanOrEqualTo(dislivelloSalita, criteria.getDislivelloSalitaMin()));
		}
		if(criteria.getDislivelloSalitaMax() != null) {
			predicates.add(cb.lessThanOrEqualTo(dislivelloSalita, criteria.getDislivelloSalitaMax()));
		}
		if(criteria.getDislivelloDiscesaMin() != null) {
			predicates.add(cb.greaterThanOrEqualTo(dislivelloDiscesa, criteria.getDislivelloDiscesaMin()));
		}
		if(criteria.getDislivelloDiscesaMax() != null) {
			predicates.add(cb.lessThanOrEqualTo(dislivelloDiscesa, criteria.getDislivelloDiscesaMax()));
		}
		if(criteria.getLunghezzaMin() != null) {
			predicates.add(cb.greaterThanOrEqualTo(lunghezza, criteria.getLunghezzaMin()));
		}
		if(criteria.getLunghezzaMax() != null) {
			predicates.add(cb.lessThanOrEqualTo(lunghezza, criteria.getLunghezzaMax()));
		}
		if(criteria.getTipologia() != null) {
			predicates.add(cb.equal(tipologia, criteria.getTipologia()));
		}
		if(criteria.getDifficolta() != null) {
			predicates.add(cb.equal(difficolta, criteria.getDifficolta()));
		}
		if(criteria.getMassiccioMontuoso() != null) {
			predicates.add(cb.like(massiccioMontuoso, "%" + criteria.getMassiccioMontuoso() + "%"));
		}
		predicates.add(cb.equal(completo, true));
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		
		
	}
}
