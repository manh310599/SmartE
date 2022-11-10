package com.example.learn_english_smart.Class;





import java.util.List;

public class course {



    public course() {
    }

    String image_course;
    String type;


    public course(String image_course, String type) {
        this.image_course = image_course;
        this.type = type;
    }

    public String getImage_course() {
        return image_course;
    }

    public void setImage_course(String image_course) {
        this.image_course = image_course;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
