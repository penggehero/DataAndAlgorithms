package AimToOffer;

import java.util.HashSet;

/**
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。
 * 例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。  
 * <p>
 * 示例 1：
 * <p>
 * 输入：[3,4,5,1,2]
 * 输出：1
 * 示例 2：
 * <p>
 * 输入：[2,2,2,0,1]
 * 输出：0
 */
public class Offer11旋转数组的最小数字 {
    /**
     * 本人的暴力法，哈哈
     *
     * @param numbers
     * @return
     */
    public int minArray2(int[] numbers) {
        int pre = 0, index = 1;
        while (index < numbers.length) {
            if (numbers[index] < numbers[pre]) {
                return numbers[index];
            }
            pre = index;
            index++;
        }
        return numbers[0];
    }


    /**
     * 二分查找，官方推荐
     *
     * @param numbers
     * @return
     */
    public int minArray(int[] numbers) {
        int low = 0;
        int high = numbers.length - 1;
        while (low < high) {
            int pivot = low + (high - low) / 2;
            if (numbers[pivot] < numbers[high]) {
                high = pivot;
            } else if (numbers[pivot] > numbers[high]) {
                low = pivot + 1;
            } else {
                high -= 1;
            }
        }
        return numbers[low];
    }


}
