
import Task1.service.CatFactsServiceImpl;
import Task2.service.NasaServiceImpl;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        showMenu(sc);
    }

    private static void showMenu(Scanner sc) {
        while (true) {
            System.out.println("Menu:\n" +
                    "1. GetCatFacts\n" +
                    "2. loadFromNasa\n" +
                    "3. Exit\n" +
                    "Choose menu point (1-3):");
            switch (sc.nextLine()) {
                case "1":
                    catFacts();
                    break;
                case "2":
                    loadFromNasa();
                    break;
                case "3":
                    System.out.println("Exit");
                    return;
            }
        }
    }

    private static void catFacts() {
        new CatFactsServiceImpl()
                .loadCatFacts().filterCatFacts().print();
    }

    private static void loadFromNasa() {
        new NasaServiceImpl()
                .loadData().saveData();
    }
}