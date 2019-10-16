package service;

import po.Fraction;
import util.FileUtil;
import util.FractionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mexinz
 * @Date: 2019/10/16
 */
public class JudgeService {

    private CalculateService calculateService = new CalculateService();

    /**
     * 批改
     * @param formulas 给定的题目List
     * @return 成功写入文件返回true
     */
    public boolean judge(List<String> formulas) {
        List<String> equations = new ArrayList<>(formulas.size());
        List<String> results = new ArrayList<>(formulas.size());
        String[] formulaAndResult;
        for (String f : formulas) {
            formulaAndResult = f.split(" = ");
            int start = formulaAndResult[0].indexOf(".") + 2;
            String formula = formulaAndResult[0].substring(start);
            if (formula.contains("'")){
                String[] nums = formula.split(" ");
            }
            equations.add(formula);
            results.add(formulaAndResult[1]);
        }
        List<String> trueResults = FractionUtil.getRealFractions(calculateService.calculateOfList(equations));
        List<Integer> corrects = new ArrayList<>();
        List<Integer> wrongs = new ArrayList<>();
        for (int i = 0; i < results.size() ; i++) {
            if (results.get(i).equals(trueResults.get(i))) {
                corrects.add(i + 1);
            } else {
                wrongs.add(i + 1);
            }
        }
        return FileUtil.writeGrades(corrects,wrongs);
    }
}
