package raycaster.io
import raycaster._
import scala.collection.mutable.Buffer

object Defaults {
  var ceilingHeight: Double = 2
  var fov:           Int =    60
  var camResH:       Int =    200
  var camResW:       Int =    320
  var camLocZ:       Double = 1
  var grid:          Array[Array[Boolean]] = Array(Array(false))
  var map:           Array[String] = Array("")
  var plrLocX:       Double = 0
  var plrLocY:       Double = 0
  var plrRot:        Double = 0
  var renderLimit:   Double = 3
}

object GridReader {
  def readFile(input: Iterator[String]): Scene = {
    def read: String = {
      var line = input.next().trim
      while (line.isEmpty()) { line = input.next().trim}
      line.toLowerCase
    }
    
    while (input.hasNext) {
      val l = read.filter(_ != ' ').split('=')
      l(0) match {
        case "ceilingheight"    => Defaults.ceilingHeight = l(1).toDouble
        case "fieldofview"      => Defaults.fov = l(1).toInt
        case "resolutionwidth"  => if (l(1).toInt > 0) Defaults.camResW = l(1).toInt
        case "resolutionheight" => if (l(1).toInt > 0) Defaults.camResH = l(1).toInt
        case "cameraheight"     => if (l(1).toDouble > Defaults.ceilingHeight) Defaults.camLocZ = Defaults.ceilingHeight / 2 
                                   else if (l(1).toDouble > 0) Defaults.camLocZ = l(1).toDouble
                                   else  Defaults.camLocZ = Defaults.ceilingHeight / 2 
        case "renderlimit"      => Defaults.renderLimit = l(1).toDouble
        case "map"              => {
          val lines: Buffer[String] = Buffer()
          while (input.hasNext) {
            lines += read
          }
          readMap(lines.toArray)
        }
        case _ =>
      }
    }

    def readMap(lines: Array[String]) = {
      val xSize = lines(0).size
      val ySize = lines.size
      val newGrid = Array.fill(xSize, ySize)(false) 
      var rowNum = ySize
      for (row <- lines) {
        var colNum = 1
        for (col <- row) {
          var rot = -1
          col match {
            case 'x' => {
              newGrid(colNum - 1)(rowNum - 1) = true
            }
            case 'e' => setChar(colNum, rowNum, 0)
            case 's' => setChar(colNum, rowNum, 90)
            case 'w' => setChar(colNum, rowNum, 180)
            case 'n' => setChar(colNum, rowNum, 270)
            case _   =>
          }
          colNum += 1
        }
        rowNum -= 1
      }
      Defaults.grid = newGrid
    }
    def setChar(x: Int, y: Int, rot: Int) = {
      Defaults.plrLocX = x.toDouble - 0.5
      Defaults.plrLocY = y.toDouble - 0.5
      Defaults.plrRot  = rot.toDouble
    }
    
    val scene = 
      new Scene(
        Defaults.ceilingHeight, 
        Defaults.grid
      )
    val player = 
      new Character(
        scene,
        new Coords(Defaults.plrLocX, Defaults.plrLocY),
        Defaults.plrRot
      )
    val cam = 
      new Cam(
          player,
          Defaults.camLocZ,
          Defaults.fov,
          Defaults.camResW,
          Defaults.camResH,
          Defaults.renderLimit
      )
    player.cam = Some(cam)
    scene.player = Some(player)
    scene
  }
}