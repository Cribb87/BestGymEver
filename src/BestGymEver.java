import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Christoffer Grännby
 * Date: 2020-10-09
 * Time: 12:03
 * Project: Inlämningsuppgift
 * Copyright: MIT
 */
public class BestGymEver {

    String title = "Best Gym Ever";
    boolean test = false;
    ImageIcon LOGO = new ImageIcon(BestGymEver.class.getResource("/images/logo.png"));

    public static void main(String[] args) {
        BestGymEver program = new BestGymEver();
        program.mainProgram();
    }
    public void mainProgram() {
        Path theFile = Paths.get("src\\files\\customers.txt");
        List<Customer> customers = createListWithCustomers(theFile);

        while (true) {          // Används enbart för redovisningen, radera efter
            outputDialogMessage("Welcome to Best Gym Ever");
            String nameOrSocialSecurityNumber = inputDialogMessage("State a customer, name or social security number");
            Customer customer = checkIfCustomerExist(customers, nameOrSocialSecurityNumber);
            if (customer == null) {
                if (nameOrSocialSecurityNumber.isEmpty())
                    outputDialogMessage("You need to state a name or social security number");
                else outputDialogMessage(nameOrSocialSecurityNumber + " is not a customer in this gym");
            } else {
                if (checkIfCustomerHavePaidInTheLastYear(customer)) {
                    outputDialogMessage(customer.getName() + " have paid for the membership");
                    createFileForPersonalTrainerWithCustomersVisitDates(customer, null);
                } else outputDialogMessage(customer.getName() + " have´nt paid for the membership in the last year");
            }
        }
    }
    public List<Customer> createListWithCustomers(Path file){
        List<Customer> listWithCustomers = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(String.valueOf(file)))){
        while (scanner.hasNext()) {
            String ssNumber = scanner.next().trim().replace(",", "");
            String name = scanner.nextLine().trim();
            String paidDate = scanner.nextLine().trim();
            listWithCustomers.add(new Customer(ssNumber,name,paidDate));
        }
        }catch (FileNotFoundException e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return listWithCustomers;
    }
    public String inputDialogMessage (String message){
        String input = (String) JOptionPane.showInputDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE, LOGO, null, "");
        if (input == null)
            System.exit(0);
        return input;
    }
    public void outputDialogMessage (String message){
        int box = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, LOGO);
        if (box == JOptionPane.CLOSED_OPTION){
            System.exit(0);
        }
    }

    public Customer checkIfCustomerExist(List<Customer> customerList, String nameOrSocialSecurityNumber) {
        for(Customer customer: customerList)
            if (customer.getName().equalsIgnoreCase(nameOrSocialSecurityNumber.trim()) ||
                customer.getSocialSecurityNumber().equalsIgnoreCase(nameOrSocialSecurityNumber.trim()))
                return customer;
        return null;
    }

    public boolean checkIfCustomerHavePaidInTheLastYear(Customer customer){
        LocalDate todaysDateOneYearAgo = LocalDate.now().minusYears(1);
        LocalDate dateWhenCustomerLastPaid = LocalDate.parse(customer.getLastDatePaid());

        return todaysDateOneYearAgo.isBefore(dateWhenCustomerLastPaid) || todaysDateOneYearAgo.isEqual(dateWhenCustomerLastPaid);
    }

    public File getCustomerFileForPersonalTrainer(String customerSocialSecurityNumber){
        return new File("src/files/" + customerSocialSecurityNumber + ".txt");
    }

    public void createFileForPersonalTrainerWithCustomersVisitDates(Customer customer, Path customerFile){
        Path theFile = Paths.get("src/files/" + customer.getSocialSecurityNumber() + ".txt");
        if (test)
            theFile = customerFile;
        boolean checkIfFileExist = Files.exists(theFile);

        try (BufferedWriter buff = new BufferedWriter(new FileWriter(String.valueOf(theFile), true))){
            if (!checkIfFileExist)
            buff.write("Customer: " + customer.getName() + ", " + customer.getSocialSecurityNumber());
            buff.write("\n" + LocalDate.now());
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
