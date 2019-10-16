package service;

import po.Equation;
import po.Fraction;
import po.Process;
import util.FractionUtil;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * @Author: Mexinz
 * @Date: 2019/10/16
 */
public class Calculation implements Callable<Equation> {

    private String formula;

    public Calculation(String formula) {
        this.formula = formula;
    }

    @Override
    public Equation call() {
        String[] elements = formula.split(" ");
        if (formula.contains("(")) {
            List<Process> processList = new ArrayList<>();
            Stack<String> calculateStack = new Stack<>();
            Equation equation = new Equation(formula,processList);
            Equation equation1;
            String s;
            List<String> strings = new ArrayList<>();
            for (String element : elements) {
                if (element.endsWith(")")) {
                    strings.add(" " + element);
                    while (!(s = calculateStack.pop()).startsWith("(")){
                        strings.add(" " + s);
                    }
                    strings.add(s);
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i = strings.size()-1 ; i >= 0 ; i--) {
                        stringBuilder.append(strings.get(i));
                    }
                    //去掉头尾括号
                    s = stringBuilder.toString().substring(1,stringBuilder.length() - 1);
                    equation1 = simpleCalculate(s);
                    if (equation1 == null) {
                        return null;
                    } else {
                        equation.getProcesses().addAll(equation1.getProcesses());
                        strings.clear();
                    }
                } else {
                    calculateStack.push(element);
                }
            }
            //处理完括号后栈中剩余的式子转换成字符串
            while (!calculateStack.empty()) {
                strings.add(calculateStack.pop());
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = strings.size() - 1; i > 0; i++) {
                stringBuilder.append(strings.get(i)).append(" ");
            }
            stringBuilder.append(strings.get(0));
            equation1 = simpleCalculate(stringBuilder.toString());
            if (equation1 == null) {
                return null;
            } else {
                equation.getProcesses().addAll(equation1.getProcesses());
                return equation;
            }
        } else {
            return simpleCalculate(formula);
        }
    }

    /**
     * 无括号的计算
     * @param s 无括号的式子
     * @return 式子和计算过程
     */
    private Equation simpleCalculate(String s) {
        List<String> elementList = Arrays.asList(s.split(" "));
        List<Process> processList = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        String symbol;
        Iterator<String> it = elementList.iterator();
        Fraction a;
        Fraction b;
        Fraction answer;
        while (it.hasNext()){
            symbol = it.next();
            if("×".equals(symbol) || "÷".equals(symbol)) {
                a = Fraction.stringToFraction(stack.pop());
                String param = it.next();
                if (param.contains("'")) {
                    param = FractionUtil.toFakeFraction(param);
                }
                b = Fraction.stringToFraction(param);
                switch (symbol) {
                    case "×" :
                        answer = a.multiply(b);
                        break;
                    case "÷" :
                        answer = a.divide(b);
                        break;
                    default:
                        answer = a.multiply(b);
                }
                processList.add(new Process(a,b,symbol,answer));
                if (it.hasNext() || !stack.empty()) {
                    stack.push(answer.toString());
                }
            } else {
                if (symbol.contains("'")) {
                    symbol = FractionUtil.toFakeFraction(symbol);
                }
                stack.push(symbol);
            }
        }
        Stack<String> stack1 = new Stack<>();
        while (!stack.empty()) {
            stack1.push(stack.pop());
        }
        while (!stack1.empty()) {
            a = Fraction.stringToFraction(stack1.pop());
            symbol = stack1.pop();
            b = Fraction.stringToFraction(stack1.pop());
            switch (symbol) {
                case "+" :
                    answer = a.add(b);
                    break;
                case "-" :
                    if (!a.compare(b)) {
                        answer = null;
                    } else {
                        answer = a.subtract(b);
                    }
                    break;
                default :
                    answer = a.add(b);
            }
            if (answer == null) {
                return null;
            }
            processList.add(new Process(a,b,symbol,answer));
            if (!stack1.empty()) {
                stack1.push(answer.toString());
            }
        }
        return new Equation(s,processList);
    }
}
