import java.time.LocalDate;
import java.time.Period;

public class Player {

    public String name;
    public String position;
    public int statAtt;
    public int statDef;
    public int statMed;
    public String status;
    public String nationality;
    private LocalDate birthDate;
    public String fullName;

    public Player(String[] player) {
        this.name = player[0];
        this.position = player[1];
        this.statAtt = Integer.valueOf(player[2]);
        this.statDef = Integer.valueOf(player[3]);
        this.statMed = (statAtt + statAtt) / 2;
        this.status = player[4];
        this.nationality = player[5];
        this.birthDate = LocalDate.parse(player[6]);
        this.fullName = player[7];
    }

    public int getAge(){
        LocalDate today = LocalDate.now();

        return Period.between(this.birthDate, today).getYears();
    }
}
