#### 方法一：广度优先搜索

**思路和算法**

我们将行坐标和列坐标数位之和大于 `k` 的格子看作障碍物，那么这道题就是一道很传统的搜索题目，我们可以使用广度优先搜索或者深度优先搜索来解决它，本文选择使用广度优先搜索的方法来讲解。

那么如何计算一个数的数位之和呢？我们只需要对数 `x` 每次对 `10` 取余，就能知道数 `x` 的个位数是多少，然后再将 `x` 除 `10`，这个操作等价于将 `x` 的十进制数向右移一位，删除个位数（类似于二进制中的 `>>` 右移运算符），不断重复直到 `x` 为 `0` 时结束。

同时这道题还有一个隐藏的优化：我们在搜索的过程中搜索方向可以缩减为向右和向下，而不必再向上和向左进行搜索。如下图，我们展示了 `16 * 16` 的地图随着限制条件 `k` 的放大，可行方格的变化趋势，每个格子里的值为行坐标和列坐标的数位之和，蓝色方格代表非障碍方格，即其值小于等于当前的限制条件 `k`。我们可以发现随着限制条件 `k` 的增大，`(0, 0)` 所在的蓝色方格区域内新加入的非障碍方格都可以由上方或左方的格子移动一步得到。而其他不连通的蓝色方格区域会随着 `k` 的增大而连通，且连通的时候也是由上方或左方的格子移动一步得到，因此我们可以将我们的搜索方向缩减为向右或向下。

![img](https://pic.leetcode-cn.com/6c80ef7224efa9a8f3589afdc83681fa50b2b231ad5f188ff2e5a92d4a6db47c-%E5%B9%BB%E7%81%AF%E7%89%871.JPG)

![img](https://pic.leetcode-cn.com/a5addecac64254ee7f899819d5c4e65117b786c747975de1d2d9cd2f309aab92-%E5%B9%BB%E7%81%AF%E7%89%872.JPG)

![img](https://pic.leetcode-cn.com/7eab5519c96fef9d4b06a413d9ad4ab18dc47012c0db0b9b60947f60229bb8e1-%E5%B9%BB%E7%81%AF%E7%89%873.JPG)



```java
class Solution {
    public int movingCount(int m, int n, int k) {
        if (k == 0) {
            return 1;
        }
        Queue<int[]> queue = new LinkedList<int[]>();
        // 向右和向下的方向数组
        int[] dx = {0, 1};
        int[] dy = {1, 0};
        boolean[][] vis = new boolean[m][n];
        queue.offer(new int[]{0, 0});
        vis[0][0] = true;
        int ans = 1;
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int x = cell[0], y = cell[1];
            for (int i = 0; i < 2; ++i) {
                int tx = dx[i] + x;
                int ty = dy[i] + y;
                if (tx < 0 || tx >= m || ty < 0 || ty >= n || vis[tx][ty] || get(tx) + get(ty) > k) {
                    continue;
                }
                queue.offer(new int[]{tx, ty});
                vis[tx][ty] = true;
                ans++;
            }
        }
        return ans;
    }

    private int get(int x) {
        int res = 0;
        while (x != 0) {
            res += x % 10;
            x /= 10;
        }
        return res;
    }
}
```

#### 方法二：递推

**思路**

考虑到方法一提到搜索的方向只需要朝下或朝右，我们可以得出一种递推的求解方法。

**算法**

定义 `vis[i][j]` 为 `(i, j)` 坐标是否可达，如果可达返回 `1`，否则返回 `0`。

首先 `(i, j)` 本身需要可以进入，因此需要先判断 `i` 和 `j` 的数位之和是否大于 `k` ，如果大于的话直接设置 `vis[i][j]` 为不可达即可。

否则，前面提到搜索方向只需朝下或朝右，因此 `(i, j)` 的格子只会从 `(i - 1, j)` 或者 `(i, j - 1)` 两个格子走过来（不考虑边界条件），那么 `vis[i][j]` 是否可达的状态则可由如下公式计算得到：

*vis*[*i*][*j*]=*vis*[*i*−1][*j*] or *vis*[*i*][*j*−1]

即只要有一个格子可达，那么 `(i, j)` 这个格子就是可达的，因此我们只要遍历所有格子，递推计算出它们是否可达然后用变量 `ans` 记录可达的格子数量即可。

初始条件 `vis[i][j] = 1` ，递推计算的过程中注意边界的处理。

**复杂度分析**

- 时间复杂度：O(mn)*O*(*m**n*)，其中 `m` 为方格的行数， `n` 为方格的列数。一共有 O(mn)*O*(*m**n*) 个状态需要计算，每个状态递推计算的时间复杂度为 O(1)*O*(1)，所以总时间复杂度为 O(mn)*O*(*m**n*)。
- 空间复杂度：O(mn)*O*(*m**n*)，其中 `m` 为方格的行数，`n` 为方格的列数。我们需要 O(mn)*O*(*m**n*) 大小的结构来记录每个位置是否可达。

```java
class Solution {
    public int movingCount(int m, int n, int k) {
        if (k == 0) {
            return 1;
        }
        boolean[][] vis = new boolean[m][n];
        int ans = 1;
        vis[0][0] = true;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if ((i == 0 && j == 0) || get(i) + get(j) > k) {
                    continue;
                }
                // 边界判断
                if (i - 1 >= 0) {
                    vis[i][j] |= vis[i - 1][j];
                }
                if (j - 1 >= 0) {
                    vis[i][j] |= vis[i][j - 1];
                }
                ans += vis[i][j] ? 1 : 0;
            }
        }
        return ans;
    }

    private int get(int x) {
        int res = 0;
        while (x != 0) {
            res += x % 10;
            x /= 10;
        }
        return res;
    }
}
```

**复杂度分析**

- 时间复杂度：O(mn)*O*(*m**n*)，其中 `m` 为方格的行数， `n` 为方格的列数。一共有 O(mn)*O*(*m**n*) 个状态需要计算，每个状态递推计算的时间复杂度为 O(1)*O*(1)，所以总时间复杂度为 O(mn)*O*(*m**n*)。
- 空间复杂度：O(mn)*O*(*m**n*)，其中 `m` 为方格的行数，`n` 为方格的列数。我们需要 O(mn)*O*(*m**n*) 大小的结构来记录每个位置是否可达。

