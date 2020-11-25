package dev.benswift.consosvehicules.controller;

import dev.benswift.consosvehicules.dao.CategorieRepository;
import dev.benswift.consosvehicules.dao.SousCategorieRepository;
import dev.benswift.consosvehicules.model.SousCategorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/parameter")
public class ParametersController {

    @Autowired
    private SousCategorieRepository sousCategorieRepository;
    @Autowired
    private CategorieRepository categorieRepository;

    
}
