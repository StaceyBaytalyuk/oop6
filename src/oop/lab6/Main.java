package oop.lab6;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private void run() {
        Scanner in = new Scanner(System.in);
        StudentProcessor processor = new StudentProcessor();
        System.out.println(
                "1) Read from file\n" +
                "2) Read from binary file\n" +
                "3) Write to file\n" +
                "4) Write to binary file\n" +
                "5) Add record\n" +
                "6) Delete record\n" +
                "7) Show faculty list\n" +
                "8) Show students born after year\n" +
                "9) Show group list\n" +
                "10) Show all records\n" +
                "0) Exit"
        );

        while (true) {
            System.out.println();
            System.out.print("Choose number from menu: ");
            byte answer = in.nextByte();
            switch (answer) {
                case 0: System.exit(0);

                case 1: {
                    System.out.print("Enter name of file: ");
                    processor.readTextFile(in.next());
                    break;
                }

                case 2: {
                    System.out.print("Enter name of file: ");
                    processor.readBinaryFile(in.next());
                    break;
                }

                case 3: {
                    System.out.print("Enter name of file: ");
                    processor.writeTextFile(in.next());
                    break;
                }

                case 4: {
                    System.out.print("Enter name of file: ");
                    processor.writeBinaryFile(in.next());
                    break;
                }

                case 5: {
                    if ( processor.addRecord() ) {
                        System.out.println("Successfully added!");
                    } else {
                        System.out.println("Can't add record");
                    }
                    break;
                }

                case 6: {
                    System.out.print("Enter ID: ");
                    if ( processor.deleteRecord(in.nextInt()) ) {
                        System.out.println("Successfully deleted!");
                    } else {
                        System.out.println("Student is not found");
                    }
                    break;
                }

                case 7: {
                    System.out.print("Enter faculty: ");
                    in.nextLine();
                    String faculty = in.nextLine();
                    ArrayList<Student> students = processor.searchFaculty(faculty);
                    if ( students.size() > 0 ) {
                        processor.printStudents(students, faculty);
                    } else {
                        System.out.println("No students from "+faculty);
                    }
                    break;
                }

                case 8: {
                    System.out.print("Enter year: ");
                    int year = in.nextInt();
                    ArrayList<Student> students = processor.searchAfterYear(year);
                    if ( students.size() > 0 ) {
                        processor.printStudents(students, "Born after "+year);
                    } else {
                        System.out.println("No students born after "+year);
                    }
                    break;
                }

                case 9: {
                    System.out.print("Enter group: ");
                    int group = in.nextInt();
                    ArrayList<Student> students = processor.searchGroup(group);
                    if ( students.size() > 0 ) {
                        processor.printStudents(students, "Group "+group);
                    } else {
                        System.out.println("No students from "+group);
                    }
                    break;
                }

                case 10: {
                    processor.showAll();
                    break;
                }

                default: System.out.println("Wrong number. Try again!");
            }
        }
    }
}