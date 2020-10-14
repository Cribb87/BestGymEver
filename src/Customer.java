/**
 * Created by Christoffer Grännby
 * Date: 2020-10-09
 * Time: 12:09
 * Project: Inlämningsuppgift
 * Copyright: MIT
 */
public class Customer {
    private final String name;
    private final String socialSecurityNumber;
    private String lastDatePaid;

    public Customer (String socialSecurityNumber, String name, String lastDatePaid){
        this.socialSecurityNumber = socialSecurityNumber;
        this.name = name;
        this.lastDatePaid = lastDatePaid;
    }
    public String getName() {
        return name;
    }
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }
    public String getLastDatePaid() {
        return lastDatePaid;
    }

}