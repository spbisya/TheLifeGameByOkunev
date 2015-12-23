
import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	public static final int SPACE = 32;
	public static final int KEY_ENTER = 10;

	private static boolean[] keysDown = new boolean[256];

	public static void create(Canvas c) {
		c.addKeyListener(new Keyboard());
	}

	public static boolean isKeyDown(int keyCode) {
		return keysDown[keyCode];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keysDown[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysDown[e.getKeyCode()] = false;
	}

	// NOT USED
	@Override
	public void keyTyped(KeyEvent e) {

	}

}
