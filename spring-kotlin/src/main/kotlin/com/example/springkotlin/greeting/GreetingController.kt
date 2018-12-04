package com.example.springkotlin.greeting

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@RestController
@RequestMapping("/greeting")
class GreetingController {

	@GetMapping("/hello/{subject}")
	fun greeting(@PathVariable("subject") subject: String, @RequestParam("prefix") prefix: String?): String {
		val completeSubject = if (prefix == null) {
			"Hello, $subject"
		} else {
			"Hello, $prefix $subject"
		}

		return completeSubject
	}

	@GetMapping("/hello/{subject}.json")
	fun greetingJson(@PathVariable("subject") subject: String): Greeting {
		return Greeting(subject)
	}
}
