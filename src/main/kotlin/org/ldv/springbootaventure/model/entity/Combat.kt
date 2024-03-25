package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*

@Entity
class Combat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val nombreTours: Int,

    @ManyToOne
    @JoinColumn(name = "personnage_id")
        var personnage: Personnage? = null
)
/**
 * Méthode représentant le tour d'un monstre dans la campagne.
 * @return Un message décrivant les actions effectuées par le monstre pendant son tour.
 */
fun tourDeMonstre(): String {
    // Incrémenter le compteur de tours pour le personnage
    this.nbTour++
    // Initialiser le message avec des informations sur le tour du monstre
    var msg = "---Tour de ${monstre.nom} (points de vie: ${monstre.pointDeVie})---<br>"
    // Générer un nombre aléatoire entre 0 et 100
    val nbAlea = (0..100).random()
    // Le monstre a une faible chance (par exemple, 10%) de boire une potion s'il est blessé
    if (monstre.pointDeVie < monstre.pointDeVieMax / 2 && nbAlea < 10 && monstre.aUnePotion()) {
        // Si la condition est satisfaite, le monstre boit une potion sans la consommer
        msg += monstre.boirePotion(false)
    } else {
        // Sinon, le monstre prend une décision en fonction du nombre aléatoire
        if (nbAlea < 60) {
            // Le monstre attaque le héros avec une probabilité de 60%
            msg += monstre.attaquer(this.campagne.hero!!)
        } else {
            // Le monstre choisit de passer son tour avec une probabilité de 40%
            msg += "${monstre.nom} choisit de passer."
        }
    }
    // Ajouter une balise HTML de saut de ligne à la fin du message
    msg += "<br>"
    // Retourner le message décrivant les actions du monstre pendant son tour
    return msg
}


{

}