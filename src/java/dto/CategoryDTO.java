/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author ACER
 */
public class CategoryDTO {
    private int categoryID;
    private String categoryNAME;
    private String description;

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryNAME() {
        return categoryNAME;
    }

    public void setCategoryNAME(String categoryNAME) {
        this.categoryNAME = categoryNAME;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryDTO(int categoryID, String categoryNAME, String description) {
        this.categoryID = categoryID;
        this.categoryNAME = categoryNAME;
        this.description = description;
    }

    public CategoryDTO() {
    }
    
}
