import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.event.CaretListener;

import java.util.ArrayList;

class Car{
    private String carId;

    private String brand;

    private String model;

    private double basePricePerDay;

    private boolean isAvaliable;

    //consturctor create krke jaise hi hum us class ka object create karenge 
    //turant uski saari details run ho jayengi
    public Car(String carId, String brand, String model, double basePricePerDay){
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvaliable = true;
    }

    public String getCarId(){
        return carId;
    }

    public String getbrand(){
        return brand;
    }

    public String getmodel(){
        return model;
    }

    //a method
    public double calculatePrice(int rentalDays){
        return basePricePerDay * rentalDays;
    }


    public double getbasePricePerDay(){
        return basePricePerDay;
    }

    public boolean isAvaliable(){
        return isAvaliable;
    }

    //ek method 
    public void rent(){
        isAvaliable = false;
    }

    public void returnCar(){
        isAvaliable = true;
    }
}

class Customer{
    private String customerId;

    private String name;

    public Customer(String customerId, String name){
        this.customerId = customerId;
        this.name = name;
    }

    //now create getter and setter methods 
    public String getcustomerId(){
        return customerId;
    }

    public String getname(){
        return name;
    }

}


//kaun sa customer kaun si car le ja rha h ye cheez is class me rahegi 
class Rental{
    //car type ka car banaya similary other variables also
    private Car car;

    private Customer customer;

    private int days;

    //creating constructor now 
    public Rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar(){
        return car;
    }

    public Customer getCustomer(){
        return customer;
    }

    public int getDays(){
        return days;
    }
}

class carRentalSystem{
      private List<Car> cars;
      
      private List<Customer> customers;

      private List<Rental> rentals;

      //constructore of the class we created
      public carRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();

      }

      public void addCar(Car car){
        cars.add(car);
      }

      public void addCustomer(Customer customer){
        customers.add(customer);
      }

      public void rentCar(Car car, Customer customer, int days){
        //pehle check krna padega
        //car rent ke liye availabe h ya ni agar huyi then 
        if(car.isAvaliable()){
            car.rent();
            rentals.add(new Rental(car, customer, days));
        }else{
            System.out.println("Car is not availabe for rent.");
        }
      }

      public void returnCar(Car car){
        car.returnCar();
        //ek variable banaya 
        //car , no. of days, customer 
        Rental RentalToRemove = null;
        for(Rental rental : rentals){
            //getCar hume car return karega 
            //agar wo car h toh usko remove kr denge
            if(rental.getCar() == car){
                RentalToRemove = rental;
                break;
            }
        }

        //agar rentalToRemove hai null ni h toh usko remove kr diya 
        if(RentalToRemove != null){
            rentals.remove(RentalToRemove);
            System.out.println("Car returned successfully.");
        }else{
            System.out.println("Car was not rented.");
        }
      } 

      public void menu(){
        //input lene ke liye 
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("==== Car Rental System ====");
            System.out.println("1. Rent a car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit");
            System.out.println("Enter your Choice");

            //user se input le liya 
            int choice = scanner.nextInt();
            //consume new line
            scanner.nextLine();


            //agar 1st choice accept kri toh rent a car wali choice h 
            if(choice == 1){
                System.out.println("\n== rent a Car == \n");
                System.out.println("Enter your name:");
                //customerName naam ka variable banaya aur use input le liya
                String customerName = scanner.nextLine(); 

                System.out.println("\nAvailable cars:");
                for(Car car : cars){
                    if(car.isAvaliable()){
                        System.out.println(car.getCarId() + " - " + car.getbrand() + " " + car.getmodel());
                    }
                }

                System.out.println("\nEnter the car id you want to rent : ");
                String carId = scanner.nextLine();

                System.out.println("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                //consume new line 
                scanner.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for(Car car : cars){
                    if(car.getCarId().equals(carId) && car.isAvaliable()){
                        selectedCar = car;
                        break;
                    }
                }
                if(selectedCar != null){
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getcustomerId());
                    System.out.println("Customer Name: " + newCustomer.getname());
                    System.out.println("Car: " + selectedCar.getbrand() + " " + selectedCar.getmodel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);


                    System.out.println("\nConfirm Rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if(confirm.equalsIgnoreCase("Y" )){
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                    }
                    else{
                        System.out.println("\nRental Canceled");
                    }
                }
                else{
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            }else if(choice == 2){
                System.out.println("\n== Return a Car == \n");
                System.out.println("Enter the car ID you want to return: ");
                String carId = scanner.nextLine();  

                Car carToReturn = null;
                for(Car car : cars){
                    if(car.getCarId().equals(carId) && !car.isAvaliable()){
                        carToReturn = car;
                        break;
                    }
                }

                if(carToReturn != null){
                    Customer customer = null;
                    for(Rental rental : rentals){
                        if(rental.getCar() == carToReturn){
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if(customer != null){
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getname());
                    }else{
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                }else{
                    System.out.println("Invalid car ID or car is not rented.");
                }
            }else if(choice == 3){
                break;
            }else{
                System.out.println("Invvalid choice. Please enter a valid option.");
            }
        } 
        System.out.println("\nThank you for using the car rental System!");
      }
}

public class App {

    public static void main(String[] args) throws Exception {

        carRentalSystem rentalSystem = new carRentalSystem();
        Car car1 = new Car("C001", "Toyota", "Canry", 60.0);
        Car car2 = new Car("C002", "Honda", "Accord", 70.0);
        Car car3 = new Car("C003", "Mahindra", "Thar", 150.0);

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();
    }
}
