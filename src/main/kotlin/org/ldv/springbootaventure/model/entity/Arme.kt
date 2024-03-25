package org.ldv.springbootaventure.model.entity


import jakarta.persistence.*

@Entity
@DiscriminatorValue("arme")
class Arme(
    id: Long? = null,
    nom: String,
    description: String,
    cheminImage: String?,

    // Association avec Qualite
    @ManyToOne
    @JoinColumn(name = "id_qualite")
    var qualite: Qualite? = null,

    // Association avec TypeArme
    @ManyToOne
    @JoinColumn(name = "id_typearme")
    var typeArme: TypeArme? = null
) : Item(id, nom, description, cheminImage) {

    // Autres méthodes ou logique spécifique aux armes

    fun calculerDegat(type: TypeArme): String {
        // Exemple : 1d6 +2 ( cad un nombre entre 1 et 6 plus le modificateur 2)
        //      On tire 1 dè de 6 (min: 1 max:6 )
        //      Si on tire 6 alors on multiplie par le multiplicateur de critique du type de l'arme ( par exemple 3)
        //      Sinon on garde le nombre du tirage tel quelle
        //      On ajoute le bonus de qualite au dégat ici 2

        // Instanciation d'un tirage de dés

        val deDegat = TirageDes(type.nombreDes, type.valeurDeMax)
        // on lance les dés
        var resultatLancer = deDegat.lance()
        val deCritique = TirageDes(1, 20).lance()
        if (deCritique >= type.activationCritique) {
            // Coup critique (si le nombre tiré correspond au maximum)
            resultatLancer = resultatLancer * type.multiplicateurCritique
        }
        return ("Coup critique !")
    }

    /**
     * Équipe l'arme sur un personnage, permettant au personnage de l'utiliser pour attaquer.
     *
     * @param cible Le personnage sur lequel l'arme est équipée.
     */
    override fun utiliser(cible: Personnage):String {
        return cible.equipe(this)
    }


    @OneToMany(mappedBy = "armeEquipee", orphanRemoval = true)
    var personnage: MutableList<Personnage> = mutableListOf()
}

