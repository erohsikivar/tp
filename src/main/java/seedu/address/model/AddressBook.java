package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.*;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;

    private List<Team> teams;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        teams = new ArrayList<>();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied, List<Team> teamStructure) {
        this();
        resetData(toBeCopied, teamStructure);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }
    public void setTeamsStructure(List<Team> teams) {
        this.teams = teams;
    }

    /**
     * Clears all existing data in the address book.
     */
    public void clear() {
        AddressBook empty = new AddressBook();
        setPersons(empty.getPersonList());
        setTeamsStructure(empty.getTeamsList());
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData, List<Team> newStructure) {
        requireNonNull(newData);
        requireNonNull(newStructure);

        setPersons(newData.getPersonList());
        setTeamsStructure(newStructure);
    }

    //// person-level operations-------------------------------------------------------------------------------------

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    public boolean invalidAddToTeam(Name teamToAddTo, Name devToAdd) {
        requireNonNull(teamToAddTo);
        requireNonNull(devToAdd);

        Team team = getTeam(teamToAddTo);
        if (team == null) {
            return false;
        } else {
            return !team.containsDev(devToAdd);  //if true, then you can add this dev. Else he alr exists.
        }

    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    public Person getPersonByHashCode(int hash) {
        return persons.getPersonByHashCode(hash);
    }
    public Person getPerson(Name name) {
        return persons.getPerson(name);
    }


    //// teams-level operations----------------------------------------------------------------------------------------

    public boolean hasTeam(Name teamName) {
        requireNonNull(teamName);
        String teamNameInString = teamName.toString();
        boolean teamNameExists = teams.stream()
                .anyMatch(teamNames -> teamNames.getTeamName().equals(teamNameInString));
        return teamNameExists;
    }
    public Team getTeam(Name teamName) {
        requireNonNull(teamName);
        String teamNameInString = teamName.toString();

        Optional<Team> matchingTeam = teams.stream()
                .filter(team -> team.getTeamName().equals(teamNameInString))
                .findFirst();

        return matchingTeam.orElse(null);
    }

    /**
     * Adds a team to the team structure.
     * The team must not already exist in the address book.
     */
    public void addTeam(Team t) {
        teams.add(t);
    }

    /**
     * Removes {@code key} from team structure.
     * {@code key} must exist in the address book.
     */
    public void removeTeam(Team key) {
        teams.remove(key);
    }



    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setTeams(Team target, Team editedTeam) {
        requireNonNull(editedTeam);

        //todo: more data protection
        teams.set(teams.indexOf(target), editedTeam);

    }


    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }


    //todo: need override? data protection will be implemented later
    public List<Team> getTeamsList() {
        return teams;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons) && teams.equals(otherAddressBook.teams);
    }


    //todo: see if we need to incorporate the hash code of teams
    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
