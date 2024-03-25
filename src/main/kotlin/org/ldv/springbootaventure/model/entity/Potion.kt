package org.ldv.springbootaventure.model.entity

import Item
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

// Déclare la classe comme une entité JPA.
@Entity
// Spécifie la valeur du discriminateur associée à cette sous-classe.
@DiscriminatorValue("potion")
class Potion(
// Déclaration des propriétés de la classe Potion, héritées de la classe Item
// L'identifiant unique de la potion
  id: Long? = null,
  // Le nom de la potion
 nom: String,

// La description de la potion
  description: String,
  //Chemin ver l'image
    cheminImage:String?,

   // La propriété spécifique à la potion, le soin qu'elle procure
  val soin: Int

) : Item(id, nom, description,cheminImage) {
    // TODO Sprint 5 : méthode à implémenter pour la fonctionnalité "utiliser"

    /**
     * Utilise l'objet de type Potion de Soin sur un personnage cible, augmentant ses points de vie.
     *
     * @param cible Le personnage sur lequel appliquer la potion de soin.
     * @return Un message décrivant l'action effectuée, notamment le soin apporté.
     */
    override fun utiliser(cible: Personnage): String {
        // Stocker la valeur initiale des points de vie de la cible
        val pvInitiale = cible.pointDeVie

        // Ajouter le montant de soin aux points de vie de la cible
        cible.pointDeVie += soin

        // Ajouter une ligne d'inventaire pour enregistrer l'utilisation de la potion de soin
        cible.ajouterLigneInventaire(this, -1)

        // Construire et retourner un message décrivant l'action effectuée
        return "${this.nom} soigne ${cible.nom} pour ${cible.pointDeVie - pvInitiale} PV"
    }


}
