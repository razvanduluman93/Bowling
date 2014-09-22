import java.util.List;

/**
 * Created by r.Duluman on 9/18/2014.
 */
public class BowlingGameService {
    private BowlingGameDao bowlingGameDao;
    public Integer score=0;
    public int getScore(int gameId){
        List<Integer> rolls = bowlingGameDao.getGameRolls(gameId);
        int max=rolls.size();
        if(rolls.size()==12) {
            max = 10;
            score += rolls.get(11) + rolls.get(10);
        }
        for(int i=0 ; i<max-1 ; i++) {
            if (rolls.get(i) == 10)
            {
                onStrike(rolls, i);
            }
            else if ((rolls.get(i) + rolls.get(i + 1)) == 10) {
                onSpare(rolls, i);
                i++;
            }
            else
            {
                score = score + rolls.get(i);
            }
        }
        score += rolls.get(max-1);
        return  score;
    }

    private void onSpare(List<Integer> rolls, int i) {
        score +=10;
        if(i+2<21)
            score += rolls.get(i+2);
    }

    private void onStrike(List<Integer> rolls, int i) {
        score += 10 + rolls.get( i + 1 ) + rolls.get( i + 2 );
    }

    public void setBowlingGameDao(BowlingGameDao bowlingGameDao) {
        this.bowlingGameDao = bowlingGameDao;
    }


}
