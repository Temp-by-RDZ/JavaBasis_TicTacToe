package TRDZ.task;

import java.util.Arrays;
import java.util.Scanner;

public class Initialization {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String[] accept = {"Yes","yes","1","true","True","Да","да","+"}; //Задаем перечень согласия
    private static final String[] Players = {"◦","Х","◯","ИИ","ИИ","пользователем.","компьютером."}; //ЕИ - человек ИИ -компьютер
    private static String[][] Field;                //Поле
    private static int Field_weight;                //Ширина
    private static int Field_height;                //Высота
    private static int Win_Line;                    //Условие победы
    private static int[] Lines;                     //Анализатор ИИ
    private static boolean error;                   //Флаг ошибки

    public static void main(String[] args) {
        Get_numbers();      //Получаем щначения
        do{//while (isAgain());
            Lines = new int[] {0, -1, -1, -1, -1, 0, 0, 0, 0,   0, 0, 0, 0, -1, -1, -1, -1};
                            //    x1  y1  x2  y2  p1 p2 l1 l2 B:p1 p2 l1 l2 x1  y1  x2  y2
            Prepare_field();    //Готовим поле
            Create_Field();     //Отрисовываем поле
            System.out.println(Game_Process());
            }while (isAgain());
        System.out.println("Досвидания");
        }

    /**
     * Быстрый вывод ключевых фраз
     */
    private static void  hello(){
        System.out.println("\nДобро пожаловать в игру крестики нолики:");
        System.out.println("Пожалуйста введите размерность желаемого поля не менее 1к1");
        System.out.println("Количество столбцов (X):");
        }

    /**
     * Обьявление стартовых значений игры,
     * таких как размерность поля и условие победы
     */
    private static void Get_numbers() {

    //region Ввод количества столбцов
        while (!(Field_weight>0)) {
            error=false;
            hello();
            if (scanner.hasNextInt()) {Field_weight = scanner.nextInt();} //Проверка на некоректный ввод
            else {error=true;}
            if (error||(Field_weight<1)) {      //Оформление ошибки ввода
                System.out.println("Введено некоректное значение!");}
            scanner.nextLine();
            }
    //endregion

    //region  Ввод количества строк
        while (!(Field_height>0)) {
            error=false;
            System.out.println("Количество строк (Y):");
            if (scanner.hasNextInt()) {Field_height = scanner.nextInt();} //Проверка на некоректный ввод
            else {error=true;}
            if (error||(Field_height<1)) {      //Оформление ошибки ввода
                System.out.println("Введено некоректное значение!");
                hello();
                System.out.println(Field_weight);}
            scanner.nextLine();
            }
    //endregion

    //region  Ввод условия победы
        while (!(Win_Line>0)) {
            System.out.println("Пожалуйста введите условие победы(большее 1 и меньше размера):");
            if (scanner.hasNextInt()) {Win_Line = scanner.nextInt();} //Проверка на некоректный ввод
            else {error=true;}
            if (error||(Win_Line<1)||(Win_Line>Field_weight)||(Win_Line>Field_height)) {      //Оформление ошибки ввода
                System.out.println("Введено некоректное значение!");
                hello();
                System.out.println(Field_weight);
                System.out.println("Количество строк (Y):");
                System.out.println(Field_height);
                Win_Line=0;}
            scanner.nextLine();
            }
    //endregion
        }

    /**
     * Подготовка/очистка состояния поля
     */
    private static void Prepare_field(){
        Field=new String[Field_weight][Field_height];
        for (int x=0; x<Field_weight; x++){
            for (int y=0; y<Field_height; y++){
                Field[x][y]=Players[0];
                }
            }
        }

    /**
     * Отрисовка поля игры
     */
    private static void Create_Field() {

    //region Отрисовка верхней рамки
        System.out.print("┌");
        for (int x=0; x<Field_weight; x++) {
            if (x<9) {System.out.print("───"+(x+1));}
            else if  (x<99) {System.out.print("──"+(x+1));}
            else {System.out.print("─"+(x+1));}
            }
        System.out.print("───┐ ->X");
    //endregion

    //region Отрисовка поля
        for (int y=1; y<(Field_height*2+2); y++){
            //Отрисовка левого края поля
            switch( y==1 ? 1 : y==(Field_height*2+1) ? 2 : y%2==1 ? 3 : 4 ) {
            case 1:
                System.out.print("\n│ ╔");
                break;
            case 2:
                System.out.print("\n│ ╚");
                break;
            case 3:
                System.out.print("\n│ ╠");
                break;
            case 4:
                System.out.print("\n"+y/2+" ║");
                }
            //Отрисовка центра поля
            for (int x=2; x<(Field_weight*2+1); x++){
                if (x%2==0) { //Ячейка
                    if (y%2==1) {System.out.print("═══");}
                    else {System.out.print(" "+Field[x/2-1][y/2-1]+" ");}
                    }
                else { //Граница
                    switch( y==1 ? 1 : y==(Field_height*2+1) ? 2 : y%2==1 ? 3 : 4 ) {
                    case 1:
                        System.out.print("╦");
                        break;
                    case 2:
                        System.out.print("╩");
                        break;
                    case 3:
                        System.out.print("╬");
                        break;
                    case 4:
                        System.out.print("║");
                        }
                    }
                }
            //Отрисовка правого края поля
            switch( y==1 ? 1 : y==(Field_height*2+1) ? 2 : y%2==1 ? 3 : 4 ) {
            case 1:
                System.out.print("╗ │");
                break;
            case 2:
                System.out.print("╝ │");
                break;
            case 3:
                System.out.print("╣ │");
                break;
            case 4:
                System.out.print("║ │");
                }
            }
    //endregion

    //region Отрисовка верхней рамки
        System.out.print("\n└");
        for (int x=0; x<Field_weight; x++) {System.out.print("────");}
        System.out.println("───┘");
        System.out.println("y");
    //endregion
        }

    /**
     * По ходовая обработка игры
     * @return Возвращает чем закончилась игра
     */
    private static String Game_Process() {
        for (int turn=1;turn<=(Field_weight*Field_height);turn++) { //Чередование ходов
            Game_turn((2-turn%2));

            System.out.println("\nХод "+turn);
            if (isWin(2-turn%2)) {
                return ("\nНа "+(turn/2+turn%2)+" ходу Победил игрок "+(2-turn%2)+" - \""+Players[2+2-turn%2]+"\" Управляемый "+Players[4+2-turn%2]);
                }
            }
        return ("\nНа "+(Field_weight*Field_height)+" ходу кончились пустые клетки но никто так и не смог достичь победы. Игра окончилась ничьей.");
        }

    /**
     * Обработка хода игрока
     * @param Player Номер игрока
     */
    private static void Game_turn(int Player) {
        System.out.println("\nХод игрока "+Player);
        int x=-1;
        int y=-1;
        int x1=0; //Х счетчик линии
        int y1=0; //Y счетчик линии
        int x2=0; //Откланение поиска по Х
        int y2=0; //Откланение поиска по Y
        int Enemy = (1+(Player%2)); //Получаем ID опонента

        if (Players[2+Player].equals("ИИ")) {   //Ходящий игрок - компьютер
            //region Инициализация хода компьютера
            if (Lines[(Enemy*2-1)]!=-1) {           //ИИ Смотрит на ход опонента...
                if (Lines[(Player*2-1)]!=-1) {
                    Lines[Player+6]= analyze(Player);}      //Анализируем последний ход текущего игрока
                Lines[Enemy+6] = analyze(Enemy);            //Анализируем последний ход его опонента
                /*
                if (Lines[Enemy + 4]<Lines[Enemy + 8]) {    //Защита от ложных ходов пользователя
                    Lines[Enemy + 6] = Lines[Enemy + 10];
                    Lines[Enemy + 4]= Lines[Enemy + 8];
                    Lines[(Enemy*2-1)]= Lines[12+(Enemy*2-1)];
                    Lines[(Enemy*2)] = Lines[12+(Enemy*2)];
                    } */
                if (Lines[Enemy+4]>Lines[Player+4]) {
                    //region Логика ИИ когда враг доминирует
                    System.out.println("Игрок "+Player+" боится "+Lines[Enemy+6]+" с силой="+Lines[Enemy+4]+" x="+Lines[(Enemy*2-1)]+" y="+Lines[(Enemy*2)]);
                    switch (Lines[Enemy+6]) {
                    case 1: x2=-1;  y2=1;       break;
                    case 2: x2=1;   y2=-1;      break;
                    case 3: x2=-1;              break;
                    case 4: x2=1;               break;
                    case 5: x2=-1;  y2=-1;      break;
                    case 6: x2=1;   y2=1;       break;
                    case 7:         y2=-1;      break;
                    case 8:         y2=1;       break;
                        }
                    do { // while (x<0);
                        x1=Lines[(Enemy*2-1)];
                        y1=Lines[(Enemy*2)];
                        while ((x1>=0)&&(x1<Field_weight)&&(y1>=0)&&(y1<Field_height)) {
                            x1+=x2;
                            y1+=y2;
                            if ((isInRange(false,x1,y1)&&isEmpty(false,x1,y1))) {
                                x=x1;
                                y=y1;
                                break;
                                }
                            }
                        x2*=-1;
                        y2*=-1;
                        } while (x<0);
                    //endregion
                    }
                else if ((Lines[Enemy+6]==0)&&Lines[Player+4]<1) {
                    //region Логика ИИ когда ситуация не инициативна
                    if (Lines[(Player*2-1)]==-1) {
                        //region - ИИ отвечает на первые действия
                        if (isEnemyMakeBadMove(Enemy)){         //Логика ИИ - Мой опонент в удобной для меня позиции
                            x=Lines[(Enemy*2-1)];
                            y=Lines[(Enemy*2)];
                            if (Lines[(Enemy*2-1)]==0) {x=1;}
                            else if (Lines[(Enemy*2-1)]==Field_weight-1) {x=Field_weight-2;}
                            if (Lines[(Enemy*2)]==0) {y=1;}
                            else if (Lines[(Enemy*2)]==Field_height-1) {y=Field_height-2;}
                            }
                        else {                                  //Логика ИИ - Опонент закончил ход нужен план
                            if (Field_weight%2==1) {   //Добаление легкого рандома для придания человечности
                                if ((int)(1+Math.random()*2)==1) {x=Lines[(Enemy*2-1)]-1;}
                                else  {x=Lines[(Enemy*2-1)]+1;}
                                }
                            else {
                                x = ((Field_weight/2)<=Lines[(Enemy*2-1)]) ? (Lines[(Enemy*2-1)]-1) : (Lines[(Enemy*2-1)]+1);
                                }
                            if (Field_height%2==1) {   //Добаление легкого рандома для придания человечности
                                if ((int)(1+Math.random()*2)==1) {y=Lines[(Enemy*2)]-1;}
                                else {y=Lines[(Enemy*2)]+1;}
                                }
                            else {
                                y = ((Field_height/2)<=Lines[(Enemy*2)]) ? (Lines[(Enemy*2)]-1) : (Lines[(Enemy*2)]+1);
                                }
                            }
                        //endregion
                        }
                    else {
                        //region - ИИ наращивает преимуществое
                        x=Lines[(Player*2-1)];
                        y=Lines[(Player*2)];
                        switch (getspase(Enemy,x,y)) {
                        case 1: x--; y--; break;    //-7
                        case 2: x++; y--; break;    //-9
                        case 3: x--; y++; break;    //-1
                        case 4: x++; y++; break;    //-3
                        case 5: x--; break;         //-4
                        case 6: x++; break;         //-6
                        case 7: y--; break;         //-8
                        case 8: y++; break;         //-2
                            }
                        if (!isEmpty(false,x, y)) { //Дебаг на критический случай
                            do{
                                x = (int) (Math.random()*(Field_weight-1));
                                y = (int) (Math.random()*(Field_weight-1));
                                } while (!isEmpty(false,x,y));
                            }
                        //endregion
                        }
                    //endregion
                    }
                else {
                    //region Логика ИИ когда есть преимущество
                    switch (Lines[Player+6]) {
                        case 1: x2=-1;  y2=1;       break;
                        case 2: x2=1;   y2=-1;      break;
                        case 3: x2=-1;              break;
                        case 4: x2=1;               break;
                        case 5: x2=-1;  y2=-1;      break;
                        case 6: x2=1;   y2=1;       break;
                        case 7:         y2=-1;      break;
                        case 8:         y2=1;       break;
                        }
                    do { // while (x<0);
                        x1=Lines[(Player*2-1)];
                        y1=Lines[(Player*2)];
                        while ((x1>=0)&&(x1<Field_weight)&&(y1>=0)&&(y1<Field_height)) {
                            x1+=x2;
                            y1+=y2;
                            if ((isInRange(false,x1,y1)&&isEmpty(false,x1,y1))) {
                                x=x1;
                                y=y1;
                                break;
                                }
                            }
                        x2*=-1;
                        y2*=-1;
                        } while (x<0);
                    //endregion
                    }
                }
            else {                                  //ИИ - ходит первым
                x= Field_weight<2 ? (int)(Math.random()*(Field_weight-1)) : (Field_weight/2);
                y= Field_height<2 ? (int)(Math.random()*(Field_height-1)) : (Field_weight/2);
                }
            //endregion
            }
        else {                                  //Ходящий игрок - пользователь
            //region Инициализация хода пользователя
            do {
            //region получение новых корректны значений от пользователя
                x=0;
                y=0;
                while (!((x > 0) && (y > 0))) {
                    error = false;
                    System.out.println("Введите координаты хода X(от 1 до "+Field_weight+") и Y(от 1 до "+Field_weight+") через пробел >>>");
                    if (scanner.hasNextInt()) { //Проверка на некоректный ввод
                        x = scanner.nextInt();
                        if (scanner.hasNextInt()) {y = scanner.nextInt();}
                        else {error = true; scanner.nextLine();}
                        }
                    else {error = true; scanner.nextLine();}
                    if (error || ((x < 1) && (y > Field_weight)) || ((y < 1) && (y > Field_height))) {      //Оформление ошибки ввода
                        System.out.println("Введено некоректное значение!");
                        }
                    }
                x--;
                y--;
            //endregion
                } while (!(isInRange(true,x,y)&&isEmpty(true,x,y)));         //Возможен ли ход?
            //endregion
            }
        System.out.println("Игрок "+Player+" занимает клетку "+(x+1)+" "+(y+1));
        Field[x][y]=Players[Player];        //Делаем ход
        Lines[(Player*2-1)]=x;              //Запоминаем последний ход(x)
        Lines[(Player*2)]=y;                //Запоминаем последний ход(y)
        if (Lines[(Enemy*2-1)]!=-1) {       //Сохраняем данные о угрозе против переключения внимания
            Lines[Enemy + 6] = analyze(Enemy);              //Анализируем изменение в состоянии врага после хода
            if (Lines[Enemy + 8]<Lines[Enemy + 4]) {        //Сохраняем данные в случае обнаружения угрозы
                Lines[Enemy + 10] = Lines[Enemy + 6];
                Lines[Enemy + 8] = Lines[Enemy + 4];
                Lines[12+(Enemy*2-1)] = Lines[(Enemy*2-1)];
                Lines[12+(Enemy*2)] = Lines[(Enemy*2)];
                }
            }
        Create_Field();                     //Перерисовываем
        }

    /**
     * Проверка опонента на глупый ход
     * @param Enemy Ваш опонент
     * @return true / false
     */
    private static boolean isEnemyMakeBadMove(int Enemy) {
        return ((Lines[(Enemy*2-1)]==0)||(Lines[(Enemy*2)]==0)||(Lines[(Enemy*2-1)]==Field_weight-1)||(Lines[(Enemy*2)]==Field_height-1));
        }

    /**
     * Проверка на пустую клетку
     * @param isShow Нужно ли выводить сообщение о ошибке?
     * @param x координата клетки по х
     * @param y координата клетки по y
     * @return true / false
     */
    private static boolean isEmpty(boolean isShow,int x, int y) {
        if (Field[x][y].equals(Players[0])) {
            return true;
            }
        if (isShow) {System.out.println("Введенная ячейка занята");}
        return false;
        }

    /**
     * Проверка на существование клетки
     * @param isShow Нужно ли выводить сообщение о ошибке?
     * @param x координата клетки по х
     * @param y координата клетки по y
     * @return true / false
     */
    private static boolean isInRange(boolean isShow,int x, int y) {
        if (x>=0&&x<Field_weight&&y>=0&&y<Field_height) {
            return true;
            }
        if (isShow) {System.out.println("Введенная ячейка не существует");}
        return false;
        }

    /**
     * Проверка был ли последний ход игрока победным
     * @param Player Ходивший игрок
     * @return true / false
     */
    private static boolean isWin(int Player) {
        for (int y=0;y<Field_height;y++) {
            for (int x=0; x<Field_weight;x++) {
                if (Field[x][y].equals(Players[Player])) {if (isGood(Player,x,y)) {return true;};}
                }
            }
        //проверка
        return false; // Затычка
        }

    /**
     * Проверка можно ли из заданной точки проложить линию необходимой длинны?
     * @param Player Символы какого из игроков нужно проверять
     * @param x Координата х начальной точки
     * @param y Координата y начальной точки
     * @return true / false
     */
    private static boolean isGood(int Player, int x,int y) {
        int MyLine=1;

    //Если справа достаточно места
        if ((x+Win_Line)<=Field_weight){
        //region Горизонтальная победа?
            for (int x1=x+1; (x1<Field_weight);x1++) {
                if (Field[x1][y].equals(Players[Player])) {MyLine+=1;}
                else {break;}
                }
            if (MyLine>=Win_Line) {return true;}
        //endregion

        //region Нисходящая горизонтальная победа?
            if (y+Win_Line<=Field_height){
                MyLine=1;
                for (int i=1; ((i+x)<Field_weight)&&((i+y)<Field_height);i++) {
                    if (Field[x+i][y+i].equals(Players[Player])) {MyLine+=1;}
                    else {break;}
                    }
                if (MyLine>=Win_Line) {return true;}
                }
        //endregion
            }

    //Если снизу достаточно места
        if (y+Win_Line<=Field_height){
        //region Вертикальная победа?
            MyLine=1;
            for (int y1=y+1; (y1<Field_height);y1++) {
                if (Field[x][y1].equals(Players[Player])) {MyLine+=1;}
                else {break;}
                }
            if (MyLine>=Win_Line) {return true;}
        //endregion

        //region Восходящая горизонтальная победа?
            if (1+x-Win_Line>=0){
                MyLine=1;
                for (int i=1; ((x-i)>=0)&&((i+y)<Field_height);i++) {
                    if (Field[x-i][y+i].equals(Players[Player])) {MyLine+=1;}
                    else {break;}
                    }
                return (MyLine >= Win_Line);
                }
        //endregion
            }
        return false; // Затычка неудачи
        }

    /**
     * Определяет потенциальную угрозу от вражеского хода
     * @param Player Ваш опонент
     * @return Возвращает линию на которую делает ставку ваш опонент
     */
    private static int analyze(int Player) {
        int x=Lines[(Player*2-1)];  //Координата х отправной точки
        int y=Lines[(Player*2)];    //Координата y отправной точки
        int Line=0;                 //Линия угрозы для ИИ
        int potential=1;            //Определитель степени угрозы если < условия победы то точка игнорируется
        int Best=1;                 //Наиболее опасный ход развития
        int MyLine=1;               //просматриваемый ход развития
        int left=0;                 //Левый вектор
        int right=0;                //правый вектор
        int corr=0;                 //Коректор спорных ситуаций

        /*
        7 8 9
        4 5 6
        1 2 3
        */

    //region 1 -> 9?
        for (int i=1; ((x-i>=0)&&(y+i<Field_height));i++) {
            if (Field[x-i][y+i].equals(Players[Player])) {left++;}
            else if (!Field[x-i][y+i].equals(Players[0])) {corr=1; break;}      //Преграда
            potential++;
            if ((i+1)>=Win_Line) {corr=1; break;}                               //Преграда
            }
        MyLine+=left;
        for (int i=1; ((x+i<Field_weight)&&(y-i>=0));i++) {
            if (Field[x+i][y-i].equals(Players[Player])) {right++;}
            else if (!Field[x+i][y-i].equals(Players[0])) {corr=-1; break;}     //Преграда
            potential++;
            if ((i+1)>=Win_Line) {corr=-1; break;}                              //Преграда
            }
        MyLine+=right;
        if ((MyLine > Best)&&!(potential<Win_Line)) {
            Best = MyLine;
            Line = left>right ? 1 : 2;
            if (corr>0&&left==1||corr<0&&Line==2) {Line += corr; }          //При столкновении смена приоритета
            }
    //endregion
    //region 4 -> 6?
        left=0;
        right=0;
        MyLine=1;
        potential=1;
        for (int i=1; (x-i>=0);i++) {
            if (Field[x-i][y].equals(Players[Player])) {left++;}
            else if (!Field[x-i][y].equals(Players[0])) {corr=1; break;}        //Преграда
            potential++;
            if ((i+1)>=Win_Line) {corr=1; break;}                               //Преграда
            }
        MyLine+=left;
        for (int i=1; (x+i<Field_weight);i++) {
            if (Field[x+i][y].equals(Players[Player])) {right++;}
            else if (!Field[x+i][y].equals(Players[0])) {corr=-1; break;}       //Преграда
            potential++;
            if ((i+1)>=Win_Line) {corr=-1; break;}                              //Преграда
            }
        MyLine+=right;
        if ((MyLine > Best)&&!(potential<Win_Line)) {
            Best = MyLine;
            Line = left>right ? 3 : 4;
           if (corr>0&&left==3||corr<0&&Line==4) {Line += corr; }          //При столкновении смена приоритета
            }
    //endregion
    //region 7 -> 3?
        left=0;
        right=0;
        MyLine=1;
        potential=1;
        for (int i=1; ((x-i>=0)&&(y-i>=0));i++) {
            if (Field[x-i][y-i].equals(Players[Player])) {left++;}
            else if (!Field[x-i][y-i].equals(Players[0])) {corr=1; break; }      //Преграда
            potential++;
            if ((i+1)>=Win_Line) {corr=1; break; }                              //Преграда
            }
        MyLine+=left;
        for (int i=1; ((x+i<Field_weight)&&(y+i<Field_height));i++) {
            if (Field[x+i][y+i].equals(Players[Player])) {right++;}
            else if (!Field[x+i][y+i].equals(Players[0])) {corr=-1; break; }    //Преграда
            potential++;
            if ((i+1)>=Win_Line) {corr=-1; break;}                              //Преграда
            }
        MyLine+=right;
        if ((MyLine > Best)&&!(potential<Win_Line)) {
            Best = MyLine;
            Line = left>right ? 5 : 6;
            if (corr>0&&left==5||corr<0&&Line==6) {Line += corr; }          //При столкновении смена приоритета
            }
    //endregion
    //region 8 -> 2?
        left=0;
        right=0;
        MyLine=1;
        potential=1;
        for (int i=1; (y-i>=0);i++) {
            if (Field[x][y-i].equals(Players[Player])) {left++;}
            else if (!Field[x][y-i].equals(Players[0])) {corr=1; break;}        //Преграда
            potential++;
            if ((i+1)>=Win_Line) {corr=1; break;}                               //Преграда
            }
        MyLine+=left;
        for (int i=1; (y+i<Field_height);i++) {
            if (Field[x][y+i].equals(Players[Player])) {right++;}
            else if (!Field[x][y+i].equals(Players[0])) {corr=-1; break;}       //Преграда
            potential++;
            if ((i+1)>=Win_Line) {corr=-1; break;}                              //Преграда
            }
        MyLine+=right;
        if ((MyLine > Best)&&!(potential<Win_Line)) {
            Best = MyLine;
            Line = left>right ? 7 : 8;
            if (corr>0&&left==7||corr<0&&Line==8) {Line += corr; }          //При столкновении смена приоритета
            }
    //endregion
        Lines[Player+4]=Best-1;
        return Line;
        }

    /**
     * Определяет наилучшее направление для развития
     * @param Enemy Информация о противнике
     * @param x Координата Х начала
     * @param y Координата Y начала
     * @return Номер направления
     */
    private static int getspase(int Enemy, int x, int y) {
        int Line=0;                 //Линия угрозы для ИИ
        int potential=1;            //Определитель степени угрозы если < условия победы то точка игнорируется
        int Best=1;                 //Наиболее опасный ход развития

        /*
        7 8 9
        4 5 6
        1 2 3
        */

        //region 1 <- 5?
        for (int i=1; ((x-i>=0)&&(y+i<Field_height));i++) {
            if (!Field[x-i][y+i].equals(Players[0])) {break;}
            potential++;
            }
        if (potential > Best) {Best = potential; Line=3;}
        //endregion
        //region 5 -> 9?
        potential=1;
        for (int i=1; ((x+i<Field_weight)&&(y-i>=0));i++) {
            if (!Field[x+i][y-i].equals(Players[0])) {break;}
            potential++;
            }
        if (potential > Best) {Best = potential; Line=2;}
        //endregion
        //region 4 <- 5?
        potential=1;
        for (int i=1; (x-i>=0);i++) {
            if (!Field[x-i][y].equals(Players[0])) {break;}
            potential++;
            }
        if (potential > Best) {Best = potential; Line=5;}
        //endregion
        //region 5 -> 6?
        potential=1;
        for (int i=1; (x+i<Field_weight);i++) {
            if (!Field[x+i][y].equals(Players[0])) {break;}
            potential++;
            }
        if (potential > Best) {Best = potential; Line=6;}
        //endregion
        //region 7 <- 5?
        potential=1;
        for (int i=1; ((x-i>=0)&&(y-i>=0));i++) {
            if (!Field[x-i][y-i].equals(Players[0])) {break;}
            potential++;
            }
        if (potential > Best) {Best = potential; Line=1;}
        //endregion
        //region 5 -> 3?
        potential=1;
        for (int i=1; ((x+i<Field_weight)&&(y+i<Field_height));i++) {
            if (!Field[x+i][y+i].equals(Players[0])) {break;}
            potential++;
            }
        if (potential > Best) {Best = potential; Line=4;}
        //endregion
        //region 8 <- 5?
        potential=1;
        for (int i=1; (y-i>=0);i++) {
            if (!Field[x][y-i].equals(Players[0])) {break;}
            potential++;
            }
        if (potential > Best) {Best = potential; Line=7;}
        //endregion
        //region 5 -> 2?
        potential=1;
        for (int i=1; (y+i<Field_height);i++) {
            if (!Field[x][y+i].equals(Players[0])) {break;}
            potential++;
            }
        if (potential > Best) {Best = potential; Line=8;}
        //endregion

        return Line;
        }

    /**
     * Запрос пользователю нужно ли начать играть заново
     * @return true / false
     */
    private static boolean isAgain() {
        System.out.println("\nБудете еще играть?");
        String close = scanner.next();
        scanner.nextLine();
        return Arrays.asList(accept).contains(close);
        }

    }