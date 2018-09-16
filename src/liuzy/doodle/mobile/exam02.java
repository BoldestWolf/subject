package liuzy.doodle.mobile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Doodle Mobile
 * 笔试题二答案
 * 
 * @author liuzy
 *
 */
public class exam02 {

	/**
	 * 常量
	 * 
	 * @author liuzy
	 *
	 */
	class ConstantField {
		// 数字数目（题目中给出）
		public static final int AMOUNT = 100;
		// 高分
		public static final String HIGHSCORE = "highscore";
		// 低分
		public static final String LOWSCORE = "lowscore";
	}

	/**
	 * 获取胜利者分数
	 * 
	 * @param scores
	 * 		选手1和选手2的分数集（数组）
	 * @return
	 * 		胜利者分数
	 */
	private static int score_of_winner(int[] scores) {
		Map<String, Integer> scoresMap = getScoresMap(scores);
		// 获取高分方案
		Set<Set<Integer>> highScoreSet = new HashSet<Set<Integer>>();
		Set<Integer> highSet = new HashSet<Integer>();
		highSet.add(1);
		highSet.add(scoresMap.get(ConstantField.HIGHSCORE));
		if (scoresMap.get(ConstantField.HIGHSCORE) <= ConstantField.AMOUNT) {
			highScoreSet.add(highSet);
		}
		getScoreSet(scoresMap.get(ConstantField.HIGHSCORE), highScoreSet, highSet);
		// 获取低分方案
		Set<Set<Integer>> lowScoreSet = new HashSet<Set<Integer>>();
		Set<Integer> lowSet = new HashSet<Integer>();
		lowSet.add(1);
		lowSet.add(scoresMap.get(ConstantField.LOWSCORE));
		if (scoresMap.get(ConstantField.LOWSCORE) <= ConstantField.AMOUNT) {
			lowScoreSet.add(lowSet);
		}
		getScoreSet(scoresMap.get(ConstantField.LOWSCORE), lowScoreSet, lowSet);
		String winner = getWinnerFlagBySet(highScoreSet, lowScoreSet);
		return scoresMap.get(winner);
	}

	/**
	 * 获取胜利者标识
	 * 
	 * @param highScoreSet
	 * 		高分结果集
	 * @param lowScoreSet
	 * 		低分结果集
	 * @return
	 * 		ConstantField.HIGHSCORE or ConstantField.LOWSCORE
	 */
	private static String getWinnerFlagBySet(Set<Set<Integer>> highScoreSet, Set<Set<Integer>> lowScoreSet) {
		// 低分者计算法分数犯错误
		if (lowScoreSet.size() == 0) {
			return ConstantField.HIGHSCORE;
		}

		Iterator<Set<Integer>> lowSetIt = lowScoreSet.iterator();
		while (lowSetIt.hasNext()) {
			Set<Integer> lowScoreScheme = lowSetIt.next();
			Iterator<Integer> lowSchemeIt = lowScoreScheme.iterator();
			boolean pass = true;
			while (lowSchemeIt.hasNext()) {
				Iterator<Set<Integer>> highSetIt = highScoreSet.iterator();
				Integer lowscore = lowSchemeIt.next();
				int countTimes = 0;
				while (lowscore != 1 && highSetIt.hasNext()) {
					Set<Integer> highScoreScheme = highSetIt.next();
					Iterator<Integer> highSchemeIt = highScoreScheme.iterator();
					while (highSchemeIt.hasNext()) {
						Integer highScore = highSchemeIt.next();
						if (lowscore == highScore) {
							pass = false;
							break;
						}
					}
					if (pass == true) {
						if (countTimes == 1) {
							return ConstantField.HIGHSCORE;
						}
						countTimes++;
					}
				}
			}
		}
		return ConstantField.LOWSCORE;
	}

	/**
	 * 获取分数集
	 * 
	 * @param score
	 * 		父节点分数
	 * @param resultSet
	 * 		成绩结果集
	 * @param headHashSet
	 * 		父节点结果集
	 */
	private static void getScoreSet(Integer score, Set<Set<Integer>> resultSet, Set<Integer> headHashSet) {
		for (int first = 2; first < Math.sqrt(score); first++) {
			if (score % first == 0) {
				int second = score / first;
				if (first < ConstantField.AMOUNT && second < ConstantField.AMOUNT) {
					Set<Integer> newResultSet = getNewSet(headHashSet, first, second, score);
					if (newResultSet != null) {
						resultSet.add(newResultSet);
						getScoreSet(first, resultSet, newResultSet);
						getScoreSet(second, resultSet, newResultSet);
					}
				}
				if (first > ConstantField.AMOUNT || second > ConstantField.AMOUNT) {
					Set<Integer> newResultSet = getNewSet(headHashSet, first, second, score);
					if (newResultSet != null) {
						getScoreSet(first, resultSet, newResultSet);
						getScoreSet(second, resultSet, newResultSet);
					}

				}
			}
		}
	}

	/**
	 * 获取最新的set 
	 * 公式： newSet = headSet+first+second-first*second
	 * *****************************************************************************
	 * 为空说明结果集中与父节点结果集有重复（踩爆的球不能重复，题目（需求）中表述不明确）
	 * 注意：需要同需求确认（撞到、踩爆、踩到区别），这里将三种结果都处理为踩爆
	 * *****************************************************************************
	 * @param headHashSet
	 * 		父节点结果集
	 * @param first
	 * 		前置结果
	 * @param second
	 * 		后置结果
	 * @param score
	 * 		父节点结果（非集）
	 * @return
	 * 		最新结果集
	 */
	private static Set<Integer> getNewSet(Set<Integer> headHashSet, int first, int second, Integer score) {
		Set<Integer> newResultSet = new HashSet<Integer>();
		Iterator<Integer> it = headHashSet.iterator();
		while (it.hasNext()) {
			int next = it.next();
			if (next == first || next == second) {
				return null;
			}
			newResultSet.add(next);
		}
		newResultSet.add(first);
		newResultSet.add(second);
		newResultSet.remove(score);
		return newResultSet;
	}

	/**
	 * 获取高低分的map
	 * 
	 * @param scores
	 * 		选手1和选手2的分数集（数组）
	 * @return
	 * 		选手1和选手2的分数集（Map）
	 */
	private static Map<String, Integer> getScoresMap(int[] scores) {
		Map<String, Integer> scoresMap = new HashMap<String, Integer>();
		scoresMap.put(ConstantField.HIGHSCORE, scores[0] > scores[1] ? scores[0] : scores[1]);
		scoresMap.put(ConstantField.LOWSCORE, scores[0] < scores[1] ? scores[0] : scores[1]);
		return scoresMap;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter player 1 and player 2 score：");
		String scoresString = sc.nextLine();
		String[] scoresArr = scoresString.split(" ");
		int[] scores = new int[scoresArr.length];
		for (int i = 0; i < scoresArr.length; i++) {
			scores[i] = Integer.parseInt(scoresArr[i]);
		}
		System.out.println("Score of winner：" + score_of_winner(scores));
	}
}
