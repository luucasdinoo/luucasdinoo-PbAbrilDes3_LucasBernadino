package br.com.bancoamericano.mscustomer.common;

import br.com.bancoamericano.mscustomer.models.dto.CustomerCreateDto;
import br.com.bancoamericano.mscustomer.models.dto.CustomerUpdateDto;
import br.com.bancoamericano.mscustomer.models.entities.Customer;

import java.io.File;
import java.util.Date;

public class CustomersConstants {


    public static final Customer CUSTOMER = new Customer("209.606.860-61", "name", "MALE",new Date(),"email@gmail.com", 0L, "https://photo.png" );
    public static final CustomerCreateDto CUSTOMER_CREATE_DTO = new CustomerCreateDto("209.606.860-61", "name", "MALE", new Date(),"email@gmail.com", "https://photo.png" );
    public static final Customer INVALID_CUSTOMER = new Customer("", "", "", null, "", null , "");

    public static final Customer DINO = new Customer(1L,"218.644.210-88", "Lucas Bernadino", "MALE", new Date(), "luucasdinoo@gmail.com", 0L , "https://photodino.png");
    public static final Customer BUNNY = new Customer(2L,"869.114.560-96", "Yumi Muniz", "FEMALE", new Date(), "bunny@gmail.com", 0L , "https://photobunny.png");

    public static final CustomerUpdateDto UPDATE_DTO = new CustomerUpdateDto("name update", "emailupdate@gmail.com");
    public static final CustomerUpdateDto INVALID_UPDATE_DTO = new CustomerUpdateDto("", "");

    public static File file;
    public static final String FILE_URL = "https://example.com/file.jpg";


}
