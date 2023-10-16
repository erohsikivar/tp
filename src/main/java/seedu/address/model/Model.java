package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Developer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Team;

import static java.util.Objects.requireNonNull;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook, List<Team> teamStructure);

    void clearAddressBook();

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    boolean invalidAddToTeam(Name teamToAddTo, Name devToAdd);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    //team level functions to model
    boolean hasTeam(Name teamName);

    /**
     * Adds a team to the team structure.
     * The team must not already exist in the address book.
     */
    void addTeam(Name teamName, Person teamLeader);
    void addToTeam(Name teamToAddTo, Name devToAdd);

    /**
     * Removes {@code key} from team structure.
     * {@code key} must exist in the address book.
     */
    void removeTeam(Team key);

    Team getTeam(Name teamName);

    /**
     * Replaces the given team {@code target} in the list with {@code editedTeam}.
     * {@code target} must exist in the address book.
     * The team identity of {@code editedTeam} must not be the same as another existing team in the address book.
     */
    void setTeams(Team target, Team editedTeam);

    AddressBook getWritableAddressBook();
}
