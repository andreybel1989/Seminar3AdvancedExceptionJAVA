import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            int result = getUserData();
            if (result == 0) {
                System.out.println("Данные успешно записаны в файл.");
            } else if (result == -1) {
                System.out.println("Ошибка: введено недостаточное количество данных");
            } else if (result == -2) {
                System.out.println("Ошибка: введено избыточное количество данных");
            }
        } catch (DataFormatException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static int getUserData() throws DataFormatException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные в следующем порядке, разделяя их пробелом:");
        System.out.println("Фамилия Имя Отчество Дата_рождения Номер_телефона Пол");
        System.out.println("Пример ввода: 'Иванов Иван Иванович 15.06.2011 375278432363 m'");

        String userInput = scanner.nextLine();

        String[] userData = userInput.split("\\s+");


        if (userData.length != 6) {
            scanner.close();
            if (userData.length < 6) {
                return -1;
            } else {
                return -2;
            }
        }

        if (!isValidFormat(userData)) {
            scanner.close();
            throw new DataFormatException("неверный формат данных");
        }
        saveUserData(userData);
        scanner.close();
        return 0;
    }
    public static boolean isValidFormat(String[] userData) throws DataFormatException {

        try {
            if (userData[0].isEmpty()) {
                throw new DataFormatException("не введена фамилия");
            }
            if (userData[1].isEmpty()) {
                throw new DataFormatException("не введено имя");
            }
            if (userData[2].isEmpty()) {
                throw new DataFormatException("не введено отчество");
            }

            try {
                LocalDate birthDate = LocalDate.parse(userData[3], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (DateTimeParseException e) {
                throw new DataFormatException("неверный формат ввода даты рождения");
            }

            try {
                Long.parseLong(userData[4]);
            } catch (NumberFormatException e) {
                throw new DataFormatException("неверный формат номера телефона");
            }

            if (!userData[5].equals("f") && !userData[5].equals("m")) {
                throw new DataFormatException("неверный формат пола");
            }

            return true;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {

            throw new DataFormatException("неверный формат данных");
        }
    }

    public static void saveUserData(String[] userData) {
        String fileName = userData[0] + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(String.join(" ", userData));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

}

