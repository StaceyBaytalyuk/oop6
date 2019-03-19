package oop.lab6;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentProcessor {
    private Scanner in = new Scanner(System.in);
    private ArrayList<Student> students = new ArrayList<>();

    public boolean addRecord() {
        return students.add(enterStudentData());
    }

    public boolean deleteRecord(int id) {
        return students.removeIf( student -> ( student.getId() == id ) );
    }

    public void readTextFile(String fileName) {
        try ( BufferedReader reader = new BufferedReader(new FileReader(fileName)) ) {
            Student.StudentBuilder builder = new Student.StudentBuilder();
            String line;
            while ( (line = reader.readLine()) != null ){
                String[] tmp = line.split(",");
                builder.setID(Integer.parseInt(tmp[0].split("=")[1])).setSurname(tmp[1].split("=")[1]).setFirstName(tmp[2].split("=")[1]).setSecondName(tmp[3].split("=")[1]).setBirthday(tmp[4].split("=")[1]).setFaculty(tmp[5].split("=")[1]).setAddress(tmp[6].split("=")[1]).setPhone(tmp[7].split("=")[1]).setCourse(Integer.parseInt(tmp[8].split("=")[1])).setGroup(Integer.parseInt(tmp[9].split("=")[1].split("}")[0]));
                students.add(builder.build());
            }
            System.out.println("Successfully read!");
        } catch (IOException e) {
            System.err.println("Error reading file");
        }
    }

    public void writeTextFile(String fileName) {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)) ) {
            for (Student student : students) {
                writer.write(student.toString()+'\n');
            }
            System.out.println("Successfully written!");
        } catch (IOException e) {
            System.err.println("Error writing file");
        }
    }

    public void readBinaryFile(String fileName) {
        try ( FileInputStream fis = new FileInputStream(fileName) ) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            while ( fis.available() != 0 ) {
                students.addAll( (ArrayList<Student>)ois.readObject() );
            }
            System.out.println("Successfully read!");
        } catch ( IOException | ClassNotFoundException e ) {
            e.printStackTrace();
            System.err.println("Error reading binary file");
        }
    }

    public void writeBinaryFile(String fileName) {
        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName)) ) {
            oos.writeObject(students);
            System.out.println("Successfully written!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing binary file");
        }
    }

    public void showAll() {
        printStudents(students, "All records:");
    }

    public void printStudents(ArrayList<Student> students, String text) {
        System.out.println(text);
        int i = 1;
        for (Student student : students) {
            System.out.println((i++)+") "+student);
        }
    }

    public ArrayList<Student> searchFaculty(String faculty) {
        ArrayList<Student> res = new ArrayList<>();
        for (Student student : students) {
            if ( student.getFaculty().equals(faculty) ) {
                res.add(student);
            }
        }
        return res;
    }

    public ArrayList<Student> searchAfterYear(int year) {
        ArrayList<Student> res = new ArrayList<>();
        for (Student student : students) {
            if ( student.getBirthday().getYear() > year ) {
                res.add(student);
            }
        }
        return res;
    }

    public ArrayList<Student> searchGroup(int group) {
        ArrayList<Student> res = new ArrayList<>();
        for (Student student : students) {
            if ( student.getGroup() == group ) {
                res.add(student);
            }
        }
        return res;
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