
import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {

	private static int x;
	private static int y;
	private static volatile boolean[] buttonsDown = new boolean[256];

	public static void create(Canvas c) {
		Mouse listener = new Mouse();
		c.addMouseListener(listener);
		c.addMouseMotionListener(listener);
	}

	public static int getX() {
		return x;
	}

	public static int getY() {
		return y;
	}

	public static int getCellX() {
		return x / Cell.CELL_SIZE;
	}

	public static int getCellY() {
		return y / Cell.CELL_SIZE;
	}

	public static boolean isButtonDown(int buttonCode) {
		return buttonsDown[buttonCode];
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		buttonsDown[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttonsDown[e.getButton()] = false;
	}

	// NOT USED
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
