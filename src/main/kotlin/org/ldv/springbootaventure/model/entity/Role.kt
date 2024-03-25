package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*

@Entity
class Role (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val nom: String,

    @ManyToMany
@JoinTable(
    name = "Role_utilisateurs",
    joinColumns = [JoinColumn(name = "role_id")],
    inverseJoinColumns = [JoinColumn(name = "utilisateurs_id")]
)
var utilisateurs: MutableList<Utilisateur> = mutableListOf()
) {

}