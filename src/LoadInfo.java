import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadInfo {

    public Team homeTeam;
    public Team awayTeam;

    public LoadInfo(String homeFile, String awayFile) throws Exception {
        List<List<String>> home = new ArrayList<>();
        BufferedReader br1 = new BufferedReader(new FileReader(homeFile));

        List<List<String>> away = new ArrayList<>();
        BufferedReader br2 = new BufferedReader(new FileReader(awayFile));

        String line;

        // Load info about team
        getTeamInfo(br1, br2);

        while ((line = br1.readLine()) != null) {
            String[] values = line.split(";");
            home.add(Arrays.asList(values));
        }

        while ((line = br2.readLine()) != null) {
            String[] values = line.split(";");
            away.add(Arrays.asList(values));
        }


        homeTeam.buildTeam(home);
        awayTeam.buildTeam(away);
    }


    private void getTeamInfo(BufferedReader br1, BufferedReader br2) throws Exception {
        String line = br1.readLine();
        String[] valuesHome = line.split(";");
        this.homeTeam = new Team(valuesHome[0], //Name
                Integer.valueOf(valuesHome[1]), //Form
                Integer.valueOf(valuesHome[2]), //Supporters
                Integer.valueOf(valuesHome[3])); //PlayingStyle

        String line2 = br2.readLine();
        String[] valuesAway = line2.split(";");
        this.awayTeam = new Team(valuesAway[0], //Name
                Integer.valueOf(valuesAway[1]), //Form
                Integer.valueOf(valuesAway[2]), //Supporters
                Integer.valueOf(valuesAway[3])); //PlayingStyle
    }

}
