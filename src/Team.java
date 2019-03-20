import java.util.ArrayList;
import java.util.List;

public class Team {

    public String name;
    public List<Player> players;
    public List<Player> startingEleven;
    public int form;
    public int supporters;
    public int playingStyle;

    public Team(
            String name,
            int form,
            int supporters,
            int playingStyle) {
        this.name = name;
        this.players = new ArrayList<>();
        this.startingEleven = new ArrayList<>();
        this.form = form;
        this.supporters = supporters;
        this.playingStyle = playingStyle;
    }

    public void buildTeam(List<List<String>> list) {
        for (List<String> player : list) {

            String[] itemsArray = new String[player.size()];
            Player pl = new Player(player.toArray(itemsArray));

            if(pl.status.equals("T"))
                this.addTitularPlayer(pl);
            else
                this.addPlayer(pl);
        }
    }

    private void addTitularPlayer(Player pl){
        this.startingEleven.add(pl);
    }

    private void addPlayer(Player pl){
        this.players.add(pl);
    }

    public float getMeanAge(){

        float sumAge = 0;

        for(Player pl: this.startingEleven){
            sumAge += pl.getAge();
        }

        return (sumAge/11);
    }

    public float getAttack(){
        float sumAttack = 0;
        int count = 0;

        for(Player pl: this.startingEleven){
            if (pl.position.equals("A")) {
                sumAttack += pl.statAtt;
                count++;
            }
        }

        return (sumAttack/count);
    }

    private double getMeanAttackingValue() {
        float sumAtt = 0;

        for(Player pl: this.startingEleven){
            sumAtt += pl.statAtt;
        }
        return (sumAtt/11);

    }


    public float getDefence(){
        float sumDefence = 0;
        int count = 0;

        for(Player pl: this.startingEleven){
            if(pl.position.equals("D") || pl.position.equals("G")  ){
                sumDefence += pl.statDef;
                count++;
            }

        }

        return (sumDefence/count);
    }

    public float getMeanValue(){
        float sumMed = 0;

        for(Player pl: this.startingEleven){
            sumMed += pl.statMed;
        }

        return (sumMed/11);
    }

    public float calculateAttack(int minute, int redCards){

        float att = 0;

        //Value of Attack
        att += (this.getAttack() * 0.75) + (this.getMeanValue() * 0.25);

        //Time Effect on Age
        att -= this.ageEffect(minute);

        //Time Effect on Suspensions
        att -= this.redCardsEffect(redCards);

        //Supporters Rate Effect
        att += this.formEffect();

        //Form Rate Effect - High Form increases chances of goal
        //If form good it increases chances of scoring/starting attack
        if(this.form > Variables.MINIMAL_FORM)
            att += this.formEffect();
        else
            att -= this.formEffect();

        //Playing Style Effect
        // 0: Super Defensive
        // 100: Super Attacking
        att += this.playingStyleEffect();

        return att;
    }

    public float calculateGoal(int minute, int redCards){
        float goal = 0;

        //Value of Scoring
        goal += (this.getAttack() * 0.75) + (this.getMeanAttackingValue() * 0.25);

        //Time Effect on Age with time
        goal -= this.ageEffect(minute);

        //Time Effect on Suspensions
        goal -= this.redCardsEffect(redCards);

        //Form Rate Effect - High Form increases chances of goal
        //If form good it increases chances of scoring/starting attack
        if(this.form > Variables.MINIMAL_FORM)
            goal += this.formEffect();
        else
            goal -= this.formEffect();

        return goal;
    }

    public float calculateDefend(int minute, int redCards){
        float defence = 0;

        //Value of Defence
        defence += (this.getDefence() * 0.75) + (this.getMeanValue() * 0.25);

        //Time Effect on Age with time
        defence -= this.ageEffect(minute);

        //Time Effect on Suspensions
        defence -= this.redCardsEffect(redCards);

        //Supporters Rate Effect
        defence += this.supportersEffect();

        //Form Rate Effect
        defence += this.formEffect();

        //Playing Style Effect
        // 0: Super Defensive
        // 100: Super Attacking
        // More attack less defence
        defence += playingStyleEffect();

        return defence;
    }


    private double redCardsEffect(int redCards){
        return redCards * Variables.RED_CARD_EFFECT;
    }

    private double formEffect(){
        return this.form * Variables.FORM_EFFECT;
    }

    private double playingStyleEffect(){
         return  (
                 - this.playingStyle * Variables.PLAYING_STYLE_EFFECT //Attack
         + (100 - this.playingStyle * Variables.PLAYING_STYLE_EFFECT)); //Defence

    }

    private double supportersEffect(){
        int rivalsSupporters = 100 - this.supporters;
        return (this.supporters - rivalsSupporters) * Variables.SUPPORTERS_EFFECT;
    }

    private double ageEffect(int minute){
        return (this.getMeanAge() * Variables.AGE_EFFECT) * (minute * Variables.TIME_EFFECT);
    }


}
