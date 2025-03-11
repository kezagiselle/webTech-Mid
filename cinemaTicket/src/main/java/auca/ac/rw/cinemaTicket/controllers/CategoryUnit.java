package auca.ac.rw.cinemaTicket.controllers;

import org.slf4j.LoggerFactory;

// import java.util.List;
// import java.util.Optional;
// import java.util.UUID;

import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import auca.ac.rw.cinemaTicket.repositories.CategoryUnitRepository;
import auca.ac.rw.cinemaTicket.services.CategoryUnitService;

@RestController
@RequestMapping("/categories")
public class CategoryUnit {

   private static final Logger logger = LoggerFactory.getLogger(CategoryUnit.class);

      @Autowired
    private CategoryUnitService categoryUnitService;

    // Endpoint to add a new category
    @PostMapping(value = "/addParentCategory", consumes = "application/json")
    public ResponseEntity<?> saveParent(@RequestBody CategoryUnit unit) {
        // Null check for the input unit
        if (unit == null) {
            return new ResponseEntity<>("Invalid input: Parent category data is null.", HttpStatus.BAD_REQUEST);
        }

        // Call the service to save the parent category
        String saveParent = categoryUnitService.SaveParent(unit);

        // Return appropriate response based on the service result
        if (saveParent.equalsIgnoreCase("Parent category saved successfully.")) {
            return new ResponseEntity<>(saveParent, HttpStatus.CREATED); // HTTP 201 for successful creation
        } else {
            return new ResponseEntity<>(saveParent, HttpStatus.CONFLICT); // HTTP 409 for conflict (already exists)
        }
    }
}


 
    // public ResponseEntity<String> addCategory(
    //         @RequestBody auca.ac.rw.cinemaTicket.models.CategoryUnit unit,
    //         @RequestParam(required = false) String parentName,
    //         @RequestParam(required = false) String parentDescription) {
        
    //     String result = categoryUnitService.saveCategoryUnit(unit, parentName, parentDescription);
    //     return ResponseEntity.ok(result);
    // }

    // @PostMapping(value = "/addCategory" , consumes = "application/json")
    // public ResponseEntity<String> addCategory(
    //         @RequestBody CategoryUnit unit,
    //         @RequestParam(required = false) String parentName,
    //         @RequestParam(required = false) String parentDescription) {
        
    //     // Ensure the unit object is not null
    //     if (unit == null) {
    //         return ResponseEntity.badRequest().body("Category data is required");
    //     }
    
    //     // Ensure the name is not null or empty
    //     if (unit.getName() == null || ((String) unit.getName()).isEmpty()) {
    //                 return ResponseEntity.badRequest().body("Category name is required");
    //             }
            
    //             // Save the unit to the repository
    //             String result = categoryUnitService.saveCategoryUnit(unit, parentName, parentDescription);
    //             return ResponseEntity.ok(result);
    //         }
    



  
 

