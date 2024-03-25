package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*

@Entity
class Utilisateur(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val email: String,
    val mdp: String,

    @OneToMany(mappedBy = "utilisateur", orphanRemoval = true)
    var campagnes: MutableList<Campagne> = mutableListOf(),

    @OneToMany(mappedBy = "utilisateur", orphanRemoval = true)
    var personnages: MutableList<Personnage> = mutableListOf(),

    @ManyToMany(mappedBy = "utilisateurs")
    var roles: MutableList<Role> = mutableListOf()
) {

}


