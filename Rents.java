import Utils.InputValidation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Rents {
    private int idRent;
    private long nifNumber;
    private int registration;
    private LocalDate rentDateBegin;
    private LocalDate rentDateEnd;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * This method is a constructor to create a new object with existing data
     *
     * @param       idRent              Rent id
     * @param       nifNumber           Client nif number
     * @param       registration        Car registration number
     * @param       rentDateBegin       Start date of the rent
     * @param       rentDateEnd         End date of the rent
     */
    public Rents(int idRent, long nifNumber, int registration, LocalDate rentDateBegin, LocalDate rentDateEnd) {
        this.idRent = idRent;
        this.nifNumber = nifNumber;
        this.registration = registration;
        this.rentDateBegin = rentDateBegin;
        this.rentDateEnd = rentDateEnd;
    }

    /**
     * This method is a constructor to create a new object with new data
     *
     * @param       sc              Scanner
     * @param       clients         Clients from csv file
     * @param       viaturas        Cars from csv file
     * @param       rents           Rents from csv file
     */
    public Rents(Scanner sc, Clients[] clients, Viaturas[] viaturas, Rents[] rents) {
        idRent = validateExistIdRent(rents);
        nifNumber = Clients.validateExistNifNumber(sc, "Informe o número do NIF do cliente: ", clients);
        registration = Viaturas.validateExistRegistration(sc, "Informe o número do registro do veículo: ", viaturas);

        LocalDate[] dates = validateDateBeginAndEnd(sc, "Informe a data de início do aluguel: ", "Informe a data de fim do aluguel: ", rents, registration);
        rentDateBegin = dates[0];
        rentDateEnd = dates[1];
    }

    /**
     * This method verify if the random idRent was already used
     *
     * @param       rents       Rents from csv file
     *
     * @return      Unique rent id
     */
    public static int validateExistIdRent(Rents[] rents) {
        while(true) {
            boolean exist = false;
            int value = new Random().nextInt(Integer.MAX_VALUE - 1) + 1;

            for (int i = 0; i < rents.length; i++) {
                if (rents[i].getIdRent() == value) {
                    exist = true;
                    break;
                }
            }

            if(!exist) {
                return value;
            }
        }
    }

    /**
     * This method check if the rent id provided exists
     *
     * @param       rents       Rents from csv file
     * @param       sc          Scanner
     * @param       message     Message to show
     *
     * @return      Unique rent id
     */
    public static int validateExistIdRent(Rents[] rents, Scanner sc, String message) {
        while (true) {
            boolean exist = false;

            try {
                System.out.print(message);
                int value = sc.nextInt();
                sc.nextLine();

                for (int i = 0; i < rents.length; i++) {
                    if (rents[i].getIdRent() == value) {
                        exist = true;
                        break;
                    }
                }

                if (value > 0 && exist) {
                    return value;
                }

                System.out.println("Introduza um número inteiro maior do que 0 que esteja cadastrado.");
            } catch (InputMismatchException e) {
                System.out.println("Introduza um número inteiro maior do que 0.");
                sc.nextLine();
            }
        }
    }

    /**
     * This method verify if a range of dates was already reserved to the same registration number
     *
     * @param       sc                  Scanner
     * @param       m1                  First message
     * @param       m2                  Second message
     * @param       rents               Rents from csv file
     * @param       registration        Registration number to check
     *
     * @return      Validated dates to new rent
     */
    public static LocalDate[] validateDateBeginAndEnd(Scanner sc, String m1, String m2, Rents[] rents, int registration) {
        while (true) {
            LocalDate[] dates = new LocalDate[2];
            boolean over = false;

            dates[0] = InputValidation.validateDate(sc, m1);
            dates[1] = InputValidation.validateDate(sc, m2);

            for(int i = 0; i < rents.length; i++) {
                if (rents[i].getRegistration() == registration) {
                    LocalDate tempRentDateBegin = rents[i].getRentDateBegin();
                    LocalDate tempRentDateEnd = rents[i].getRentDateEnd();

                    if(overPeriods(dates[0], dates[1], tempRentDateBegin, tempRentDateEnd)) {
                        over = true;
                    }
                }
            }

            if(!over) {
                return dates;
            }

            System.out.println("Datas já foram reservadas para o veículo " + registration);
        }
    }

    /**
     * This method check if 2 periods of dates override each other
     *
     * @param       start1      First start date
     * @param       end1        First end date
     * @param       start2      Second start date
     * @param       end2        Second end date
     *
     * @return      The dates period override each other or note
     */
    public static boolean overPeriods(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !(end1.isBefore(start2) || end2.isBefore(start1));
    }

    /**
     * This method is a getter to idRent attribute
     *
     * @return      Rent Id
     */
    public int getIdRent() {
        return idRent;
    }

    /**
     * This method is a setter to idRent attribute
     *
     * @param       idRent        Rent id
     */
    public void setIdRent(int idRent) {
        this.idRent = idRent;
    }

    /**
     * This method is a getter to nifNumber attribute
     *
     * @return      Nif number
     */
    public long getNifNumber() {
        return nifNumber;
    }

    /**
     * This method is a setter to nifNumber attribute
     *
     * @param       nifNumber       Nif number
     */
    public void setNifNumber(long nifNumber) {
        this.nifNumber = nifNumber;
    }

    /**
     * This method is a getter to registration attribute
     *
     * @return      Registration number
     */
    public int getRegistration() {
        return registration;
    }

    /**
     * This method is a setter to registration attribute
     *
     * @param       registration        Registration number
     */
    public void setRegistration(int registration) {
        this.registration = registration;
    }

    /**
     * This method is a getter to rentDateBegin attribute
     *
     * @return      Start rent date
     */
    public LocalDate getRentDateBegin() {
        return rentDateBegin;
    }

    /**
     * This method is a setter to rentDateBegin attribute
     *
     * @param       rentDateBegin       Start rent date
     */
    public void setRentDateBegin(LocalDate rentDateBegin) {
        this.rentDateBegin = rentDateBegin;
    }

    /**
     * This method is a getter to rentDateEnd attribute
     *
     * @return      End rent date
     */
    public LocalDate getRentDateEnd() {
        return rentDateEnd;
    }

    /**
     * This method is a setter to rentDateEnd attribute
     *
     * @param       rentDateEnd     End rent date
     */
    public void setRentDateEnd(LocalDate rentDateEnd) {
        this.rentDateEnd = rentDateEnd;
    }

    /**
     * This method override the default toString method
     *
     * @return      All attributes in form of the string
     */
    public String toString() {
        return " Reserva: " + idRent + "\n Cliente: " + nifNumber + "\n Veículo: " + registration + "\n Data de início: " + rentDateBegin.format(dateFormat) + "\n Data de fim: " + rentDateEnd.format(dateFormat) + "\n";
    }
}
