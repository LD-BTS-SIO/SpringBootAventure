package org.ldv.springbootaventure.model.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
@DiscriminatorValue("armure")
class Armure(
             id: Long? = null,
              nom: String,
              description: String,
             cheminImage: String?,

    // Association avec Qualite
              @ManyToOne
              @JoinColumn(name = "id_qualite")
              var qualite: Qualite? = null,

    // Association avec TypeArmure
              @ManyToOne
              @JoinColumn(name = "id_typearmure")
              var typeArme: TypeArme? = null
): Item(id, nom, description, cheminImage) {


//    fun calculerProtection();

    /**
     * Équipe l'arme sur un personnage, permettant au personnage de l'utiliser pour attaquer.
     *
     * @param cible Le personnage sur lequel l'arme est équipée.
     */
    override fun utiliser(cible: Personnage):String {
        return cible.equipe(this)
    }
}
