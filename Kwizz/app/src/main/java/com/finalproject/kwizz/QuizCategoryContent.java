package com.finalproject.kwizz;

public class QuizCategoryContent
{
    private String category_name;
    private int category_image;

    public static final String quiz_key = "key";

    public QuizCategoryContent(String category_name, int category_image)
    {
        this.category_name = category_name;
        this.category_image = category_image;
    }

    public String getCategoryName()
    {
        return category_name;
    }

    public int getCategoryImage()
    {
        return category_image;
    }
}
