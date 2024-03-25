package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*


@Entity
class Campagne(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null,
    var nom:String,
    var score: Int,
    var etat: String,
    var dernierScore: Int,
    var meilleurScore:Int
) {


    @ManyToOne
    @JoinColumn(name = "personnage_id")
    var personnage: Personnage? = null

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    open var utilisateur: Utilisateur? = null
}


