package raycaster.tests
import java.io._
import org.scalatest._
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import raycaster._
import raycaster.io._

@RunWith(classOf[JUnitRunner])
class GridReaderTest extends FlatSpec with Matchers{
    "raycaster.io.GridReader.readFile" should "return a scene with the right properties" in {
      val testFile: String = {
        "ceiling height = 2\n" +
        "field of view     = 90\n" +
        "resolution width  = 400\n" +
        "resolution height = 300\n" +
        "camera height     = 1\n" +
        "\n" + 
        "MAP =\n" +
        "XXXX\n" +
        "X WX\n" +
        "XXXX\n"
      }
      val sc: Scene = GridReader.readFile(testFile.lines)
      sc.ceilingHeight shouldBe 2
      sc.player.get.loc.x shouldBe 2.5
      sc.player.get.loc.y shouldBe 1.5
      sc.player.get.rot shouldBe 180
      sc.player.get.cam.get.character shouldBe sc.player.get
      sc.player.get.cam.get.heightLoc shouldBe 1
      sc.player.get.cam.get.fov shouldBe 90
      sc.player.get.cam.get.resWidth shouldBe 400
      sc.player.get.cam.get.resHeight shouldBe 300
  }
    it should "return a scene with a correct grid" in {
      val testFile: String = {
        "ceiling height = 2\n" +
        "field of view     = 90\n" +
        "resolution width  = 400\n" +
        "resolution height = 300\n" +
        "camera height     = 1\n" +
        "\n" + 
        "MAP =\n" +
        "XXXX\n" +
        "X WX\n" +
        "XXXX\n"
      }
      val sc: Scene = GridReader.readFile(testFile.lines)
      assert(sc.hasWall(new CoordsGrid(0, 0)) == true,  "0, 0 didn't have wall")
      assert(sc.hasWall(new CoordsGrid(0, 1)) == true,  "0, 1 didn't have wall")
      assert(sc.hasWall(new CoordsGrid(0, 2)) == true,  "0, 2 didn't have wall")
      assert(sc.hasWall(new CoordsGrid(1, 0)) == true,  "1, 0 didn't have wall")
      assert(sc.hasWall(new CoordsGrid(1, 1)) == false, "1, 1 had a wall")
      assert(sc.hasWall(new CoordsGrid(1, 2)) == true,  "1, 2 didn't have wall")
      assert(sc.hasWall(new CoordsGrid(2, 0)) == true,  "2, 0 didn't have wall")
      assert(sc.hasWall(new CoordsGrid(2, 1)) == false, "2, 1 had a wall")
      assert(sc.hasWall(new CoordsGrid(2, 2)) == true,  "2, 2 didn't have wall")
      assert(sc.hasWall(new CoordsGrid(3, 0)) == true,  "3, 0 didn't have wall")
      assert(sc.hasWall(new CoordsGrid(3, 1)) == true,  "3, 1 didn't have wall")
      assert(sc.hasWall(new CoordsGrid(3, 2)) == true,  "3, 2 didn't have wall")
  }
}