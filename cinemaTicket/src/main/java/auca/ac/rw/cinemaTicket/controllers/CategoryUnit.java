package auca.ac.rw.cinemaTicket.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import auca.ac.rw.cinemaTicket.services.CategoryUnitService;

@CrossOrigin("*")
@RestController
@RequestMapping("/categories")
public class CategoryUnit {

    @Autowired
    private CategoryUnitService categoryUnitService;

  
 @PostMapping(value = "/addParentCategory", consumes = "application/json")
    public ResponseEntity<?> saveParent(@RequestBody auca.ac.rw.cinemaTicket.models.CategoryUnit unit) {
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
        if (unit == null) {
            return new ResponseEntity<>("Invalid input: Category data is null.", HttpStatus.BAD_REQUEST);
        }

        // If parentName and parentDescription are not provided, save
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
        if (saveResult.contains("saved successfully")) {
            return new ResponseEntity<>(saveResult, HttpStatus.CREATED); 
        } else {
            return new ResponseEntity<>(saveResult, HttpStatus.NOT_FOUND); 
        }
    }
}




 

    



  
 

