package com.netec.pocs.clients.steps;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.netec.pocs.clients.dto.ClientDTO;
import io.cucumber.java.en.*;
import io.cucumber.spring.CucumberContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class ClientsSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private ClientDTO testClient;
    private ClientDTO response;

    @Given("a client exists with id {long}")
    public void clientExistsWithId(Long id) {
        testClient = new ClientDTO();
        testClient.setIdClient(id);
        testClient.setName("Test Client");
    }

    @When("I request all clients")
    public void requestAllClients() {
        ClientDTO[] clients = restTemplate.getForObject("/clients", ClientDTO[].class);
        if (clients != null && clients.length > 0) {
            response = clients[0];
        }
    }

    @Given("I have a new client with name {string}")
    public void haveNewClient(String name) {
        testClient = new ClientDTO();
        testClient.setName(name);
    }

    @When("I create the client")
    public void createClient() {
        response = restTemplate.postForObject("/clients", testClient, ClientDTO.class);
    }

    @Then("the client should be saved successfully")
    public void clientShouldBeSaved() {
        assertNotNull(response);
        assertNotNull(response.getIdClient());
        assertEquals(testClient.getName(), response.getName());
    }

    @Then("I should receive a list of clients")
    public void i_should_receive_a_list_of_clients() {
        ClientDTO[] clients = restTemplate.getForObject("/clients", ClientDTO[].class);
        assertNotNull(clients);
        assertTrue(clients.length > 0);
    }

    @When("I request client with {string}")
    public void requestClientWithId(String idClient) {
        response = restTemplate.getForObject("/clients/" + idClient, ClientDTO.class);
    }

    @Then("I should receive the client details")
    public void shouldReceiveClientDetails() {
        assertNotNull(response);
    }

    @Then("the client name should be {string}")
    public void theClientNameShouldBe(String expectedName) {
        assertNotNull(response);
        assertEquals(expectedName, response.getName());
    }

    @Given("a client exists with id {string}")
    public void a_client_exists_with_id(String id) {
        testClient = new ClientDTO();
        testClient.setIdClient(Long.parseLong(id));
        testClient.setName("Test Client");
    }

    @When("I update the client name to {string}")
    public void i_update_the_client_name_to(String newName) {
        testClient.setName(newName);
        response = restTemplate.exchange(
                "/clients",
                HttpMethod.PUT,
                new HttpEntity<>(testClient),
                ClientDTO.class).getBody();
    }

    @Then("the client should be updated successfully")
    public void the_client_should_be_updated_successfully() {
        assertNotNull(response);
        assertEquals(testClient.getName(), response.getName());
        assertEquals(testClient.getIdClient(), response.getIdClient());
    }

    @When("I delete the client")
    public void i_delete_the_client() {
        restTemplate.delete("/clients/" + testClient.getIdClient());
    }

    @Then("the client should be removed successfully")
    public void the_client_should_be_removed_successfully() {
        // Intentar obtener el cliente eliminado debería retornar null
        response = restTemplate.getForObject("/clients/" + testClient.getIdClient(), ClientDTO.class);
        assertNull(response);
    }

}