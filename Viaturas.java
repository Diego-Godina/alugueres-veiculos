import Utils.InputValidation;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;

public class Viaturas {
    private String brand;
    private String model;
    private int registration;
    private int year;
    private double km;
    private int numberRentals;

    /**
     * This method is a constructor to create a new object with existing data
     *
     * @param       brand               Car brand
     * @param       model               Car model
     * @param       registration        Car registration number
     * @param       year                Car year
     * @param       km                  Car kilometers
     * @param       numberRentals       Car number rentals
     */
    public Viaturas(String brand, String model, int registration, int year, double km, int numberRentals) {
        this.brand = brand;
        this.model = model;
        this.registration = registration;
        this.year = year;
        this.km = km;
        this.numberRentals = numberRentals;
    }

    /**
     * This method is a constructor to create a new object with new data
     *
     * @param       sc              Scanner
     * @param       viaturas        Cars from csv file
     */
    public Viaturas(Scanner sc, Viaturas[] viaturas) {
        System.out.print("Informe a marca da viatura: ");
        brand = sc.nextLine();

        System.out.print("Informe o modelo da viatura: ");
        model = sc.nextLine();

        registration = validateIntGT0AndUniqueRegistration(sc, "Informe o número do registro: ", viaturas);
        year = InputValidation.validateIntBetween(sc, "Informe o ano do veículo: ", 1900, LocalDate.now().getYear());
        km = InputValidation.validateDoubleGE0(sc, "Informe a kilometragem do veículo: ");
        numberRentals = 0;
    }

    /**
     * This method validate registration number greater than 0 and verify if the registration number is unique
     *
     * @param       sc              Scanner
     * @param       message         User message
     * @param       viaturas        Cars from csv file
     *
     * @return      Unique registration number
     */
    public static int validateIntGT0AndUniqueRegistration(Scanner sc, String message, Viaturas[] viaturas) {
        while (true) {
            boolean exists = false;

            try {
                System.out.print(message);
                int value = sc.nextInt();
                sc.nextLine();

                for(int i=0; i < viaturas.length; i++) {
                    if(viaturas[i].registration == value) {
                        exists = true;
                        break;
                    }
                }

                if (value > 0 && !exists) {
                    return value;
                }

                System.out.println("Introduza um número inteiro maior do que 0 e que não esteja cadastrado.");
            } catch (InputMismatchException e) {
                System.out.println("Introduza um número inteiro maior do que 0.");
                sc.nextLine();
            }
        }
    }

    /**
     * This method verify if the registration number is unique
     *
     * @param       sc              Scanner
     * @param       message         User message
     * @param       viaturas        Cars from csv file
     *
     * @return      Unique registration number
     */
    public static int validateExistRegistration(Scanner sc, String message, Viaturas[] viaturas) {
        while (true) {
            try {
                System.out.print(message);
                int value = sc.nextInt();
                sc.nextLine();

                for(int i=0; i < viaturas.length; i++) {
                    if(viaturas[i].registration == value) {
                        return value;
                    }
                }

                System.out.println("Introduza um número de registro cadastrado.");
            } catch (InputMismatchException e) {
                System.out.println("Introduza um número inteiro maior do que 0.");
                sc.nextLine();
            }
        }
    }

    /**
     * This method is a getter to brand attribute
     *
     * @return      Car brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * This method is a setter to brand attribute
     *
     * @param       brand       Car brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * This method is a getter to model attribute
     *
     * @return      Car model
     */
    public String getModel() {
        return model;
    }

    /**
     * This method is a setter to model attribute
     *
     * @param       model       Car model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * This method is a getter to registration attribute
     *
     * @return      Car registration number
     */
    public int getRegistration() {
        return registration;
    }

    /**
     * This method is a setter to registration attribute
     *
     * @param       registration        Car registration number
     */
    public void setRegistration(int registration) {
        this.registration = registration;
    }

    /**
     * This method is a getter to year attribute
     *
     * @return      Car year
     */
    public int getYear() {
        return year;
    }

    /**
     * This method is a setter to year attribute
     *
     * @param       year        Car year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * This method is a getter to numberRentals attribute
     *
     * @return      Car rentals number
     */
    public int getNumberRentals() {
        return numberRentals;
    }

    /**
     * This method is a setter to numberRentals attribute
     *
     * @param       numberRentals       Car rentals number
     */
    public void setNumberRentals(int numberRentals) {
        this.numberRentals = numberRentals;
    }

    /**
     * This method is a getter to km attribute
     *
     * @return      Car kilometers
     */
    public double getKm() {
        return km;
    }

    /**
     * This method is a setter to km attribute
     *
     * @param       km      Car kilometers
     */
    public void setKm(double km) {
        this.km = km;
    }

    /**
     * This method override the default toString method
     *
     * @return      All attributes in form of the string
     */
    public String toString() {
        return " Marca: " + brand + "\n Modelo: " + model + "\n Matrícula: " + registration + "\n Ano: " + year + "\n Km: " + km + "\n Número de aluguéis: " + numberRentals + "\n";
    }
}