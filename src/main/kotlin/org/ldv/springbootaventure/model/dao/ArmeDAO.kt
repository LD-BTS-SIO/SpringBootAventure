package org.ldv.springbootaventure.model.dao
import org.ldv.springbootaventure.model.entity.Arme
import org.springframework.data.jpa.repository.JpaRepository

interface ArmeDAO : JpaRepository<Arme,Long> {
}