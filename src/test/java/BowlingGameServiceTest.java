import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by r.Duluman
 */
public class BowlingGameServiceTest {
    private static final int GAME_ID = 5;

    private BowlingGameService bowlingGameService;
    private BowlingGameDao bowlingGameDao;

    @Before
    public void setUp() throws Exception {
        bowlingGameService = new BowlingGameService();
        bowlingGameDao = mock(BowlingGameDao.class);
        bowlingGameService.setBowlingGameDao(bowlingGameDao);
    }

    @Test
    public void onGutterGame_scoreIs0() {
        List<Integer> pins = new LinkedList<Integer>();
        for (int i = 0; i < 24; i++)
            pins.add(0);
        mockDao(pins);
        assertThat("score is 0 on a gutter game",
                bowlingGameService.getScore(GAME_ID), is(0));
    }

    @Test
    public void rollOne_scoreIsOne() throws Exception {
        List<Integer> pins = new LinkedList<Integer>(asList(1));
        mockDao(pins);
        assertThat("score is one ",
                bowlingGameService.getScore(GAME_ID), is(1));
    }

    @Test
    public void rollOneThenSpareThenOne_scoreIs13() throws Exception {
        mockDao(asList(1, 7, 3, 1));
        assertThat("when roll is 1 than spare than 1",
                bowlingGameService.getScore((GAME_ID)), is(13));

    }

    @Test
    public void rollStrikeThenOneThenOne_scoreIs14() throws Exception {
        mockDao(asList(10, 1, 1));
        assertThat("when roll is strike than 1 than 1",
                bowlingGameService.getScore(GAME_ID), is(14));

    }

    @Test
    public void rollOneThenOneThenOneThenStrikeThenOneThenOne_scoreIs16() throws Exception {
        mockDao(asList(1, 1, 10, 1, 1));
        assertThat("when roll is 11 Strike 11",
                bowlingGameService.getScore(GAME_ID), is(16));
    }

    @Test
    public void rollSpareThenOne_scoreIs12() throws Exception {
        mockDao(asList(5, 5, 1));
        assertThat("if roll is spare than one score is 12",
                bowlingGameService.getScore(GAME_ID), is(12));
    }

    @Test
    public void onStrike() throws Exception {
        mockDao(asList(10, 10, 4, 2));
        assertThat("when hit is a strike add next two hits on score",
                bowlingGameService.getScore(GAME_ID), is(46));
    }

    @Test
    public void onSpare() throws Exception {
        mockDao(asList(7, 3, 10, 2, 4));
        assertThat("when frame is a spare add next hit on score",
                bowlingGameService.getScore(GAME_ID), is(42));
    }

    @Test
    public void onPerfectGame() throws Exception {
        mockDao(asList(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10));
        assertThat("when Perfect",
                bowlingGameService.getScore(GAME_ID), is(300));
    }

    @Test
    public void onSpareLastFrame() throws Exception {
        mockDao(asList(10, 10, 10, 10, 10, 10, 10, 10, 10, 7, 3, 4));
        assertThat("when last hit is a spare",
                bowlingGameService.getScore(GAME_ID), is(271));
    }

    private void mockDao(List<Integer> pins) {
        when(bowlingGameDao.getGameRolls(GAME_ID))
                .thenReturn(pins);
    }




}
