import java.awt.Color;

public class Triangle implements ShapeVisitable {

    protected Point startPoint;
    protected Point middlePoint;
    protected Point endPoint;
    protected Color colorContur;
    protected int alphaContur;
    protected Color colorFill;
    protected int alphaFill;

    public Triangle(final Point startPoint, final Point middlePoint, final
            Point endPoint, final Color colorContur, final int alphaContur,
            final Color colorFill, final int alphaFill) {

        this.startPoint = startPoint;
        this.middlePoint = middlePoint;
        this.endPoint = endPoint;

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
