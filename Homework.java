import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Homework {

    /**
     * Необходимо описать класс Box, который должен хранить фрукты одного типа.
     * У этого класса должны быть методы:
     * 1. Добавить фрукт какого-то типа
     * 2. Узнать суммарный вес всех фруктов в коробке
     * 3. Пересыпать все фрукты в другую коробку
     * 4. Реализовать возможность итерирования по коробке
     */

    public static void main(String[] args) {
        Box<String> strings = new Box<>(); // не должно компилироваться

        Box<Orange> oranges = new Box<>();
        oranges.add(new Orange(1));
        System.out.println(oranges.getWeight()); // 1
        oranges.add(new Orange(2));
        System.out.println(oranges.getWeight()); // 3

        Box<Apple> apples = new Box<>();
        apples.add(new Orange(3)); // не должно компилироваться!
        apples.add(new GoldenApple(5)); // это ок

        Box<GoldenApple> goldenApples = new Box<>();
        goldenApples.add(new GoldenApple(5)); // это ок
        goldenApples.add(new Apple(3)); // не должно компилироваться!

        oranges.move(apples); // не должно компилироваться!
        goldenApples.move(apples); // это ок
        apples.move(goldenApples); // не должно компилироваться!

        Box<Orange> newOranges = new Box<>();
        oranges.move(newOranges);
        System.out.println(oranges.getWeight()); // 0 после пересыпания
        System.out.println(newOranges.getWeight()); // 3 после пересыпания

        for (Orange o: oranges) { // цикл компилируется, но не запускатся, потому oranges - пустой
            System.out.println(o.getWeight());
        }

        for (Orange o: newOranges) { // цикл компилируется, и запускается
            // Должно вывести 1 2 (или 2 1) - порядок неважен
            System.out.println(o.getWeight());
        }

        for (Apple a: apples) { // цикл компилируется, и запускается
            // Должно вывести 5 5
            System.out.println(a.getWeight());
        }
    }

    static class Box<T extends Fruit> implements Iterable<T> {
        List<T> box = new LinkedList<>();

        public void add(T fruit){
            box.add(fruit);
        }

        public List<T> getBox() {
            return box;
        }

        public void move(Box<? super T> to){
            to.getBox().addAll(this.box);
        }

        @Override
        public Iterator<T> iterator() {
            while (iterator().hasNext()){
                T fruit = iterator().next();
                System.out.println(fruit);
            }
            return iterator();
        }

        public int getWeight() {
            int boxWeight = 0;
            for (T fruit : box){
                boxWeight += fruit.getWeight();
            }
            return boxWeight;
        }
    }

    static class Fruit {
        private final int weight;

        public Fruit(int weight) {
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }
    }

    static class Orange extends Fruit {
        public Orange(int weight) {
            super(weight);
        }
    }

    static class Apple extends Fruit {
        public Apple(int weight) {
            super(weight);
        }
    }

    static class GoldenApple extends Apple {
        public GoldenApple(int weight) {
            super(weight);
        }
    }

}
