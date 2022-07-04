package sk.stasko.model.tree;

import sk.stasko.model.tree.comparator.AbstractComparator;
import sk.stasko.model.tree.interval.AbstractInterval;
import sk.stasko.model.tree.key.Key;
import sk.stasko.model.tree.nodes.IKDNode;
import sk.stasko.model.tree.nodes.KDNode;

import java.util.*;

public class KDTree<T, S> implements IKDTree<T, S> {
    protected IKDNode<T, S> root;
    private final AbstractComparator<S> comparator;
    private final int numberOfKeys;

    public KDTree(AbstractComparator<S> comparator, int numberOfKeys) {
        this.comparator = comparator;
        this.numberOfKeys = numberOfKeys;
        this.root = null;
    }

    @Override
    public AbstractComparator<S> getComparator() {
        return comparator;
    }

    @Override
    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    @Override
    public boolean hasRoot() {
        return this.root != null;
    }

    @Override
    public void clearRoot() {
         this.root = null;
    }

    @Override
    public int countElements() {
        Stack<IKDNode<T, S>> stack = new Stack<>();
        IKDNode<T, S> node = this.root;
        int foundIt = 0;
        while (!stack.empty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.getLeftSon();
            } else {
                node = stack.pop();
                foundIt++;
                node = node.getRightSon();
            }
        }
        return foundIt;
    }

    @Override
    public List<T> getLevelOrder() {
        Queue<IKDNode<T ,S>> queue = new LinkedList<>();
        List<T> list = new LinkedList<>();
        queue.add(this.root);
        while (!queue.isEmpty()) {
            IKDNode<T, S> node = queue.poll();
            list.add(node.getData());
            if (node.getLeftSon() != null) {
                queue.add(node.getLeftSon());
            }
            if (node.getRightSon() != null) {
                queue.add(node.getRightSon());
            }
        }
        return list;
    }

    @Override
    public T insert(T data, Key<S> key) {
        IKDNode<T, S> newNode = new KDNode<>(data, key);
        if (this.root == null) {
            this.root = newNode;
            newNode.getKey().setCurrentIndexLevel(0);
            return newNode.getData();
        }
        boolean foundIt = false;
        int currentLevel = 0;
        IKDNode<T, S> currentNode = this.root;
        while (!foundIt) {
            int compareValue = this.comparator.compare(key.getKey(), currentNode.getKey().getKey());
            if (compareValue > 0) {
                if (currentNode.getRightSon() != null) {
                    currentNode = currentNode.getRightSon();
                    currentLevel++;
                    continue;
                }
                currentNode.setRightSon(newNode);
                currentNode.getRightSon().setParent(currentNode);
            } else {
                if (currentNode.getLeftSon() != null) {
                    currentNode = currentNode.getLeftSon();
                    currentLevel++;
                    continue;
                }
                currentNode.setLeftSon(newNode);
                currentNode.getLeftSon().setParent(currentNode);
            }
            newNode.getKey().setCurrentIndexLevel(currentLevel + 1);
            foundIt = true;
        }
        return newNode.getData();
    }

    @Override
    public List<T> find(Key<S> key) {
        if (this.root == null) {
            return null;
        }
        List<T> foundNodes = new LinkedList<>();
        this.findByPoint(this.root, foundNodes, null, key);
        return foundNodes;
    }

    @Override
    public List<T> find(List<AbstractInterval<S>> interval) {
        if (interval.size() != this.numberOfKeys) {
            return null;
        }
        if (this.root == null) {
            return null;
        }
        List<T> nodesInInterval = new LinkedList<>();
        List<IKDNode<T, S>> inOrderNodes = new LinkedList<>();
        this.inOrderKdTree(this.root, inOrderNodes, interval);
        for (IKDNode<T, S> node: inOrderNodes) {
            boolean isBelongToList = false;
            for (int x = 0; x < this.getNumberOfKeys(); x++) {
                if (!interval.get(x).checkIfIsInInterval(node.getKey().getKey(), this.numberOfKeys, x)) {
                    isBelongToList = false;
                    break;
                }
                isBelongToList = true;
            }
            if (isBelongToList) {
                nodesInInterval.add(node.getData());
            }
        }
        return nodesInInterval;
    }

    @Override
    public boolean delete(T myNode, Key<S> keys,Comparator<T> dataComparator, AbstractComparator<S> comp) {
        if (myNode == null) {
            return false;
        }

        List<IKDNode<T, S>> foundedNodes = new LinkedList<>();
        this.findByPoint(this.root, null ,foundedNodes, keys);
        if (foundedNodes.size() == 0) {
            return false;
        }
        List<IKDNode<T, S>> listOfEquals = new ArrayList<>();

        for (IKDNode<T, S> foundNode: foundedNodes) {
            if (dataComparator.compare(foundNode.getData(), myNode) == 0) {
                listOfEquals.add(foundNode);
            }
        }

        for (int x = 0; x < listOfEquals.size(); x++) {
            IKDNode<T, S> node = listOfEquals.get(x);
            if (node.getParent() == null &&
                    node.getLeftSon() == null &&
                    node.getRightSon() == null) {
                this.root = null;
            }
            while (node.getRightSon() != null || node.getLeftSon() != null) {
                IKDNode<T, S> foundCorrectNode;
                int index = node.getKey().getCurrentIndexLevel() % this.getNumberOfKeys();
                comp.setValue(index);
                if (node.getLeftSon() != null) {
                    foundCorrectNode = foundHighestNodeByIndex(node.getLeftSon(), node.getLeftSon(), comp);
                } else {
                    foundCorrectNode = foundSmallestNodeByIndex(node.getRightSon(), node.getRightSon(), comp, listOfEquals);
                }
                int i = listOfEquals.indexOf(foundCorrectNode);
                changeDataInNodes(node, foundCorrectNode, x, i,listOfEquals);
                node = foundCorrectNode;
            }

            if (node.getRightSon() == null && node.getLeftSon() == null && node.getParent() != null) {
                if (node.getParent().getLeftSon() != null &&
                        node.getParent().getLeftSon().getKey().checkIfIsEqual(node.getKey()) &&
                        dataComparator.compare(node.getParent().getLeftSon().getData(), node.getData()) == 0) {
                    node.getParent().setLeftSon(null);
                } else {
                    node.getParent().setRightSon(null);
                }
            }
        }

        for (int x = 1; x < listOfEquals.size(); x++) {
            this.insert(listOfEquals.get(x).getData(), listOfEquals.get(x).getKey());
        }

        return true;
    }

    private void changeDataInNodes(IKDNode<T, S> nodeOne, IKDNode<T, S> nodeTwo, int z, int l, List<IKDNode<T, S>> nodeList) {
        IKDNode<T, S> helpNode = new KDNode<>((KDNode<T, S>) nodeOne);
        nodeOne.setData(nodeTwo.getData());
        nodeOne.setKey(nodeTwo.getKey());

        nodeTwo.setData(helpNode.getData());
        nodeTwo.setKey(helpNode.getKey());
        nodeList.set(z, nodeTwo);
        if (l >= 0) {
            nodeList.set(l, nodeOne);
        }
    }

    private IKDNode<T, S> foundSmallestNodeByIndex(IKDNode<T, S> node, IKDNode<T, S> min, AbstractComparator<S> comp, List<IKDNode<T, S>> listOfEquals) {
        Stack<IKDNode<T, S>> stack = new Stack<>();
        List<IKDNode<T ,S>> helpList = new LinkedList<>();
        int countElements = 0;
        while (!stack.empty() || node != null) {
            if (node != null) {
                stack.push(node);
                countElements++;
                node = node.getLeftSon();
            } else {
                node = stack.pop();
                int value = comp.compare(min.getKey().getKey(), node.getKey().getKey());
                if (value > 0) {
                    min = node;
                    helpList.clear();
                    if (!listOfEquals.contains(node)) {
                        helpList.add(min);
                    }
                } else if (value == 0 && countElements == 1 && node.getRightSon() == null) {
                    min = node;
                    break;
                } else if (value == 0) {
                    if (!listOfEquals.contains(node)) {
                        helpList.add(node);
                    }
                }
                node = node.getRightSon();
            }
        }
        if (helpList.size() == 1) {
            return helpList.get(0);
        }
        listOfEquals.addAll(helpList);
        return min;
    }

    private IKDNode<T, S> foundHighestNodeByIndex(IKDNode<T, S> node, IKDNode<T, S> max, AbstractComparator<S> comp) {
        Stack<IKDNode<T, S>> stack = new Stack<>();
        while (!stack.empty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.getLeftSon();
            } else {
                node = stack.pop();
                int value = comp.compare(max.getKey().getKey(), node.getKey().getKey());
                if (value < 0) {
                    max = node;
                }
                node = node.getRightSon();
            }
        }
        return max;
    }

    protected void findByPoint(IKDNode<T, S> node, List<T> foundItem, List<IKDNode<T, S>> foundNodes, Key<S> key) {
        IKDNode<T, S> currentNode = node;
        while (currentNode != null) {
            int value = this.comparator.compare(key.getKey(), currentNode.getKey().getKey());
            if (value > 0) {
                currentNode = currentNode.getRightSon();
            } else {
                if (value == 0 && currentNode.getKey().checkIfIsEqual(key)) {
                    if (foundItem != null) {
                        foundItem.add(currentNode.getData());
                    }
                    if (foundNodes != null) {
                        foundNodes.add(currentNode);
                    }
                }
                currentNode = currentNode.getLeftSon();
            }
        }
    }

    protected void inOrderKdTree(IKDNode<T, S> node, List<IKDNode<T, S>> chosenNodes, List<AbstractInterval<S>> interval) {
        Stack<IKDNode<T, S>> stack = new Stack<>();
        while (!stack.empty() || node != null) {
            if (node != null) {
                stack.push(node);
                int index = node.getKey().getCurrentIndexLevel() % this.getNumberOfKeys();
                S keyValue = node.getKey().getKey();
                if (interval.get(index).checkIfIsInInterval(keyValue, this.numberOfKeys) ||
                        interval.get(index).checkIfItIsGreaterThanInterval(keyValue, this.numberOfKeys)) {
                    node = node.getLeftSon();
                } else {
                    node = node.getRightSon();
                }
            } else {
                node = stack.pop();
                chosenNodes.add(node);
                int index = node.getKey().getCurrentIndexLevel() % this.getNumberOfKeys();
                S keyValue = node.getKey().getKey();
                if (interval.get(index).checkIfIsInInterval(keyValue, this.numberOfKeys) &&
                        !interval.get(index).checkIfItIsRightBorder(keyValue, this.numberOfKeys)) {
                    node = node.getRightSon();
                } else {
                    node = null;
                }
            }
        }
    }
}
