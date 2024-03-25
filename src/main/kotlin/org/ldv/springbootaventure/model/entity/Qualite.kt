package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*

@Entity
class Qualite constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var nom: String,
    var couleur: String,
    var bonusQualite: Int,

    //Association entre Qualite et Arme
    //Une qualite peut avoir plusieurs armes
    @OneToMany(mappedBy = "qualite", cascade = [CascadeType.REMOVE])
    var armes: MutableList<Arme> = mutableListOf(),
//TODO Ajouter les  autres associations
) {
    @OneToMany(mappedBy = "qualite", orphanRemoval = true)
    open var accessoires: MutableList<Accessoire> = mutableListOf()
}




