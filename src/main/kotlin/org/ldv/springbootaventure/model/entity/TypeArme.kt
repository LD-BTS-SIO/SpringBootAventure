package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*

@Entity
class TypeArme constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var nombreDes: Int,
    var valeurDeMax: Int,
    var multiplicateurCritique: Int,
    var activationCritique: Int,
    var nom: String,
//Association entre TypeArme et Arme
//Une qualite peut avoir plusieurs armes
@OneToMany(mappedBy = "typeArme", cascade = [CascadeType.REMOVE])
var armes: MutableList<Arme> = mutableListOf(),
//TODO Ajouter les  autres assocations
) {
}