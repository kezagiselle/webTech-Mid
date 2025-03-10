package auca.ac.rw.cinemaTicket.controllers;

import java.util.List;
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

// import auca.ac.rw.cinemaTicket.repositories.CategoryUnitRepository;
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

        // Endpoint to get all categories
        @GetMapping("/getAll")
        public ResponseEntity<List<CategoryUnit>> getAllCategories() {
            List<CategoryUnit> categories = categoryUnitService.getAllCategoryUnits();
            return ResponseEntity.ok(categories);
        }
    }


  
 

}