package Application;

import lib.Validator;

import java.util.Scanner;

public class Application
{

    static public Scanner inScanner = new Scanner(System.in);

    static public boolean running = true;

    public static void start()
    {
        Board board = new Board();
        board.setColorGaming('w');
        board.init();

        while (running)
        {
            board.printBoard();
            print("\n");
            print("Команды: ");
            print("----- exit: Выход из игры");
            print("------y1 x1 y2 x2: Ход фигуры из клетки x1, y1 в клекту x2, y2");


            print("Взятые Белые:" + board.getTakeWhite().toString());
            print("Взятые Черные:" + board.getTakeBlack().toString());

            switch (board.getColorGaming())
            {
                case 'w':
                    print("Ход Белых:");
                    break;
                case 'b':
                    print("Ход Черных:");
                    break;
            }

            if (board.isCheckMate())
            {
                System.out.println("checkmate");
                stop();
                return;
            }

            String inputLine = getUserStringWhileIsNotValid(8);

            if (inputLine.equals("exit"))
            {
                stop();
                return;
            }

            int x1, y1, x2, y2;
            int[] coords = getUserCoordsWhileIsNotValid(inputLine);
            y1 = coords[0];
            x1 = coords[1];
            y2 = coords[2];
            x2 = coords[3];


            while (!board.moveFigure(y1, x1, y2, x2))
            {
                if (!running)
                {
                    stop();
                    return;
                }

                print("Ошибка хода, повторите ввод хода!");

                inputLine = getUserStringWhileIsNotValid(8);

                if (inputLine.equals("exit"))
                {
                    stop();
                    return;
                }

                coords = getUserCoordsWhileIsNotValid(inputLine);
                y1 = coords[0];
                x1 = coords[1];
                y2 = coords[2];
                x2 = coords[3];
            }

            switch (board.getColorGaming())
            {
                case 'w':
                    board.setColorGaming('b');
                    break;
                case 'b':
                    board.setColorGaming('w');
                    break;
            }
        }
    }

    public static void stop()
    {
        running = false;
        print("Игра завршена.");
        inScanner.close();
    }

    public static String getUserStringWhileIsNotValid(int maxLen)
    {
        while (true)
        {
            String command = inScanner.nextLine();

            if (!Validator.isStringValid(command, maxLen))
            {
                print("COMMAND_IS_NOT_VALID");
                continue;
            }

            return command;
        }
    }

    public static int[] getUserCoordsWhileIsNotValid(String command)
    {
        while (true)
        {
            if (!Validator.isStringSplit(command))
            {
                print("CANT_SPLIT_COMMAND");
                command = getUserStringWhileIsNotValid(8);
                continue;
            }

            int[] coords = Validator.isCoordsValid(command.split(" "));

            if (coords == null)
            {
                print("CANT_FIND_INT_IN_COMMAND");
                command = getUserStringWhileIsNotValid(8);
                continue;
            }

            return coords;
        }
    }

    public static void print(String message)
    {
        System.out.println(message);
    }
}