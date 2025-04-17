
import javafx.beans.property.SimpleStringProperty;

/**
 * gestione team sulla gui
 */
public class TeamMember {
    private final SimpleStringProperty name;
    private final SimpleStringProperty github;

    /**
     * visione team
     * @param name name
     * @param github github
     */
    public TeamMember(String name, String github) {
        this.name = new SimpleStringProperty(name);
        this.github = new SimpleStringProperty(github);
    }

    /**
     *  getname
     * @return return
     */
    public String getName() {
        return name.get();
    }

    /**
     *  link per github
     * @return return
     */

    public String getGithub() {
        return github.get();
    }
}