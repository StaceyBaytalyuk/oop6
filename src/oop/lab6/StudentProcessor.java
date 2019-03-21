package oop.lab6;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class StudentProcessor {
    private Scanner in = new Scanner(System.in);
    private Student[] students = new Student[100];
    private int size = 0;

    public boolean addRecord() {
        if ( size < students.length ) {
            students[size++] = enterStudentData();
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteRecord(int id) {
        int index=0;
        while ( students[index].getId() != id ) {
            index++;
        }
        if ( index < size ) {
            size--;
            System.arraycopy(students, index+1, students, index, size-index);
            students[size] = null;
            return true;
        }
        return false;
    }

    public void readTextFile(String fileName) {
        try ( FileReader fr = new FileReader(fileName) ) {
            Scanner scan = new Scanner(fr);
            Student.StudentBuilder builder = new Student.StudentBuilder();
            while ( scan.hasNextLine() ){
                String[] tmp = scan.nextLine().split(",");
                builder.setID(Integer.parseInt(tmp[0].split("=")[1])).setSurname(tmp[1].split("=")[1]).setFirstName(tmp[2].split("=")[1]).setSecondName(tmp[3].split("=")[1]).setBirthday(tmp[4].split("=")[1]).setFaculty(tmp[5].split("=")[1]).setAddress(tmp[6].split("=")[1]).setPhone(tmp[7].split("=")[1]).setCourse(Integer.parseInt(tmp[8].split("=")[1])).setGroup(Integer.parseInt(tmp[9].split("=")[1].split("}")[0]));
                students[size] = builder.build();
                size++;
            }
            System.out.println("Successfully read!");
        } catch (IOException e) {
            System.err.println("Error reading file");
        }
    }

    public void writeTextFile(String fileName) {
        try ( FileWriter fw = new FileWriter(fileName) ) {
            for (int i=0; i<size; i++) {
                fw.write(students[i].toString()+'\n');
            }
            System.out.println("Successfully written!");
        } catch (IOException e) {
            System.err.println("Error writing file");
        }
    }

    public void readBinaryFile(String fileName) {
        try ( FileInputStream fis = new FileInputStream(fileName) ) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            while(fis.available() != 0){
                students[size] = (Student)ois.readObject();
                size++;
            }
            System.out.println("Successfully read!");
        } catch ( IOException | ClassNotFoundException e ) {
            e.printStackTrace();
            System.err.println("Error reading binary file");
        }
    }

    public void writeBinaryFile(String fileName) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            for (int i=0; i<size; i++) {
                oos.writeObject(students[i]);
            }
            oos.close();
            System.out.println("Successfully written!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing binary file");
        }
    }

    public void printAllStudents() {
        int i = 0;
        while ( students[i] != null ) {
            System.out.println((i+1)+") "+students[i]);
            i++;
        }
    }

    public void printStudents(Student[] students, String text) {
        System.out.println(text);
        for (int i=0; i<students.length; i++) {
            System.out.println((i+1)+") "+students[i]);
        }
    }

    public void printAllSurnames() {
        printSurnames(students);
    }

    public void printSurnames(Student[] students) {
        int i = 0;
        while ( students[i] != null ) {
            System.out.println(students[i].getId()+" "+students[i].getSurname());
            i++;
        }
    }

    public Student[] searchFaculty(String faculty) {
        Student[] res = new Student[size];
        int k = 0;
        for (int i=0; i<size; i++) {
            if ( students[i].getFaculty().equals(faculty) ) {
                res[k] = students[i];
                k++;
            }
        }
        return Arrays.copyOf(res, k);
    }

    public Student[] searchAfterYear(int year) {
        Student[] res = new Student[size];
        int k = 0;
        for (int i=0; i<size; i++) {
            if ( students[i].getBirthday().getYear() > year ) {
                res[k] = students[i];
                k++;
            }
        }
        return Arrays.copyOf(res, k);
    }

    public Student[] searchGroup(int group) {
        Student[] res = new Student[size];
        int k = 0;
        for (int i=0; i<size; i++) {
            if ( students[i].getGroup() == group ) {
                res[k] = students[i];
                k++;
            }
        }
        return Arrays.copyOf(res, k);
    }

    public boolean addSomeRecords() {
        Student[] arr = generateArray();
        if ( (students.length - size) >= arr.length ) {
            System.arraycopy(arr, 0, students, size, arr.length);
            size+=arr.length;
            return true;
        }
        return false;
    }

    private Student[] generateArray() {
        Student.StudentBuilder builder = new Student.StudentBuilder();
        return new Student[] {
                builder.setID(1).setFullName("Petrov Petr Petrovich").setBirthday("01.02.1998").setFaculty("Computer Science").setAddress("Stroiteley 45").setPhone("3456754").setCourse(3).setGroup(3141).build(),
                builder.setID(2).setFullName("Ivanov Ivan Ivanovich").setBirthday("17.09.1999").setFaculty("Computer Science").setAddress("Mira 12").setPhone("778611").setCourse(2).setGroup(2141).build(),
                builder.setID(3).setFullName("Sidorov Sidor Sidorovich").setBirthday("30.05.2000").setFaculty("Software Engineering").setAddress("Yuzhnaya 59").setPhone("001343").setCourse(1).setGroup(1151).build(),
                builder.setID(4).setFullName("Vasiliev Vasiliy Vasilievich").setBirthday("23.01.2000").setFaculty("Computer Science").setAddress("Sobornaya 3").setPhone("765434").setCourse(2).setGroup(2141).build(),
                builder.setID(5).setFullName("Mihaylov Mihail Mihaylovich").setBirthday("08.11.1999").setFaculty("Computer Science").setAddress("Pogranichnaya 27").setPhone("08467").setCourse(2).setGroup(2141).build()
        };
    }

    private Student enterStudentData() {
        Student.StudentBuilder builder = new Student.StudentBuilder();
        System.out.print("Enter ID: ");
        builder.setID(in.nextInt());
        System.out.print("Enter full name: ");
        builder.setSurname(in.next());
        builder.setFirstName(in.next());
        builder.setSecondName(in.next());
        System.out.print("Enter birthday in format dd.mm.yyyy: ");
        builder.setBirthday(in.next());
        System.out.print("Enter faculty: ");
        in.nextLine();
        builder.setFaculty(in.nextLine());
        System.out.print("Enter adress: ");
        builder.setAddress(in.nextLine());
        System.out.print("Enter phone: ");
        builder.setPhone(in.next());
        System.out.print("Enter course: ");
        builder.setCourse(in.nextInt());
        System.out.print("Enter group: ");
        builder.setGroup(in.nextInt());
        return builder.build();
    }
}