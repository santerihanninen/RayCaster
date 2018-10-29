package raycaster
import scala.math._

class Coords(val x: Double, 
             val y: Double){
  // Return the Coords in the given direction
  def to(rot: Double): Coords = Coords.to(this, rot)
  
  // Returns the CoordsGrid at this Coords
  def toCoordsGrid: CoordsGrid = {
    Coords.toCoordsGrid(this)
  }
  
  // Return the distance between this and another Coords
  def distance(b: Coords): Double = {
    Coords.distance(this, b)
  }
}

object Coords {
  
  // Return a new Coords
  def apply(x: Double, y: Double): Coords = new Coords(x, y)
  
  // Return the Coords in the given direction r from l
  def to(l: Coords, r: Double): Coords = {
    r match {
      case 0   => new Coords(l.x + 1, l.y)
      case 90  => new Coords(l.x,     l.y - 1)
      case 180 => new Coords(l.x - 1, l.y)
      case 270 => new Coords(l.x,     l.y + 1)
    }
  }
  
  // Return the distance between two Coords
  def distance(a: Coords, b: Coords): Double = {
    sqrt( pow( (b.x - a.x), 2 ) + pow( (b.y - a.y), 2 ) )
  }
  
  // Return the CoordsGrid at the given Coords
  def toCoordsGrid(what: Coords): CoordsGrid = {
    new CoordsGrid(what.x.toInt, what.y.toInt)
  }
}