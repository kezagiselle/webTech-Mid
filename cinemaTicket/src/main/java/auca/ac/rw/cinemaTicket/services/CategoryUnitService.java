package auca.ac.rw.cinemaTicket.services;

import auca.ac.rw.cinemaTicket.models.CategoryUnit;
import auca.ac.rw.cinemaTicket.repositories.CategoryUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
// import java.util.UUID;
import java.util.UUID;

@Service
public class CategoryUnitService {

    @Autowired
    private CategoryUnitRepository categoryUnitRepository;

    public String saveCategoryUnit(auca.ac.rw.cinemaTicket.controllers.CategoryUnit unit, String parentName,
            String parentDescription) {
        // Fetch the parent category by name and description
        Optional<CategoryUnit> parentCategoryOpt = categoryUnitRepository.findByNameAndDescription(parentName,
                parentDescription);

        // If parent exists, proceed with saving the CategoryUnit
        if (parentCategoryOpt.isPresent()) {
            if (categoryUnitRepository.existsByNameAndDescription(unit.getName(), unit.getDescription())) {
                return "Category " + unit.getName() + " already exists";
            } else {
                // Set the parent category
                unit.setParentCategory(parentCategoryOpt.get());
                categoryUnitRepository.save(unit);
                return "Category saved successfully";
            }
        } else {
            // Create a new parent category if not found
            CategoryUnit parentCategory = new CategoryUnit();
            parentCategory.setName(parentName); // Set the name of the parent
            parentCategory.setDescription(parentDescription); // Set the description of the parent

            // Save the parent category first
            categoryUnitRepository.save(parentCategory);

            // Now save the CategoryUnit with the newly created parent
            unit.setParentCategory(parentCategory);
            categoryUnitRepository.save(unit);

            return "Parent and Category saved successfully";
        }
    }

    // getting all categories
    public Optional<CategoryUnit> getCategoryUnitById(UUID id) {
        return categoryUnitRepository.findById(id);
    }

    public Optional<CategoryUnit> getCategoryUnitByNameAndDescription(String name, String description) {
        return categoryUnitRepository.findByNameAndDescription(name, description);
    }

    public CategoryUnitRepository getCategoryUnitRepository() {
        return categoryUnitRepository;
    }

    public void setCategoryUnitRepository(CategoryUnitRepository categoryUnitRepository) {
        this.categoryUnitRepository = categoryUnitRepository;
    }
}
