package service;

import po.Equation;
import po.Formula;
import po.Fraction;
import po.Process;
import util.Constants;


import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: Mexinz
 * @Date: 2019/10/15
 */
public class CalculateService {

    private FormulaFactory formulaFactory = new FormulaFactory(Constants.max);

    public static ExecutorService es = GenerateService.es;

    public List<Fraction> calculateOfList (List<String> formulas) {
        List<Equation> equations = new ArrayList<>(formulas.size());
        List<Future<Equation>> futures = new ArrayList<>(formulas.size());
        for (String formula : formulas) {
            Future<Equation> future = es.submit(new Calculation(formula));
            futures.add(future);
        }
        Equation equation;
        Future<Equation> future;
        for (int i = 0; i < futures.size(); i++) {
            future = futures.get(i);
            try {
                equation = future.get();
                if (equation != null && !equations.contains(equation)) {
                    equations.add(equation);
                } else {
                    Formula formula = null;
                    while (equation == null || equations.contains(equation)) {
                        formula = formulaFactory.call();
                        equation = new Calculation(formula.toString()).call();
                    }
                    Collections.replaceAll(formulas,formulas.get(i),formula.toString());
                    equations.add(equation);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        List<Fraction> resultList = new ArrayList<>(equations.size());
        for (Equation e : equations) {
            List<Process> processes = e.getProcesses();
            resultList.add(processes.get(processes.size() - 1).getAnswer());
        }
        return resultList;
    }

    /**
     * 无括号的计算
     * @param s 无括号的式子
     * @return 式子和计算过程
     *//*
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
                b = Fraction.stringToFraction(it.next());
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
                stack.push(answer.toString());
            } else {
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
                    answer = a.subtract(b);
                    if (answer.getNumerator() < 0) {
                        answer = null;
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

*/

}
