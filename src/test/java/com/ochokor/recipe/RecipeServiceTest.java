package com.ochokor.recipe;

import com.ochokor.recipe.entity.RecipeValueObject;
import com.ochokor.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@DataJpaTest
public class RecipeServiceTest {


    @MockBean
    RecipeService recipeService;

    List<RecipeValueObject> valueObjects;

    @BeforeEach
    void init() {
        valueObjects = List.of(
            new RecipeValueObject("Eba", "Swallow made from garri and hot water",
                "Boil water, then pour garri and stir", false, 2, "Garri", "Hot water"),
            new RecipeValueObject("Suya", "Roasted meat",
                "Smoked seasoned beef", false, 3, "Tomato", "onions", "goat meat", "suya pepper"),
            new RecipeValueObject("Lasagne", "A type of pasta, made of very wide, flat sheets ",
                "Roast and season raw beef", false, 12, "Pasta", "Tomato", "Ground beef", "Garlic", "Cheese"),
            new RecipeValueObject("Egusi soup", "A Nigerian vegetable soup",
                "", true, 6, "Egusi", "Palm oil", "Onions", "Garlic", "Uwgu", "Water leaf")
        );
    }


    @Test
    void testSaveEntity() {

    }
}
