package auca.ac.rw.cinemaTicket.controllers;


// import java.util.List;
// import java.util.Optional;
// import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import auca.ac.rw.cinemaTicket.repositories.CategoryUnitRepository;
import auca.ac.rw.cinemaTicket.services.CategoryUnitService;

@RestController
@RequestMapping("/categories")
public class CategoryUnit {

    @Autowired
    private CategoryUnitService categoryUnitService;

  
 @PostMapping(value = "/addParentCategory", consumes = "application/json")
    public ResponseEntity<?> saveParent(@RequestBody auca.ac.rw.cinemaTicket.models.CategoryUnit unit) {
        // Null check for the input unit
        if (unit == null) {
            return new ResponseEntity<>("Invalid input: Parent category data is null.", HttpStatus.BAD_REQUEST);
        }
        String saveParent = categoryUnitService.SaveParent(unit);

        if (saveParent.equalsIgnoreCase("Parent category saved successfully.")) {
            return new ResponseEntity<>(saveParent, HttpStatus.CREATED); 
        } else {
            return new ResponseEntity<>(saveParent, HttpStatus.NOT_FOUND); 
        }
    }


    @PostMapping(value = "/addCategoryWithParent", consumes = "application/json")
    public ResponseEntity<?> saveCategoryWithParent(
            @RequestBody auca.ac.rw.cinemaTicket.models.CategoryUnit unit,
            @RequestParam(required = false) String parentName,
            @RequestParam(required = false) String parentDescription
    ) {
        // Null check for the input unit
        if (unit == null) {
            return new ResponseEntity<>("Invalid input: Category data is null.", HttpStatus.BAD_REQUEST);
        }

        // If parentName and parentDescription are not provided, treat it as a parent category
        if (parentName == null || parentDescription == null) {
            // Save as a parent category
            String saveResult = categoryUnitService.SaveParent(unit);
            if (saveResult.contains("saved successfully")) {
                return new ResponseEntity<>(saveResult, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(saveResult, HttpStatus.NOT_FOUND);
            }
        }

        // Call the service to save the category with parent
        String saveResult = categoryUnitService.saveCategoryWithParent(unit, parentName, parentDescription);

        // Return appropriate response based on the service result
        if (saveResult.contains("saved successfully")) {
            return new ResponseEntity<>(saveResult, HttpStatus.CREATED); // HTTP 201 for successful creation
        } else {
            return new ResponseEntity<>(saveResult, HttpStatus.NOT_FOUND); // HTTP 409 for conflict (already exists or invalid parent)
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
    



  
 

