import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BoardTest.class,
        CatanTest.class,
        CommandParserTest.class,
        PlayerTest.class,
        RoadTest.class,
        TileTest.class,
        StructureTest.class,
        ValidateMoveTest.class
})
public class AllTest {
}