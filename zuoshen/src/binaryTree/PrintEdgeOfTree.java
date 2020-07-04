package binaryTree;

public class PrintEdgeOfTree {
    /**
     * 按照标准1来打印边界
     * @param head
     */
    public void printEdgeByType1(Node head){
        int height = getHeight(head, 0);
        Node[][] edgeMap = new Node[height][2];

        setEdgeMap(head, 0, edgeMap);

        for (int i = 0; i < height; i++){
            System.out.println(edgeMap[i][0].value); // 打印左边界
        }

        printLeaf(head, 0, edgeMap); // 打印非边界叶子节点

        for (int i = 0; i < height; i++){
            if (edgeMap[i][0] != edgeMap[i][1])
                System.out.println(edgeMap[i][1].value); // 打印右边界且不是左边界
        }
    }

    /**
     *
     * @param head
     * @param curDeep 不包括head的深度
     * @return
     */
    private int getHeight(Node head, int curDeep){
        if (head == null){
            return curDeep;
        }

        return Math.max(getHeight(head.left, curDeep+1), getHeight(head.right, curDeep+1));
    }

    /**
     * 这个方法很简洁，实现了得到一个树的所有层中最左和最右的节点，如果一层只有一个节点
     * 则既是最左也是最右
     * @param head
     * @param curDeep
     * @param edgeMap
     */
    private void setEdgeMap(Node head, int curDeep, Node[][] edgeMap){
        if (head == null)
            return;

        edgeMap[curDeep][0] = edgeMap[curDeep][0] == null ? head : edgeMap[curDeep][0];
        edgeMap[curDeep][1] = head;

        setEdgeMap(head.left, curDeep+1, edgeMap);
        setEdgeMap(head.right, curDeep+1, edgeMap);
    }

    /**
     * 打印出非边界节点的叶子节点
     * @param head
     * @param curDeep
     * @param edgeMap
     */
    private void printLeaf(Node head, int curDeep, Node[][] edgeMap){
        if (head == null){
            return;
        }

        if (head.left == null && head.right == null && edgeMap[curDeep][0] != head && edgeMap[curDeep][1] != head){
            System.out.println(head.value);
        }
        printLeaf(head.left, curDeep+1,edgeMap);
        printLeaf(head.right,curDeep+1,edgeMap);
    }
}
