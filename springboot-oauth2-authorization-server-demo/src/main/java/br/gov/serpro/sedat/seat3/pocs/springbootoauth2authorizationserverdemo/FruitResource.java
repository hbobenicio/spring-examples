package br.gov.serpro.sedat.seat3.pocs.springbootoauth2authorizationserverdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/fruits")
public class FruitResource {

    @Autowired
    private FruitService fruitService;

    @GetMapping
    public List<Fruit> list() {
        return this.fruitService.list();
    }

}
