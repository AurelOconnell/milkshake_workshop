package com.wildcodeschool.milkshake.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wildcodeschool.milkshake.entity.Recipe;
import com.wildcodeschool.milkshake.repository.RecipeRepository;

@RestController
public class RecipeController {

    private final RecipeRepository recipeRepository;

    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/recipes")
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            return ResponseEntity.ok(recipe);
        } else {
            return new ResponseEntity<>("Nope", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/recipes")
    public String addNewRecipe(@RequestBody Recipe recipe) {
        recipeRepository.save(recipe);
        return "saved";
    }

    @PutMapping("/recipes/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Recipe recipe) {
        Optional<Recipe> optionalRecipeToUpdate = recipeRepository.findById(id);
        if (optionalRecipeToUpdate.isPresent()) {
            Recipe recipeToUpdate = optionalRecipeToUpdate.get();
            recipeToUpdate.setName(recipe.getName());
            recipeToUpdate.setQuantity(recipe.getQuantity());
            recipeToUpdate.setMainIngredient(recipe.getMainIngredient());
            return ResponseEntity.ok(recipeRepository.save(recipeToUpdate));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/recipes/{id}")
    public String deleteRecipe(@RequestParam Long id) {
        recipeRepository.deleteById(id);
        return "deleted";
    }

}
