public class Game {

    public Team homeTeam;
    public Team awayTeam;
    public Referee ref;
    public Result finalResult;

    public Game(Team homeTeam,
                Team awayTeam,
                Referee ref) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.ref = ref;
    }


    // Calculate what team starts an attack
    private Team startsAttack(int minute){

        // Value of attack of each team
        float homeTeamStartAttack =
                this.homeTeam.calculateAttack(minute, this.finalResult.redCardsHomeTeam);
        float awayTeamStartAttack =
                this.awayTeam.calculateAttack(minute, this.finalResult.redCardsAwayTeam);

        // Random Factoring
        double probHome = homeTeamStartAttack / (Math.random() * 100);
        double probAway = awayTeamStartAttack / (Math.random() * 100);

        if( (probHome > probAway) && probHome > Variables.SCORE_FACTOR){
            return this.homeTeam;
        }
        if( (probAway > probHome) && probAway > Variables.SCORE_FACTOR){
            return  this.awayTeam;
        }

        else return null;
    }


    // Calculate if defending team is able to stop goal
    private boolean defendAttack(Team att, Team def, int minute){

        int attSuspensions, defSuspensions;

        if(att == this.awayTeam) {
            attSuspensions = this.finalResult.redCardsAwayTeam;
            defSuspensions = this.finalResult.redCardsHomeTeam;
        }
        else {
            attSuspensions = this.finalResult.redCardsHomeTeam;
            defSuspensions = this.finalResult.redCardsAwayTeam;
        }

        // Value of team defending attack
        float defendAttack = def.calculateDefend(minute, defSuspensions);

        // Value of team scoring goal
        float score = att.calculateGoal(minute, attSuspensions);

        // Random factoring and Luck factor
        double scoreProb = score / (Math.random() * 100);
        double defendProb = defendAttack / (Math.random() * 100) + Variables.LUCK_FACTOR;

        return !(scoreProb > defendProb) || !(scoreProb > Variables.SCORE_FACTOR);
    }


    public void faultEvent(int minute){
        double redCard = this.ref.personality *
                Math.random() *
                Variables.REFEREE_PERSONALITY_FACTOR;

        // If is likely to send off
        if(redCard > Variables.REFEREE_RED_CARD){
            // Randomly Pick Team
            if(Math.random() > 0.5){
                finalResult.addSuspension("home");
                System.out.println(minute + "'' [RED CARD] " + this.homeTeam.name);
            }

            else {
                finalResult.addSuspension("away");
                System.out.println(minute + "'' [RED CARD] " + this.awayTeam.name);
            }
        }
    }


    public void attackEvent(int minute){

        //Get team that starts an attack
        Team attack = this.startsAttack(minute);
        Team def;

        if(attack == this.awayTeam) def = this.homeTeam;
        else def = this.awayTeam;

        //If there will start an attack
        if(attack != null){

            if(!this.defendAttack(attack, def, minute)){

                System.out.println(minute + "'' [GOAL] " + attack.name);

                //Add goal to result
                if(attack == this.awayTeam)
                    this.finalResult.addGoal("away");
                else
                    this.finalResult.addGoal("home");
            }
            else
                System.out.println(minute + "'' [FAILED] " + attack.name);
        }
    }


    private void printStarting11(Team t){

        System.out.println("[" + t.name + "]");

        for(Player pl: t.startingEleven){
            System.out.println(pl.position + ": " + pl.name);
        }

        System.out.println("########\n");
    }


    // Game Simulation
    public Result startSimulation(){

        this.finalResult = new Result();

        //Starting 11
        this.printStarting11(this.homeTeam);
        this.printStarting11(this.awayTeam);


        System.out.println("[GAME STARTED]");
        //90 minutes Game
        for(int minute=0; minute < 90; minute++){

            //What happens in this minute?
            double action = Math.random();

            // Fault
            if(action < Variables.FAULT_FACTOR){
                this.faultEvent(minute);
            }

            // Attack
            if(action > Variables.ATTACK_FACTOR){
                this.attackEvent(minute);
            }

            // Otherwise nothing happens

            if(minute == 45)
                System.out.println("[BREAK]");

        }

        return this.finalResult;
    }
}
