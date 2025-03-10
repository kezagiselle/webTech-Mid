package auca.ac.rw.cinemaTicket.services;

import auca.ac.rw.cinemaTicket.models.CategoryUnit;
import auca.ac.rw.cinemaTicket.repositories.CategoryUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
// import java.util.UUID;

@Service
public class CategoryUnitService {

    @Autowired
    private CategoryUnitRepository categoryUnitRepository;

    public String saveCategoryUnit(CategoryUnit unit, String name, String description) {
        // Fetch the parent category by name and description
        Optional<CategoryUnit> getParent = categoryUnitRepository.existsByNameAndDescription(name, description);
        
        // If parent exists, proceed with saving the CategoryUnit
        if (getParent.isPresent()) {
            if (categoryUnitRepository.existsByNameAndDescription(unit.getName(), unit.getDescription()) != null) {
                return "Category " + unit.getName() + " already exists";
            } else {
                // Set the parent category
                unit.setParentCategory(getParent.get());
                categoryUnitRepository.save(unit);
                return "Category saved successfully";
            }
        } else {
            // Create a new parent category if not found
            CategoryUnit parentCategory = new CategoryUnit();
            parentCategory.setName(name);  // Set the name of the parent
            parentCategory.setDescription(description);  // Set the description of the parent
            
            // Save the parent category first
            categoryUnitRepository.save(parentCategory);
            
            // Now save the CategoryUnit with the newly created parent
            unit.setParentCategory(parentCategory);
            categoryUnitRepository.save(unit);
            
            return "Parent and Category saved successfully";
        }
    }
}
