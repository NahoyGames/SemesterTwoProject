package Util.Engine;

import Util.Math.Vec2f;

import java.awt.*;

public interface IDrawable
{
	// Draws onto the render buffer
	void draw(Graphics2D renderBuffer);

	int getLayer();

	float getAlpha();

	float setAlpha(float val);
}
