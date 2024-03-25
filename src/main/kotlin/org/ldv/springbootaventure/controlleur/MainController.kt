package org.ldv.springbootaventure.controlleur

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController {
    @GetMapping("/")
    fun index(model: Model): String {
        return "/admin/accueil"
    }

    @GetMapping("/visiteur/login.html")
    fun login(model: Model): String {

       return "admin/visiteur/login"

    }

    @GetMapping("/visiteur/inscription.html")
    fun inscription(model: Model): String {

        return "admin/visiteur/inscription"

    }


}
