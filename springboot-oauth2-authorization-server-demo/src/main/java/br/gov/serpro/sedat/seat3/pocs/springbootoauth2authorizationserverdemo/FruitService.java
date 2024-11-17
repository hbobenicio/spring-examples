package br.gov.serpro.sedat.seat3.pocs.springbootoauth2authorizationserverdemo;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FruitService {

    private static final List<Fruit> FRUITS = Arrays.asList(
            new Fruit("Apple", 11.90F),
            new Fruit("Banana", 4.50F),
            new Fruit("Uva", 9.75F)
    );

    public List<Fruit> list() {
        return FRUITS;
    }

    public Optional<Fruit> findByName(String name) {
        return FRUITS.stream()
                .filter(f -> f.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
