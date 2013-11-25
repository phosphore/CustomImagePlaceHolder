package ip.imagegenerator.builders;

import ip.imagegenerator.Margin;
import ip.imagegenerator.impl.TextImageImpl;

import org.TextImage;

import java.awt.image.BufferedImage;

/**
 * Builder for creating image instances with a watermark.
 */
public final class WaterMarkImageBuilder {
	private final int width;

	private final int heigth;

	private final Margin margin;

    /**
     * Constructs a new instance of the WaterMarkImageBuilder class.
     * @param width The width of the image returned by this builder.
     * @param heigth The height of the image returned by this builder.
     * @param margin The margins used of the image returned by this builder.
     */
    public WaterMarkImageBuilder(final int width, final int heigth, final Margin margin) {
        if (margin == null) {
            throw new IllegalArgumentException("The margin may not be null.");
        }

		this.width = width;
		this.heigth = heigth;
		this.margin = margin;
	}

	/**
	 * Creates an image with a watermark.
	 * 
	 * @param waterMark The watermark used for the image.
	 * @return Watermarked image.
	 */
	public TextImage build(final BufferedImage waterMark) {
		TextImageImpl image = new TextImageImpl(width, heigth);

		for (int x = 0; x < this.width; x += waterMark.getWidth()) {
			for (int y = 0; y < this.heigth; y += waterMark.getHeight()) {
				image.write(waterMark, x, y);
			}
		}

		image.setMargin(margin);

		return image;
	}

}
