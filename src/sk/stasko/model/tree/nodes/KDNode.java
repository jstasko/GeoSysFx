package sk.stasko.model.tree.nodes;

import sk.stasko.model.tree.key.Key;

public class KDNode<T, S> implements IKDNode<T, S> {
    private IKDNode<T, S> rightSon;
    private IKDNode<T, S> leftSon;
    private IKDNode<T, S> parent;
    private T data;
    private Key<S> key;

    public KDNode(T data, Key<S> key) {
        this.data = data;
        this.key = key;
    }

    public KDNode(KDNode<T, S> anotherNode) {
        this.key = anotherNode.getNewKey(anotherNode.getKey());
        this.data = anotherNode.getData();
        this.leftSon = anotherNode.getLeftSon();
        this.rightSon = anotherNode.getRightSon();
        this.parent = anotherNode.getParent();
    }

    @Override
    public void setRightSon(IKDNode<T, S> rightSon) {
        this.rightSon = rightSon;
    }

    @Override
    public void setLeftSon(IKDNode<T, S> leftSon) {
        this.leftSon = leftSon;
    }

    @Override
    public void setParent(IKDNode<T, S> parent) {
        this.parent = parent;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public IKDNode<T, S> getRightSon() {
        return rightSon;
    }

    @Override
    public IKDNode<T, S> getLeftSon() {
        return leftSon;
    }

    @Override
    public IKDNode<T, S> getParent() {
        return parent;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public Key<S> getKey() {
        return key;
    }

    @Override
    public Key<S> getNewKey(Key<S> keys) {
        return keys.getCopyKeyObject(keys);
    }

    @Override
    public void setKey(Key<S> key) {
        this.key.setKey(key.getKey());
    }
}
