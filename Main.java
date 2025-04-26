import Utils.InputValidation;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class Main {
    private final static String viaturasFilePath = Paths.get(Paths.get("").toAbsolutePath().toString(), "src\\Csv", "viaturas.csv").toString();
    private static Viaturas[] viaturas = new Viaturas[2];
    private static int nextViatura = 0;

    private final static String clientsFilePath = Paths.get(Paths.get("").toAbsolutePath().toString(), "src\\Csv", "clients.csv").toString();
    private static Clients[] clients = new Clients[2];
    private static int nextClient = 0;

    private final static String rentsFilePath = Paths.get(Paths.get("").toAbsolutePath().toString(), "src\\Csv", "rents.csv").toString();
    private static Rents[] rents = new Rents[2];
    private static int nextRent = 0;

    /**
     * This method is the main method
     *
     * @param       args        Arguments
     */
    public static void main(String[] args) {
        if(!loadViaturasFile() || !loadClientsFile() || !loadRentsFile()) {
            return;
        }

        Scanner sc = new Scanner(System.in);

        int option = -1;
        while(option != 0) {
            int idRent;
            int registration;
            long nifNumber;

            nextViatura = 0;
            nextClient = 0;
            nextRent = 0;

            loadViaturasFile();
            loadClientsFile();
            loadRentsFile();

            int lengthViatura = nextViatura;
            int lengthClient = nextClient;
            int lengthRent = nextRent;

            Viaturas[] oldViaturas = Arrays.copyOf(viaturas, lengthViatura);
            Clients[] oldClients = Arrays.copyOf(clients, lengthClient);
            Rents[] oldRents = Arrays.copyOf(rents, lengthRent);

            menu();
            option = InputValidation.validateIntBetween(sc, "Opção: ", 0, 13);
            switch(option) {
                case 0:
                    System.out.println("Até a próxima");
                    break;

                case 1:
                    System.out.println("Adicionar viaturas");
                    if(nextViatura == viaturas.length) {
                        viaturas = Arrays.copyOf(viaturas, viaturas.length*2);
                    }
                    viaturas[nextViatura] = new Viaturas(sc, oldViaturas);
                    nextViatura++;
                    addViaturaToFile();
                    break;

                case 2:
                    System.out.println("Remover viaturas");

                    registration = Viaturas.validateExistRegistration(sc, "Informe o número de registro da viatura: ", oldViaturas);
                    cleanViaturaFile();
                    viaturas = new Viaturas[viaturas.length];
                    nextViatura = 1;
                    for(int i=0; i < lengthViatura; i++) {
                        if(registration != oldViaturas[i].getRegistration()) {
                            viaturas[nextViatura-1] = oldViaturas[i];
                            addViaturaToFile();
                            nextViatura++;
                        }
                    }
                    break;

                case 3:
                    System.out.println("Editar viatura");

                    registration = Viaturas.validateExistRegistration(sc, "Informe o número de registro da viatura: ", oldViaturas);
                    cleanViaturaFile();
                    nextViatura = 1;
                    for(int i=0; i < lengthViatura; i++) {
                        if(registration == viaturas[i].getRegistration()) {
                            System.out.println("\tDados atuais da viatura\n" + viaturas[i]);

                            int registrationNew = Viaturas.validateExistRegistration(sc, "Informe o número de registro da viatura: ", oldViaturas);

                            System.out.print("Informe a nova marca da viatura: ");
                            String brand = sc.nextLine();

                            System.out.print("Informe o novo modelo da viatura: ");
                            String model = sc.nextLine();

                            int year = InputValidation.validateIntBetween(sc, "Informe o novo ano do veículo: ", 1800, LocalDate.now().getYear());
                            double km = InputValidation.validateDoubleGE0(sc, "Informe a nova kilometragem do veículo: ");
                            viaturas[i] = new Viaturas(brand, model, registrationNew, year, km, viaturas[i].getNumberRentals());
                            addViaturaToFile();
                            nextViatura++;
                        } else {
                            addViaturaToFile();
                            nextViatura++;
                        }
                    }
                    break;

                case 4:
                    System.out.println("Listar viaturas");
                    for(int i = 0; i < nextViatura; i++) {
                        System.out.println("\n" + viaturas[i]);
                    }
                    break;

                case 5:
                    System.out.println("Adicionar clientes");
                    if(nextClient == clients.length) {
                        clients = Arrays.copyOf(clients, clients.length*2);
                    }
                    clients[nextClient] = new Clients(sc, oldClients);
                    nextClient++;
                    addClientToFile();
                    break;

                case 6:
                    System.out.println("Remover cliente");

                    nifNumber = Clients.validateExistNifNumber(sc, "Informe o número NIF do cliente: ", oldClients);
                    cleanClientFile();
                    clients = new Clients[clients.length];
                    nextClient = 1;
                    for(int i=0; i < lengthClient; i++) {
                        if(nifNumber != oldClients[i].getNifNumber()) {
                            clients[nextClient-1] = oldClients[i];
                            addClientToFile();
                            nextClient++;
                        }
                    }
                    break;

                case 7:
                    System.out.println("Editar cliente");

                    nifNumber = Clients.validateExistNifNumber(sc, "Informe o número de NIF do cliente: ", oldClients);
                    cleanClientFile();
                    nextClient = 1;
                    for(int i=0; i < lengthClient; i++) {
                        if(nifNumber == clients[i].getNifNumber()) {
                            System.out.println("\tDados atuais do cliente\n" + clients[i]);

                            long nifNumberNew = Clients.validateExistNifNumber(sc, "Informe o número do NIF: ", oldClients);

                            System.out.print("Informe o nome: ");
                            String name = sc.nextLine();

                            System.out.print("Informe o endereço: ");
                            String address = sc.nextLine();

                            System.out.print("Informe o telefone: ");
                            String phone = sc.nextLine();

                            System.out.print("Informe o email: ");
                            String email = sc.nextLine();

                            LocalDate birthDate = InputValidation.validateDate(sc, "Informe a data de nascimento: ");

                            long number = InputValidation.validateLongGT0(sc, "Informe o número da carta de condução: ");
                            clients[i] = new Clients(nifNumberNew, name, address, phone, email, birthDate, number);
                            addClientToFile();
                            nextClient++;
                        } else {
                            addClientToFile();
                            nextClient++;
                        }
                    }
                    break;

                case 8:
                    System.out.println("Listar clientes");
                    for(int i = 0; i < nextClient; i++) {
                        System.out.println("\n" + clients[i]);
                    }
                    break;

                case 9:
                    System.out.println("Adicionar aluguéis");
                    if(nextRent == rents.length) {
                        rents = Arrays.copyOf(rents, rents.length*2);
                    }
                    rents[nextRent] = new Rents(sc, oldClients, oldViaturas, oldRents);
                    nextRent++;
                    addRentToFile();
                    break;

                case 10:
                    System.out.println("Remover aluguel");

                    idRent = Rents.validateExistIdRent(oldRents, sc, "Informe a reserva do aluguel");
                    cleanRentFile();
                    rents = new Rents[rents.length];
                    nextRent = 1;
                    for(int i=0; i < lengthRent; i++) {
                        if(idRent != oldRents[i].getIdRent()) {
                            rents[nextRent-1] = oldRents[i];
                            addRentToFile();
                            nextRent++;
                        }
                    }
                    break;

                case 11:
                    System.out.println("Editar aluguel");

                    idRent = Rents.validateExistIdRent(oldRents, sc, "Informe a reserva do aluguel: ");
                    cleanRentFile();
                    nextRent = 1;
                    for(int i=0; i < lengthRent; i++) {
                        if(idRent == rents[i].getIdRent()) {
                            System.out.println("\tDados atuais do aluguel\n" + rents[i]);

                            int newRegistration = InputValidation.validateIntGT0(sc, "Informe o número do novo registro do veículo: ");
                            LocalDate[] dates = Rents.validateDateBeginAndEnd(sc, "Informe a nova data de início do aluguel: ", "Informe a nova data de fim do aluguel: ", rents, newRegistration);

                            rents[i] = new Rents(rents[i].getIdRent(), rents[i].getNifNumber(), newRegistration, dates[0], dates[1]);
                            addRentToFile();
                            nextRent++;
                        } else {
                            addRentToFile();
                            nextRent++;
                        }
                    }
                    break;

                case 12:
                    System.out.println("Listar aluguéis");
                    for(int i = 0; i < nextRent; i++) {
                        System.out.println("\n" + rents[i]);
                    }
                    break;

                case 13:
                    System.out.println("Viaturas disponíveis");

                    LocalDate starDate = InputValidation.validateDate(sc, "Informe a data de início do aluguel: ");
                    LocalDate endDate = InputValidation.validateDate(sc, "Informe a data de fim do aluguel: ");

                    for(int i = 0; i < oldViaturas.length; i++) {
                        boolean over = false;
                        for(int j = 0; j < oldRents.length; j++) {
                            if(oldViaturas[i].getRegistration() == oldRents[j].getRegistration() && Rents.overPeriods(starDate, endDate, rents[j].getRentDateBegin(), rents[j].getRentDateEnd())) {
                                over = true;
                                break;
                            }
                        }

                        if(!over) {
                            System.out.println("\tDados da viatura\n" + viaturas[i]);
                        }
                    }
                    break;
            }
        }
    }

    /**
     * This method show a menu option to user
     */
    public static void menu() {
        System.out.println("\nMenu");
        System.out.println("1 -> Viaturas - Adicionar viatura");
        System.out.println("2 -> Viaturas - Remover viatura");
        System.out.println("3 -> Viaturas - Editar viatura");
        System.out.println("4 -> Viaturas - Listar viaturas");
        System.out.println("5 -> Clientes - Adicionar cliente");
        System.out.println("6 -> Clientes - Remover cliente");
        System.out.println("7 -> Clientes - Editar cliente");
        System.out.println("8 -> Clientes - Listar clientes");
        System.out.println("9 -> Aluguéis - Adicionar aluguel");
        System.out.println("10 -> Aluguéis - Remover aluguel");
        System.out.println("11 -> Aluguéis - Editar aluguel");
        System.out.println("12 -> Aluguéis - Listar aluguéis");
        System.out.println("13 -> Aluguéis - Listar viaturas disponíveis");
        System.out.println("0 -> Sair");
    }

    /**
     * This method load the cars into an attribute from csv file
     *
     * @return      Success or not
     */
    private static boolean loadViaturasFile() {
        String[] sArray;

        try(BufferedReader br = new BufferedReader(new FileReader(viaturasFilePath, StandardCharsets.ISO_8859_1))) {
            String line;
            while((line = br.readLine()) != null) {
                sArray = line.split(";");

                if(nextViatura == viaturas.length) {
                    viaturas = Arrays.copyOf(viaturas, viaturas.length * 2);
                }

                viaturas[nextViatura] = new Viaturas(sArray[0], sArray[1], Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3]), Double.parseDouble(sArray[4]), Integer.parseInt(sArray[5]));
                nextViatura++;
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar ficheiro de viaturas no caminho " + viaturasFilePath);
            return false;
        }

        return true;
    }

    /**
     * This method add a new car into the csv file
     */
    private static void addViaturaToFile() {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(viaturasFilePath, StandardCharsets.ISO_8859_1, true))) {
            Viaturas vt = viaturas[nextViatura-1];
            bw.write(vt.getBrand() + ";" + vt.getModel() + ";" + vt.getRegistration() + ";" + vt.getYear() + ";" + vt.getKm() + ";" + vt.getNumberRentals() + "\n");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar arquivo de viaturas: " + viaturasFilePath);
        }
    }

    /**
     * This method clean the csv cars file to edit or remove one of the cars
     */
    private static void cleanViaturaFile() {
        try {
            new BufferedWriter(new FileWriter(viaturasFilePath, false));
        } catch (Exception e) {
            System.out.println("Erro ao limpar o arquivo de viaturas.");
        }
    }

    /**
     * This method load the clients into an attribute from csv file
     *
     * @return      Success or not
     */
    private static boolean loadClientsFile() {
        String[] sArray;

        try(BufferedReader br = new BufferedReader(new FileReader(clientsFilePath, StandardCharsets.ISO_8859_1))) {
            String line;
            while((line = br.readLine()) != null) {
                sArray = line.split(";");

                if(nextClient == clients.length) {
                    clients = Arrays.copyOf(clients, clients.length * 2);
                }

                clients[nextClient] = new Clients(Long.parseLong(sArray[0]), sArray[1], sArray[2], sArray[3], sArray[4], LocalDate.parse(sArray[5]), Long.parseLong(sArray[6]));
                nextClient++;
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar ficheiro de clientes no caminho " + clientsFilePath);
            return false;
        }

        return true;
    }

    /**
     * This method add a new client into the csv file
     */
    private static void addClientToFile() {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(clientsFilePath, StandardCharsets.ISO_8859_1, true))) {
            Clients c = clients[nextClient-1];
            bw.write(c.getNifNumber() + ";" + c.getName() + ";" + c.getAddress() + ";" + c.getPhone() + ";" + c.getEmail() + ";" + c.getBirthDate() + ";" + c.getNumber() + "\n");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar arquivo de clientes: " + clientsFilePath);
        }
    }

    /**
     * This method clean the csv clients file to edit or remove one of the clients
     */
    private static void cleanClientFile() {
        try {
            new BufferedWriter(new FileWriter(clientsFilePath, false));
        } catch (Exception e) {
            System.out.println("Erro ao limpar o arquivo de clientes.");
        }
    }

    /**
     * This method load the rents into an attribute from csv file
     *
     * @return      Success or not
     */
    private static boolean loadRentsFile() {
        String[] sArray;

        try(BufferedReader br = new BufferedReader(new FileReader(rentsFilePath, StandardCharsets.ISO_8859_1))) {
            String line;
            while((line = br.readLine()) != null) {
                sArray = line.split(";");

                if(nextRent == rents.length) {
                    rents = Arrays.copyOf(rents, rents.length * 2);
                }

                rents[nextRent] = new Rents(Integer.parseInt(sArray[0]), Long.parseLong(sArray[1]), Integer.parseInt(sArray[2]), LocalDate.parse(sArray[3]), LocalDate.parse(sArray[4]));
                nextRent++;
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar ficheiro de aluguéis no caminho " + rentsFilePath);
            return false;
        }

        return true;
    }

    /**
     * This method add a new rent into the csv file
     */
    private static void addRentToFile() {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(rentsFilePath, StandardCharsets.ISO_8859_1, true))) {
            Rents rt = rents[nextRent-1];
            bw.write(rt.getIdRent() + ";" + rt.getNifNumber() + ";" + rt.getRegistration() + ";" + rt.getRentDateBegin() + ";" + rt.getRentDateEnd() + "\n");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar arquivo de rents: " + rentsFilePath);
        }
    }

    /**
     * This method clean the csv rents file to edit or remove one of the cars
     */
    private static void cleanRentFile() {
        try {
            new BufferedWriter(new FileWriter(rentsFilePath, false));
        } catch (Exception e) {
            System.out.println("Erro ao limpar o arquivo de aluguéis.");
        }
    }
}