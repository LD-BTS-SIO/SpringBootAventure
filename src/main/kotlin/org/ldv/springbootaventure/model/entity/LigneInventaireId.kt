package org.ldv.springbootaventure.model.entity

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
class LigneInventaireId (
    var itemId:Long,
    var personnageId:Long
):Serializable {

}