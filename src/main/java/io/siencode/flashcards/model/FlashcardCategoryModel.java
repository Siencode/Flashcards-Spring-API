package io.siencode.flashcards.model;

public class FlashcardCategoryModel {

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
