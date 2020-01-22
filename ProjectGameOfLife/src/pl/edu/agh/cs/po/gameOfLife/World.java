package pl.edu.agh.cs.po.gameOfLife;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class World {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<Integer> toSurvive = new ArrayList<>();
        List<Integer> toRevive = new ArrayList<>();
        List<Vector2d> initialCells = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        System.out.println("Do You want to import game conditions or to set up custom ones: ");
        System.out.println("1) Import");
        System.out.println("2) Custom");
        int importOrCustom = scan.nextInt();
        switch(importOrCustom) {
            case 1: {
                System.out.println("Select condition set: ");
                System.out.println("1) Conway Classic Rules");
                System.out.println("2) Three-Four");
                System.out.println("3) No Death Rules");
                int importChoice = scan.nextInt();
                String fileName = "";
                switch(importChoice)
                {
                    case 1:
                    {
                        fileName = "Conway.gol";
                        break;
                    }
                    case 2:
                    {
                        fileName = "ThreeFour.gol";
                        break;
                    }
                    case 3:
                    {
                        fileName = "NoDeath.gol";
                        break;
                    }
                }
                Scanner fileScanner = new Scanner(new File(fileName));
                int numberOfSurvivalNumbers = fileScanner.nextInt();
                for (int i = 0; i < numberOfSurvivalNumbers; i++) {
                    toSurvive.add(fileScanner.nextInt());
                }
                int numberOfRevivalNumbers = fileScanner.nextInt();
                for (int i = 0; i < numberOfRevivalNumbers; i++) {
                    toRevive.add(fileScanner.nextInt());
                }
                fileScanner.close();
                break;
            }
            case 2: {
                System.out.print("Enter number of survival conditions: ");
                int numberOfSurvivalNumbers = scan.nextInt();
                System.out.println(numberOfSurvivalNumbers);
                System.out.print("Enter the survival conditions: ");
                for (int i = 0; i < numberOfSurvivalNumbers; i++) {
                    toSurvive.add(scan.nextInt());
                }
                System.out.print("Enter number of revival conditions: ");
                int numberOfRevivalNumbers = scan.nextInt();
                System.out.print("Enter the revival conditions: ");
                for (int i = 0; i < numberOfRevivalNumbers; i++) {
                    toRevive.add(scan.nextInt());
                }
                System.out.println(numberOfRevivalNumbers);
                break;
            }
        }
        System.out.println("Do You want to run some examples or a custom design: ");
        System.out.println("1) Run example");
        System.out.println("2) Custom design");
        int runChoice = scan.nextInt();
        switch(runChoice) {
            case 1: {
                System.out.println("Select an example tu run: ");
                System.out.println("1) Blinker");
                System.out.println("2) Glider");
                int exampleChoice = scan.nextInt();
                String exampleFileName = "";
                switch(exampleChoice)
                {
                    case 1:
                    {
                        exampleFileName = "Blinker.gle";
                        break;
                    }
                    case 2:
                    {
                        exampleFileName = "Glider.gle";
                        break;
                    }
                }
                Scanner exampleFileScanner = new Scanner(new File(exampleFileName));
                int numberOfInitialCells = exampleFileScanner.nextInt();
                for (int i = 0; i < numberOfInitialCells; i++) {
                    initialCells.add(new Vector2d(exampleFileScanner.nextInt(), exampleFileScanner.nextInt()));
                }
                exampleFileScanner.close();
                break;
            }
            case 2: {
                System.out.print("Enter number of initial cells: ");
                int numberOfInitialCells = scan.nextInt();
                System.out.print("Enter the coordinates of the initial cells: ");
                for (int i = 0; i < numberOfInitialCells; i++) {
                    initialCells.add(new Vector2d(scan.nextInt(), scan.nextInt()));
                    //System.out.println(initialCells.get(initialCells.size() - 1).toString());
                }
                System.out.println(numberOfInitialCells);
                break;
            }
        }
                System.out.print("Enter simulation delay (ms): ");
                int simulationDelay = scan.nextInt();
                scan.close();
                Map map = new Map(toSurvive, toRevive, initialCells, simulationDelay);
                while (true) {
                    map.oneDayGone();
                }
    }
}