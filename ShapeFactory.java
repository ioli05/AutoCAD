import java.awt.Color;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import fileio.implementations.FileReader;

public final class ShapeFactory  {
    /*
     * Clasa folosita pentru a instantia elementele de tip ShapeVisitable
     */
    private static ShapeFactory instance = new ShapeFactory();

    public static ShapeFactory getInstance() {

        return instance;
    }

    private ShapeFactory() { }


    /*
     * Functie folosita pentru transformarea culorii din string, in culoare de
     * tip RGBA, prin impartirea stringului de 6 caractere, in grupuri de cate 2
     * si transformarea in element de tip intreg, din element in baza 16;
     */
    public Color createColor(final String color, final int alpha) {

        /*
         * Limitele sirului sunt astfel : primul caracter este #, astfel,
         * caracterele necare incep de pe pozitia 1, si continua pana pe
         * pozitia 7 inclusiv;
         * In crearea culorii, dupa transformarea caracterelor, se va adauga
         * si factorul alpha;
         */
        final int fLimit = 3;
        final int sLimit = 5;
        final int tLimit = 7;
        final int lLimit = 16;

        Color c = new Color(Integer.parseInt(color.substring(1, fLimit),
                lLimit), Integer.parseInt(color.substring(fLimit, sLimit),
                lLimit), Integer.parseInt(color.substring(sLimit, tLimit),
                lLimit), alpha);
        // La final, functia va returna culoarea in formatul dorit;
        return c;

    }

    /*
     * Dupa apelul acestei functii, va fi returnat o lista cu elemente de tip
     * ShapeVistable
     */

    public List<ShapeVisitable> returnShape(final String fileInput)
            throws IOException {

        /*
         * Citirea tuturor elementelor, se va face din fisier. Fiecare tip de
         * forma, va avea un numar diferit de argumente, de aceea citirea
         * acestora se va afce pe cazuri;
         */
        FileReader file = new FileReader(fileInput);
        // n reprezinta numarul de elemnte ale listei;
        int n = file.nextInt();
        /*
         * shape va fi lista in care vor fi adaugate elementele ce urmeaza a
         * fi desenate;
         */

        List<ShapeVisitable> shape = new LinkedList<ShapeVisitable>();

        //LinkedList<ShapeVisitable> shape = new LinkedList<ShapeVisitable>();

        for (int i = 0; i < n; i++) {

            /*
             * stringType va stoca tipul elementului, iar continuarea citirii
             * va fi diferita in functie forma citita;
             */

            String stringType = file.nextWord();

            if (stringType.equals("CANVAS")) {
                /*
                 * Pentru tipul canvas vom citi inaltimea, lungimea, culoarea
                 * si transparenta;
                 */

                int height = file.nextInt();
                int width = file.nextInt();
                String color = file.nextWord();
                int alpha = file.nextInt();

                Color c = createColor(color, alpha);
                shape.add(new Canvas(height, width, c, alpha));

            }

            if (stringType.equals("LINE")) {
                /*
                 * Pentru tipul Line, vom citi cele doua puncte, reprezentand
                 * inceputul si sfarsitul liniei, respectiv culoarea si
                 * transparenta;
                 */

                int xStart = file.nextInt();
                int yStart = file.nextInt();
                Point startPoint = new Point(xStart, yStart);

                int xEnd = file.nextInt();
                int yEnd = file.nextInt();
                Point endPoint = new Point(xEnd, yEnd);

                String colorContur = file.nextWord();

                int alphaContur = file.nextInt();

                Color c = createColor(colorContur, alphaContur);

                shape.add(new Line(startPoint, endPoint, c, alphaContur));

            }

            if (stringType.equals("SQUARE")) {
                /*
                 * Pentru tipul Square, vom citi punctul de start, lungimea
                 * laturii, respectiv culoarea si transparenta pentru contur
                 * si pentru umplerea formei;
                 */
                int xStart = file.nextInt();
                int yStart = file.nextInt();
                Point startPoint = new Point(xStart, yStart);

                int length = file.nextInt();

                String colorContur = file.nextWord();
                int alphaContur = file.nextInt();
                Color c1 = createColor(colorContur, alphaContur);
                String colorFill = file.nextWord();
                int alphaFill = file.nextInt();
                Color c2 = createColor(colorFill, alphaFill);

                shape.add(new Square(startPoint, length - 1, c1,
                        alphaContur,  c2, alphaFill));


            }

            if (stringType.equals("RECTANGLE")) {
                /*
                 * Pentru tipul Rectangle, vom citi punctul de start, lungimea,
                 * latimea, respectiv culoarea si transparenta pentru contur
                 * si pentru umplerea formei;
                 */
                int xStart = file.nextInt();
                int yStart = file.nextInt();
                Point startPoint = new Point(xStart, yStart);

                int height = file.nextInt();
                int width = file.nextInt();

                String colorContur = file.nextWord();
                int alphaContur = file.nextInt();
                Color c1 = createColor(colorContur, alphaContur);
                String colorFill = file.nextWord();
                int alphaFill = file.nextInt();
                Color c2 = createColor(colorFill, alphaFill);

                shape.add(new Rectangle(startPoint, height - 1, width - 1, c1,
                        alphaContur, c2, alphaFill));

            }

            if (stringType.equals("CIRCLE")) {
                /*
                 * Pentru tipul Circle, vom citi punctul ce se afla in centru
                 * formei, lungimea razei, respectiv culoarea si transparenta
                 * pentru contur respectiv pentru umplerea formei;
                 */

               int xCenter = file.nextInt();
               int yCenter = file.nextInt();
               Point centerPoint = new Point(xCenter, yCenter);

               int radius = file.nextInt();
               String colorContur = file.nextWord();
               int alphaContur = file.nextInt();
               Color c1 = createColor(colorContur, alphaContur);
               String colorFill = file.nextWord();
               int alphaFill = file.nextInt();
               Color c2 = createColor(colorFill, alphaFill);


               shape.add(new Circle(centerPoint, radius, c1, alphaContur,
                       c2, alphaFill));
            }

            if (stringType.equals("TRIANGLE")) {

                /*
                 * Pentru tipul Triangle, vom citi coordonatele celor trei
                 * puncte, respectiv culoarea si transparenta pentru contur
                 * si pentru umplerea formei;
                 */

                int xStart = file.nextInt();
                int yStart = file.nextInt();
                Point startPoint = new Point(xStart, yStart);

                int xMiddle = file.nextInt();
                int yMiddle = file.nextInt();
                Point middlePoint = new Point(xMiddle, yMiddle);

                int xEnd = file.nextInt();
                int yEnd = file.nextInt();
                Point endPoint = new Point(xEnd, yEnd);

                String colorContur = file.nextWord();
                int alphaContur = file.nextInt();
                Color c1 = createColor(colorContur, alphaContur);
                String colorFill = file.nextWord();
                int alphaFill = file.nextInt();
                Color c2 = createColor(colorFill, alphaFill);

                shape.add(new Triangle(startPoint, middlePoint, endPoint,
                        c1, alphaContur, c2, alphaFill));


            }

            if (stringType.equals("DIAMOND")) {
                /*
                 * Pentru tipul Diamond, vom citi punctul de mijloc al formei,
                 * diagonala mica, diagonala mare, respectiv culoarea si
                 * transparenta pentru contur si pentru umplerea formei;
                 */

                int xCenter = file.nextInt();
                int yCenter = file.nextInt();
                Point centerPoint = new Point(xCenter, yCenter);

                int horizontalDiagonal = file.nextInt();
                int verticalDiagonal = file.nextInt();

                String colorContur = file.nextWord();
                int alphaContur = file.nextInt();
                Color c1 = createColor(colorContur, alphaContur);
                String colorFill = file.nextWord();
                int alphaFill = file.nextInt();
                Color c2 = createColor(colorFill, alphaFill);

                shape.add(new Diamond(centerPoint, horizontalDiagonal,
                        verticalDiagonal, c1, alphaContur,
                        c2, alphaFill));

            }

            if (stringType.equals("POLYGON")) {
                /*
                 * Pentru tipul Polygon, vom citi numarul de puncte, iar mai apoi
                 * in vectorul points[] vor fi salvate toate punctele ce determina
                 * colturile poligonului, respectiv vor mai fi citite culorile si
                 * transparentele pentru contur si pentru umplere;
                 */

                int numberPoints = file.nextInt();
                Point[] points = new Point[numberPoints];

                for (int j = 0; j < numberPoints; j++) {

                    int xValue = file.nextInt();
                    int yValue = file.nextInt();
                    points[j] = new Point(xValue, yValue);

                }

                String colorContur = file.nextWord();
                int alphaContur = file.nextInt();
                Color c1 = createColor(colorContur, alphaContur);
                String colorFill = file.nextWord();
                int alphaFill = file.nextInt();
                Color c2 = createColor(colorFill, alphaFill);

                shape.add(new Polygon(points, c1, alphaContur,
                        c2, alphaFill));

            }

        }
        return shape;

    }

}
