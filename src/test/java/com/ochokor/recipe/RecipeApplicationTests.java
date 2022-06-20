package com.ochokor.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ochokor.recipe.entity.RecipeValueObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RecipeApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;
	private List<RecipeValueObject> valueObjects;

	@BeforeEach
	void init() {
		valueObjects = List.of(
			new RecipeValueObject("Suya", "Roasted meat",
				"Smoked seasoned beef", false, 3, "Tomato", "onions", "goat meat", "suya pepper"),
			new RecipeValueObject("Lasagne", "A type of pasta, made of very wide, flat sheets ",
				"Roast and season raw beef", false, 12, "Pasta", "Tomato", "Ground beef", "Garlic", "Cheese"),
			new RecipeValueObject("Egusi soup", "A Nigerian vegetable soup",
				"", true, 6, "Egusi", "Palm oil", "Onions", "Garlic", "Uwgu", "Water leaf"),
			new RecipeValueObject("Eba", "Fufu made from garri and hot water",
				"Boil water, then pour garri and stir", false, 2, "Garri", "Hot water")
		);
	}

	@Test
	public void contextLoads() throws Exception {
		assertThat(mockMvc).isNotNull();
	}


	@Test
	public void shouldCreateEntity() throws Exception {
		String json = mapper.writeValueAsString(valueObjects.get(0));

		mockMvc.perform(
				post("/api/recipes/add")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(json))
			.andExpect(status().isCreated());
	}

	@Test
	public void shouldRetrieveEntity() throws Exception {
		RecipeValueObject ebaVO = valueObjects.get(0);

		String json = mapper.writeValueAsString(ebaVO);

		mockMvc.perform(post("/api/recipes/add")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
			.andExpect(status().isCreated()).andReturn();

		mockMvc.perform(get("/api/recipes/find")
				.param("name", ebaVO.name))
			.andExpect(status().isOk())
			.andExpect(
				jsonPath("$.recipeName").value(ebaVO.name.toLowerCase()))
			.andExpect(
				jsonPath("$.description").value(ebaVO.description))
			.andExpect(jsonPath("$.servings").value(ebaVO.servings))
			.andExpect(jsonPath("$.vegetarian").value(ebaVO.isVegetarian))
			.andReturn();
	}

	@Test
	public void shouldRetrieveAllEntities() throws Exception {

		for (RecipeValueObject vO : valueObjects) {
			String json = mapper.writeValueAsString(vO);

			mockMvc.perform(post("/api/recipes/add")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(json))
				.andExpect(status().isCreated()).andReturn();
		}

		int responseContentLength = mockMvc.perform(get("/api/recipes"))
			.andReturn().getResponse().getContentLength();

		assertThat(responseContentLength).isEqualTo(valueObjects.size());
	}

	@Test
	public void shouldUpdateEntity() throws Exception {

		RecipeValueObject ebaVO = valueObjects.get(0);
		final String newName = "newRandomRecipeName";

		String json = mapper.writeValueAsString(ebaVO);

		mockMvc.perform(
				post("/api/recipes/add")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(json))
			.andExpect(status().isCreated());

		mockMvc.perform(put("/update")
				.param("name", ebaVO.name)
				.param("newName", newName)
				.content(json))
			.andExpect(
				status().isNoContent());

		mockMvc.perform(get("/find").param("name", newName))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.recipeName").value(newName));
	}
}