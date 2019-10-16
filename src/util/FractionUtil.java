package util;

import com.sun.deploy.util.StringUtils;
import po.Fraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Mexinz
 * @Date: 2019/10/16
 */
public class FractionUtil {

    public static List<String> getRealFormulas(List<String> formula) {
        List<String> results = new ArrayList<>(formula.size());
        for (String f : formula) {
            results.add(toRealFormula(f));
        }
        return results;
    }

    public static List<String> getRealFractions(List<Fraction> answers) {
        List<String> results = new ArrayList<>(answers.size());
        for (Fraction f : answers) {
            results.add(toRealFraction(f));
        }
        return results;
    }

    public static String toRealFraction(Fraction fraction) {
        fraction = Fraction.reduceFraction(fraction);
        int numerator = fraction.getNumerator();
        int denominator = fraction.getDenominator();
        if (numerator % denominator == 0) {
            return numerator / denominator + "";
        } else if (numerator > denominator) {
            return numerator / denominator + "'" + numerator % denominator + "/" + denominator;
        } else {
            return fraction.toString();
        }
    }

    public static String toRealFormula(String formula) {
        String[] elements = formula.split(" ");
        for (int i = 0; i < elements.length; i++) {
            if (!Constants.symbolList.contains(elements[i])) {
                elements[i] = toRealFraction(Fraction.stringToFraction(elements[i]));
            }
        }
        return StringUtils.join(Arrays.asList(elements)," ");
    }

    /**
     * 获取最大公因数
     * @param a 数字a
     * @param b 数字b
     * @return ab的最大公因数
     */
    public static int getCommonDivisor(int a, int b) {
        int temp;
        if (a < b) {
            temp = a;
            a = b;
            b = temp;
        }
        while ((temp = a % b) != 0) {
            a = b;
            b = temp;
        }
        return b;
    }

    /**
     * 获取最小公倍数
     * @param a 数字a
     * @param b 数字b
     * @return ab的最小公倍数
     */
    public static int getCommonDenominator(int a, int b) {
        return a * b / getCommonDivisor(a,b);
    }

}
