package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RecetteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Recette getRecetteSample1() {
        return new Recette().id(1L).title("title1").description("description1");
    }

    public static Recette getRecetteSample2() {
        return new Recette().id(2L).title("title2").description("description2");
    }

    public static Recette getRecetteRandomSampleGenerator() {
        return new Recette().id(longCount.incrementAndGet()).title(UUID.randomUUID().toString()).description(UUID.randomUUID().toString());
    }
}
