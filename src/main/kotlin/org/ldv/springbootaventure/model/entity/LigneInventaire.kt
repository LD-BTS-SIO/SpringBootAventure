package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*


@Entity
class LigneInventaire(
    @EmbeddedId
    var ligneInventaireId: LigneInventaireId,
    var quantite: Int,

    @MapsId("personnageId")
    @ManyToOne
@JoinColumn(name = "personnage_id")
open var personnage: Personnage? = null,

    @MapsId("itemId")
@ManyToOne
@JoinColumn(name = "item_id")
    var item: Item? = null,
) {

}