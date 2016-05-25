package ru.kaidanova.sandbox;

/**
 * Created by i.loputneva on 2016-05-25.
 */
public class MySecondProgram {
    public static void main(String[] args) {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        System.out.println(distance(p1, p2));
        System.out.println(p1.distance(p2));
    }
    public static double distance(Point p1, Point p2)  {
     double distance = Math.sqrt((Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2)));
        return distance;
    }
}
