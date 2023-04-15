package com.example.poc.infra.repositories;

import com.example.poc.infra.annotations.ReaderDataSource;
import com.example.poc.infra.entities.TesteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TesteRepository extends JpaRepository<TesteEntity, Integer> {
    @Query("SELECT t from TesteEntity t where t.id=1")
    TesteEntity getByRw();

    @Query("SELECT t from TesteEntity t where t.id=1")
    @ReaderDataSource
    TesteEntity getByRo();

}
