import java.text.DecimalFormat;
import java.util.*;

// модель, тут происходит расчет
public class Model {
    public String calculation(String input){
        input = input.replace("//", "d");
        input = input.replace("**", "^");
        input = input.replace(",", ".");

        ArrayList<String> elements = inputToElements(input);

        CalcQueue<String> postfix = infixToPostfix(elements);
        return postfixEval(postfix);
    }

    //метод вычисляет постфиксные выражения
    public static String postfixEval(CalcQueue<String> postfixExp) {

        //используется для проверки, является ли символ оператором
        String operators = "+-*/%d^";

        Stack<String> evaluation = new Stack<>();
        String currentToken;//текущий токен, который обрабатывается
        int operatorIndex;//используется для оператора switch
        String operandA, operandB;//операнды строки
        double opA, opB;//операнды doubles
        String result;//результат строка
        boolean opBooleanA, opBooleanB;//операнды булевы

        //десятичный формат, включающий ровно два десятичных знака
        DecimalFormat twoDecimals = new DecimalFormat("0.00");

        //продолжаем обработку до тех пор, пока очередь, содержащая постфиксное выражение, не станет пустой
        while(!postfixExp.isEmpty()) {
            //получаем текущий токен
            currentToken = postfixExp.dequeue();

            //если токен является операндом, поместим его в стек
            if(!operators.contains(currentToken)) {
                evaluation.push(currentToken);
            }
            else{
                //сброс всех операндов на всякий случай
                operandA = operandB = result = "";
                opA = opB = 0;
                opBooleanA = opBooleanB = false;

                //получает индекс оператора из приведенной выше строки операторов
                operatorIndex = operators.indexOf(currentToken);

                // вычисления
                switch (operatorIndex) {
                    // сложение
                    case 0 -> {
                        operandA = evaluation.pop();
                        operandB = evaluation.pop();
                        try {
                            opA = Double.parseDouble(operandA);
                            opB = Double.parseDouble(operandB);
                        } catch (NumberFormatException e) {
                            return "Ошибка!";
                        }
                        result = Double.toString((opA + opB));
                        evaluation.push(result);
                    }
                    // вычитание
                    case 1 -> {
                        operandA = evaluation.pop();
                        operandB = evaluation.pop();
                        try {
                            opA = Double.parseDouble(operandA);
                            opB = Double.parseDouble(operandB);
                        } catch (NumberFormatException e) {
                            return "Ошибка!";
                        }
                        result = Double.toString((opB - opA));
                        evaluation.push(result);
                    }
                    // умножение
                    case 2 -> {
                        operandA = evaluation.pop();
                        operandB = evaluation.pop();
                        try {
                            opA = Double.parseDouble(operandA);
                            opB = Double.parseDouble(operandB);
                        } catch (NumberFormatException e) {
                            return "Ошибка!";
                        }
                        result = Double.toString((opA * opB));
                        evaluation.push(result);
                    }
                    // деление
                    case 3 -> {
                        operandA = evaluation.pop();
                        operandB = evaluation.pop();
                        try {
                            opA = Double.parseDouble(operandA);
                            opB = Double.parseDouble(operandB);
                        } catch (NumberFormatException e) {
                            return "Ошибка!";
                        }
                        if (opA != 0) {
                            result = Double.toString((opB / opA));
                        } else {
                            return "Деление на 0";
                        }
                        evaluation.push(result);
                    }
                    // модуль
                    case 4 -> {
                        operandA = evaluation.pop();
                        operandB = evaluation.pop();
                        try {
                            opA = Double.parseDouble(operandA);
                            opB = Double.parseDouble(operandB);
                        } catch (NumberFormatException e) {
                            return "Ошибка";
                        }
                        result = Double.toString(opA % opB);
                        evaluation.push(result);
                    }
                    // целочисленное деление
                    case 5 -> {
                        operandA = evaluation.pop();
                        operandB = evaluation.pop();
                        try {
                            opA = Double.parseDouble(operandA);
                            opB = Double.parseDouble(operandB);
                        } catch (NumberFormatException e) {
                            return "Ошибка!";
                        }
                        if (opA != 0) {
                            result = Integer.toString((int) (opB / opA));
                        } else {
                            return "Деление на 0";
                        }
                        evaluation.push(result);
                    }
                    // степень
                    case 6 -> {
                        operandA = evaluation.pop();
                        operandB = evaluation.pop();
                        try {
                            opA = Double.parseDouble(operandA);
                            opB = Double.parseDouble(operandB);
                        } catch (NumberFormatException e) {
                            return "Ошибка";
                        }
                        result = Double.toString(Math.pow(opA, opB));
                        evaluation.push(result);
                    }
                }
            }
        }

        //окончательный результат - последнее число стека
        String finalResult = evaluation.pop();

        //обработка ошибок для ответа, который не является допустимым числом
        try{
            //отформатируем окончательный результат с двумя десятичными знаками
            Double finalResultDouble = Double.parseDouble(finalResult);
            finalResult = twoDecimals.format(finalResultDouble);
        }
        catch(NumberFormatException e) {
            return "Ошибка!";
        }

        //если стек теперь пуст, обработка прошла успешно
        if(evaluation.isEmpty()) {
            return finalResult;
        }
        else {
            //если стек не пуст, возникла проблема
            return "Ошибка!";
        }
    }

    // метод конвертирует инфиксное выражение в постфиксное
    public static CalcQueue<String> infixToPostfix(ArrayList<String> infixExpression) {
        CalcQueue<String> outputQueue = new CalcQueue<>();
        Stack<String> pendingOperators = new Stack<String>();

        //используется для проверки, является ли символ оператором
        String operators = "()+-/%*d^";

        //для каждого символа в массиве символов
        for (String s : infixExpression) {
            //если символ не является оператором (т. е. операндом), он добавляется в очередь
            if (!operators.contains(s)) {
                outputQueue.enqueue(s);
            } else {
                //если операнд является закрывающей скобкой, продолжаем
                //извлекать и ставить в очередь символы из стека
                //пока не дойдем до конца стека или открытой скобки
                if (s.equals(")")) {
                    while (!pendingOperators.isEmpty() && !pendingOperators.peek().equals("(")) {

                        outputQueue.enqueue(pendingOperators.pop());
                    }
                    //избавляемся от открывающей скобки
                    pendingOperators.pop();
                } else {
                    //если символ является операндом, но не закрывающей скобкой
                    // продолжаем извлекать из стека, пока не достигнем оператора с более низким приоритетом
                    while (!pendingOperators.isEmpty() && !pendingOperators.peek().equals("(")) {
                        //Метод приоритета возвращает целочисленное представление приоритета операторов с точки зрения порядка операций
                        if (precedence(pendingOperators.peek()) >= precedence(s)) {
                            outputQueue.enqueue(pendingOperators.pop());
                        } else {
                            break;
                        }
                    }
                    //помещаем текущий символ в стек
                    pendingOperators.push(s);
                }
            }
        }

        //если стек все еще содержит символы, добавляем их в очередь вывода
        while(!pendingOperators.isEmpty()) {
            outputQueue.enqueue(pendingOperators.pop());
        }

        return outputQueue;
    }

    ArrayList<String> inputToElements(String input){
        ArrayList<String> elements = new ArrayList<>();
        String temp = "";
        int i = 0;
        Character s;
        String operators = "()+-/%*d^";
        while(i < input.length())
        {
            s = input.charAt(i);

            if(operators.indexOf(s) != -1){
                if(!Objects.equals(temp, "")){
                    elements.add(temp);
                    temp = "";
                }
                elements.add(String.valueOf(s));
            }else{
                temp += String.valueOf(s);
            }
            i++;
        }
        if(!Objects.equals(temp, "")){
            elements.add(temp);
        }
        return elements;
    }

    // метод определяет приоритет операторов
    static int precedence(String operator) {
        if(Objects.equals(operator, "+") || Objects.equals(operator, "-"))
            return 0;
        if(Objects.equals(operator, "*") || Objects.equals(operator, "/")  || Objects.equals(operator, "d"))
            return 1;
        if(Objects.equals(operator, "%"))
            return 2;
        if(Objects.equals(operator, "(") || Objects.equals(operator, ")"))
            return 3;
        return 4;
    }
}
