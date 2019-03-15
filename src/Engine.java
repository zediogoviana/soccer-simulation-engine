
public class Engine {

    public static void main(String[] args) throws Exception {

        //Load info from CSV files
        LoadInfo info = new LoadInfo(args[0], args[1]);

        //Start Simulation
        Team home = info.homeTeam;
        Team away = info.awayTeam;

        //Input info about Referee
        Referee ref = new Referee("Jose Viana",
                "portuguese",
                Integer.valueOf(args[2]));


        Game game = new Game(home, away, ref);
        Result res = game.startSimulation();

        //Show results
        System.out.println("\n");
        System.out.println(home.name + ": " + res.goalsHomeTeam);
        System.out.println(away.name + ": " + res.goalsAwayTeam);

        System.out.println("Red Cards " + home.name + ": " + res.redCardsHomeTeam);
        System.out.println("Red Cards " + away.name + ": " + res.redCardsAwayTeam);




    }

}
