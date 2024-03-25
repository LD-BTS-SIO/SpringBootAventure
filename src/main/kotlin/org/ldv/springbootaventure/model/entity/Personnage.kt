package org.ldv.springbootaventure.model.entity


import jakarta.persistence.*

import org.ldv.springbootaventure.model.entity.Accessoire
import org.ldv.springbootaventure.model.entity.Arme
import org.ldv.springbootaventure.model.entity.LigneInventaire
import org.ldv.springbootaventure.model.entity.Qualite

import java.nio.file.Files
import java.nio.file.Paths

@Entity

class Personnage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val nom: String,
    val attaque: Int,
    val defense: Int,
    val endurance: Int,
    val vitesse: Int,
    val cheminImage: String,
    @OneToMany(mappedBy = "personnage", cascade = [CascadeType.REMOVE])
    var ligneInventaires: MutableList<LigneInventaire> = mutableListOf(),
    @OneToMany(mappedBy = "personnage", orphanRemoval = true)
    open var campagnes: MutableList<Campagne> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    open var utilisateur: Utilisateur? = null,

    @ManyToMany(mappedBy = "personnages")
    open var items: MutableList<Item> = mutableListOf(),


    @OneToMany(mappedBy = "personnage", orphanRemoval = true)
    var combats: MutableList<Combat> = mutableListOf(),
    @ManyToOne
    @JoinColumn(name = "armure_equipee_id")
    open var armureEquipee: Armure? = null,


    @ManyToOne
    @JoinColumn(name = "arme_equipee_id")
    open var armeEquipee: Arme? = null
) {

    val pointDeVieMax: Int
        get() = 50 + (10 * (this.endurance))


    var pointDeVie: Int = this.pointDeVieMax
        set(value) {
            field = minOf(value, this.pointDeVieMax)
        }


//    open fun attaquer(adversaire: Personnage):String {
//        // Vérifier si le personnage a une arme équipée
//        var degats = this.attaque / 2
//        if (armeEquipee != null) {
//            // Calculer les dégâts en utilisant les attributs du personnage et la méthode calculerDegat de l'arme
//            degats += this.armeEquipee!!.calculerDegats()
//        }
//        // Appliquer la défense de l'adversaire (au minimum au moins 1 de dégat)
//        val degatsInfliges = maxOf(1, degats - adversaire.calculeDefense())
//
//
//        // Appliquer les dégâts à l'adversaire
//        adversaire.pointDeVie = adversaire.pointDeVie - degatsInfliges
//
//        return("$nom attaque ${adversaire.nom} avec ${armeEquipee?.nom ?: "une attaque de base"} et inflige $degatsInfliges points de dégâts.")
//    }
//
//
//    // Méthode pour boire une potion de l'inventaire
//    fun boirePotion() {
//        val potions = inventaire.filterIsInstance<Potion>()
//        if (potions.isNotEmpty()) {
//            val potion = potions.first()
//            val soin = potion.soin
//            pointDeVie += soin
//            if (pointDeVie > pointDeVieMax) {
//                pointDeVie = pointDeVieMax
//            }
//            inventaire.remove(potion)
//            println("$nom boit ${potion.nom} et récupère $soin points de vie.")
//        } else {
//            println("$nom n'a pas de potion dans son inventaire.")
//        }
//    }
//
//    fun equipe(arme: Arme): String {
//
//
//
//    }
//
//    fun equipe(accessoire: Accessoire)
//    {
//
//    }
//
//    fun loot(cible: Personnage) {
//        cible.armeEquipee = null
//        cible.armureEquipee = null
//        this.inventaire.addAll(cible.inventaire)
//        cible.inventaire.forEach({ println("${this.nom} récupère un/une $it") })
//        println()
//        cible.inventaire.clear()
//    }
//
//    fun ajouterLigne(item: Item, quantite : int)
//    {
//
//    }
//
//    fun calculeDefense(): Int {
//        var resultat = this.defense / 2
//        val scoreArmure =
//            (this.armureEquipee?.typeArmure?.bonusType ?: 0) + (this.armureEquipee?.qualite?.bonusRarete ?: 0)
//        resultat += scoreArmure
//        return resultat;
//
//    }


    // Association avec LignrInventaire


    /**
     * Ajoute une ligne d'inventaire pour l'item spécifié avec la quantité donnée.
     * Si une ligne d'inventaire pour cet item existe déjà, met à jour la quantité.
     * Si la quantité résultante est inférieure ou égale à zéro, la ligne d'inventaire est supprimée.
     *
     * @param unItem L'item pour lequel ajouter ou mettre à jour la ligne d'inventaire.
     * @param uneQuantite La quantité à ajouter à la ligne d'inventaire existante ou à la nouvelle ligne.
     */
    fun ajouterLigneInventaire(unItem: Item, uneQuantite: Int) {
        // Chercher une ligne d'inventaire existante pour l'item spécifié
        val ligneItem = this.ligneInventaires.find { ligneInventaire -> ligneInventaire.item == unItem }

        // Si aucune ligne d'inventaire n'est trouvée, en créer une nouvelle
        if (ligneItem == null) {
            // Créer un nouvel identifiant pour la ligne d'inventaire
            val ligneInventaireId = LigneInventaireId(this.id!!, unItem.id!!)

            // Ajouter une nouvelle ligne d'inventaire à la liste
            this.ligneInventaires.add(LigneInventaire(ligneInventaireId, uneQuantite, this, unItem))
        } else {
            // Si une ligne d'inventaire existante est trouvée, mettre à jour la quantité
            ligneItem.quantite += uneQuantite

            // Si la quantité résultante est inférieure ou égale à zéro, supprimer la ligne d'inventaire
            if (ligneItem.quantite <= 0) {
                this.ligneInventaires.remove(ligneItem)
            }

            /**
             * Ajoute une ligne d'inventaire pour l'item spécifié avec la quantité donnée.
             * Si une ligne d'inventaire pour cet item existe déjà, met à jour la quantité.
             * Si la quantité résultante est inférieure ou égale à zéro, la ligne d'inventaire est supprimée.
             *
             * @param unItem L'item pour lequel ajouter ou mettre à jour la ligne d'inventaire.
             * @param uneQuantite La quantité à ajouter à la ligne d'inventaire existante ou à la nouvelle ligne.
             */
            /**
             * Ajoute une ligne d'inventaire pour l'item spécifié avec la quantité donnée.
             * Si une ligne d'inventaire pour cet item existe déjà, met à jour la quantité.
             * Si la quantité résultante est inférieure ou égale à zéro, la ligne d'inventaire est supprimée.
             *
             * @param unItem L'item pour lequel ajouter ou mettre à jour la ligne d'inventaire.
             * @param uneQuantite La quantité à ajouter à la ligne d'inventaire existante ou à la nouvelle ligne.
             */
            fun ajouterLigneInventaire(unItem: Item, uneQuantite: Int) {
                // Chercher une ligne d'inventaire existante pour l'item spécifié
                val ligneItem = this.ligneInventaires.find { ligneInventaire -> ligneInventaire.item == unItem }

                // Si aucune ligne d'inventaire n'est trouvée, en créer une nouvelle
                if (ligneItem == null) {
                    // Créer un nouvel identifiant pour la ligne d'inventaire
                    val ligneInventaireId = LigneInventaireId(this.id!!, unItem.id!!)

                    // Ajouter une nouvelle ligne d'inventaire à la liste
                    this.ligneInventaires.add(LigneInventaire(ligneInventaireId, uneQuantite, this, unItem))
                } else {
                    // Si une ligne d'inventaire existante est trouvée, mettre à jour la quantité
                    ligneItem.quantite += uneQuantite

                    // Si la quantité résultante est inférieure ou égale à zéro, supprimer la ligne d'inventaire
                    if (ligneItem.quantite <= 0) {
                        this.ligneInventaires.remove(ligneItem)
                    }
                }
            }
        }
    }

    /**
     * Loot l'inventaire de la cible en transférant les items et équipements dans l'inventaire du looteur.
     *
     * @param cible Le personnage dont l'inventaire sera looted.
     * @return Un message décrivant les items looted et les actions effectuées.
     */
    fun loot(cible: Personnage): String {
        // Déséquiper l'arme et l'armure de la cible
        cible.armeEquipee = null
        cible.armureEquipee = null
        // Variable pour stocker les messages générés pendant le loot
        var msg = ""
        // Utiliser forEach pour parcourir chaque ligne d'inventaire de la cible
        cible.ligneInventaires.forEach { uneLigne ->
            // Ajouter les items et quantités lootés à l'inventaire du looteur
            this.ajouterLigneInventaire(uneLigne.item!!, uneLigne.quantite)

            // Construire un message décrivant l'action pour chaque item looté
            msg += "${this.nom} récupère ${uneLigne.quantite} ${uneLigne.item}\n"
        }

        // Retourner le message global décrivant l'action de loot
        return msg
    }

    /**
     * Vérification si le personnage a une potion dans son inventaire.
     *
     * @return true si le personnage a une potion, false sinon.
     */
    fun aUnePotion(): Boolean {
        // Utiliser la méthode any pour vérifier si une ligne d'inventaire contient une Potion
        return this.ligneInventaires.any { ligneInventaire -> ligneInventaire.item is Potion }
    }

    fun equipe(arme: Arme): String {
        var msg: String = ""
        if (this.ligneInventaires.any { ligneInventaire -> ligneInventaire.item == arme }) {
            this.armeEquipee = arme
            msg = "${this.nom} equipe ${this.armeEquipee!!.nom} <br>"
        } else {
            msg = "Impasible d'equiper l'arme <br>"
        }
        return msg
    }

    fun equipe(armure: Armure): String {
        var msg: String = ""
        if (this.ligneInventaires.any { ligneInventaire -> ligneInventaire.item == armure }) {
            this.armureEquipee = armure
            msg = "${this.nom} equipe ${this.armureEquipee!!.nom} <br>"
        } else {
            msg = "Impasible d'equiper l'arme <br>"
        }
        return msg


    }

            fun equipe(accessoire: Accessoire): String{
                var msg:String=""
                if(this.ligneInventaires.any { ligneInventaire -> ligneInventaire.item == accessoire }){
                    this.accessoireEquipee=accessoire
                    msg="${this.nom} equipe ${this.acc!!.nom} <br>"
                }
                else {
                    msg = "Impasible d'equiper l'arme <br>"
                }
                return msg


}


}


//    fun equipe(armure: Armure): String {
//        TODO("Not yet implemented")
//    }

