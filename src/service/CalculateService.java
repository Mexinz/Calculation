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

}
