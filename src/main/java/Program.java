import java.util.Random;
import java.util.Scanner;

public class Program {


    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static final int WIN_COUNT = 3;
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = '*';
    private static int fieldSizeX;
    private static int fieldSizeY;
    private static char[][] field;


    public static void main(String[] args) {
        while (true) {
            initialize();
            printField();
            while (true) {
                if (humanTurn()) {
                    printField();
                    System.out.println("Вы победили!");
                    break;
                }
                printField();

                if (aiTurn3()) {
                    printField();
                    System.out.println("Победа за компьютером!");
                    break;
                }
                printField();
            }
            System.out.println("Желаете сыграть еще раз? (Y - да): ");
            if (!scanner.next().equalsIgnoreCase("Y"))
                break;
        }

    }

    /**
     * Инициализация объектов игры
     */
    static void initialize() {
        fieldSizeX = 3;
        fieldSizeY = 3;
        field = new char[fieldSizeX][fieldSizeY];
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    /**
     * Печать текущего состояния игрового поля
     */
    static void printField() {
        System.out.print("+");
        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print("-" + (x + 1));
        }
        System.out.println("-");


        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print(x + 1 + "|");
            for (int y = 0; y < fieldSizeY; y++) {
                System.out.print(field[x][y] + "|");
            }
            System.out.println();
        }

        for (int x = 0; x < fieldSizeX * 2 + 2; x++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Ход игрока (человека)
     */
    static boolean humanTurn() {
        int x;
        int y;
        do {
            System.out.println("Введите координаты хода X и Y\n(от 1 до " + WIN_COUNT + ") через пробел: ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
        return checkALL(DOT_HUMAN, WIN_COUNT);
    }


    /**
     * Проверка, является ли ячейка игрового поля пустой
     */
    static boolean isCellEmpty(int x, int y) {
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка валидности координат хода
     */
    static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }


    /**
     * Ход игрока (компьютера)
     */
    static boolean aiTurn() {
        int x;
        int y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;

        if (checkALL(DOT_AI, WIN_COUNT)) {
            return true;
        }

        return false;
    }

    static boolean aiTurn3() {
        int x;
        int y;


        if (checkG(DOT_HUMAN, WIN_COUNT - 1)) {
            System.out.println();
            outerLoopСheckG:
            for (int i = 0; i < fieldSizeX; i++) {
                for (int j = 0; j < fieldSizeY; j++) {
                    if (isCellEmpty(j, i)) {
                        field[j][i] = DOT_AI;
                        break outerLoopСheckG;
                    }
                }
            }
        } else if (checkV(DOT_HUMAN, WIN_COUNT - 1)) {
            outerLoopСheckG:
            for (int i = 0; i < fieldSizeX; i++) {
                for (int j = 0; j < fieldSizeY; j++) {
                    if (isCellEmpty(i, j)) {
                        field[i][j] = DOT_AI;
                        break outerLoopСheckG;
                    }
                }
            }
        } else if (checkD(DOT_HUMAN, WIN_COUNT - 1)) {
            for (int i = 0; i < fieldSizeX; i++) {
                if (isCellEmpty(i, i)) {
                    field[i][i] = DOT_AI;
                    break;
                }
                if (isCellEmpty(fieldSizeX - i - 1, i)) {
                    field[fieldSizeX - i - 1][i] = DOT_AI;
                    break;
                }
            }
        } else {
            do {
                x = random.nextInt(fieldSizeX);
                y = random.nextInt(fieldSizeY);
            }
            while (!isCellEmpty(x, y));
            field[x][y] = DOT_AI;
        }
        if (checkALL(DOT_AI, WIN_COUNT)) return true;

        return false;

    }


    /**
     * Проверка на ничью
     *
     * @return
     */
    static boolean checkDraw() {
        for (int x = 0; x < fieldSizeX; x++) {

            for (int y = 0; y < fieldSizeY; y++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    /**
     * TODO: Переработать в рамках домашней работы
     * Метод проверки победы
     *
     * @param dot фишка игрока
     * @return
     */
    static boolean checkWin(char dot) {
        // Проверка победы по горизонталям
        if (field[0][0] == dot && field[0][1] == dot && field[0][2] == dot) return true;
        if (field[1][0] == dot && field[1][1] == dot && field[1][2] == dot) return true;
        if (field[2][0] == dot && field[2][1] == dot && field[2][2] == dot) return true;

        // Проверка победы по вертикалям
        if (field[0][0] == dot && field[1][0] == dot && field[2][0] == dot) return true;
        if (field[0][1] == dot && field[1][1] == dot && field[2][1] == dot) return true;
        if (field[0][2] == dot && field[1][2] == dot && field[2][2] == dot) return true;

        // Проверка победы по диагоналям
        if (field[0][0] == dot && field[1][1] == dot && field[2][2] == dot) return true;
        if (field[0][2] == dot && field[1][1] == dot && field[2][0] == dot) return true;

        return false;
    }

    static boolean checkWinV2(char dot, int win) {

        return false;
    }

    /*Проверка на выйгрыш по вертикали*/
    static boolean checkV(char dot, int win) {
        int countDot = 0;
        for (int i = 0; i < fieldSizeX; i++) {
            if (win - countDot != 0) {
                for (int j = 0; j < fieldSizeX; j++) {
                    if (field[i][j] == dot) {
                        countDot++;
                    }
                }
            }
            if (win - countDot == 0) {
                return true;

            }
            countDot = 0;
        }
        return false;
    }

    /*Проверка на выйгрыш по горизонтали*/
    static boolean checkG(char dot, int win) {
        int countDot = 0;
        for (int i = 0; i < fieldSizeX; i++) {
            if (win - countDot != 0) {
                for (int j = 0; j < fieldSizeX; j++) {
                    if (field[j][i] == dot) {
                        countDot++;
                    }
                }
            }
            if (win - countDot == 0) {
                return true;

            }
            countDot = 0;
        }
        return false;
    }


    /*Проверка на выйгрыш  диагоналям*/
    static boolean checkD(char dot, int win) {
        int firstDiagonalCount = 0;
        int secondDiagonalCount = 0;
        for (int i = 0; i < fieldSizeX; i++) {
            if (field[i][i] == dot) {
                firstDiagonalCount++;
//               System.out.println(firstDiagonalCount);
            }
            if (field[fieldSizeX - i - 1][i] == dot) {
                secondDiagonalCount++;
//                System.out.println(secondDiagonalCount);
            }
        }
        if (firstDiagonalCount == win || secondDiagonalCount == win) return true;

        return false;
    }

    /*Общая проверка на выйгрыш*/
    static boolean checkALL(char dot, int win) {
        if (checkG(dot, win) || checkV(dot, win) || checkD(dot, win)) return true;


        return false;


    }

    /**
     * Проверка состояния игры
     *
     * @param dot фишка игрока
     * @param s   победный слоган
     * @return
     */
    static boolean checkState(char dot, String s) {
        if (checkWin(dot)) {
            System.out.println(s);
            return true;
        } else if (checkDraw()) {
            System.out.println("Ничья!");
            return true;
        }
        return false; // Игра продолжается
    }

}
