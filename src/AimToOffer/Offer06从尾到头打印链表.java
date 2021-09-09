package AimToOffer;

import entity.ListNode;

import java.util.Stack;

/**
 * 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：head = [1,3,2]
 * 输出：[2,3,1]
 */
public class Offer06从尾到头打印链表 {

    /**
     * 使用栈
     *
     * @param head
     * @return
     */
    public int[] reversePrint1(ListNode head) {
        Stack<Integer> stack = new Stack<>();
        while (head != null) {
            stack.add(head.val);
            head = head.next;
        }
        int[] res = new int[stack.size()];
        int i = 0;
        while (!stack.isEmpty()) {
            res[i++] = stack.pop();
        }
        return res;
    }


    private int size = 0;
    private int[] arr;

    /**
     * 用递归
     * @param head
     * @return
     */
    public int[] reversePrint2(ListNode head) {
        helper(head, 0);
        return arr;
    }

    private void helper(ListNode head, int i) {
        if (head == null) {
            size = i;
            arr = new int[size];
            return;
        }
        helper(head.next, i + 1);
        arr[size - i - 1] = head.val;
    }


}
