package goorm.badaon.domain.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/hello")
@RestController
public class HelloController {

	@GetMapping
	public String hello() {
		return "Hello World!";
	}
}
