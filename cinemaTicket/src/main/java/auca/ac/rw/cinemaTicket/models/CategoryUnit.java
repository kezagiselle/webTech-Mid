package auca.ac.rw.cinemaTicket.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "category_unit")
public class CategoryUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_type", nullable = false) 
    private ECategoryUnit categoryType;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true) 
    @JsonBackReference 
    private CategoryUnit parentCategory;

    public CategoryUnit() {}

    public CategoryUnit(String name, String description, ECategoryUnit categoryType, CategoryUnit parentCategory) {
        this.name = name;
        this.description = description;
        this.categoryType = categoryType;
        this.parentCategory = parentCategory;
    }

    // Getters & Setters
    public UUID getId() { 
        return id; 
    }

    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }

    public String getDescription() { 
        return description; 
    }
    public void setDescription(String description) { 
        this.description = description; 
    }

    public ECategoryUnit getCategoryType() { 
        return categoryType; 
    }
    public void setCategoryType(ECategoryUnit categoryType) { 
        this.categoryType = categoryType; 
    }

    public CategoryUnit getParentCategory() { 
        return parentCategory; 
    }
    public void setParentCategory(CategoryUnit parentCategory) { 
        this.parentCategory = parentCategory; 
    }

    
}