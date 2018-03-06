import java.awt.Color;

public class Diamond implements ShapeVisitable {

    protected Point centerPoint;
    protected int horizontalDiagonal;
    protected int verticalDiagonal;
    protected Color colorContur;
    protected int alphaContur;
    protected Color colorFill;
    protected int alphaFill;


    public Diamond(final Point centerPoint, final int horizontalDiagonal,
            final int verticalDiagonal, final Color colorContur, final int
            alphaContur, final Color colorFill, final int alphaFill) {

        this.centerPoint = centerPoint;
        this.horizontalDiagonal = horizontalDiagonal;
        this.verticalDiagonal = verticalDiagonal;

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
