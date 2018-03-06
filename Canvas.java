import java.awt.Color;

public class Canvas implements ShapeVisitable {

    protected int height;
    protected int width;
    protected Color color;
    protected int alpha;


    public Canvas(final int height, final int width, final Color color,
            final int alpha) {

        this.height = height;

        this.width = width;

        this.color = color;

        this.alpha = alpha;

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
