package main;

import po.Fraction;
import service.CalculateService;
import service.GenerateService;
import service.JudgeService;
import util.FileUtil;
import util.FractionUtil;
import util.StringUtil;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author: Mexinz
 * @Date: 2019/10/8
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("------------欢迎使用小学四则运算题目自动生成程序------------");

            System.out.println("该程序有两种使用方法\n使用方法一：");
            System.out.println("-n：生成题目的个数");
            System.out.println("-r：题目中数值（自然数、真分数和真分数分母）小于这个数");
            System.out.println("示例：\"-n 10 -r 10\"为生成10个题目，题目中数值 < 10");
            System.out.println("使用方法二：");
            System.out.println("-e + 题目文件文件名.txt + -a + 答案文件文件名.txt");
            System.out.println("统计结果会输出到程序运行目录下Grade.txt中，格式如下");
            System.out.println("Correct: 5 (1, 3, 5, 7, 9)\nWrong: 5 (2, 4, 6, 8, 10)");
            System.out.println("括号前数字表示对/错的题目数量，括号内为对应的题号");
            System.out.println("请输入指令：");
            String[] params = scanner.nextLine().split(" ");
            if ("-n".equals(params[0])) {
                Map<String,Integer> paramMap = StringUtil.getParamMap1(params);
                GenerateService generateService = new GenerateService();
                List<String> formulas = generateService.getFormulaStirngList(generateService.getFormulaList(paramMap));
                List<Fraction> answers = new CalculateService().calculateOfList(formulas);
                GenerateService.es.shutdown();
                FileUtil.writeFormulas(FractionUtil.getRealFormulas(formulas));
                FileUtil.writeAnswers(FractionUtil.getRealFractions(answers));
                System.exit(0);
            } else if ("-e".equals(params[0])) {
                Map<String,String> paramMap = StringUtil.getParamMap2(params);
                JudgeService judgeService = new JudgeService();
                boolean isSuccess = judgeService.judge(FileUtil.readFiles(paramMap.get("-e")));
                System.out.println(isSuccess);
            }  else {
                System.out.println("参数输入格式错误");
            }
    }
}
