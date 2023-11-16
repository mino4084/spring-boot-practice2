package com.restApi.restapidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/coffee")
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
	@GetMapping
	Iterable<Coffee> getCoffeeList() {
		return coffeeList;
	}

	@GetMapping("/{id}")
	Optional<Coffee> getCoffeeById(@PathVariable String id) {
		return coffeeList
				.stream()
				.filter(c -> c.getId().equals(id))
				.findFirst();
	}

	@PostMapping
	Coffee postCoffee(@RequestBody Coffee coffee) {
		coffeeList.add(coffee);
		return coffee;
	}

	@PutMapping("/{id}")
	ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee) {
		int coffeeIndex = -1;

		for (int i = 0; i < coffeeList.size(); i++) {
			if (coffeeList.get(i).getId().equals(id)) {
				coffeeIndex = i;
				coffeeList.set(i, coffee);
				break;
			}
		}


		return (coffeeIndex == -1) ?
				new ResponseEntity<>(postCoffee(coffee), HttpStatus.CREATED) :
				new ResponseEntity<>(coffee, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	void deleteCoffee(@PathVariable String id) {
		//Lambda
		coffeeList.removeIf(c -> c.getId().equals(id));
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