package raycaster.gui
import raycaster.io._
import java.awt.image._
import BufferedImage._
import scala.swing._
import event._

object RayCaster extends SimpleSwingApplication {
  val scene = GridReader.readFile(scala.io.Source.fromFile("raycasterscene.txt").getLines)
  val cam = scene.player.get.cam.get
  val plr = scene.player.get
  def top = new MainFrame {
    title = "RayCaster v1.0"
    resizable = false
    centerOnScreen()
    preferredSize = new Dimension(cam.resWidth, cam.resHeight)
    val view =  new Panel {
      override def paintComponent(g: Graphics2D) {
        val image = new BufferedImage(cam.resWidth, cam.resHeight, TYPE_INT_RGB)
        cam.render(image)
        g.drawImage(image, 0, 0, null)
        focusable = true
        requestFocus
      }
    }
    contents = view
    listenTo(view.keys)  
    reactions += {
      case kp:KeyPressed =>
        if(kp.key==Key.Q || kp.key==Key.Left) plr.turnLeft(); view.repaint
        if(kp.key==Key.W || kp.key==Key.Up) plr.moveForward(); view.repaint
        if(kp.key==Key.E || kp.key==Key.Right) plr.turnRight(); view.repaint
        if(kp.key==Key.A) plr.moveLeft(); view.repaint
        if(kp.key==Key.S || kp.key==Key.Down) plr.moveBackwards(); view.repaint
        if(kp.key==Key.D) plr.moveRight(); view.repaint
    }
  }
}