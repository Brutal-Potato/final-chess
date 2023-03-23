package com.chess.registration;

import jakarta.persistence.criteria.*;

import java.util.ArrayList;

public class PlayerTableFilter implements org.springframework.data.jpa.domain.Specification<Players>{

    String playerQuery;

    public PlayerTableFilter(String queryString) {
        this.playerQuery = queryString;
    }

    @Override
    public Predicate toPredicate(Root<Players> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (playerQuery != null && playerQuery != "") {
            predicates.add(criteriaBuilder.like(root.get("firstName"), '%' + playerQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("lastName"), '%' + playerQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("dateStarted"), '%' + playerQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("emailAddress"), '%' + playerQuery + '%'));
        }

        return (! predicates.isEmpty() ? criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()])) : null);
    }
}
