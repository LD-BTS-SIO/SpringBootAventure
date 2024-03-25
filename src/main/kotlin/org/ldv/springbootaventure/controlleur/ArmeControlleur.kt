package org.ldv.springbootaventure.controlleur
import org.ldv.springbootaventure.model.dao.ArmeDAO
import org.ldv.springbootaventure.model.dao.QualiteDAO
import org.ldv.springbootaventure.model.dao.TypeArmeDAO
import org.ldv.springbootaventure.model.entity.Arme

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class ArmeController(val armeDAO: ArmeDAO,val qualiteDAO: QualiteDAO, val typeArmeDAO: TypeArmeDAO) {
    /**
     * Affiche la liste de toutes les qualités.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/arme")
    fun index(model: Model): String {
        // Récupère toutes les qualités depuis la base de données
        val armes = this.armeDAO.findAll()

        // Ajoute la liste des armes au modèle pour affichage dans la vue
        model.addAttribute("armes", armes)

        // Retourne le nom de la vue à afficher
        return "/admin/arme/index"
    }

    /**
     * Affiche les détails d'une qualité en particulier.
     *
     * @param id L'identifiant unique de la qualité à afficher.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     * @throws NoSuchElementException si la qualité avec l'ID spécifié n'est pas trouvée.
     */
    @GetMapping("/admin/arme/{id}")
    fun show(@PathVariable id: Long, model: Model): String {
        // Récupère la qualité avec l'ID spécifié depuis la base de données
        val uneArme = this.armeDAO.findById(id).orElseThrow()

        // Ajoute la qualité au modèle pour affichage dans la vue
        model.addAttribute("arme", uneArme)

        // Retourne le nom de la vue à afficher
        return "admin/arme/show"
    }
    /**
     * Affiche le formulaire de création d'une nouvelle qualité.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher (le formulaire de création).
     */
    /**
     * Affiche le formulaire de création d'une nouvelle qualité.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher (le formulaire de création).
     */
    /**
     * Affiche le formulaire de création d'une nouvelle qualité.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher (le formulaire de création).
     */
    @GetMapping("/admin/arme/create")
    fun create(model: Model): String {
        // Crée une nouvelle instance de Typearme avec des valeurs par défaut
        val nouvelleArme = Arme(0, "", "", "")
        val qualites= this.qualiteDAO.findAll()
        val typearmes= this.typeArmeDAO.findAll()
        // Ajoute la nouvelle qualité au modèle pour affichage dans le formulaire de création
        model.addAttribute("nouvelleArme", nouvelleArme)
        model.addAttribute("qualites",qualites)
        model.addAttribute("typearmes",typearmes)
        // Retourne le nom de la vue à afficher (le formulaire de création)
        return "/admin/arme/create"
    }

    /**
     * Gère la soumission du formulaire d'ajout de qualité.
     *
     * @param nouvelleArme L'objet TypeArme créé à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des qualités après l'ajout réussi.
     */
    @PostMapping("/admin/arme")
    fun store(@ModelAttribute nouvelleArme: Arme, redirectAttributes: RedirectAttributes): String {
        // Sauvegarde la nouvelle qualité dans la base de données
        val savedArme = this.armeDAO.save(nouvelleArme)
        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedArme.nom} réussi")
        // Redirige vers la page d'administration des qualités
        return "redirect:/admin/arme"
    }

    @GetMapping("/admin/arme/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {
        // Récupère la qualité avec l'ID spécifié depuis la base de données
        val arme = this.armeDAO.findById(id).orElseThrow()

        // Ajoute la qualité au modèle pour affichage dans la vue
        model.addAttribute("arme", arme)

        // Retourne le nom de la vue à afficher
        return "/admin/arme/edit"
    }


    /**
     * Gère la soumission du formulaire de mise à jour de qualité.
     *
     * @param arme L'objet TypeArme mis à jour à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des qualités après la mise à jour réussie.
     * @throws NoSuchElementException si la qualité avec l'ID spécifié n'est pas trouvée dans la base de données.
     */
    @PostMapping("/admin/arme/update")
    fun update(@ModelAttribute arme: Arme, redirectAttributes: RedirectAttributes): String {
        // Recherche de la qualité existante dans la base de données
        val armeModifier = this.armeDAO.findById(arme.id ?: 0).orElseThrow()

        // Mise à jour des propriétés de typearme avec les nouvelles valeurs du formulaire
        armeModifier.nom = arme.nom
        armeModifier.description = arme.description
        armeModifier.cheminImage = armeModifier.cheminImage


        // Sauvegarde la qualité modifiée dans la base de données
        val savedArme = this.armeDAO.save(armeModifier)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${savedArme.nom} réussie")

        // Redirige vers la page d'administration des typearmes
        return "redirect:/admin/arme"
    }

    /**
     * Gère la suppression d'une qualité par son identifiant.
     *
     * @param id L'identifiant de la qualité à supprimer.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des qualités après la suppression réussie.
     * @throws NoSuchElementException si la qualité avec l'ID spécifié n'est pas trouvée dans la base de données.
     */
    @PostMapping("/admin/arme/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {
        // Recherche de typearme à supprimer dans la base de données
        val arme: Arme = this.armeDAO.findById(id).orElseThrow()

        // Suppression de la qualité de la base de données
        this.armeDAO.delete(arme)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${arme.nom} réussie")

        // Redirige vers la page d'administration des qualités
        return "redirect:/admin/arme"
    }
}








