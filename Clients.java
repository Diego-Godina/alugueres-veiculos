import Utils.InputValidation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Clients {
    private long nifNumber;
    private String name;
    private String address;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private long number;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * This method is a constructor to create a new object with existing data
     *
     * @param       nifNumber       Client nif number
     * @param       name            Client name
     * @param       address         Client address
     * @param       phone           Client phone
     * @param       email           Client email
     * @param       birthDate       Client birthday date
     * @param       number          Client number
     */
    public Clients(long nifNumber, String name, String address, String phone, String email, LocalDate birthDate, long number) {
        this.nifNumber = nifNumber;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.birthDate = birthDate;
        this.number = number;
    }

    /**
     * This method is a constructor to create a new object with new data
     *
     * @param       sc          Scanner
     * @param       clients     Clients from csv file
     */
    public Clients(Scanner sc, Clients[] clients) {
        nifNumber = validateLongGT0AndUniqueNifNumber(sc, "Informe o número do NIF: ", clients);

        System.out.print("Informe o nome: ");
        name = sc.nextLine();

        System.out.print("Informe o endereço: ");
        address = sc.nextLine();

        System.out.print("Informe o telefone: ");
        phone = sc.nextLine();

        System.out.print("Informe o email: ");
        email = sc.nextLine();

        birthDate = InputValidation.validateDate(sc, "Informe a data de nascimento: ");

        number = InputValidation.validateLongGT0(sc, "Informe o número da carta de condução: ");
    }

    /**
     * This method verify if the nifNumber does not exist
     *
     * @param       sc          Scanner
     * @param       message     User message
     * @param       clients     Clients from csv file
     *
     * @return      Unique nif number validated
     */
    public static long validateLongGT0AndUniqueNifNumber(Scanner sc, String message, Clients[] clients) {
        while (true) {
            boolean exists = false;

            try {
                System.out.print(message);
                long value = sc.nextLong();
                sc.nextLine();

                for(int i=0; i < clients.length; i++) {
                    if(clients[i].nifNumber == value) {
                        exists = true;
                        break;
                    }
                }

                if (value > 0 && !exists) {
                    return value;
                }

                System.out.println("Introduza um número maior do que 0 e que não esteja cadastrado.");
            } catch (InputMismatchException e) {
                System.out.println("Introduza um número maior do que 0.");
                sc.nextLine();
            }
        }
    }

    /**
     * This method verify if the nif number already exists
     *
     * @param       sc          Scanner
     * @param       message     User message
     * @param       clients     Clients from csv file
     *
     * @return      Unique nif number
     */
    public static long validateExistNifNumber(Scanner sc, String message, Clients[] clients) {
        while (true) {
            try {
                System.out.print(message);
                long value = sc.nextLong();
                sc.nextLine();

                for(int i=0; i < clients.length; i++) {
                    if(clients[i].nifNumber == value) {
                        return value;
                    }
                }

                System.out.println("Introduza um número de NIF cadastrado.");
            } catch (InputMismatchException e) {
                System.out.println("Introduza um número maior do que 0.");
                sc.nextLine();
            }
        }
    }

    /**
     * This method is a getter to nifNumber attribute
     *
     * @return      Client nif number
     */
    public long getNifNumber() {
        return nifNumber;
    }

    /**
     * This method is a setter to nifNumber attribute
     *
     * @param       nifNumber       Client nif number
     */
    public void setNifNumber(long nifNumber) {
        this.nifNumber = nifNumber;
    }

    /**
     * This method is a getter to name attribute
     *
     * @return      Client name
     */
    public String getName() {
        return name;
    }

    /**
     * This method is a setter to name attribute
     *
     * @param       name        Client name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method is a getter to address attribute
     *
     * @return      Client address
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method is a setter to address attribute
     *
     * @param       address     Client address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method is a getter to phone attribute
     *
     * @return      Client phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method is a setter to phone attribute
     *
     * @param       phone       Client phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This method is a getter to email attribute
     *
     * @return      Client email
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method is a setter to email attribute
     *
     * @param       email       Client email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method is a getter to birthDate attribute
     *
     * @return      Client birthday
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * This method is a setter to birthDate attribute
     *
     * @param       birthDate       Client birthday
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * This method is a getter to number attribute
     *
     * @return      Client number
     */
    public long getNumber() {
        return number;
    }

    /**
     * This method is a setter to number attribute
     *
     * @param       number      Client number
     */
    public void setNumber(long number) {
        this.number = number;
    }

    /**
     * This method override the default toString method
     *
     * @return      All attributes in form of the string
     */
    public String toString() {
        return " NIF: " + nifNumber + "\n Nome: " + name + "\n Endereço: " + address + "\n Telefone: " + phone + "\n Email: " + email + "\n Data de nascimento: " + birthDate.format(dateFormat) + "\n Número da carteira de habilitação: " + number + "\n";
    }
}
