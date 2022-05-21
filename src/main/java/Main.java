import org.bson.Document;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DBManager manager = new DBManager();

    public static void main(String[] args) {


        System.out.println("Welcome to Java High School");
        while (true) {
            printMenu();
            System.out.print("Enter your choice: ");
            switch (getChoice(4)) {
                case 1 -> addNewStudent();
                case 2 -> addExamGrades();
                case 3 -> getStudentsWithHigherGrades();
                case 4 -> getStudentsWithLowerGrades();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Wrong input, try again");
            }
        }
    }

    private static void printMenu() {
        System.out.println("***************************");
        System.out.println("Choose your option:");
        System.out.println("[1] - Add new student");
        System.out.println("[2] - Add finished exam");
        System.out.println("[3] - Get students with higher grades");
        System.out.println("[4] - Get students with lower grades");
        System.out.println("[0] - Exit");
    }

    private static void addNewStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter student id: ");
        String id = scanner.nextLine();

        manager.addStudentToDatabase(new Student(name, surname, id));
    }

    private static void addExamGrades() {

        Document document = getStudentFile();

        if (document == null) {
            return;
        }

        String exam = getExam();

        if (exam == null) {
            return;
        }

        System.out.print("Enter exam score: ");
        int score = getChoice(100);

        manager.addExamScoreToDatabase(document, exam, score);
    }

    private static Document getStudentFile() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student surname: ");
        String surname = scanner.nextLine();

        Document document = manager.getStudentFromDatabase(name, surname);

        if (document == null) {
            System.out.printf("Student with name %s %s cannot be found\n", name, surname);
        }

        return document;
    }

    private static String getExam() {
        System.out.println("Choose exam");
        System.out.println("[1] - Lithuanian");
        System.out.println("[2] - Mathematics");
        System.out.println("[3] - Geography");
        System.out.println("[4] - History");
        System.out.println("[5] - Chemistry");
        System.out.println("[6] - Biology");
        System.out.println("[7] - Latin");
        System.out.println("[0] - Finish");

        System.out.print("Enter your choice: ");
        int choice = getChoice(7);

        for (Exam exam : Exam.values()) {
            if (exam.getNumber() == choice) {
                return exam.toString();
            }
        }
        return null;
    }

    private static void getStudentsWithHigherGrades() {
        String exam = getExam();
        System.out.print("Enter minimum grade for exam");
        int grade = getChoice(100);

        ArrayList<ExamGrade> grades = manager.getStudentGradesByExam(exam);
        grades.removeIf(grade1 -> grade1.grade() < grade);

        System.out.printf("Students with minimum grade %d for %s exam is: \n", grade, exam);
        printStudentsAndExamGrades(grades);
    }

    private static void getStudentsWithLowerGrades() {
        String exam = getExam();
        System.out.print("Enter maximum grade for exam");
        int grade = getChoice(100);

        ArrayList<ExamGrade> grades = manager.getStudentGradesByExam(exam);
        grades.removeIf(grade1 -> grade1.grade() > grade);

        System.out.printf("Students with minimum grade %d for %s exam is: \n", grade, exam);
        printStudentsAndExamGrades(grades);

    }

    private static void printStudentsAndExamGrades(ArrayList<ExamGrade> grades) {
        for (ExamGrade examGrade : grades) {
            System.out.printf("Name: %s %s, grade = %d\n", examGrade.name(), examGrade.surname(), examGrade.grade());
        }
    }

    private static int getChoice(int upperLimit) {
        while (true) {
            String option = scanner.nextLine();
            try {
                int value = Integer.parseInt(option);
                if (value >= 0 && value <= upperLimit) {
                    return value;
                } else {
                    System.out.println("Wrong choice, try again");
                }
            } catch (NumberFormatException e) {
                System.out.println("Wrong input, try again");
            }
            System.out.print("Enter correct value: ");
        }
    }
}

