import java.util.Scanner;

class Student {
    int id;
    String name;
    String course;
    int roomNumber;
    boolean staying;

    Student(int id, String name, String course, int roomNumber) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.roomNumber = roomNumber;
        this.staying = true;
    }
}

public class Main {
    static Scanner sc = new Scanner(System.in);

    static final int TOTAL_ROOMS = 5;
    static final int ROOM_CAPACITY = 2;
    static final int MAX_STUDENTS = 100;

    static Student[] students = new Student[MAX_STUDENTS];
    static int studentCount = 0;
    static int nextId = 1001;

    static int getInt(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number.");
            }
        }
    }

    static int countRoomOccupants(int roomNumber) {
        int count = 0;

        for (int i = 0; i < studentCount; i++) {
            if (students[i].staying &&
                students[i].roomNumber == roomNumber) {
                count++;
            }
        }

        return count;
    }

    static int findAvailableRoom() {
        for (int i = 0; i < TOTAL_ROOMS; i++) {
            int roomNumber = 101 + i;

            if (countRoomOccupants(roomNumber) < ROOM_CAPACITY) {
                return roomNumber;
            }
        }

        return -1;
    }

    static int findStudentIndex(int id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].id == id) {
                return i;
            }
        }

        return -1;
    }

    static void registerStudent() {
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("Student storage is full.");
            return;
        }

        int roomNumber = findAvailableRoom();

        if (roomNumber == -1) {
            System.out.println("Sorry! All hostel rooms are full.");
            return;
        }

        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter course name: ");
        String course = sc.nextLine().trim();

        if (course.isEmpty()) {
            System.out.println("Course cannot be empty.");
            return;
        }

        Student s = new Student(nextId, name, course, roomNumber);
        students[studentCount] = s;
        studentCount++;

        System.out.println("\nStudent registered successfully!");
        System.out.println("Student ID : " + nextId);
        System.out.println("Name       : " + name);
        System.out.println("Course     : " + course);
        System.out.println("Room Number: " + roomNumber);

        nextId++;
    }

    static void viewStudents() {
        boolean found = false;

        System.out.println("\n--- STUDENT STAY DETAILS ---");

        System.out.printf("%-10s %-22s %-18s %-10s%n",
                "ID", "Name", "Course", "Room");

        for (int i = 0; i < studentCount; i++) {
            Student s = students[i];

            if (s.staying) {
                System.out.printf("%-10d %-22s %-18s %-10d%n",
                        s.id, s.name, s.course, s.roomNumber);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No students are currently staying.");
        }
    }

    static void searchStudent() {
        int id = getInt("Enter student ID: ");
        int index = findStudentIndex(id);

        if (index == -1) {
            System.out.println("Student ID not found.");
            return;
        }

        Student s = students[index];

        System.out.println("\n--- STUDENT DETAILS ---");
        System.out.println("Student ID : " + s.id);
        System.out.println("Name       : " + s.name);
        System.out.println("Course     : " + s.course);
        System.out.println("Room Number: " + s.roomNumber);
        System.out.println("Stay Status: " +
                (s.staying ? "Currently Staying" : "Checked Out"));
    }

    static void checkoutStudent() {
        int id = getInt("Enter student ID for checkout: ");
        int index = findStudentIndex(id);

        if (index == -1) {
            System.out.println("Student ID not found.");
            return;
        }

        Student s = students[index];

        if (!s.staying) {
            System.out.println("This student has already checked out.");
            return;
        }

        s.staying = false;

        System.out.println("Checkout completed successfully!");
        System.out.println("Student Name: " + s.name);
        System.out.println("Vacated Room: " + s.roomNumber);
        System.out.println("Room is now available for allocation.");
    }

    static void viewRoomStatus() {
        System.out.println("\n--- HOSTEL ROOM STATUS ---");

        int totalOccupied = 0;

        for (int i = 0; i < TOTAL_ROOMS; i++) {
            int roomNumber = 101 + i;
            int occupied = countRoomOccupants(roomNumber);
            int available = ROOM_CAPACITY - occupied;

            totalOccupied += occupied;

            System.out.println("\nRoom Number: " + roomNumber);
            System.out.println("Occupied Beds: " + occupied);
            System.out.println("Available Beds: " + available);
            System.out.println("Status: " +
                    (available > 0 ? "Available" : "Full"));
        }

        System.out.println("\nTotal Rooms: " + TOTAL_ROOMS);
        System.out.println("Total Beds: " +
                (TOTAL_ROOMS * ROOM_CAPACITY));
        System.out.println("Occupied Beds: " + totalOccupied);
        System.out.println("Available Beds: " +
                (TOTAL_ROOMS * ROOM_CAPACITY - totalOccupied));
    }

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n==================================");
            System.out.println(" HOSTEL ROOM ALLOCATION SYSTEM");
            System.out.println("==================================");
            System.out.println("1. Register Student and Allocate Room");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Student Checkout");
            System.out.println("5. View Room Availability");
            System.out.println("6. Exit");

            choice = getInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    registerStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    checkoutStudent();
                    break;

                case 5:
                    viewRoomStatus();
                    break;

                case 6:
                    System.out.println(
                            "Thank you for using the Hostel Management System!");
                    break;

                default:
                    System.out.println(
                            "Invalid choice! Please select 1 to 6.");
            }

        } while (choice != 6);

        sc.close();
    }
}
