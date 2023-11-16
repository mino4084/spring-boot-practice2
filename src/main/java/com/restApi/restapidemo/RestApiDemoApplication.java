package com.restApi.restapidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication

public class RestApiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiDemoApplication.class, args);
	}

}

@RestController
@RequestMapping("/")
class RestApiDemoController {
	private List<Coffee> coffeeList = new ArrayList<>();

	public RestApiDemoController() {
		coffeeList.addAll(List.of(
				new Coffee("Cafe Latte"),
				new Coffee("Americano"),
				new Coffee("Espresso"),
				new Coffee("Cappuccino")
		));
	}

	//@RequestMapping(value = "/coffee", method = RequestMethod.GET)
	@GetMapping("/coffee")
	Iterable<Coffee> getCoffeeList() {
		return coffeeList;
	}

	@GetMapping("/coffee/{id}")
	Optional<Coffee> getCoffeeById(@PathVariable String id) {
		return coffeeList
				.stream()
				.filter(c -> c.getId().equals(id))
				.findFirst();
	}
}

class Coffee {
	private final String id;
	private String name;

	public Coffee(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public Coffee(String name) {
		this(UUID.randomUUID().toString(), name);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}