package com.devrish.martcart;

import com.devrish.martcart.model.CartItem;
import com.devrish.martcart.model.User;
import com.devrish.martcart.model.UserType;
import com.devrish.martcart.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MartcartApplication {

	public static void main(String[] args) {
		SpringApplication.run(MartcartApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(UserRepository userRepository) {
		return args -> {

			List<CartItem> sampleList = new ArrayList<>();
			sampleList.add(CartItem.builder().productId(new ObjectId()).quantity(1L).build());

			User sampleUser = User.builder()
					.firstname("John")
					.lastname("Doe")
					.email("johndoe@test.com")
					.phone("9988776655")
					.userType(UserType.BUYER)
					.username("test_buyer")
					.password("7f6f467575be3c486742105261a957e8c3984f7cfe08d04d6c22ff5ca2499f807b25ca8d7423a4be26cca5d1a03261de0ae973ff736eaa687b150805b377f527") // encoded qwerty
					.cart(sampleList)
					.build();

			userRepository.save(sampleUser);
		};
	}

}
