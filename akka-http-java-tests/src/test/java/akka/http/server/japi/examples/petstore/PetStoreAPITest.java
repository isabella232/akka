/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi.examples.petstore;

import akka.http.marshallers.jackson.Jackson;
import akka.http.model.japi.HttpRequest;
import akka.http.model.japi.MediaTypes;
import akka.http.server.japi.JUnitRouteTest;
import akka.http.server.japi.TestResponse;
import akka.http.server.japi.Route;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PetStoreAPITest extends JUnitRouteTest {
    @Test
    public void testGetPet() {
        TestResponse response = runRoute(HttpRequest.GET.withUri("/pet/1"), createRoute());

        response.assertStatusCode(200);
        response.assertMediaType("application/json");

        Pet pet = response.entityAs(Jackson.jsonAs(Pet.class));
        assertEquals("cat", pet.getName());
        assertEquals(1, pet.getId());
    }
    @Test
    public void testGetMissingPet() {
        TestResponse response = runRoute(HttpRequest.GET.withUri("/pet/999"), createRoute());
        response.assertStatusCode(404);
    }
    @Test
    public void testPutPet() {
        HttpRequest request =
            HttpRequest.PUT
                .withUri("/pet/1")
                .withEntity(MediaTypes.APPLICATION_JSON.toContentType(), "{\"id\": 1, \"name\": \"giraffe\"}");

        TestResponse response = runRoute(request, createRoute());

        response.assertStatusCode(200);

        Pet pet = response.entityAs(Jackson.jsonAs(Pet.class));
        assertEquals("giraffe", pet.getName());
        assertEquals(1, pet.getId());
    }
    @Test
    public void testDeletePet() {
        Map<Integer, Pet> data = createData();

        HttpRequest request = HttpRequest.DELETE.withUri("/pet/0");

        TestResponse response = runRoute(request, createRoute(data));
        response.assertStatusCode(200);
        assertFalse(data.containsKey(0));
    }

    private Route createRoute() {
        return createRoute(createData());
    }
    private Route createRoute(Map<Integer, Pet> pets) {
                return PetStoreExample.appRoute(pets);
    }
    private Map<Integer, Pet> createData() {
        Map<Integer, Pet> pets = new HashMap<Integer, Pet>();
        Pet dog = new Pet(0, "dog");
        Pet cat = new Pet(1, "cat");
        pets.put(0, dog);
        pets.put(1, cat);

        return pets;
    }
}
