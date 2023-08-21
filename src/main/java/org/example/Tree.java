package org.example;


public class Tree {
    Node root;

    enum Color {
        RED, BLACK
    }

    public static class Node {
        int value;
        Node leftChildren;
        Node rightChildren;
        Color color;

        public Node(int value) {
            this.value = value;
            this.leftChildren = null;
            this.rightChildren = null;
            this.color = Color.RED;
        }

        @Override
        public String toString() {
            return "Node{" +
                    " value= " + value +
                    " color= " + color +
                    ", left=" + leftChildren +
                    ", right=" + rightChildren +
                    '}';
        }
    }

    /**
     * @param value -значение узла дерева, от потомках которого распечатаются данные
     * @apiNote - метод для вывода информации о потомках заданного по значению содержимого узла
     */
    public void printTree(int value) {
        Node currentNode = findDeep(value, root);
        if (currentNode != null) {
            printTree(currentNode);
        }
    }

    /**
     * @param currentNode - текущая нода, информацию о потомках которой необходимо распечатать
     * @apiNote -метод для вывода информации о потомках заданной ноды
     */
    private void printTree(Node currentNode) {
        System.out.println(currentNode.toString());
        System.out.print(' ');
    }

    /**
     * @param value - значение нового узла дерева
     * @apiNote метод, добавляющий новый узел в дерево и запускающий балансировку дерева после этого
     */
    public void addNode(int value) {
        Node node = new Node(value);
        if (root == null) {
            root = node;
            root.color = Color.BLACK;
        } else {
            Node r = findPlaceRebalance_(root, node);
        }
    }

    /**
     * @param currentParent - текущий узел, который проверяется на возможность добавления дочернего узла.
     * @param newNode       - новый узел для добавления в дерево
     * @apiNote рекурсивный метод поиска подходящего места для нового узла и запускающий ребалансировку в случае его добавления
     */
    private Node findPlaceRebalance_(Node currentParent, Node newNode) {
        if (currentParent.value > newNode.value) {
            if (currentParent.leftChildren == null) currentParent.leftChildren = newNode;
            else {
                currentParent.leftChildren = findPlaceRebalance_(currentParent.leftChildren, newNode);
            }

        } else if (currentParent.value < newNode.value) {
            if (currentParent.rightChildren == null) currentParent.rightChildren = newNode;
            else currentParent.rightChildren = findPlaceRebalance_(currentParent.rightChildren, newNode);
        }

        return reBalanceTree(currentParent);
    }

    /**
     * @param currentNode - текущий узел, который проверяется на необходимость балансировки
     * @return возвращает текущий или новый узел после балансировки узла
     * @apiNote - метод ребалансировки по текущему узлу( родительский узел, левый ребенок, правый ребенок или родительский узел, левый ребенок, левый ребенок левого ребенка), запускающий повороты и смену цвета в  в зависимости от состояния узлов.
     */
    private Node reBalanceTree(Node currentNode) {
        if (currentNode.leftChildren != null && currentNode.rightChildren != null)
            if (currentNode.leftChildren.color.equals(Color.BLACK) && currentNode.rightChildren.color.equals(Color.RED)) { // Если правый ребенок – красный, а левый - черный, то применяем малый правый поворот
                currentNode = rightTinyTurn(currentNode);
            }
        if (currentNode.leftChildren == null && currentNode.rightChildren != null)
            if (currentNode.rightChildren.color.equals(Color.RED)) { // Если правый ребенок – красный, а левый - null(по умолчанию счситается черным), то применяем малый правый поворот
                currentNode = rightTinyTurn(currentNode);
            }
        if (currentNode.leftChildren != null && currentNode.leftChildren.leftChildren != null)
            if (currentNode.leftChildren.color.equals(Color.RED) && currentNode.leftChildren.leftChildren.color.equals(Color.RED)) { // Если левый ребенок красный и его левый ребенок тоже красный – применяем малый левый поворот
                currentNode = leftTinyTurn(currentNode);
            }
        if (currentNode.leftChildren != null && currentNode.rightChildren != null)
            if (currentNode.color.equals(Color.BLACK) && currentNode.rightChildren.color.equals(Color.RED) && currentNode.leftChildren.color.equals(Color.RED)) { // Если родитель черный и оба ребенка красные – делаем смену цвета
                changeColor(currentNode);
            }
        if (root.color.equals(Color.RED)) { // Если корень стал красным – просто перекрашиваем его в черный
            root.color = Color.BLACK;
        }

        return currentNode;
    }

    /**
     * @apiNote метод, реализующий левый малый поворот
     * @param node - текущая вершина дерева, вокруг которой будет совершаться поворот
     * @return - возвращает новую вершину в результате разворота, окрашенную в цвет бывшего родителя
     */
    private Node leftTinyTurn(Node node) {
        Node newTop = node.leftChildren;
        node.leftChildren = newTop.rightChildren;
        newTop.rightChildren = node;
        newTop.color = node.color;
        node.color = Color.RED;
        if (node.value == root.value) root = newTop;

        return newTop;
    }

    /**
     * @apiNote метод, реализующий правый малый поворот
     * @param node - текущая вершина дерева, вокруг которой будет совершаться поворот
     * @return - возвращает новую вершину в результате разворота, окрашенную в цвет бывшего родителя
     */
    private Node rightTinyTurn(Node node) {
        Node newTop = node.rightChildren;
        node.rightChildren = newTop.leftChildren;
        newTop.leftChildren = node;
        newTop.color = node.color;
        node.color = Color.RED;
        if (node.value == root.value) root = newTop;

        return newTop;
    }

    /**
     * метод смены цвета у текущей черной вершины и двух его красных детей
     * @param node - текущая вершина дерева, к которой будет применена данная операция
     */
    private void changeColor(Node node) {
        node.color = Color.RED;
        node.leftChildren.color = Color.BLACK;
        node.rightChildren.color = Color.BLACK;
    }

    /**
     * @apiNote метод находит значение вершины дерева
     * @return возвращает значение вершины дерева
     */
    public int findRoot() {
        return root.value;
    }

    /**
     * @apiNote метод проверяет, существует ли вершина с указанным значением
     * @param value - значение вершины
     * @return возвращает истину или ложь, указывая на наличие или отсутствия вершины с заданным значением
     */
    public boolean isExistNode(int value) {
        Node node = findDeep(value, root);

        return node != null;
    }

    /**
     * @apiNote рекурсивный метод обхода дерева в глубину с целью поиска узла по заданному значению
     * @param value - значение узла
     * @param node - текущая вершина, проверяемая на совпадение значений
     * @return - возвращает узел с заданным значением или null в случае отсутствия такового
     */
    private Node findDeep(int value, Node node) {
        if (node != null) {
            if (node.value == value) {
                return node;
            }
            if (node.value > value) {
                Node result = findDeep(value, node.leftChildren);
                if (result != null) return result;
            }
            if (node.value < value) {
                Node result = findDeep(value, node.rightChildren);
                if (result != null) return result;
            }
        }

        return null;
    }
}
