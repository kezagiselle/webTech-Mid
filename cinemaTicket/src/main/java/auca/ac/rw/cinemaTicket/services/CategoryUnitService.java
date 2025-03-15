package auca.ac.rw.cinemaTicket.services;

import auca.ac.rw.cinemaTicket.models.CategoryUnit;
import auca.ac.rw.cinemaTicket.models.ECategoryUnit;
import auca.ac.rw.cinemaTicket.repositories.CategoryUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import java.util.List;
import java.util.Optional;
// import java.util.UUID;
// import java.util.UUID;

@Service
public class CategoryUnitService { 

    @Autowired
    private CategoryUnitRepository categoryUnitRepository;

    public String SaveParent(CategoryUnit unit) { 
        if (unit == null) { 
            return "Parent category data is invalid or null.";
        } 
        // Check if the parent category already exists
        if (categoryUnitRepository.existsByNameAndDescription(unit.getName(), unit.getDescription())) { 
            return "Parent category '" + unit.getName() + "' already exists.";
        } 
        // Save the parent category to the database
        categoryUnitRepository.save(unit);
        return "Parent category saved successfully.";
    } 



    public String saveCategoryWithParent(CategoryUnit unit, String parentName, String parentDescription) {
    if (unit == null) {
        return "Category data is invalid or null.";
    }

    // Fetch the parent category by name and description
    Optional<CategoryUnit> getParent = categoryUnitRepository.findByNameAndDescription(parentName, parentDescription);

    if (getParent.isPresent()) {
        // If parent exists, check if the subcategory already exists
        if (categoryUnitRepository.existsByNameAndDescription(unit.getName(), unit.getDescription())) {
            return "Subcategory '" + unit.getName() + "' already exists under parent '" + parentName + "'.";
        } else {
            // Set the parent for the subcategory
            unit.setParentCategory(getParent.get());
            categoryUnitRepository.save(unit);
            return "Subcategory saved successfully under parent '" + parentName + "'.";
        }
    } else {
        // If parent does not exist, create and save the parent first
        CategoryUnit parent = new CategoryUnit();
        parent.setName(parentName); 
        parent.setDescription(parentDescription);
        parent.setCategoryType(ECategoryUnit.ACTION); 

        // Save the parent category
        categoryUnitRepository.save(parent);

        //saving the subcategory with the newly created parent
        unit.setParentCategory(parent);
        categoryUnitRepository.save(unit);

        return "Parent category and subcategory saved successfully.";
    }
}



  

}

  



    
    

    
    
