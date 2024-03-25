package org.ldv.springbootaventure.model.dao
import org.ldv.springbootaventure.model.entity.TypeArme
import org.springframework.data.jpa.repository.JpaRepository

interface TypeArmeDAO : JpaRepository<TypeArme,Long> {
}