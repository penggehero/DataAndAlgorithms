package AimToOffer;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 找出数组中重复的数字。
 * <p>
 * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。
 * 请找出数组中任意一个重复的数字。
 * 示例 1：
 * 输入：
 * [2, 3, 1, 0, 2, 5, 3]
 * 输出：2 或 3
 * 限制：
 * 2 <= n <= 10000
 */

public class Offer03数组中重复的数字 {

    public static void main(String[] args) {
        int[] arrays = {2, 3, 1, 0, 2, 5, 3};
        // 原地交换
        System.out.println(findRepeatNumber2(arrays));

    }

    /**
     * 原地交换 （排序的思想）
     * <p>
     * 选择一个数 改值为多少 则放到对应的下标下，如果改值为2 则放到下标为3的位置
     * 如果发现这个数在对应下标的值相等则返回，不等 则交换2个值
     *
     * @param nums
     * @return
     */
    public static int findRepeatNumber2(int[] nums) {
        int i = 0;
        while (i < nums.length) {
            // 如果nums[i]和下标i相同，则i++
            if (nums[i] == i) {
                i++;
                continue;
            }

            // 如果nums[i]等于nums[nums[i]]，说明nums[i] 重复直接返回
            if (nums[nums[i]] == nums[i])
                return nums[i];

            // 交换nums[i]和nums[nums[i]]的值
            int tmp = nums[i];
            nums[i] = nums[tmp];
            nums[tmp] = tmp;

        }
        return -1;
    }


    /**
     * 哈希表
     *
     * @param nums
     * @return
     */
    public int findRepeatNumber1(int[] nums) {
        HashSet<Integer> hashSet = new HashSet<>();
        for (int num : nums) {
            if (hashSet.contains(num))
                return num;
            else
                hashSet.add(num);
        }
        return -1;
    }

}
