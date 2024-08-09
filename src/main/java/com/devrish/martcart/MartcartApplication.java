package com.devrish.martcart;

import com.devrish.martcart.model.*;
import com.devrish.martcart.repository.*;
import com.devrish.martcart.repository.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MartcartApplication {

	public static void main(String[] args) {
		SpringApplication.run(MartcartApplication.class, args);
	}

//	@Bean
	public CommandLineRunner loadData(
			EventRepository eventRepository,
			CategoryRepository categoryRepository,
			UserRepository userRepository,
			ProductRepository productRepository,
			OrderRepository orderRepository,
			SessionRepository sessionRepository
	) {
		return args -> {

			Event sampleEvent = Event.builder()
					.ctaLink("/search")
					.imagePath("/images/65870fe1f6eaba3d89f6e4f4.png")
					.tagLines(Arrays.asList("Nike Sports shoes at 30% off! Grab your pair now!"))
					.colorLight("#9C4EBE")
					.colorDark("#230033")
					.colorCTA("#E27856")
					.build();

			eventRepository.save(sampleEvent);

			Category sampleCategory = Category.builder()
					.name("Footwear")
					.itemCount(0L)
					.build();

			categoryRepository.save(sampleCategory);

			User sampleSeller = User.builder()
					.firstname("Jack")
					.lastname("Doe")
					.email("jackdoe@test.com")
					.phone("9988776688")
					.userType(UserType.SELLER)
					.username("test_seller")
					.password("7f6f467575be3c486742105261a957e8c3984f7cfe08d04d6c22ff5ca2499f807b25ca8d7423a4be26cca5d1a03261de0ae973ff736eaa687b150805b377f527") // encoded qwerty
					.cart(Arrays.asList())
					.build();

			userRepository.save(sampleSeller);

			ChoiceItem sampleChoice = ChoiceItem.builder()
					.name("color")
					.values(Arrays.asList("red","black","orange"))
					.build();
			SpecificationItem sampleSpecification = SpecificationItem.builder()
					.name("material")
					.value("fabric")
					.build();
			Product sampleProduct = Product.builder()
					.name("Nike Fraizer")
					.currentPrice(2330.0)
					.originalPrice(3699.0)
					.rating(4.5)
					.category(sampleCategory)
					.soldBy(sampleSeller)
					.imagePath("/images/623c3ca2c8e443b2ca021970.png")
					.choices(Arrays.asList(sampleChoice))
					.specifications(Arrays.asList(sampleSpecification))
					.build();

			productRepository.save(sampleProduct);

			List<CartItem> sampleList = new ArrayList<>();
			sampleList.add(CartItem.builder().productId(sampleProduct).quantity(1L).build());

			User sampleBuyer = User.builder()
					.firstname("John")
					.lastname("Doe")
					.email("johndoe@test.com")
					.phone("9988776655")
					.userType(UserType.BUYER)
					.username("test_buyer")
					.password("7f6f467575be3c486742105261a957e8c3984f7cfe08d04d6c22ff5ca2499f807b25ca8d7423a4be26cca5d1a03261de0ae973ff736eaa687b150805b377f527") // encoded qwerty
					.cart(sampleList)
					.build();

			userRepository.save(sampleBuyer);

			Order sampleOrder = Order.builder()
					.address("123 XYZ Street, India")
					.quantity(2L)
					.productId(sampleProduct)
					.totalPrice(sampleProduct.getCurrentPrice() * 2)
					.userId(sampleBuyer)
					.build();

			orderRepository.save(sampleOrder);

			Session sampleSession = Session.builder()
					.userId(sampleBuyer)
					.build();

			sessionRepository.save(sampleSession);

		};
	}

}
