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
        add("Center", new CvMorph(stages));
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        show();
   }
}

class CvMorph extends Canvas {
    Vector poly1 = new Vector();
    Vector poly2 = new Vector();
    Vector poly3 = new Vector();

    Point2D center      = new Point2D(0,0);
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

    int stage_counter = 0;
    int stages;


    CvMorph(int stages) {

        this.stages = stages;
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                int xPoint = evt.getX();
                int yPoint = evt.getY();

                if (!centerDef) {
                    center.x = 300;
                    center.y = 300;
                    centerDef = true;
                }
                else if (!poly1Fin) {
                    // Point2D pt = new Point2D(xPoint, yPoint);
                    // if (poly1.size() > 0) {
                    //     Point2D origPt = (Point2D)(poly1.elementAt(0));
                    //     double xComp   = Math.pow(origPt.x - pt.x, 2);
                    //     double yComp   = Math.pow(origPt.y - pt.y, 2);
                    //     float  dist    = (float) Math.sqrt(xComp + yComp);
                    //     if (dist < 16) {
                    //         pt = origPt;
                            poly1Fin = true;
                        // }
                    // }
                    // if (poly1.size() >= 1) {
                    //     // Calculate unit vector of last point.
                    //     Point2D lastPt = (Point2D)(poly1.lastElement());
                    //     double xCompU   = Math.pow(lastPt.x - center.x, 2);
                    //     double yCompU   = Math.pow(lastPt.y - center.y, 2);
                    //     float  distU    = (float) Math.sqrt(xCompU + yCompU);
                    //     u = new Point2DUnit((pt.x - center.x)/distU, (pt.y - center.y)/distU);
                    //     // Calculate unit vector uM "unit Mouse" to mouse new point.
                    //     double xCompUM   = Math.pow(pt.x - center.x, 2);
                    //     double yCompUM   = Math.pow(pt.y - center.y, 2);
                    //     float  distUM    = (float) Math.sqrt(xCompUM + yCompUM);
                    //     uM = new Point2DUnit((xPoint - center.x)/distUM, (yPoint - center.y)/distUM);
                    //
                    //     // Find dot product and angle.
                    //     float dotUandUM = (u.uX * uM.uX) + (u.uY * uM.uY);
                    //     double cosUandUM = Math.acos(dotUandUM);
                    //     // System.out.println("Unit vector check: " + u.uX + " " + u.uY);
                    //     // System.out.println("Unit vector check: " + uM.uX + " " + uM.uY);
                    //     // System.out.println("Theta: " + cosUandUM);
                    // }

                    Point2D pt = new Point2D(434,277);
                    poly1.addElement(pt);
                    pt = new Point2D(329,277);
                    poly1.addElement(pt);
                    pt = new Point2D(300,178);
                    poly1.addElement(pt);
                    pt = new Point2D(263,277);
                    poly1.addElement(pt);
                    pt = new Point2D(163,277);
                    poly1.addElement(pt);
                    pt = new Point2D(245,347);
                    poly1.addElement(pt);
                    pt = new Point2D(207,429);
                    poly1.addElement(pt);
                    pt = new Point2D(287,382);
                    poly1.addElement(pt);
                    pt = new Point2D(329,418);
                    poly1.addElement(pt);
                    pt = new Point2D(362,348);
                    poly1.addElement(pt);
                    // Point2D pt = new Point2D(345, 269);
                    // poly1.addElement(pt);
                    // pt = new Point2D(263, 208);
                    // poly1.addElement(pt);
                    // pt = new Point2D(147, 267);
                    // poly1.addElement(pt);
                    // pt = new Point2D(140, 388);
                    // poly1.addElement(pt);
                    // pt = new Point2D(250, 382);
                    // poly1.addElement(pt);
                    // pt = new Point2D(306, 347);
                    // poly1.addElement(pt);
                }
                else if (!poly2Fin && poly1Fin) {
                    // Point2D pt = new Point2D(xPoint, yPoint);
                    // if (poly2.size() > 0) {
                    //     Point2D origPt = (Point2D)(poly2.elementAt(0));
                    //     double xComp   = Math.pow(origPt.x - pt.x, 2);
                    //     double yComp   = Math.pow(origPt.y - pt.y, 2);
                    //     double dist    = Math.sqrt(xComp + yComp);
                    //     if (dist < 16) {
                            poly2Fin = true;
                    //     }
                    // }
                    Point2D pt = new Point2D(392,217);
                    poly2.addElement(pt);
                    pt = new Point2D(392,217);
                    poly2.addElement(pt);
                    pt = new Point2D(209,219);
                    poly2.addElement(pt);
                    pt = new Point2D(206,381);
                    poly2.addElement(pt);
                    pt = new Point2D(378,380);
                    poly2.addElement(pt);
                    // Point2D pt = new Point2D(367, 278);
                    // poly2.addElement(pt);
                    // pt = new Point2D(262, 264);
                    // poly2.addElement(pt);
                    // pt = new Point2D(267, 192);
                    // poly2.addElement(pt);
                    // pt = new Point2D(125, 259);
                    // poly2.addElement(pt);
                    // pt = new Point2D(105, 350);
                    // poly2.addElement(pt);
                    // pt = new Point2D(209, 359);
                    // poly2.addElement(pt);
                }
                else if (poly1Fin && poly2Fin) {
                    stage_counter++;
                    if (stage_counter > stages) {
                        stage_counter = 0;
                    }
                }
                repaint();
            }
        });
        // addMouseListener(new MouseAdapter() {
		// 	public void mousePressed(MouseEvent evt) {
        //         int xPoint = evt.getX();
        //         int yPoint = evt.getY();
        //
        //         if (!centerDef) {
        //             center.x = xPoint;
        //             center.y = yPoint;
        //             centerDef = true;
        //         }
        //         else if (!poly1Fin) {
        //             pt = new Point2D(xPoint, yPoint);
        //             if (poly1.size() > 0) {
        //                 Point2D origPt = (Point2D)(poly1.elementAt(0));
        //                 double xComp   = Math.pow(origPt.x - pt.x, 2);
        //                 double yComp   = Math.pow(origPt.y - pt.y, 2);
        //                 float  dist    = (float) Math.sqrt(xComp + yComp);
        //                 if (dist < 16) {
        //                     poly1Fin = true;
        //                     poly2Kick = true;
        //                 }
        //             }
        //             if (poly1.size() >= 1) {
        //                 Point2D lastPt = (Point2D)(poly1.lastElement());
        //                 double xCompU  = Math.pow(lastPt.x - center.x, 2);
        //                 double yCompU  = Math.pow(lastPt.y - center.y, 2);
        //                 float  distU   = (float) Math.sqrt(xCompU + yCompU);
        //                 u = new Point2DUnit((pt.x - center.x)/distU, (pt.y - center.y)/distU);
        //             }
        //             if (!poly1Fin) posDetection(poly1, pt);
        //         }
        //         else if (!poly2Fin && poly1Fin) {
        //             pt = new Point2D(xPoint, yPoint);
        //             if (poly2.size() > 0) {
        //                 Point2D origPt = (Point2D)(poly2.elementAt(0));
        //                 double xComp   = Math.pow(origPt.x - pt.x, 2);
        //                 double yComp   = Math.pow(origPt.y - pt.y, 2);
        //                 double dist    = Math.sqrt(xComp + yComp);
        //                 if (dist < 16) {
        //                     poly2Fin = true;
        //                 }
        //             }
        //             if (!poly2Fin) posDetection(poly2, pt);
        //         }
        //         repaint();
        //     }
        // });
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

    public void paint(Graphics g) {

        // Establish center point of polygons.
        if (centerDef) {
            g.drawRect(center.x - 2, center.y - 2, 4, 4);

            // define inital reference point to be x axis, don't come back here again.
            if (!initialRef) {
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
        g.setColor(Color.blue);

        a = (Point2D)(poly2.elementAt(0));
        if (poly2Size == 1) g.drawRect(a.x - 2, a.y - 2, 4, 4);

        for (int i=0; i<poly2Size; i++) {
            Point2D b = (Point2D)(poly2.elementAt(i % poly2Size));
            g.drawLine(a.x, a.y, b.x, b.y);
            a = b;
        }
        if (poly2Fin) {
            Point2D b = (Point2D)(poly2.elementAt(0));
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

        if (poly2Fin && poly1Fin) {
            g.setColor(Color.magenta);
            CorPoints morph = new CorPoints(poly1, poly2, center, center);

            poly3 = morph.morphPoly(stage_counter,stages);

            a = (Point2D)(poly3.elementAt(0));

            for (int i=0; i < poly3.size(); i++) {
                Point2D b = (Point2D)(poly3.elementAt(i %  poly3.size()));
                g.drawLine(a.x, a.y, b.x, b.y);
                a = b;
            }

            Point2D b = (Point2D)(poly3.elementAt(0));
            g.drawLine(a.x, a.y, b.x, b.y);
        }
    }
}

class CorPoints {
    // CorPoint unit vectors and their distances.
    Vector corUnits = new Vector();
    Vector distances = new Vector();

    Vector corPtsRed = new Vector();
    Vector corPtsBlue = new Vector();

    Vector red = new Vector();
    Vector blue = new Vector();

    Point2D redPoint = new Point2D(0,0);
    Point2D bluePoint = new Point2D(0,0);
    Point2D redPointM1 = new Point2D(0,0);
    Point2D bluePointM1 = new Point2D(0,0);
    Point2D firstPoint  = new Point2D(0,0);
    Point2D secondPoint = new Point2D(0,0);
    Point2D centerRed = new Point2D(0,0);
    Point2D centerBlue = new Point2D(0,0);


    Point2D refPoint = new Point2D(0,0);

    double eps = 0.01;

    CorPoints(Vector r, Vector b, Point2D cR, Point2D cB) {
        this.red = r;
        this.blue = b;
        this.centerRed = cR;
        this.centerBlue = cB;
        FindCorPoints();
    }

    public void FindCorPoints() {
        int index1 = 0;
        int index2 = 0;
        int count = 0;

        refPoint.x = centerRed.x + 20;
        refPoint.y = centerRed.y;

        int m = red.size();
        int n = blue.size();

        redPoint  = (Point2D)(red.elementAt(index1));
        bluePoint = (Point2D)(blue.elementAt(index2));

        double sine1 = angleBetween(centerRed, redPoint, refPoint);
        double sine2 = angleBetween(centerRed, bluePoint, refPoint);

        while(count < (m+n)) {
            if (Math.abs(sine1 - sine2) < eps) {
                refPoint = redPoint;

                corPtsRed.addElement(redPoint);
                corPtsBlue.addElement(bluePoint);

                index1++;
                index2++;
            }
            else if (sine1 < sine2) {
                firstPoint = redPoint;

                mVector line1 = new mVector(centerRed, redPoint);

                if (index2 == 0) {
                    bluePointM1 = (Point2D)(blue.lastElement());
                }
                else {
                    bluePointM1 = (Point2D)(blue.elementAt((index2 - 1) % m));
                }

                mVector line2 = new mVector(bluePoint, bluePointM1);
                secondPoint = intersectionPoint(line1, line2);

                refPoint = redPoint;

                corPtsRed.addElement(firstPoint);
                corPtsBlue.addElement(secondPoint);

                if (index1 < red.size() - 1) {
                    index1++;
                }
            }
            else if (sine1 > sine2) {
                firstPoint = bluePoint;

                mVector line1 = new mVector(centerRed, bluePoint);

                if (index1 == 0) {
                    redPointM1 = (Point2D)(red.lastElement());
                }
                else {
                    redPointM1 = (Point2D)(red.elementAt((index1 - 1) % m));
                }

                mVector line2 = new mVector(redPoint, redPointM1);

                secondPoint = intersectionPoint(line1, line2);

                refPoint = bluePoint;

                corPtsRed.addElement(secondPoint);
                corPtsBlue.addElement(firstPoint);

                if (index2 < blue.size() - 1) {
                    index2++;
                }
            }

            if (count < (m+n)) {
                redPoint  = (Point2D)(red.elementAt(index1));
                bluePoint = (Point2D)(blue.elementAt(index2));

                sine1 = angleBetween(centerRed, redPoint, refPoint);
                sine2 = angleBetween(centerRed, bluePoint, refPoint);
            }
            else {
                break;
            }
            count++;
        }

        for (int i = 0; i < corPtsRed.size(); i++) {
            Point2D p1 = (Point2D)(corPtsRed.elementAt(i));
            Point2D p2 = (Point2D)(corPtsBlue.elementAt(i));
            mVector unitmVector = new mVector(p1, p2);

            double dist = unitmVector.Length();
            distances.addElement(dist);

            Point2DUnit corrUnitVector = unitmVector.unitVector();
            corUnits.addElement(corrUnitVector);
        }
    }

    public void printCorPts() {

        // print cor points pairs.
        for (int i = 0; i < corPtsRed.size(); i++) {
            Point2D p1 = (Point2D)(corPtsRed.elementAt(i));
            Point2D p2 = (Point2D)(corPtsBlue.elementAt(i));
            System.out.println("cp1 - (" + p1.x + "," + p1.y + ") cp2 - ("+ p2.x + "," + p2.y + ")");
        }
    }

    public void printDistances() {

        // print cor points pairs.
        for (int i = 0; i < distances.size(); i++) {
            float dist = (float) (double)(distances.elementAt(i));
            System.out.println("dist - (" + dist + ")");
        }
    }

    public Vector getCorPtsRed() {
        return corPtsRed;
    }
    public Vector getCorPtsBlue() {
        return corPtsBlue;
    }

    public Vector morphPoly(int iteration, int stages) {
        Vector poly = new Vector();
        float scaleFactor = (float) iteration / (float) stages;

        for (int i = 0; i < corUnits.size(); i++) {

            Point2DUnit corUnitVector = (Point2DUnit)(corUnits.elementAt(i));
            float dist = (float) (double)(distances.elementAt(i));

            Point2D v = (Point2D)(corPtsRed.elementAt(i));

            int morphX = Math.round(corUnitVector.uX * dist * scaleFactor);
            int morphY = Math.round(corUnitVector.uY * dist * scaleFactor);

            Point2D polyPoint = new Point2D(v.x + morphX, v.y + morphY);

            poly.addElement(polyPoint);
        }

        return poly;
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
