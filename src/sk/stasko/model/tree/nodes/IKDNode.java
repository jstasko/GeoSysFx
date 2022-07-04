package sk.stasko.model.tree.nodes;

import sk.stasko.model.tree.key.Key;

public interface IKDNode<T, S> {
    /**
     *
     * @return return key
     */
    Key<S> getKey();

    /**
     *
     * @param key set key
     */
    void setKey(Key<S> key);

    /**
     *
     * @return get right son
     */
    IKDNode<T, S> getRightSon();

    /**
     *
     * @return get left son
     */
    IKDNode<T, S> getLeftSon();

    /**
     *
     * @return get parent
     */
    IKDNode<T, S> getParent();

    /**
     *
     * @param rightSon new right son
     */
    void setRightSon(IKDNode<T, S> rightSon);

    /**
     *
     * @param leftSon new left son
     */
    void setLeftSon(IKDNode<T, S> leftSon);

    /**
     *
     * @param parent new parent
     */
    void setParent(IKDNode<T, S> parent);

    /**
     *
     * @return data of node
     */
    T getData();
    void setData(T data);

    /**
     *
     * @param key key of node
     * @return new copy of key
     */
    Key<S> getNewKey(Key<S> key);

}
