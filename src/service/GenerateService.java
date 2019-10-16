package service;

import po.Formula;
import util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author: Mexinz
 * @Date: 2019/10/8
 */
public class GenerateService  {

    public static ExecutorService es = Executors.newCachedThreadPool();


    public List<String> getFormulaStirngList(List<Formula> formulas) {
        List<String> formulaStrings = new ArrayList<>(formulas.size());
        for (Formula f : formulas) {
            formulaStrings.add(f.toString());
        }
        return formulaStrings;
    }

    public List<Formula> getFormulaList(Map<String,Integer> paramMap) {
        Integer num = paramMap.get("-n");
        Integer max = paramMap.get("-r");
        Constants.max = max;
        List<Formula> formulas = new ArrayList<>(num);
        List<Future<Formula>> futures = new ArrayList<>(num);
        for (int i = 0; i < num ; i ++) {
            Future<Formula> future = es.submit(new FormulaFactory(max));
            futures.add(future);
        }
     /*   es.shutdown();*/
        for (Future<Formula> future : futures) {
            try {
                formulas.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return formulas;
    }

}
