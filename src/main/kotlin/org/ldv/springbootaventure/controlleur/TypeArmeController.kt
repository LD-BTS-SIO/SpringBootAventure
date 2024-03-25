package org.ldv.springbootaventure.controlleur
import org.ldv.springbootaventure.model.dao.TypeArmeDAO

import org.ldv.springbootaventure.model.entity.TypeArme
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class TypeArmeController(val typeArmeDAO: TypeArmeDAO) {
    /**
     * Affiche la liste de toutes les qualités.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/typearme")
    fun index(model: Model): String {
        // Récupère toutes les qualités depuis la base de données
        val typearmes = this.typeArmeDAO.findAll()

        // Ajoute la liste des armes au modèle pour affichage dans la vue
        model.addAttribute("typearmes", typearmes)

        // Retourne le nom de la vue à afficher
        return "/admin/typearme/index"
    }

    /**
     * Affiche les détails d'une qualité en particulier.
     *
     * @param id L'identifiant unique de la qualité à afficher.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     * @throws NoSuchElementException si la qualité avec l'ID spécifié n'est pas trouvée.
     */
    @GetMapping("/admin/typearme/{id}")
    fun show(@PathVariable id: Long, model: Model): String {
        // Récupère la qualité avec l'ID spécifié depuis la base de données
        val unTypeArme = this.typeArmeDAO.findById(id).orElseThrow()

        // Ajoute la qualité au modèle pour affichage dans la vue
        model.addAttribute("typearme", unTypeArme)

        // Retourne le nom de la vue à afficher
        return "admin/typearme/show"
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
    @GetMapping("/admin/typearme/create")
    fun create(model: Model): String {
        // Crée une nouvelle instance de Typearme avec des valeurs par défaut
        val nouveauTypeArme = TypeArme(null, 0, 0, 0, 0, "")

        // Ajoute la nouvelle qualité au modèle pour affichage dans le formulaire de création
        model.addAttribute("nouveauTypeArme", nouveauTypeArme)

        // Retourne le nom de la vue à afficher (le formulaire de création)
        return "/admin/typearme/create"
    }

    /**
     * Gère la soumission du formulaire d'ajout de qualité.
     *
     * @param nouveauTypeArme L'objet TypeArme créé à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des qualités après l'ajout réussi.
     */
    @PostMapping("/admin/typearme")
    fun store(@ModelAttribute nouveauTypeArme: TypeArme, redirectAttributes: RedirectAttributes): String {
        // Sauvegarde la nouvelle qualité dans la base de données
        val savedTypeArme = this.typeArmeDAO.save(nouveauTypeArme)
        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedTypeArme.nom} réussi")
        // Redirige vers la page d'administration des qualités
        return "redirect:/admin/typearme"
    }

    @GetMapping("/admin/typearme/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {
        // Récupère la qualité avec l'ID spécifié depuis la base de données
        val unTypeArme = this.typeArmeDAO.findById(id).orElseThrow()

        // Ajoute la qualité au modèle pour affichage dans la vue
        model.addAttribute("nouveauTypeArme", unTypeArme)

        // Retourne le nom de la vue à afficher
        return "/admin/typearme/edit"
    }


    /**
     * Gère la soumission du formulaire de mise à jour de qualité.
     *
     * @param typearme L'objet TypeArme mis à jour à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des qualités après la mise à jour réussie.
     * @throws NoSuchElementException si la qualité avec l'ID spécifié n'est pas trouvée dans la base de données.
     */
    @PostMapping("/admin/typearme/update")
    fun update(@ModelAttribute typearme: TypeArme, redirectAttributes: RedirectAttributes): String {
        // Recherche de la qualité existante dans la base de données
        val typearmeModifier = this.typeArmeDAO.findById(typearme.id ?: 0).orElseThrow()

        // Mise à jour des propriétés de typearme avec les nouvelles valeurs du formulaire
        typearmeModifier.nom = typearme.nom
        typearmeModifier.nombreDes = typearme.nombreDes
        typearmeModifier.valeurDeMax = typearme.valeurDeMax
        typearmeModifier.multiplicateurCritique = typearme.multiplicateurCritique
        typearmeModifier.activationCritique = typearme.activationCritique

        // Sauvegarde la qualité modifiée dans la base de données
        val savedTypeArme = this.typeArmeDAO.save(typearmeModifier)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${savedTypeArme.nom} réussie")

        // Redirige vers la page d'administration des typearmes
        return "redirect:/admin/typearme"
    }

    /**
     * Gère la suppression d'une qualité par son identifiant.
     *
     * @param id L'identifiant de la qualité à supprimer.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des qualités après la suppression réussie.
     * @throws NoSuchElementException si la qualité avec l'ID spécifié n'est pas trouvée dans la base de données.
     */
    @PostMapping("/admin/typearme/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {
        // Recherche de typearme à supprimer dans la base de données
        val typearme: TypeArme = this.typeArmeDAO.findById(id).orElseThrow()

        // Suppression de la qualité de la base de données
        this.typeArmeDAO.delete(typearme)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${typearme.nom} réussie")

        // Redirige vers la page d'administration des qualités
        return "redirect:/admin/typearme"
    }
}









