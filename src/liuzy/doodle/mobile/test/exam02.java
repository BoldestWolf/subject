package liuzy.doodle.mobile.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class exam02 {

	class ConstantField {
		// 数字数量
		public static final int AMOUNT = 100;
		// 高分
		public static final String HIGHSCORE = "highscore";
		// 低分
		public static final String LOWSCORE = "lowscore";
		// 精度
		public static final double EPS = 1e-10;
	}

	private static int score_of_winner(int[] scores) {
		Map<String, Integer> scoresMap = getScoresMap(scores);
		// 如果低分没有说实话，则挑战失败
		if (!isLegal(scoresMap.get(ConstantField.LOWSCORE))) {
			return scoresMap.get(ConstantField.HIGHSCORE);
		}
		// 如果是低分为质数||高分为质数||高分有三个因数||高分没有说实话，则挑战成功
		if (isPrime(scoresMap.get(ConstantField.LOWSCORE)) || isPrime(scoresMap.get(ConstantField.HIGHSCORE))
				|| hasThreeFactors(scoresMap.get(ConstantField.HIGHSCORE))
				|| !isLegal(scoresMap.get(ConstantField.HIGHSCORE))) {
			return scoresMap.get(ConstantField.LOWSCORE);
		}

		return scoresMap.get(ConstantField.HIGHSCORE);
	}

	private static boolean hasThreeFactors(Integer score) {
		Double doubleNum = Math.pow((double) score, 1.0 / 3);
		int intNum = doubleNum.intValue();
		if (doubleNum - intNum < ConstantField.EPS) {
			return true;
		}
		return false;
	}

	private static boolean isLegal(Integer score) {
		List<Integer> bigFactors = new ArrayList<Integer>();
		List<Integer[]> factorList = getAllFactors(score);
		for (Integer[] factorArr : factorList) {
			if (factorArr[0] < ConstantField.AMOUNT && factorArr[1] < ConstantField.AMOUNT) {
				return true;
			} else {
				if (factorArr[0] < ConstantField.AMOUNT) {
					bigFactors.add(factorArr[0]);
				}
				if (factorArr[1] < ConstantField.AMOUNT) {
					bigFactors.add(factorArr[0]);
				}
			}
		}
		if (bigFactors.size() > 0) {
			for (int j = 0; j < bigFactors.size(); j++) {
				isLegal(bigFactors.get(j));
			}
		}
		return false;
	}

	private static boolean isPrime(int score) {
		if (score < 2 || score % 2 == 0)
			return false;
		if (score == 2)
			return true;
		for (int i = 3; i < Math.sqrt(score); i += 2) {
			if (score % i == 0) {
				return false;
			}
		}
		return true;
	}

	private static List<Integer[]> getAllFactors(int score) {
		List<Integer[]> factorList = new ArrayList<Integer[]>();
		for (int i = 2; i < Math.sqrt(score) && i <= ConstantField.AMOUNT; i++) {
			if (score % i == 0) {
				factorList.add(new Integer[] { i, score / i });
			}
		}
		if (score <= ConstantField.AMOUNT)
			factorList.add(new Integer[] { 1, score });
		return factorList;
	}

	/**
	 * 获取高低分的map
	 * 
	 * @param scores
	 * @return
	 */
	private static Map<String, Integer> getScoresMap(int[] scores) {
		Map<String, Integer> scoresMap = new HashMap<String, Integer>();
		scoresMap.put(ConstantField.HIGHSCORE, scores[0] > scores[1] ? scores[0] : scores[1]);
		scoresMap.put(ConstantField.LOWSCORE, scores[0] < scores[1] ? scores[0] : scores[1]);
		return scoresMap;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("输入游戏中选手1和2获得的分数：");
		String scoresString = sc.nextLine();
		String[] scoresArr = scoresString.split(" ");
		int[] scores = new int[scoresArr.length];
		for (int i = 0; i < scoresArr.length; i++) {
			scores[i] = Integer.parseInt(scoresArr[i]);
		}

		System.out.println("获胜选手分数：" + score_of_winner(scores));
	}

}
