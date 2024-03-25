package org.ldv.springbootaventure.model.entity
import jakarta.persistence.*
import org.ldv.springbootaventure.model.entity.Personnage

// Utilise l'héritage avec une seule table pour stocker les données de toutes les sous-classes dans la même table.
// La colonne "discriminateur" est utilisée pour différencier les types d'objets stockés dans la table.
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminateur")

// Déclare la classe comme une entité JPA
@Entity

// Déclare la classe comme abstraite, ce qui signifie qu'elle ne peut pas être instanciée directement.
abstract class Item constructor(
// Clé primaire auto-incrémentée
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
// Nom de l'item
    var nom: String,
// Description de l'item
     var description: String,

//Chemin vers l'image de l'item
     var cheminImage:String?
) {
// TODO sprint 5: methode utiliser
    /**
     * Méthode abstraite permettant d'utiliser l'objet sur une cible (personnage).
     *
     * @param cible Le personnage sur lequel l'objet est utilisé.
     */
    abstract fun utiliser(cible: Personnage):String

    @ManyToMany
    @JoinTable(
        name = "Item_personnages",
        joinColumns = [JoinColumn(name = "item_id")],
        inverseJoinColumns = [JoinColumn(name = "personnages_id")]
    )
    var personnages: MutableList<Personnage> = mutableListOf()

    @OneToMany(mappedBy = "item", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    open var ligneInventaires: MutableList<LigneInventaire> = mutableListOf()
}

