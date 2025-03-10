package auca.ac.rw.cinemaTicket.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.rw.cinemaTicket.repositories.CategoryUnitRepository;
import auca.ac.rw.cinemaTicket.services.CategoryUnitService;

@RestController
@RequestMapping("/categories")
public class CategoryUnit {

      @Autowired
    private CategoryUnitService categoryUnitService;

    // Endpoint to add a new category
    @PostMapping("/add")
    public ResponseEntity<String> addCategory(
            @RequestBody CategoryUnit unit,
            @RequestParam(required = false) String parentName,
            @RequestParam(required = false) String parentDescription) {
        
        String result = categoryUnitService.saveCategoryUnit(unit, parentName, parentDescription);
        return ResponseEntity.ok(result);
    }

     // Endpoint to get a category by its ID
     @GetMapping("/{id}")
     public ResponseEntity<CategoryUnit> getCategoryById(@PathVariable UUID id) {
         Optional<CategoryUnit> category = categoryUnitService.getCategoryUnitById(id);
         if (category.isPresent()) {
             return ResponseEntity.ok(category.get()); // Return 200 OK with the category
         } else {
             return ResponseEntity.notFound().build(); // Return 404 Not Found
         }
     }

    // Endpoint to get a category by its name and description
    @GetMapping("/search")
    public ResponseEntity<CategoryUnit> getCategoryByNameAndDescription(
            @RequestParam String name,
            @RequestParam String description) {
        
        Optional<CategoryUnit> category = categoryUnitService.getCategoryUnitByNameAndDescription(name, description);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}