package sk.stasko.tester;

import sk.stasko.tester.comparators.DataComparator;
import sk.stasko.tester.comparators.IntegerKeyComparer;
import sk.stasko.tester.comparators.IntegerKeyMinMaxComparator;
import sk.stasko.model.tree.IKDTree;
import sk.stasko.model.tree.KDTree;
import sk.stasko.model.tree.interval.AbstractInterval;
import sk.stasko.model.tree.nodes.IKDNode;
import sk.stasko.model.tree.nodes.KDNode;

import java.util.*;

public class KDTreeTester {

    protected IKDTree<String, IntegerKey> tree;
    protected LinkedList<IKDNode<String, IntegerKey>> list;
    protected Random random;

    public KDTreeTester(KDTree<String ,IntegerKey> kdTree) {
        this.tree = kdTree;
        this.list = new LinkedList<>();
        this.random = new Random();
    }

    public int getSize() {
        return this.list.size();
    }

    public void randomOperation(Random random, int seed) {
        double decision = Math.random();
        if (decision < 0.2) {
            IKDNode<String, IntegerKey> node = this.list.get(random.nextInt(this.list.size() - 1));
            List<String> found = this.tree.find(node.getKey());
            boolean foundElement= false;
            for (String f: found) {
                if (f.compareTo(node.getData()) == 0) {
                    foundElement = true;
                    break;
                }
            }
            if (foundElement) {
                System.out.println("Found element with key " + node.getKey().getKey().getIntegerkeys()[0] + "," + node.getKey().getKey().getIntegerkeys()[1]);
            } else {
                throw new RuntimeException("Not Found element with key " + node.getKey().getKey().getIntegerkeys()[0] + "," + node.getKey().getKey().getIntegerkeys()[1]);
            }
        } else if (decision >= 0.2 && decision < 0.5) {
            IKDNode<String, IntegerKey> element = this.list.get(0);
            this.list.remove(element);
            this.tree.delete(element.getData(), element.getKey(), new DataComparator(), new IntegerKeyMinMaxComparator(tree.getNumberOfKeys(), element.getKey().getCurrentIndexLevel()));
            int s = this.tree.countElements();
            if (s == this.list.size()) {
                System.out.println("Element with data " + element.getData() + " keys " + element.getKey().getKey().getIntegerkeys()[0] + "," + element.getKey().getKey().getIntegerkeys()[1] + " was deleted");
            } else {
                throw new RuntimeException("Element with data " + element.getData() + " keys " + element.getKey().getKey().getIntegerkeys()[0] + "," + element.getKey().getKey().getIntegerkeys()[1] + " was NOT deleted");
            }
        } else if (decision > 0.5 && decision < 0.8) {
            int first  = random.nextInt(100);
            int second  = random.nextInt(100);
            IKDNode<String, IntegerKey> n = new KDNode<>("dataS" + seed + random.nextInt(100), new IntegerKey(new int[]{first, second}));
            this.list.add(n);
            this.tree.insert(n.getData(), new IntegerKey(n.getKey().getKey().getIntegerkeys()));
            if (this.tree.countElements() == this.list.size()) {
                System.out.println("Element with key" + first+ " " + second + " and data " + "dataS" + seed + random.nextInt(100) + " was added");
            } else {
                throw new RuntimeException("Element with key" + first+ " " + second + " and data " + "dataS" + seed + random.nextInt(100) + " was NOT added");
            }
        } else{
            List<AbstractInterval<IntegerKey>> intervals = new ArrayList<>(this.tree.getNumberOfKeys());
            intervals.add(new IntegerInterval(random.nextInt(40), false, random.nextInt(80 - 41) + 41 , false, null));
            intervals.add(new IntegerInterval(random.nextInt(40), true, random.nextInt(80 - 41) + 41, false, null));

            List<String> founde = this.tree.find(intervals);
            int foundse = this.ifElementsAreInInterval(intervals);
            if (foundse == founde.size()) {
                System.out.println("I have found all elements");
            } else {
                throw new RuntimeException("Not found elements seed " + seed);
            }
        }
    }

    public void addToListRandom(int seed) {
        random.setSeed(seed);
        for (int y = 0; y < 10000; y++) {
            int first = random.nextInt(360);
            int second = random.nextInt(360);
            this.list.add(new KDNode<>("data" + first + second + y, new IntegerKey(new int[]{first, second})));
        }
        this.list.add(new KDNode<>("Senica", new IntegerKey(new int[]{101, 201})));
        this.list.add(new KDNode<>("Bratislava", new IntegerKey(new int[]{105, 202})));
        this.list.add(new KDNode<>("Ruzbachy", new IntegerKey(new int[]{115, 210})));
        this.list.add(new KDNode<>("Presov", new IntegerKey(new int[]{117, 230})));
        this.list.add(new KDNode<>("Stropkov", new IntegerKey(new int[]{120, 240})));
    }

    public void addElements(int seed) {
        System.out.println("Adding Elements form list to tree");
        int added = 0;
        for (IKDNode<String, IntegerKey> element: this.list) {
            this.tree.insert(element.getData(), new IntegerKey(element.getKey().getKey().getIntegerkeys()));
            added++;
            if (added % 100000 == 0) {
                System.out.println(added);
            }
        }
        if (this.tree.countElements() != this.list.size()) {
            throw new RuntimeException("Bad number of Nodes with seed " + seed);
        }
        System.out.println("----------------");
    }

    public void shuffleList() {
        System.out.println("Shuffle");
        Collections.shuffle(this.list, new Random(5));
        System.out.println("----------------");
    }

    public void findByPoint(int seed) {
        System.out.println("Searching added elements");
        int found = 0;
        for (IKDNode<String, IntegerKey> element: this.list) {
            List<String> foundNodes = this.tree.find(element.getKey());
            if (foundNodes == null || foundNodes.size() == 0) {
                throw new RuntimeException("I have found nothing in seed " + seed);
            }
            int equals = 0;
            for (IKDNode<String, IntegerKey> el: this.list) {
                if (el.getKey().checkIfIsEqual(element.getKey())) {
                    equals++;
                }
            }
            if (foundNodes.size() > 0) {
                found++;
            } else {
                throw new RuntimeException("Nenasiel som vsetky prvky z listu v seed " + seed);
            }
            if (found % 100000 == 0) {
                System.out.println(found);
            }
        }
        System.out.println("Found " + found);
        System.out.println("----------------");
    }

    public void clearList() {
        this.list.clear();
    }

    public int ifElementsAreInInterval(List<AbstractInterval<IntegerKey>> intervals) {
        int count = 0;
        for (IKDNode<String, IntegerKey> node: this.list) {
            boolean found = false;
            for (int x = 0; x < intervals.size(); x++) {
                if (!intervals.get(x).checkIfIsInInterval(node.getKey().getKey(), 2, x)) {
                    found = false;
                    break;
                }
                found = true;
            }
            if (found) {
                count++;
            }
        }
        return count;
    }

    public void findByInterval(int seed) {
        System.out.println("Search by interval :");

        List<AbstractInterval<IntegerKey>> intervals = new ArrayList<>(this.tree.getNumberOfKeys());
        intervals.add(new IntegerInterval(100, false, 121, false, null));
        intervals.add(new IntegerInterval(200, true, 250, false, null));

        List<String> found = this.tree.find(intervals);
        if (this.ifElementsAreInInterval(intervals) != found.size()) {
            throw new RuntimeException("Did not found correct number of nodes at seed " + seed);
        }
        System.out.println("Size is " + found.size());
        System.out.println("----------------");
    }

    public void delete(int x) {
        int deleted = 0;
        int countOfElement = 0;
        for (IKDNode<String, IntegerKey> element : this.list) {
            countOfElement++;
            List<String> foundedNodes = this.tree.find(element.getKey());
            if (foundedNodes.size() == 0) {
                throw new RuntimeException("Did not find element " + countOfElement + " at seed " + x);
            }

            String myNode = null;
            for (String foundNode : foundedNodes) {
                if (foundNode.compareTo(element.getData()) == 0) {
                    myNode = foundNode;
                }
            }
            if (myNode == null) {
                throw new RuntimeException("I did not find correct data for seed " + x + " element " + countOfElement);
            }
            this.tree.delete(
                    myNode,
                    element.getKey(),
                    new DataComparator(),
                    new IntegerKeyMinMaxComparator(tree.getNumberOfKeys(), element.getKey().getCurrentIndexLevel()));
            deleted++;
        }
        System.out.println("I have deleted " + deleted + " for seed " + x);
    }

    public void delete(String data, IntegerKey key) {
        List<String> foundedNodes = this.tree.find(key);
        if (foundedNodes.size() == 0) {
            return;
        }
        String myNode = null;
        for (String foundNode : foundedNodes) {
            if (foundNode.compareTo(data) == 0) {
                myNode = foundNode;
            }
        }

        if (myNode == null) {
            throw new RuntimeException("I did not find correct data for seed ");
        }

        this.tree.delete(
                myNode,
                key,
                new DataComparator(),
                new IntegerKeyMinMaxComparator(tree.getNumberOfKeys(), key.getCurrentIndexLevel()));

    }

    public static void main(String[] args) {
        final int numberOfKeys = 2;
        KDTree<String, IntegerKey> kdTree = new KDTree<>(new IntegerKeyComparer(numberOfKeys), numberOfKeys);
        KDTreeTester test = new KDTreeTester(kdTree);

        for (int x = 0; x < 10000; x++) {
            System.out.println("||||||||||||||");
            System.out.println("SEED " + x);
            test.addToListRandom(x);
            test.shuffleList();
            test.addElements(x);
            test.shuffleList();
            test.findByPoint(x);
            test.findByInterval(x);
            test.delete(x);
            if (kdTree.hasRoot()) {
                throw new RuntimeException("Nieje prazdne na nasade  " + x);
            }
            test.clearList();
        }

//        test.addToListRandom(10000);
//        test.shuffleList();
//        test.addElements(10000);
//        Random rnd = new Random();
//        for (int i = 0; i < 10000; i++) {
//            System.out.println(i);
//            rnd.setSeed(i);
//            test.randomOperation(rnd, i);
//        }
    }
}
