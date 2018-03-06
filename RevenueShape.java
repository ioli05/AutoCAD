import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

public class RevenueShape implements Shape {
    /*
     * Elementul de tip BufferedImage b reprezinta imaginea pe care se vor
     * desena toate formele;
     */
    protected BufferedImage b;


    /*
     * Functie ce ne ajuta sa verificam daca un elemnt se afla sau nu in
     * interiorul imaginii;
     */
    public boolean isInside(final int x, final int y) {

        if (x < b.getWidth() && y < b.getHeight() && x >= 0 && y >= 0) {
            return true;
        }
        return false;

    }
    /*
     * Functie ce va umple cu culoarea dorita, o anumita forma, pornind
     * dintr-un anumit punct, in cazul nostru fiind mereu din centrul
     * formei;
     */

    public void floodFill(final Point point, final Color colorFill,
            final Color colorContur) {
        //Pentru salcarea vecinilor va fi folosita o coada;

        Queue<Point> queue = new LinkedList<Point>();

        //Adaugam punctul din care pornim colorarea;
        queue.add(point);

        while (!queue.isEmpty()) {

            Point p = queue.remove();

            /*
             * Extragem punctul si verificam daca acesta este in interior, atunci
             * il coloram si verificam toti vecinii (sus, jos, stanga, dreapta);
             * Daca acestia se afla in interiorul formei, si nu au fost colorati,
             * ii vom adauga in coada pentru a continua procesul;
             */
            if (isInside(p.x,p.y)) {


               b.setRGB(p.x, p.y, colorFill.getRGB());
               /*
                * Pentru a putea aduga vecinii, trebuie sa verificam ca acestia
                * sa se afle in interiorul formei, sa nu fi fost colorati si sa
                * nu fie un punct aflat pe conturul formei;
                */
               if (isInside(p.x - 1, p.y) && b.getRGB(p.x - 1, p.y)
                       != colorContur.getRGB() && b.getRGB(p.x - 1, p.y)
                       != colorFill.getRGB()) {
                   b.setRGB(p.x - 1, p.y, colorFill.getRGB());
                   queue.add(new Point(p.x - 1, p.y));
               }

               if (isInside(p.x + 1, p.y) && b.getRGB(p.x + 1, p.y)
                       != colorContur.getRGB() && b.getRGB(p.x + 1, p.y)
                       != colorFill.getRGB()) {
                   b.setRGB(p.x + 1, p.y, colorFill.getRGB());
                   queue.add(new Point(p.x + 1, p.y));
               }

               if (isInside(p.x, p.y - 1) && b.getRGB(p.x, p.y - 1)
                       != colorContur.getRGB() && b.getRGB(p.x, p.y - 1)
                       != colorFill.getRGB()) {
                   b.setRGB(p.x, p.y - 1, colorFill.getRGB());
                   queue.add(new Point(p.x, p.y - 1));
               }

               if (isInside(p.x, p.y + 1) && b.getRGB(p.x, p.y + 1)
                       != colorContur.getRGB() && b.getRGB(p.x, p.y + 1)
                       != colorFill.getRGB()) {
                   b.setRGB(p.x, p.y + 1, colorFill.getRGB());
                   queue.add(new Point(p.x, p.y + 1));
               }
            } else {
                return;
            }

         }
         return;
    }


    /*
     * Functie pentru a desena Canvas-ul
     */
    @Override
    public void draw(final Canvas canvas) {

        /*
         * Imaginea noastra b, va avea dimensiunile date in canvas;
         */
        b = new BufferedImage(
                canvas.width, canvas.height, BufferedImage.TYPE_INT_ARGB);

        //Vom colora toti pixelii cu culoarea dorita, gandindu-ne la imagine
        //drept un dreptunghi;
        for (int i = 0; i < canvas.width; i++) {
            for (int j = 0; j < canvas.height; j++) {
                b.setRGB(i, j, canvas.color.getRGB());

            }
        }

        try {
            ImageIO.write(b, "png", new File("drawing.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    /*
     *Functie pentru a desena o linie;
     */

    @Override
    public void draw(final Line line) {

        /*
         * Algoritmul folosit pentru desenarea unei linii este Forma generala
         * a Algoritmului lui Bresenham, realizat dupa pseudocodul oferit in
         * enuntul temei;
         */
        int x = line.startPoint.x;
        int y = line.startPoint.y;

        int deltaX = Math.abs(line.endPoint.x - line.startPoint.x);
        int deltaY = Math.abs(line.endPoint.y - line.startPoint.y);

        float s1 = Math.signum(line.endPoint.x - line.startPoint.x);
        float s2 = Math.signum(line.endPoint.y - line.startPoint.y);

        int interschimbare = 0;

        if (deltaY > deltaX) {

            int aux = deltaY;
            deltaY = deltaX;
            deltaX = aux;
            interschimbare += 1;

        }

        int error = 2 * deltaY - deltaX;

        for (int i = 0; i <= deltaX; i++) {

            if (isInside(x, y)) {
                b.setRGB(x, y, line.colorContur.getRGB());
            }


            while (error > 0) {
                if (interschimbare == 1) {
                    x = (int) (x + s1);
                } else {
                    y = (int) (y + s2);
                }
                error = error - 2 * deltaX;
            }

            if (interschimbare == 1) {
                y = (int) (y + s2);
            } else {
                x = (int) (x + s1);
            }

            error = error + 2 * deltaY;
        }

        try {
            ImageIO.write(b, "png", new File("drawing.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /*
     * Functie de desenare a patratului;
     */
    @Override
    public void draw(final Square square) {

        /*
         * Pentru a desena un patrat, vom stabili mai intai care sunt cele
         * patru puncte ce vor reprezenta colturile patratului, iar mai apoi
         * vom trasa linii intre cele patru extremitati;
         */

        Point secondPoint = new Point(square.startPoint.x + square.length,
                square.startPoint.y);

        Point thirdPoint = new Point(secondPoint.x, secondPoint.y
                         + square.length);
        Point fourthPoint = new Point(thirdPoint.x - square.length, thirdPoint.y);

        /*
         * Pentru a trasa linii, vom defini mai intai liniile date de cele doua
         * puncte necesare, respectiv vom da si culoarea cu care vor fi trasate;
         */
        Line firstLine = new Line(square.startPoint, secondPoint,
                square.colorContur, square.alphaContur);
        Line secondLine = new Line(secondPoint, thirdPoint,
                square.colorContur, square.alphaContur);
        Line thirdLine = new Line(thirdPoint, fourthPoint,
                square.colorContur, square.alphaContur);
        Line fourthLine = new Line(fourthPoint, square.startPoint,
                square.colorContur, square.alphaContur);

        draw(firstLine);
        draw(secondLine);
        draw(thirdLine);
        draw(fourthLine);

        /*
         * Pentru umplerea patratului vom parcurge cu doua for-uri interiorul
         * formei si vom seta fiecare pixel la culoarea data;
         */
        for (int i = square.startPoint.x + 1; i < secondPoint.x; i++) {

            for (int j = secondPoint.y + 1; j < thirdPoint.y; j++) {

                if (isInside(i, j)) {
                    b.setRGB(i, j, square.colorFill.getRGB());
                }
            }
        }

        try {
            ImageIO.write(b, "png", new File("drawing.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



    }
    /*
     * Functie pentru desenarea unui Dreptunghi;
     */

    @Override
    public void draw(final Rectangle rectangle) {

        /*
         * Pentru a desena un dreptungi, vom stabili mai intai care sunt cele
         * patru puncte ce vor reprezenta colturile formei, iar mai apoi
         * vom trasa linii intre cele patru extremitati;
         * Pentru determinarea lor ne vom folosi de valoarea lungimii si a
         * latimii;
         */

        Point secondPoint = new Point(rectangle.startPoint.x + rectangle.width,
                rectangle.startPoint.y);

        Point thirdPoint = new Point(secondPoint.x, secondPoint.y
                         + rectangle.height);
        Point fourthPoint = new Point(thirdPoint.x - rectangle.width,
                thirdPoint.y);

        /*
         * Pentru a trasa linii, vom defini mai intai liniile date de cele doua
         * puncte necesare, respectiv vom da si culoarea cu care vor fi trasate;
         */
        Line firstLine = new Line(rectangle.startPoint, secondPoint,
                rectangle.colorContur, rectangle.alphaContur);
        Line secondLine = new Line(secondPoint, thirdPoint,
                rectangle.colorContur, rectangle.alphaContur);
        Line thirdLine = new Line(thirdPoint, fourthPoint,
                rectangle.colorContur, rectangle.alphaContur);
        Line fourthLine = new Line(fourthPoint, rectangle.startPoint,
                rectangle.colorContur, rectangle.alphaContur);

        draw(firstLine);
        draw(secondLine);
        draw(thirdLine);
        draw(fourthLine);

        /*
         * Pentru umplerea dreptunghiului, vom parcurge cu doua for-uri
         * interiorul formei si vom seta fiecare pixel avand culoarea data;
         */

        for (int i = rectangle.startPoint.x + 1; i < secondPoint.x; i++) {

            for (int j = secondPoint.y + 1; j < thirdPoint.y; j++) {

                if (isInside(i, j)) {
                    b.setRGB(i, j, rectangle.colorFill.getRGB());
                }
            }
        }

        try {
            ImageIO.write(b, "png", new File("drawing.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void drawCircle(final int xc, final int yc,
            final int x, final int y, final Color c) {

        /*
         * Functie ce va desena toate cele 8 puncte necesare, in realizarea unui
         * cerc; Pentru asta vom verifica inainte, mereu, ca fiecare punct sa se
         * afle in interiorul imaginii;
         */

        if (isInside(xc + x, yc + y)) {
            b.setRGB(xc + x, yc + y, c.getRGB());
        }

        if (isInside(xc - x, yc + y)) {
            b.setRGB(xc - x, yc + y, c.getRGB());
        }

        if (isInside(xc + x, yc - y)) {
            b.setRGB(xc + x, yc - y, c.getRGB());
        }

        if (isInside(xc - x, yc - y)) {
            b.setRGB(xc - x, yc - y, c.getRGB());
        }

        if (isInside(xc + y, yc + x)) {
            b.setRGB(xc + y, yc + x, c.getRGB());
        }

        if (isInside(xc - y, yc + x)) {
            b.setRGB(xc - y, yc + x, c.getRGB());
        }

        if (isInside(xc + y, yc - x)) {
            b.setRGB(xc + y, yc - x, c.getRGB());
        }

        if (isInside(xc - y, yc - x)) {
            b.setRGB(xc - y, yc - x, c.getRGB());
        }


}
    /*
     * Functie pentru desenarea unui Cerc
     */
    @Override
    public void draw(final Circle circle) {
        /*
         *  Algoritmul de desenare a unui cerc este de tip Bresenham, iar
         *  implementarea a fost luata de urmatorul site :
         *  http://www.geeksforgeeks.org/bresenhams-circle-drawing-algorithm/
         */
            int xc = circle.centerPoint.x;
            int yc = circle.centerPoint.y;
            int r = circle.radius;

            int x = 0, y = r;
            int d = 3 - 2 * r;
            while (y >= x) {
                // for each pixel we will
                // draw all eight pixels
                drawCircle(xc, yc, x, y, circle.colorContur);
                x++;

                // check for decision parameter
                // and correspondingly
                // update d, x, y
                if (d > 0) {
                    y--;
                    d = d + 4 * (x - y) + 10;
                } else {
                    d = d + 4 * x + 6;
                }
                drawCircle(xc, yc, x, y, circle.colorContur);
            }

            /*
             * Pentru a umple aceasta forma nu vom mai avea nevoie de calculul punctului
             * din centru acesta fiind deja dat;
             */

            floodFill(circle.centerPoint, circle.colorFill, circle.colorContur);

            try {
                ImageIO.write(b, "png", new File("drawing.png"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    /*
     * Functie pentru desenarea unui Triunghi
     */

    @Override
    public void draw(final Triangle triangle) {
        /*
         * Pentru desenarea unui triunghi, vom defini liniile intre cele 3
         * puncte citite deja;
         */

        Line firstLine = new Line(triangle.startPoint,  triangle.middlePoint,
                triangle.colorContur, triangle.alphaContur);
        Line secondLine = new Line(triangle.middlePoint, triangle.endPoint,
                triangle.colorContur, triangle.alphaContur);
        Line thirdLine = new Line(triangle.endPoint, triangle.startPoint,
                triangle.colorContur, triangle.alphaContur);

        draw(firstLine);
        draw(secondLine);
        draw(thirdLine);

        /*
         * Pentru umplerea acestei forme, vom calcula centrul formei acesta
         * fiind dat de media aritmetica a celor 3 puncte;
         */
        int centerX = triangle.startPoint.x + triangle.middlePoint.x
                    + triangle.endPoint.x;
        int centerY = triangle.startPoint.y + triangle.middlePoint.y
                    + triangle.endPoint.y;

        final float nrPoints = 3;
        Point centerPoint = new Point(Math.round(centerX / nrPoints),
                Math.round(centerY / nrPoints));

        floodFill(centerPoint, triangle.colorFill, triangle.colorContur);

        try {
            ImageIO.write(b, "png", new File("drawing.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




    }

    /*
     * Functie pentru desenarea unui Romb
     */
    @Override
    public void draw(final Diamond diamond) {


        /*
         * Pentru determinarea punctelor unui romb, ne vom folosi de dimensiunile
         * diagonalelor si de punctul de centru, acesta aflandu-se la jumatatea
         * celor doua diagonale;
         */
        int halfHDiagonal = (int) Math.floor(diamond.horizontalDiagonal / 2);
        int halfVDiagonal = (int) Math.floor(diamond.verticalDiagonal / 2);

        Point firstPoint = new Point(diamond.centerPoint.x - halfHDiagonal,
                diamond.centerPoint.y);
        Point secondPoint = new Point(diamond.centerPoint.x,
                diamond.centerPoint.y - halfVDiagonal);
        Point thirdPoint = new Point(diamond.centerPoint.x + halfHDiagonal,
                diamond.centerPoint.y);
        Point fourthPoint = new Point(diamond.centerPoint.x,
                diamond.centerPoint.y + halfVDiagonal);

        Line firstLine = new Line(secondPoint, firstPoint,
                diamond.colorContur, diamond.alphaContur);
        Line secondLine = new Line(secondPoint, thirdPoint,
                diamond.colorContur, diamond.alphaContur);
        Line thirdLine = new Line(thirdPoint, fourthPoint,
                diamond.colorContur, diamond.alphaContur);
        Line fourthLine = new Line(fourthPoint, firstPoint,
                diamond.colorContur, diamond.alphaContur);

        draw(firstLine);
        draw(secondLine);
        draw(thirdLine);
        draw(fourthLine);
        /*
         * Pentru a umple aceasta forma nu vom mai avea nevoie de calculul punctului
         * din centru acesta fiind deja dat;
         */

        floodFill(diamond.centerPoint, diamond.colorFill, diamond.colorContur);

        try {
            ImageIO.write(b, "png", new File("drawing.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    /*
     * Functie pentru desenarea unui Poligon
     */

    @Override
    public void draw(final Polygon polygon) {

        /*
         * Pentru a denesa un poligon, vom definii liniile corespunzatoare
         * intre fiecare punct;
         */
        int xValue = 0;
        int yValue = 0;

        for (int i = 0; i < polygon.points.length - 1; i++) {
            xValue = xValue + polygon.points[i].x;
            yValue = yValue + polygon.points[i].y;
            Line line = new Line(polygon.points[i], polygon.points[i + 1],
                    polygon.colorContur, polygon.alphaContur);
            draw(line);

        }

        Line line = new Line(polygon.points[polygon.points.length - 1],
                polygon.points[0], polygon.colorContur, polygon.alphaContur);
        draw(line);

        xValue = xValue + polygon.points[polygon.points.length - 1].x;
        yValue = yValue + polygon.points[polygon.points.length - 1].y;


        /*
         * Pentru a umple forma, vom calcula centrul formei acesta reprezentand
         * media aritmetica a colturilor;
         */
        Point centerPoint = new Point(Math.round(xValue / (polygon.points.length)),
                Math.round(yValue / (polygon.points.length)));

        floodFill(centerPoint, polygon.colorFill, polygon.colorContur);

        try {
            ImageIO.write(b, "png", new File("drawing.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
