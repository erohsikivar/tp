package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Team in the address book.
 * A Team consists of a TeamLeader and multiple Developers.
 */
public class Team {
    private Name teamName;
    private int leaderHashCode;
    //private HashSet<> developerHashCodeSet;
    private Person teamLeader;
    private Name teamLeaderName;
    private ArrayList<Developer> developers;

    //todo: set will ignore duplicated values, do we need to notify the user that he added a duplicate value or not?
    //use the hash code for representing each developer. The hash code will be updated when there is a change
    //to the developer's information.
    //private final Set<Integer> developerHashCodeSet;


    /**
     * Constructs a new Team instance with the specified TeamLeader.
     *
     * @param leaderName The leader of the team.
     */

    public Team(Name teamName, Name leaderName) {
        this.teamName = teamName;
        //this.leaderHashCode = leader.hashCode();
        //this.developerHashCodeSet = new HashSet<>();
        this.teamLeaderName = leaderName;
    }

    public Team(Name teamName, Person teamLeader) {
        this.teamName = teamName;
        this.teamLeader = teamLeader;
        this.teamLeaderName = teamLeader.getName();
    }

    /**
     * Adds a Developer to the team.
     *
     * @param developer The developer to be added.
     */
//    public void addDeveloper(int developer) {
//        this.developerHashCodeSet.add(developer.hashCode());
//    }

    /**
     * Adds a Developer to the team.
     *
     * @param developerHashCode The developer to be removed.
     */
//    public void removeDeveloper(int developerHashCode) {
//        this.developerHashCodeSet.remove(developerHashCode);
//    }


    /**
     * Retrieves the TeamLeader of the team.
     *
     * @return The TeamLeader of the team.
     */
    public int getLeaderHashCode() {
        return leaderHashCode;
    }

    /**
     * Retrieves the list of Developers in the team.
     *
     * @return A list of Developers.
     */
//    public Set<Integer> getDevelopers() {
//        return developerHashCodeSet;
//    }

    public Name getTeamName() {
        return teamName;
    }
    public Name getTeamLeaderName() {
        return teamLeaderName;
    }

    public boolean containsDev(Name name) {
        boolean devExistsInTeam = developers.stream()
                .anyMatch(devNames -> devNames.getName().equals(name));
        return devExistsInTeam;
    }
    public void addDev(Developer developer) {
        developers.add(developer);
    }

}
