package raycaster

// Represents the grid's squares
class CoordsGrid(val x: Int, 
                 val y: Int){
  
  // Return the Coords at the center of this CoordsGrid
  def toCoordsCenter: Coords = CoordsGrid.toCoordsCenter(this)
  
  // Return the CoordsGrid in the given direction
  def to(rot: Double) = CoordsGrid.to(this, rot)

object CoordsGrid {
    
  // Return a new CoordsGrid
  def apply(x: Int, y: Int) = new CoordsGrid(x, y)
  
  // Return the Coords at the center of the given CoordsGrid
  def toCoordsCenter(what: CoordsGrid): Coords = Coords(what.x.toDouble + 0.5, what.y.toDouble + 0.5)
  
  // Return the CoordsGrid in the given direction rot
  // from the given CoordsGrid loc
  def to(loc: CoordsGrid, rot: Double): CoordsGrid = {
      Coords.to(loc.toCoordsCenter, rot).toCoordsGrid
  }
}
}