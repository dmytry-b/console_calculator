import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        boolean run = true;
        while (run) {
            try {
                System.out.println("Please enter the text\n");
                String inputString = console.nextLine().replaceAll(" ", "").toUpperCase();
                String outputString = calc(inputString);
                System.out.println("Result " + inputString + " is " + outputString + "\n");
                boolean ask = true;
                while (ask){
                    System.out.println("1 - continue\n2 - exit\n");
                    String cont = console.nextLine();
                    if (cont.equals("1")) {
                        ask = false;
                    } else if (cont.equals("2")) {
                        ask = false;
                        run = false;
                    } else {
                        System.out.println("\nIt was 'CONTINUE'? \n");
                    }
                }
            }
            catch (IOException | NumberFormatException e){
                System.out.println("ERROR\n");
            }
        }
    }

    public static String calc(String input) throws IOException {
        if (input.length() < 3 || input.length() > 9) {
            throw new  IOException();
        }
        char inputedZnak = znak(input);
        String[] numbers = split(input, inputedZnak);
        String outputResult;
        if ("123456789".contains("" + input.charAt(0))) {
            int a = Integer.parseInt(numbers[0]);
            int b = Integer.parseInt(numbers[1]);
            if (a < 1 || a > 10 || b < 1 || b > 10) {
                throw new IOException();
            }
            int res = calculate(a, b, inputedZnak);
            outputResult = "" + res;
        }
        else {
            String[] rim = new String[] {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"} ;
            int a = fromRim(rim, numbers[0]);
            int b = fromRim(rim, numbers[1]);
            if (a == 0 || b == 0) {
               throw new IOException();
            }
            else {
                int res = calculate(a, b, inputedZnak);
                outputResult = String.valueOf(toRim(rim, res));
            }
        }
        return outputResult;
    }

    private static char znak(String str) throws IOException{
        byte znakCount = 0;
        char znak = '+';
        int len = str.length();
        for (byte i = 0; i < len; i++) {
            char simbol = str.charAt(i);
            if ((simbol == '+') || (simbol == '-') || (simbol == '*') || (simbol == '/'))  {
                if (i == 0 || i == len - 1 ) {
                    throw new IOException();
                }
                znakCount++;
                znak = simbol;
            }
        }
        if (znakCount != 1) {
            throw new IOException();
        }
        return znak;
    }

    private static int calculate(int a, int b, char znak) {
        return switch (znak) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> 0;
        };
    }

    private static String[] split(String st, char zn) {
        String[] twoNumbers = new String[] {"", ""};
        byte j = 0;
        for (byte i = 0; i < st.length(); i++){
            char symbol = st.charAt(i);
            if (symbol == zn) {
                j++;
            }
            else {
                twoNumbers[j] += symbol;
            }
        }
        return twoNumbers;
    }

    private static int fromRim (String[] rim, String y) {
        int x = 0;
        for (byte i = 0; i < rim.length; i++) {
            if (rim[i].equals(y)) {
               x = i + 1;
            }
        }
        return x;
    }
    private static StringBuilder toRim (String[] rim, int res) throws IOException {
        StringBuilder rimNumber = new StringBuilder();
        if (res < 1) {
            throw new IOException();
        } else if (res < 11) {
            rimNumber.append(rim[res - 1]);
        } else if (res < 40) {
            rimNumber.append(rim40(rim, res));
        } else if (res < 50) {
            int delta = 50 - res;
            rimNumber.append(rim[delta - 1]);
            rimNumber.append("L");
        } else if (res < 90) {
            rimNumber.append("L");
            int delta = res - 50;
            rimNumber.append(rim40(rim, delta));
        } else if (res < 100) {
            int delta = 100 - res;
            rimNumber.append(rim[delta - 1]);
            rimNumber.append("C");
        } else {
            rimNumber.append("C");
        }
        return rimNumber;
    }

    private static StringBuilder rim40 (String[] rim, int r) {
        StringBuilder r40 = new StringBuilder();
        int x = r / 10;
        int y = r % 10;
        for (byte i = 0; i < x; i++) {
            r40.append("X");
        }
        if (y != 0) {
            r40.append(rim[y - 1]);
        }
        return r40;
    }
}
