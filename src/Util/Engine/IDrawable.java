package Util.Engine;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public interface IDrawable
{
	void draw(BufferedImage renderBuffer, AffineTransform cameraMatrix);

	int getLayer();
}
