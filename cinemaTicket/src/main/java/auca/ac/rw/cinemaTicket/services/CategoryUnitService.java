package auca.ac.rw.cinemaTicket.services;

import auca.ac.rw.cinemaTicket.models.CategoryUnit;
import auca.ac.rw.cinemaTicket.models.ECategoryUnit;
import auca.ac.rw.cinemaTicket.repositories.CategoryUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
// import java.util.UUID;
import java.util.UUID;

@Service
public class CategoryUnitService { // Opening brace for the class

    @Autowired
    private CategoryUnitRepository categoryUnitRepository;

    public String SaveParent(CategoryUnit unit) { // Opening brace for the method
        // Null check for the input unit
        if (unit == null) { // Opening brace for the if block
            return "Parent category data is invalid or null.";
        } // Closing brace for the if block

        // Check if the parent category already exists
        if (categoryUnitRepository.existsByNameAndDescription(unit.getName(), unit.getDescription())) { // Opening brace for the if block
            return "Parent category '" + unit.getName() + "' already exists.";
        } // Closing brace for the if block

        // Save the parent category to the database
        categoryUnitRepository.save(unit);
        return "Parent category saved successfully.";
    } // Closing brace for the method



    public String saveCategoryWithParent(CategoryUnit unit, String parentName, String parentDescription) {
    // Null check for the input unit
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
        parent.setName(parentName); // Set the name
        parent.setDescription(parentDescription); // Set the description
        parent.setCategoryType(ECategoryUnit.ACTION); // Set the category type as PARENT

        // Save the parent category
        categoryUnitRepository.save(parent);

        // Now save the subcategory with the newly created parent
        unit.setParentCategory(parent);
        categoryUnitRepository.save(unit);

        return "Parent category and subcategory saved successfully.";
    }
}



  

}

  



    
    

    
    // public String saveCategoryUnit(CategoryUnit unit, String parentName, String parentDescription) {
    //     if (unit.getName() == null || unit.getName().isEmpty()) {
    //         throw new IllegalArgumentException("Category name cannot be null or empty");
    //     }
    //     // Save the unit to the repository
    //     categoryUnitRepository.save(unit);
    //     return "Category saved successfully";
    // }
    
    // getting all categories
    // public Optional<CategoryUnit> getCategoryUnitById(UUID id) {
    //     return categoryUnitRepository.findById(id);
    // }

    // public CategoryUnitRepository getCategoryUnitRepository() {
    //     return categoryUnitRepository;
    // }

    
    // public List<CategoryUnit> getAllCategoryUnits() {
    //     return categoryUnitRepository.findAll();
    // }
    
    

    // public void setCategoryUnitRepository(CategoryUnitRepository categoryUnitRepository) {
    //     this.categoryUnitRepository = categoryUnitRepository;
    // }


    // public String saveCategoryUnit(auca.ac.rw.cinemaTicket.controllers.CategoryUnit unit, String parentName,
    //         String parentDescription) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'saveCategoryUnit'");
    // }

    // public String saveCategoryUnit(auca.ac.rw.cinemaTicket.controllers.CategoryUnit unit, String parentName,
    //         String parentDescription) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'saveCategoryUnit'");
    // }

