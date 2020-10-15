import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Christoffer Grännby
 * Date: 2020-10-09
 * Time: 12:03
 * Project: Inlämningsuppgift
 * Copyright: MIT
 */
public class BestGymEverTest {
    Path theFile = Paths.get("src/files/customers.txt");
    BestGymEver bestGymEver = new BestGymEver();

    @Test
    public void createCustomerListFromFile(){
        List<Customer> customerList = bestGymEver.createListWithCustomers(theFile);

        assertNotNull(customerList);
        assertEquals("Alhambra Aromes", customerList.get(0).getName());
        assertEquals("7603021234", customerList.get(0).getSocialSecurityNumber());
        assertEquals("2019-07-01", customerList.get(0).getLastDatePaid());

        assertEquals("Bear Belle", customerList.get(1).getName());
        assertEquals("8104021234", customerList.get(1).getSocialSecurityNumber());
        assertEquals("2018-12-02", customerList.get(1).getLastDatePaid());
    }
    @Test
    public void checkIfCustomerExistTest(){
        bestGymEver.test = true;
        String customerNameOrSocialSecurityNumber = "Alhambra Aromes";
        List<Customer> customerList = bestGymEver.createListWithCustomers(theFile);
        Customer customer = bestGymEver.checkIfCustomerExist(customerList, customerNameOrSocialSecurityNumber);

        assertNotNull(customer);
        assertEquals(customerNameOrSocialSecurityNumber, customer.getName());
        assertEquals("7603021234", customer.getSocialSecurityNumber());

        customerNameOrSocialSecurityNumber = "8104021234";
        customer = bestGymEver.checkIfCustomerExist(customerList, customerNameOrSocialSecurityNumber);
        assertEquals("Bear Belle", customer.getName());
        assertEquals(customerNameOrSocialSecurityNumber, customer.getSocialSecurityNumber());
    }
    @Test
    public void checkIfCustomerHavePaidInTheLastYearTest(){
        Customer customer = new Customer("","", "2020-10-05");
        assertTrue(bestGymEver.checkIfCustomerHavePaidInTheLastYear(customer));
    }
    @Test
    public void checkIfLastPaidDateIsOneYearFromTodayTest(){
        Customer customer = new Customer("","", "2019-10-15");
        assertTrue(bestGymEver.checkIfCustomerHavePaidInTheLastYear(customer));
    }
    @Test
    public void getCustomerFileTest(){
        String social = "customers";
        assertTrue(bestGymEver.getCustomerFileForPersonalTrainer(social).exists());
        assertFalse(bestGymEver.getCustomerFileForPersonalTrainer("cust").exists());
        assertEquals("src\\files\\customers.txt", bestGymEver.getCustomerFileForPersonalTrainer(social).getPath());
    }
    @Test
    public void createFileForPersonalTrainerWithCustomersVisitDatesTest() throws IOException {
        bestGymEver.test = true;
        File temp = File.createTempFile("temp", null);
        temp.delete();
        Path tempFile = Paths.get(temp.getPath());
        Customer tempCust = new Customer("8703311234", "Kloffe Kloffsson", "");
        bestGymEver.createFileForPersonalTrainerWithCustomersVisitDates(tempCust, tempFile);
        Scanner scanner = new Scanner(temp);

        assertEquals("Customer: Kloffe Kloffsson, 8703311234", scanner.nextLine());
        assertEquals(scanner.nextLine(), LocalDate.now().toString());
        assertFalse(scanner.hasNext());
    }
}
