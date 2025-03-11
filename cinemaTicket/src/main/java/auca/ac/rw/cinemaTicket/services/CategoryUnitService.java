package auca.ac.rw.cinemaTicket.services;

import auca.ac.rw.cinemaTicket.models.CategoryUnit;
import auca.ac.rw.cinemaTicket.repositories.CategoryUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
// import java.util.UUID;
import java.util.UUID;

@Service
public class CategoryUnitService {

    @Autowired
    private CategoryUnitRepository categoryUnitRepository;

    public String  SaveParent(CategoryUnit unit){
        if(categoryUnitRepository.existsByNameAndDescription(unit.getName(), unit.getDescription())){
            return "Parent" + unit.getName() + " Exists";
        }
        
        else{
            categoryUnitRepository.save(unit);
            return "Parent saved successfully ";
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
    public Optional<CategoryUnit> getCategoryUnitById(UUID id) {
        return categoryUnitRepository.findById(id);
    }

    public CategoryUnitRepository getCategoryUnitRepository() {
        return categoryUnitRepository;
    }

    
    public List<CategoryUnit> getAllCategoryUnits() {
        return categoryUnitRepository.findAll();
    }
    

    public void setCategoryUnitRepository(CategoryUnitRepository categoryUnitRepository) {
        this.categoryUnitRepository = categoryUnitRepository;
    }

    public String saveCategoryUnit(auca.ac.rw.cinemaTicket.controllers.CategoryUnit unit, String parentName,
            String parentDescription) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveCategoryUnit'");
    }

    // public String saveCategoryUnit(auca.ac.rw.cinemaTicket.controllers.CategoryUnit unit, String parentName,
    //         String parentDescription) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'saveCategoryUnit'");
    // }
}
