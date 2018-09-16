package liuzy.doodle.mobile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Doodle Mobile
 * ���������
 * 
 * @author liuzy
 *
 */
public class exam02 {

	/**
	 * ����
	 * 
	 * @author liuzy
	 *
	 */
	class ConstantField {
		// ������Ŀ����Ŀ�и�����
		public static final int AMOUNT = 100;
		// �߷�
		public static final String HIGHSCORE = "highscore";
		// �ͷ�
		public static final String LOWSCORE = "lowscore";
	}

	/**
	 * ��ȡʤ���߷���
	 * 
	 * @param scores
	 * 		ѡ��1��ѡ��2�ķ����������飩
	 * @return
	 * 		ʤ���߷���
	 */
	private static int score_of_winner(int[] scores) {
		Map<String, Integer> scoresMap = getScoresMap(scores);
		// ��ȡ�߷ַ���
		Set<Set<Integer>> highScoreSet = new HashSet<Set<Integer>>();
		Set<Integer> highSet = new HashSet<Integer>();
		highSet.add(1);
		highSet.add(scoresMap.get(ConstantField.HIGHSCORE));
		if (scoresMap.get(ConstantField.HIGHSCORE) <= ConstantField.AMOUNT) {
			highScoreSet.add(highSet);
		}
		getScoreSet(scoresMap.get(ConstantField.HIGHSCORE), highScoreSet, highSet);
		// ��ȡ�ͷַ���
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
	 * ��ȡʤ���߱�ʶ
	 * 
	 * @param highScoreSet
	 * 		�߷ֽ����
	 * @param lowScoreSet
	 * 		�ͷֽ����
	 * @return
	 * 		ConstantField.HIGHSCORE or ConstantField.LOWSCORE
	 */
	private static String getWinnerFlagBySet(Set<Set<Integer>> highScoreSet, Set<Set<Integer>> lowScoreSet) {
		// �ͷ��߼��㷨����������
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
	 * ��ȡ������
	 * 
	 * @param score
	 * 		���ڵ����
	 * @param resultSet
	 * 		�ɼ������
	 * @param headHashSet
	 * 		���ڵ�����
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
	 * ��ȡ���µ�set 
	 * ��ʽ�� newSet = headSet+first+second-first*second
	 * *****************************************************************************
	 * Ϊ��˵����������븸�ڵ��������ظ����ȱ��������ظ�����Ŀ�������б�������ȷ��
	 * ע�⣺��Ҫͬ����ȷ�ϣ�ײ�����ȱ����ȵ����𣩣����ｫ���ֽ��������Ϊ�ȱ�
	 * *****************************************************************************
	 * @param headHashSet
	 * 		���ڵ�����
	 * @param first
	 * 		ǰ�ý��
	 * @param second
	 * 		���ý��
	 * @param score
	 * 		���ڵ������Ǽ���
	 * @return
	 * 		���½����
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
	 * ��ȡ�ߵͷֵ�map
	 * 
	 * @param scores
	 * 		ѡ��1��ѡ��2�ķ����������飩
	 * @return
	 * 		ѡ��1��ѡ��2�ķ�������Map��
	 */
	private static Map<String, Integer> getScoresMap(int[] scores) {
		Map<String, Integer> scoresMap = new HashMap<String, Integer>();
		scoresMap.put(ConstantField.HIGHSCORE, scores[0] > scores[1] ? scores[0] : scores[1]);
		scoresMap.put(ConstantField.LOWSCORE, scores[0] < scores[1] ? scores[0] : scores[1]);
		return scoresMap;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter player 1 and player 2 score��");
		String scoresString = sc.nextLine();
		String[] scoresArr = scoresString.split(" ");
		int[] scores = new int[scoresArr.length];
		for (int i = 0; i < scoresArr.length; i++) {
			scores[i] = Integer.parseInt(scoresArr[i]);
		}
		System.out.println("Score of winner��" + score_of_winner(scores));
	}
}
