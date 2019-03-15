
public class Result {

    public int goalsHomeTeam;
    public int goalsAwayTeam;
    public int redCardsHomeTeam;
    public int redCardsAwayTeam;

    public Result() {
        this.goalsHomeTeam = 0;
        this.goalsAwayTeam = 0;
        this.redCardsHomeTeam = 0;
        this.redCardsAwayTeam = 0;
    }

    public void addGoal(String team){
        if(team.equals("away"))
            this.goalsAwayTeam++;
        else
            this.goalsHomeTeam++;
    }

    public void addSuspension(String team) {
        if(team.equals("away"))
            this.redCardsAwayTeam++;
        else
            this.redCardsHomeTeam++;
    }
}
