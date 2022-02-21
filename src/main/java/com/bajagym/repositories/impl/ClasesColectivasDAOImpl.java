package com.bajagym.repositories.impl;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("clasesColectivasDAO")
public class ClasesColectivasDAOImpl {

    @PersistenceContext
    private EntityManager entityManager;
}
