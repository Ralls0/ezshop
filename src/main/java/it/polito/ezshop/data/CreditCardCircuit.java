package it.polito.ezshop.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreditCardCircuit {

    private final String fileName = "src/res/creditCards.txt";

    private static CreditCardCircuit instance;
    private CreditCardCircuit() {}

    public static CreditCardCircuit getInstance() {
        if (instance == null) {
            instance = new CreditCardCircuit();
        }
        return instance;
    }

    public synchronized boolean isCardPresent(String cardNumber) throws FileNotFoundException {
        File cardsFile = new File(fileName);
        Scanner scanner = new Scanner(cardsFile);

        boolean cardFounded = false;
        while (scanner.hasNextLine() && !cardFounded) {
            String line = scanner.nextLine();
            if (!line.startsWith("#")) {
                String[] lineFields = line.split(";");
                if (lineFields[0].equals(cardNumber)) {
                    cardFounded = true;
                }
            }
        }

        scanner.close();
        return cardFounded;
    }

    public synchronized boolean hasEnoughBalance(String cardNumber, Double minBalance) throws FileNotFoundException {
        File cardsFile = new File(fileName);
        Scanner scanner = new Scanner(cardsFile);

        boolean enoughBalance = false, cardFounded = false;
        while (scanner.hasNextLine() && !cardFounded) {
            String line = scanner.nextLine();
            if (!line.startsWith("#")) {
                String[] lineFields = line.split(";");
                if (lineFields[0].equals(cardNumber)) {
                    cardFounded = true;
                    Double balance = Double.parseDouble(lineFields[1]);
                    if (balance >= minBalance)
                        enoughBalance = true;
                }
            }
        }

        scanner.close();
        return enoughBalance;
    }

    public synchronized boolean pay(String cardNumber, Double amount) throws IOException {
        if (amount < 0) return false;

        File cardsFile = new File(fileName);
        Scanner scanner = new Scanner(cardsFile);

        List<String> newData = new ArrayList<>();
        boolean payed = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.startsWith("#")) {
                String[] lineFields = line.split(";");
                if (lineFields[0].equals(cardNumber)) {
                    Double balance = Double.parseDouble(lineFields[1]);
                    if (balance >= amount) {
                        balance = balance - amount;
                        line = cardNumber + ";" + balance;
                        payed = true;
                    }
                }
            }
            newData.add(line);
        }
        scanner.close();

        if (payed) {
            FileWriter writer = new FileWriter(cardsFile);
            for (String line : newData) {
                writer.write(line+"\n");
            }
            writer.close();
        }

        return payed;
    }

    public synchronized boolean refund(String cardNumber, Double amount) throws IOException {
        if (amount < 0) return false;

        File cardsFile = new File(fileName);
        Scanner scanner = new Scanner(cardsFile);

        List<String> newData = new ArrayList<>();
        boolean refund = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.startsWith("#")) {
                String[] lineFields = line.split(";");
                if (lineFields[0].equals(cardNumber)) {
                    Double balance = Double.parseDouble(lineFields[1]);

                    balance = balance + amount;
                    line = cardNumber + ";" + balance;
                    refund = true;
                }
            }
            newData.add(line);
        }
        scanner.close();

        if (refund) {
            FileWriter writer = new FileWriter(cardsFile);
            for (String line : newData) {
                writer.write(line+"\n");
            }
            writer.close();
        }

        return refund;
    }
}
