import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {
    static int n;
    static ArrayList<String> words = new ArrayList<>();
    static ArrayList<Integer> numbers = new ArrayList<>();
    static LinkedHashMap<Character, Integer> vars = new LinkedHashMap<>();
    static int maxLength = 0;
    static ArrayList<String> operation = new ArrayList<>();
    static ArrayList<Character> letters = new ArrayList<>();
    static int lQtty = 0;

    static boolean isTest = false;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        if (!isTest) {
            System.out.println("Сколько слов будет в примере?");
            n = in.nextInt();
            numbers = new ArrayList<>(n);  // Инициализация списка numbers
            for (int i = 0; i < n; i++) {
                System.out.println("Введите слово:");
                words.add(in.next());
                if (words.get(i).length() > maxLength) maxLength = words.get(i).length();
                for (int j = 0; j < words.get(i).length(); j++) {
                    char c = words.get(i).charAt(j);
                    if (!vars.containsKey(c))
                        vars.put(c, 0);
                }
                if (i < n - 1) {
                    System.out.println("Введите операцию:");
                    operation.add(in.next());
                }
            }
        } else {
            n = 3;
            for (int i = 0; i < n; i++) {
                numbers.add(0);  // Инициализация списка numbers
            }
            words.add("уран");
            words.add("уран");
            words.add("наука");
            operation.add("+");
            operation.add("=");
            vars.put('у', 0);
            vars.put('р', 0);
            vars.put('а', 0);
            vars.put('н', 0);
            vars.put('к', 0);
        }

        for (int i = 0; i < n; i++) {
            numbers.add(0);  // Инициализация списка numbers
        }

        letters = new ArrayList<>(vars.keySet());
        lQtty = letters.size();

        String operand = " ";
        for (int i = 0; i < n; i++) {
            if (i != 0) {
                operand = operation.get(i - 1);
            }
            System.out.printf("%2s %10s %n", operand, words.get(i));
        }
        System.out.println();
        assignValues(0);

//        vars.put('у', 6);
//        vars.put('р', 3);
//        vars.put('а', 2);
//        vars.put('н', 1);
//        vars.put('к', 4);
//        equation();
    }

    private static void equation() {
        char c;
        for (int i = 0; i < n; i++) {
            int calculatedNumber = 0;
            int dim = words.get(i).length();
            for (int j = dim - 1; j >= 0; j--) {
                c = words.get(i).charAt(j);
                calculatedNumber += vars.get(c) * Math.pow(10, dim - j - 1);
            }
            numbers.set(i, calculatedNumber);
        }
        int result = numbers.get(0);
        for (int i = 0; i < n - 2; i++) {
            switch (operation.get(i)) {
                case "+":
                    result += numbers.get(i + 1);
                    break;
            }
        }
        if (result == numbers.get(n - 1) && noMatches() && noLeadingNull()) {
            for (char ch : letters) {
                System.out.print(ch + " ");
            }
            System.out.println();
            for (char ch : letters) {
                System.out.print(vars.get(ch) + " ");
            }
            System.out.println();

            System.out.printf("%13d %n", numbers.get(0));
            System.out.printf("%2s %10d %n", operation.get(0), numbers.get(1));
            System.out.printf("%2s %10d %n%n", operation.get(1), result);
        }
    }

    private static void assignValues(int index) {
        if (index == lQtty) {
            // Базовый случай: достигнут конец набора букв, выход из рекурсии
            equation();
            return;
        }

        char currentLetter = letters.get(index);
        for (int value = 0; value <= 9; value++) {
            // Присваиваем значение текущей букве
            vars.put(currentLetter, value);

            // Рекурсивно вызываем функцию для следующей буквы
            assignValues(index + 1);
        }
    }

    private static boolean noMatches(){
        HashSet<Integer> valuesSet = new HashSet<>(vars.values());
        return valuesSet.size() == vars.size();
    }

    private static boolean noLeadingNull(){
        for (int i = 0; i < n; i++) {
            if (vars.get(words.get(i).charAt(0)) == 0 ) return false;
        }
        return true;
    }
}
