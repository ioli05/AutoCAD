import java.awt.Color;

public class Circle implements ShapeVisitable {

    protected Point centerPoint;
    protected int radius;
    protected Color colorContur;
    protected int alphaContur;
    protected Color colorFill;
    protected int alphaFill;


    public Circle(final Point centerPoint, final int radius, final Color colorContur,
            final int alphaContur, final Color colorFill, final int alphaFill) {

        this.centerPoint = centerPoint;
        this.radius = radius;
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
