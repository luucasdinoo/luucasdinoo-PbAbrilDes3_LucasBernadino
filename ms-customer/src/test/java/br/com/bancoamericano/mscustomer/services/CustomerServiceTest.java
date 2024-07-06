package br.com.bancoamericano.mscustomer.services;

import br.com.bancoamericano.mscustomer.exception.exceptions.CustomerNotFoundException;
import br.com.bancoamericano.mscustomer.models.dto.CustomerUpdateDto;
import br.com.bancoamericano.mscustomer.models.entities.Customer;
import br.com.bancoamericano.mscustomer.repositories.CustomerRepository;
import br.com.bancoamericano.mscustomer.util.S3Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static br.com.bancoamericano.mscustomer.common.CustomersConstants.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService service;

    @Mock
    private CustomerRepository repository;

    @Mock
    private S3Util util;

    @BeforeEach
    void setUpMocks(){
        file = new File("file.jpg");
    }

    @Test
    void getCustomerById_WithExistingId(){
        when(repository.findById(1L)).thenReturn(Optional.of(DINO));
        Customer sut = service.getCustomerById(1L);

        assertNotNull(sut);
        assertNotNull(sut.getId());
        assertNotNull(sut.getBirthdate());

        assertEquals(1L, sut.getId());
        assertEquals("Lucas Bernadino", sut.getName());
        assertEquals("218.644.210-88", sut.getCpf());
        assertEquals("luucasdinoo@gmail.com", sut.getEmail());
        assertEquals("https://photodino.png", sut.getUrl_photo());
        assertEquals("MALE", sut.getGender());
    }

    @Test
    void getCustomerById_WithNonExistingId(){
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getCustomerById(99L))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(String.format("Customer with id %s not found", 99L));;
    }

    @Test
    void createCustomer_WithValidData() throws IOException {
        Customer customer = CUSTOMER;

        when(util.uploadFile(anyString(), any(File.class))).thenReturn(FILE_URL);
        when(repository.save(customer)).thenReturn(customer);

        Customer createdCustomer = service.createCustomer(customer, file);
        createdCustomer.setId(1L);

        assertNotNull(createdCustomer);
        assertNotNull(createdCustomer.getId());
        assertNotNull(createdCustomer.getBirthdate());

        assertEquals(customer, createdCustomer);
        assertEquals(0L, createdCustomer.getPoints());
        assertEquals("https://example.com/file.jpg", createdCustomer.getUrl_photo());
        assertEquals("name", createdCustomer.getName());
        assertEquals("209.606.860-61", createdCustomer.getCpf());
        assertEquals("email@gmail.com", createdCustomer.getEmail());
        assertEquals("MALE", createdCustomer.getGender());

    }

    @Test
    void createCustomer_WithInvavalidData() {
        when(repository.save(INVALID_CUSTOMER)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> service.createCustomer(INVALID_CUSTOMER, file)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateCustomer_WithValidData(){
        Customer existingCustomer = DINO;
        CustomerUpdateDto dto = UPDATE_DTO;

        when(repository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(repository.save(any(Customer.class))).thenReturn(existingCustomer);

        service.updateCustomer(1L, dto);

        assertEquals("name update", existingCustomer.getName());
        assertEquals("emailupdate@gmail.com", existingCustomer.getEmail());

    }

    @Test
     void deleteCustomer_WithtExistingId(){
        assertThatCode(() -> service.deleteCustomerById(1L)).doesNotThrowAnyException();
    }

    @Test
     void deleteCustomer_WithtUnexistingId(){
        doThrow(new RuntimeException()).when(repository).deleteById(99L);
        assertThatThrownBy(() -> service.deleteCustomerById(99L)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void addPointa(){

        Customer customer = DINO;

        when(repository.findById(1L)).thenReturn(Optional.of(customer));
        when(repository.save(customer)).thenReturn(customer);

        service.updatePoints(1L, 1000L);

        assertEquals(1000L, customer.getPoints());

    }

}
