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
class CameraTest extends FlatSpec with Matchers{
    "raycaster.Cam" should "get right column angle with 1 pixel" in {
      val testFile: String = {
        "ceiling height = 2\n" +
        "field of view     = 90\n" +
        "resolution width  = 1\n" +
        "resolution height = 1\n" +
        "camera height     = 1\n" +
        "\n" + 
        "MAP =\n" +
        "XXX\n" +
        "XEX\n" +
        "XXX\n"
      }
      val sc: Scene = GridReader.readFile(testFile.lines)
      val cam = sc.player.get.cam.get
      }
    "it" should "get right column angles" in {
      val testFile: String = {
        "ceiling height = 2\n" +
        "field of view     = 90\n" +
        "resolution width  = 1920\n" +
        "resolution height = 1\n" +
        "camera height     = 1\n" +
        "\n" + 
        "MAP =\n" +
        "XXX\n" +
        "XEX\n" +
        "XXX\n"
      }
      val sc: Scene = GridReader.readFile(testFile.lines)
      val cam = sc.player.get.cam.get
  }
    "it" should "calculate distance of 0.5 in front" in {
      val testFile: String = {
        "ceiling height = 2\n" +
        "field of view     = 90\n" +
        "resolution width  = 1\n" +
        "resolution height = 1\n" +
        "camera height     = 1\n" +
        "\n" + 
        "MAP =\n" +
        "XXX\n" +
        "XEX\n" +
        "XXX\n"
      }
      val sc: Scene = GridReader.readFile(testFile.lines)
      val cam = sc.player.get.cam.get
      sc.ceilingHeight shouldBe 2
      sc.player.get.loc.x shouldBe 1.5
      sc.player.get.loc.y shouldBe 1.5
      sc.player.get.rot shouldBe 0
      cam.character shouldBe sc.player.get
      cam.heightLoc shouldBe 1
      cam.fov shouldBe 90
      cam.resWidth shouldBe 1
      cam.resHeight shouldBe 1
      
      cam.calcDistances shouldBe Array(0.5)
      
  } 
    "it" should "calculate distances with angles" in {
      val testFile: String = {
        "ceiling height = 2\n" +
        "field of view     = 180\n" +
        "resolution width  = 30\n" +
        "resolution height = 1\n" +
        "camera height     = 1\n" +
        "\n" + 
        "MAP =\n" +
        "XXX\n" +
        "XEX\n" +
        "XXX\n"
      }
      val sc: Scene = GridReader.readFile(testFile.lines)
      val cam = sc.player.get.cam.get
  } 
   "raycaster.Cam" should "calculate distance of 1.5 in front" in {
      val testFile: String = {
        "ceiling height = 2\n" +
        "field of view     = 90\n" +
        "resolution width  = 1\n" +
        "resolution height = 1\n" +
        "camera height     = 1\n" +
        "\n" + 
        "MAP =\n" +
        "XXXX\n" +
        "XE X\n" +
        "XXXX\n"
      }
      val sc: Scene = GridReader.readFile(testFile.lines)
      val cam = sc.player.get.cam.get
      cam.calcDistances shouldBe Array(1.5)
  } 
}