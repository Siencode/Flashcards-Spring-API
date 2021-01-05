package io.siencode.flashcards.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FlashcardCategoryModel {

    @NotNull(message = "Category name cannot be null")
    @Size(min = 3, max = 20, message = "Incorrect category name length")
    private String categoryName;

    public FlashcardCategoryModel(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
