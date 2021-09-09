package AimToOffer;

/**
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
 * <p>
 * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
 * <p>
 * 示例 1：
 * <p>
 * 输入：n = 2
 * 输出：2
 * <p>
 * 示例 2：
 * <p>
 * 输入：n = 7
 * 输出：21
 * <p>
 * 示例 3：
 * <p>
 * 输入：n = 0
 * 输出：1
 */
public class Offer10青蛙跳台阶问题II {
    public int numWays(int n) {
        if (n == 1 || n == 2) {
            return n;
        }
        int f1 = 1, f2 = 2, res = 1;
        for (int i = 3; i <= n; i++) {
            res = (f1 + f2) % 1000000007;
            f1 = f2;
            f2 = res;
        }
        return res;
    }
}
