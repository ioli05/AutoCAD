import java.awt.Color;

public class Rectangle implements ShapeVisitable {

    protected Point startPoint;
    protected int height;
    protected int width;
    protected Color colorContur;
    protected int alphaContur;
    protected Color colorFill;
    protected int alphaFill;

    public Rectangle(final Point startPoint, final int height, final int width,
            final Color colorContur, final int alphaContur, final Color
            colorFill, final int alphaFill) {

        this.startPoint = startPoint;
        this.height = height;
        this.width = width;
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
