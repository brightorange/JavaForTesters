package ru.kaidanova.sandbox;

/**
 * Created by i.loputneva on 2016-05-25.
 */
public class Point {

    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public  double distance(Point p)  {
        double distance = Math.sqrt((Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2)));
        return distance;
    }

}
