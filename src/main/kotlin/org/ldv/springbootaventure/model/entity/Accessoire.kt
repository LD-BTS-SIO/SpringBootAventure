package org.ldv.springbootaventure.model.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
@DiscriminatorValue("accessoire")
class Accessoire(
    id:Long? = null,
    nom:String,
    description:String,
    cheminImage:String,

    // Association avec Qualite
    @ManyToOne
    @JoinColumn(name = "id_qualite")
    var qualite: Qualite? = null,

    // Association avec TypeAccesoire
    @ManyToOne
    @JoinColumn(name = "id_typeaccessoire")
    var typeAccessoire: TypeAccessoire? = null
): Item(id, nom, description, cheminImage) {

    /**
     * Équipe l'arme sur un personnage, permettant au personnage de l'utiliser pour attaquer.
     *
     * @param cible Le personnage sur lequel l'arme est équipée.
     */
    override fun utiliser(cible: Personnage):String {
        return cible.equipe(this)
    }



}