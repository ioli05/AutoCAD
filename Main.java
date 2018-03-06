import java.io.IOException;
import java.util.List;

final class Main {

    private Main() {

    }

    public static void main(final String[] args) throws IOException {



        String fileInput = args[0];

        /*
         * ShapeFactory este un element de tip Singleton Pattern, astfel
         * acesta nu va putea fi instantiat; Vom apela direct metoda dorita,
         * si anume, returnShae, ce va return o lista cu forme;
         */

        List<ShapeVisitable> forms = ShapeFactory.getInstance().returnShape(fileInput);


        Shape s = new RevenueShape();

        /*
         * Lista cu forme, va fi mai apoi parcursa, si fiecare elemnt va fi
         * desenat, conform algoritmului formei;
         */
        for (ShapeVisitable shapevisit : forms) {
            shapevisit.accept(s);
        }
    }

}
