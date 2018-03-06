import java.awt.Color;

public class Polygon implements ShapeVisitable {

    protected Point[] points;
    protected Color colorContur;
    protected int alphaContur;
    protected Color colorFill;
    protected int alphaFill;

    public Polygon(final Point[] points, final Color colorContur,
            final int alphaContur, final Color colorFill, final int alphaFill) {

        this.points = points;

        this.colorContur = colorContur;

        this.alphaContur = alphaContur;

        this.colorFill = colorFill;
        this.alphaFill = alphaFill;

    }
    /*
     * (non-Javadoc)
     * @see ShapeVisitable#accept(Shape)
     */

    @Override
    public void accept(final Shape shape) {
        shape.draw(this);

    }

}
