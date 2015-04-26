package raycaster
import scala.math._

class Ray(start: Coords, angle: Double, scene: Scene) {
  
  // The limit for the grid to stop calculating
  // intersections if none come up
  val limitGrid: Int = 50
  
  // Returns true if this Ray is going up
  val goingNorth: Boolean = angle > 180

  // Returns true if this Ray is going right
  val goingEast: Boolean  = (angle < 90) || (angle > 270)
  
  // Returns the intersection to a wall by choosing
  // the closest from vertical and horizontal intersections
  def intersectionDistance: Double = {
    Array(start.distance(horizontalIntersection), start.distance(verticalIntersection)).min
  }

  // Return true if the scene has wall in the given
  // Coords when calculating horizontal intersections
  def gridHasWallH(c: Coords): Boolean = {
    val add = if (goingNorth) 0.1 else -0.1
    scene.hasWall(Coords(c.x, c.y + add).toCoordsGrid)
  }
  
  // Returns true if the scene has wall in the given
  // Coords when calculating vertical intersections
  def gridHasWallV(c: Coords): Boolean = {
    val add = if (goingEast) 0.1 else -0.1
    scene.hasWall(Coords(c.x + add, c.y).toCoordsGrid)
  }
  
  // Return the tan from degrees
  def tanD(a: Double): Double = tan(a.toRadians)
  
  // Returns the angle to calculate tan in
  // horizontal intersections
  def convertAngleH(a: Double): Double = {
    if (a > 270.0)      a - 270.0
    else if (a > 180.0) 270.0 - a
    else if (a > 90.0)  a - 90.0
    else                90.0 - a 
  }
  
  // Returns the Coords of the closest
  // intersections with a wall at horizontal grid lines
  def horizontalIntersection: Coords = {
    var firstX = 0.5 * tanD(convertAngleH(angle))
      if (!goingEast) firstX *= -1
    val firstY = if (goingNorth) 0.5 else -0.5
    var end = Coords(start.x + firstX, start.y + firstY)
    if (gridHasWallH(end)) return end
    val nextX: Double = firstX * 2
    val nextY: Double = if (goingNorth) 1 else -1
    for (c <- 0 until limitGrid) {
      end = Coords(end.x + nextX, end.y + nextY)
      if (gridHasWallH(end)) return end
    }
    end
  }
  
  // Returns the angle to calculate tan in
  // vertical intersections
  def convertAngleV(a: Double): Double = {
    if (a > 270.0)      360.0 - a
    else if (a > 180.0) a - 180.0
    else if (a > 90.0)  180.0 - a
    else                a  
  }
  
  // Returns the Coords of the closest
  // intersections with a wall at vertical grid lines
  def verticalIntersection: Coords = {
    val firstX = if (goingEast) 0.5 else -0.5
    var firstY = 0.5 * tanD(convertAngleV(angle))
      if (!goingNorth) firstY *= -1
    var end = Coords(start.x + firstX, start.y + firstY)
    if (gridHasWallV(end)) return end
    val nextX: Double = if (goingEast) 1 else -1
    val nextY: Double = firstY * 2
    for (c <- 0 until limitGrid) {
      end = Coords(end.x + nextX, end.y + nextY)
      if (gridHasWallV(end)) return end
    }
    end
  }
}