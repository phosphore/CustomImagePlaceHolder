package ip.imagegenerator;


import ip.imagegenerator.impl.TextImageImpl;

import java.awt.Graphics2D;


/**
 * Callback for the textimage.
 */
public interface TextImageCallback {
	/**
	 * Exposes the graphics object which clients can use to perform more advanced functionality than
	 * that the {@link TextImageImpl} implements.
	 * @param graphics
	 */
	void doWithGraphics(Graphics2D graphics);
}
