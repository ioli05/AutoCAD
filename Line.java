import java.awt.Color;

public class Line implements ShapeVisitable {


    protected Point startPoint;
    protected Point endPoint;
    protected Color colorContur;
    protected int alphaContur;


    public Line(final Point startPoint, final Point endPoint,
            final Color colorContur, final int alphaContur) {


        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.colorContur = colorContur;
        this.alphaContur = alphaContur;


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
