import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Morph extends Frame {
    public static final int FRAME_SIZE_X = 600;
    public static final int FRAME_SIZE_Y = 600;

    public static void main(String[] args) {
        int stages = 0;

        if (args.length == 1) {
            try {
                stages = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                System.out.println("Input value must be an integer");
                System.exit(1);
            }
        }
        else {
            System.out.println("You must specify stages to show in morph\n" +
                               "Paremeters: <stages>\n" +
                               "Example:    java Morph 8");
            System.exit(1);
        }

        new Morph(stages);
    }

   Morph(int stages) {
        super("Polygon Morphing");
        addWindowListener(new WindowAdapter()
            {public void windowClosing(WindowEvent e){System.exit(0);}});
        setSize(FRAME_SIZE_X, FRAME_SIZE_Y);
        setResizable(false);
        setLocationRelativeTo(null);
        add("Center", new CvMorph());
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        show();
   }
}

class CvMorph extends Canvas {
    Vector poly1 = new Vector();
    Vector poly2 = new Vector();

    Vector corrPts1 = new Vector();
    Vector corrPts2 = new Vector();

    Point2D firstPoint  = new Point2D(0,0);
    Point2D secondPoint = new Point2D(0,0);
    Point2D redPoint    = new Point2D(0,0);
    Point2D bluePoint   = new Point2D(0,0);
    Point2D center      = new Point2D(0,0);
    Point2D refPoint    = new Point2D(0,0);
    Point2D pt          = new Point2D(0,0);
    Point2DUnit u       = new Point2DUnit(0,0);
    Point2DUnit uM      = new Point2DUnit(0,0);

    // define the center of the star shaped poly.
    boolean centerDef  = false,
            poly1Fin   = false,
            poly2Fin   = false,
            initialRef = false,
            StartP1    = false,
            StartP2    = false,
            poly2Kick  = false;


    CvMorph() {
        addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
                int xPoint = evt.getX();
                int yPoint = evt.getY();

                if (!centerDef) {
                    center.x = xPoint;
                    center.y = yPoint;
                    centerDef = true;
                }
                else if (!poly1Fin) {
                    pt = new Point2D(xPoint, yPoint);
                    if (poly1.size() > 0) {
                        Point2D origPt = (Point2D)(poly1.elementAt(0));
                        double xComp   = Math.pow(origPt.x - pt.x, 2);
                        double yComp   = Math.pow(origPt.y - pt.y, 2);
                        float  dist    = (float) Math.sqrt(xComp + yComp);
                        if (dist < 16) {
                            poly1Fin = true;
                            poly2Kick = true;
                        }
                    }
                    if (poly1.size() >= 1) {
                        Point2D lastPt = (Point2D)(poly1.lastElement());
                        double xCompU  = Math.pow(lastPt.x - center.x, 2);
                        double yCompU  = Math.pow(lastPt.y - center.y, 2);
                        float  distU   = (float) Math.sqrt(xCompU + yCompU);
                        u = new Point2DUnit((pt.x - center.x)/distU, (pt.y - center.y)/distU);
                    }
                    if (!poly1Fin) posDetection(poly1, pt);
                }
                else if (!poly2Fin && poly1Fin) {
                    pt = new Point2D(xPoint, yPoint);
                    if (poly2.size() > 0) {
                        Point2D origPt = (Point2D)(poly2.elementAt(0));
                        double xComp   = Math.pow(origPt.x - pt.x, 2);
                        double yComp   = Math.pow(origPt.y - pt.y, 2);
                        double dist    = Math.sqrt(xComp + yComp);
                        if (dist < 16) {
                            poly2Fin = true;
                        }
                    }
                    if (!poly1Fin) posDetection(poly2, pt);
                }
                repaint();
            }
        });
    }

    //To calcuate the cross product
    boolean crossProd(Point2D center, Point2D last, Point2D newPoint){

        int v1x = last.x - center.x;
        int v1y = last.y - center.y;

        int v2x = newPoint.x - last.x;
        int v2y = newPoint.y - last.y;

        int val = (v1x * v2y) - (v1y * v2x);

        if(val > 0){
            return false;
        }
        return true;
    }
    //Detect which side the point is on and add the point to the vector
    void posDetection(Vector poly, Point2D pt){
        if(!StartP1){
            poly.addElement(pt);
            StartP1 = true;
            poly2Kick = false;
        }
        if (poly.size() > 0) {
            Point2D lastPt = (Point2D)(poly.lastElement());
            //To get the direction of the triangle
            boolean Direction = crossProd(center, lastPt, pt);
            if(Direction){
                poly.addElement(pt);
            }
        }
        //Logic to Kick poly2
        if(poly2Kick){
            StartP1 = false;
        }
    }

    //To calculate the intersection point
    Point2D intersectionPoint(mVector vec1, mVector vec2){
        int x = 0;
        int y = 0;

        float L;

        int kX = vec1.p2x - vec1.p1x;
        int kY = vec1.p2y - vec1.p1y;

        int vX = vec2.p2x - vec2.p1x;
        int vY = vec2.p2y - vec2.p1y;

        Point2D intersection = null;

        float a = vX * (vec1.p1y - vec2.p1y);
        float b = vY * (vec1.p1x - vec2.p1x);
        float c = (kX * vY) - (kY * vX);

        L = ( a - b ) / c;

        x = (int) (vec1.p1x + L * kX);
        y = (int) (vec1.p1y + L * kY);

        intersection = new Point2D(x, y);

        return intersection;
    }

    private double angleBetween(Point2D center, Point2D current, Point2D previous) {
        double v1x = current.x - center.x;
        double v1y = current.y - center.y;

        //need to normalize:
        double l1 = Math.sqrt(v1x * v1x + v1y * v1y);
        v1x /= l1;
        v1y /= l1;

        double v2x = previous.x - center.x;
        double v2y = previous.y - center.y;

        //need to normalize:
        double l2 = Math.sqrt(v2x * v2x + v2y * v2y);
        v2x /= l2;
        v2y /= l2;

        double rad = Math.acos( v1x * v2x + v1y * v2y );

        double degres = Math.toDegrees(rad);
        return degres;
    }

    public void paint(Graphics g) {

        // Establish center point of polygons.
        if (centerDef) {
            g.drawRect(center.x - 2, center.y - 2, 4, 4);

            // define inital reference point to be x axis, don't come back here again.
            if (!initialRef) {
                refPoint.x = center.x + 20;
                refPoint.y = center.y;
                initialRef = true;
            }
            g.setColor(Color.black);
            // // draw axis
            // g.drawLine(center.x, center.y, center.x + 1000, center.y);
            // g.drawLine(center.x, center.y, center.x - 1000, center.y);
            // g.drawLine(center.x, center.y, center.x, center.y + 1000);
            // g.drawLine(center.x, center.y, center.x, center.y - 1000);
        }

        // Get the sie of the vector try to draw.
        int poly1Size = poly1.size();

        // Not yet defiend, dont draw.
        if (poly1Size == 0) return;
        g.setColor(Color.red);

        Point2D a = (Point2D)(poly1.elementAt(0));
        // Draw red rect to guide user.
        if (poly1Size == 1) g.drawRect(a.x - 2, a.y - 2, 4, 4);

        // Draw the polygon.
        for (int i=0; i<poly1Size; i++) {
            Point2D b = (Point2D)(poly1.elementAt(i));
            g.drawLine(a.x, a.y, b.x, b.y);
            a = b;
        }
        if (poly1Fin) {
            Point2D b = (Point2D)(poly1.elementAt(0));
            g.drawLine(a.x, a.y, b.x, b.y);
        }

        // Draw a line to guide the user on how to make a star polygon.
        if (!poly1Fin) {
            Point2D lastPt = (Point2D)(poly1.lastElement());
            double xCompU   = Math.pow(lastPt.x - center.x, 2);
            double yCompU   = Math.pow(lastPt.y - center.y, 2);
            float  distU    = (float) Math.sqrt(xCompU + yCompU);

            //To display direction message
            boolean Direction = crossProd(center, lastPt, pt);
            if(!Direction){
                g.drawString("Can't draw in this region", 650, 750);
            }

            // find the unit vector connecting the origin and last mapped point.
            u = new Point2DUnit((lastPt.x - center.x)/distU, (lastPt.y - center.y)/distU);
            // System.out.println("Unit vector check: " + u.uX + " " + u.uY);
            int scaleX = Math.round(1000 * u.uX);
            int scaleY = Math.round(1000 * u.uY);

            // Draw the lines.
            g.setColor(Color.magenta);
            g.drawLine(center.x, center.y, center.x + scaleX, center.y + scaleY);
            g.drawLine(center.x, center.y, center.x - scaleX, center.y - scaleY);
        } // End draw help

        int poly2Size = poly2.size();
        if (poly2Size == 0) return;
        System.out.print("blu");
        g.setColor(Color.blue);

        a = (Point2D)(poly2.elementAt(0));
        if (poly2Size == 1) g.drawRect(a.x - 2, a.y - 2, 4, 4);

        for (int i=0; i<poly2Size; i++) {
            Point2D b = (Point2D)(poly2.elementAt(i % poly2Size));
            g.drawLine(a.x, a.y, b.x, b.y);
            a = b;
        }
        if (poly1Fin) {
            Point2D b = (Point2D)(poly1.elementAt(0));
            g.drawLine(a.x, a.y, b.x, b.y);
        }

        // Draw a line to guide the user on how to make a star polygon.
        if (!poly2Fin) {
            Point2D lastPt = (Point2D)(poly2.lastElement());
            double xCompU   = Math.pow(lastPt.x - center.x, 2);
            double yCompU   = Math.pow(lastPt.y - center.y, 2);
            float  distU    = (float) Math.sqrt(xCompU + yCompU);

            //To display direction message
            boolean Direction = crossProd(center, lastPt, pt);
            if(!Direction){
                g.drawString("Can't draw in this region", 650,750);
            }

            // find the unit vector connecting the origin and last mapped point.
            u = new Point2DUnit((lastPt.x - center.x)/distU, (lastPt.y - center.y)/distU);
            // System.out.println("Unit vector check: " + u.uX + " " + u.uY);
            int scaleX = Math.round(1000 * u.uX);
            int scaleY = Math.round(1000 * u.uY);

            // Draw the lines.
            g.setColor(Color.magenta);
            g.drawLine(center.x, center.y, center.x + scaleX, center.y + scaleY);
            g.drawLine(center.x, center.y, center.x - scaleX, center.y - scaleY);
        } // End draw help

        // Begin correspondence point detection.

        //   Red      +-----+-----+-----+-----+-----+
        // (Source)   |  0  |  1  |  2  |  3  |  4  |     Size = m = 5
        //            +-----+-----+-----+-----+-----+
        //  index1
        //
        //
        //   Blue     +-----+-----+-----+-----+-----+-----+-----+-----+
        // (Source)   |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |    Size = m = 8
        //            +-----+-----+-----+-----+-----+-----+-----+-----+
        //  index2
        //
        // Original reference is x-axis.
        //
        // Angle: Red  - Origin - Reference Point
        //        Blue - Origin - Reference Point
        //
        // Sine1 the angle measure relative to Red
        // Sine2 the angle measure relative to Blue

        int index1 = 0;
        int index2 = 0;
        int m = poly1.size();
        int n = poly2.size();
        double eps = 0.01;

        int sine1 = 45;
        int sine2 = 37;

        // TODO: - Intersection points.
        //       - Sine of points.

        // This doesn't do anything right now.
        while((index1 < m) && (index2 < n)) {
            redPoint  = (Point2D)(poly1.elementAt(index1));
            bluePoint = (Point2D)(poly2.elementAt(index2));

            if (Math.abs(sine1 - sine2) < eps) {
                System.out.println("Sine1 - Sine2 < eps");
                // ✓ - 1. new reference point becomes: Blue[index2] OR Red[index1]
                refPoint = redPoint;
                // ✓ - 2. add (Red[index1], Blue[index2]) to list of
                //    correspondence points
                corrPts1.addElement(redPoint);
                corrPts2.addElement(bluePoint);

                // ✓ - 3. index1++     index2++
                index1++;
                index2++;
            }
            else if (sine1 < sine2) {
                System.out.println("Sine1 < Sine2");
                // ✓ - firstPoint = Red[index1]
                firstPoint = redPoint;
                // secondPoint = Intersection of Line(origin, Red[index1])
                //                           AND Line(Blue[index2], Blue[(index2 - 1) % n])
                // secondPoint = INTERSECTION;

                // ✓ - 1. new reference point becomes: Red[index1]
                refPoint = redPoint;

                // 2. add (firstPoint, secondPoint) to list of
                //    correspondence points
                corrPts1.addElement(firstPoint);
                corrPts2.addElement(secondPoint);

                // ✓ - 3. index1++
                index1++;
            }
            else if (sine1 > sine2) {
                System.out.println("Sine1 > Sine2");
                // ✓ - firstPoint = Blue[index2]
                firstPoint = bluePoint;

                // secondPoint = Intersection of Line(origin, Blue[index2])
                //                           AND Line(Red[index1], Red[(index1 - 1) % m])
                // secondPoint = INTERSECTION;

                // ✓ - 1. new reference point becomes: Blue[index2]
                refPoint = bluePoint;

                // ✓ - 2. add (secondPoint, firstPoint) to list of
                //    correspondence points
                corrPts1.addElement(secondPoint);
                corrPts2.addElement(firstPoint);

                // ✓ - 3. index2++
                index2++;
            }
        }

    }
}

class Point2D
{  int x, y;
   Point2D(int x, int y){this.x = x; this.y = y;}
}

class Point2DUnit
{  float uX, uY;
   Point2DUnit(float uX, float uY){this.uX = uX; this.uY = uY;}
}

class mVector{
    int p1x;
    int p1y;

    int p2x;
    int p2y;

    float uX;
    float uY;

    float x;
    float y;

    double Length;

    mVector(Point2D p1, Point2D p2){
        this.p1x = p1.x;
        this.p1y = p1.y;

        this.p2x = p2.x;
        this.p2y = p2.y;
    }

    public double Length(){
        x = p2x - p1x;
        y = p2y - p1y;

        return Length = Math.sqrt((x*x) + (y*y));
    }

    public Point2DUnit unitVector(){
        Length();

        uX = (float) (x/Length);
        uY = (float) (y/Length);

        Point2DUnit uP = new Point2DUnit(uX, uY);

        return uP;
    }
}
