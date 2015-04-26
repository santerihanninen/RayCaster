package raycaster

class Character(val scene: Scene,  
                initLoc: Coords, 
                initRot: Double) {
  var cam: Option[Cam] = None
  var loc: Coords = initLoc
  var rot: Double = initRot
  
  // Moves the character forward
  def moveForward()   = move(rot)
  
  // Moves the character backwards
  def moveBackwards() = move(rotate(180))
  
  // Moves the character left
  def moveLeft()      = move(rotate(-90))
  
  // Moves the character right
  def moveRight()     = move(rotate(90))

  // Moves the character to the given direction
  // if there is no wall
  def move(to: Double) = {
    if (!wallAtDir(to)) loc = loc.to(to)
  }
  
  // Returns true if there is a wall at the 
  // given direction, otherwise false
  def wallAtDir(d: Double): Boolean = {
    this.scene.hasWall(loc.to(d).toCoordsGrid)
  }
  
  // Turns the character left by 90 degrees
  def turnLeft()   = rot = rotate(-90)
  
  // Turns the character right by 90 degrees
  def turnRight()  = rot = rotate(90)
  
  // Turns the character by the given angle
  def rotate(by: Double): Double = {
    var res = this.rot + by
    if ( res >= 360 ) { res = res - 360 }
    if ( res < 0 )    { res = res + 360 }
    res
  }
}