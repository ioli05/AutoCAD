import java.awt.Color;

public class Square implements ShapeVisitable {

    protected Point startPoint;
    protected int length;
    protected Color colorContur;
    protected int alphaContur;
    protected Color colorFill;
    protected int alphaFill;


    public Square(final Point startPoint, final int length, final Color
            colorContur, final int alphaContur, final Color colorFill,
            final int alphaFill) {

        this.startPoint = startPoint;
        this.length = length;

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
