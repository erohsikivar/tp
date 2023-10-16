package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Developer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Team;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, List<Team> teamStructure) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook, teamStructure);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this.addressBook = new AddressBook();
        this.userPrefs = new UserPrefs();
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook, List<Team> teamStructure) {
        this.addressBook.resetData(addressBook, teamStructure);
    }
    @Override
    public void clearAddressBook() {
        this.addressBook.clear();
    }

    public AddressBook getWritableAddressBook() {
        return addressBook;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

    //team level operations
    public boolean hasTeam(Name teamName) {
        requireNonNull(teamName);
        return addressBook.hasTeam(teamName);
    }

    public boolean invalidAddToTeam(Name teamToAddTo, Name devToAdd) {
        return addressBook.invalidAddToTeam(teamToAddTo, devToAdd);
    }

    /**
     * Adds a team to the team structure.
     * The team must not already exist in the address book.
     */
    public void addTeam(Team team) {
        addressBook.addTeam(team);
    }
    public void addTeam(Name teamName, Person teamLeader) {
        Team team = new Team(teamName, teamLeader);
        addressBook.addTeam(team);
    }

    /**
     * Removes {@code key} from team structure.
     * {@code key} must exist in the address book.
     */
    public void removeTeam(Team key) {
        addressBook.removeTeam(key);
    }


    /**
     * Replaces the given team {@code target} in the list with {@code editedTeam}.
     * {@code target} must exist in the address book.
     * The team identity of {@code editedTeam} must not be the same as another existing team in the address book.
     */
    public void setTeams(Team target, Team editedTeam) {
        requireNonNull(editedTeam);

        //todo: more data protection
        addressBook.setTeams(target, editedTeam);

    }



    public Team getTeam(Name teamName) {
        requireNonNull(teamName);
        return addressBook.getTeam(teamName);
    }
    //only run when you know that team exists
    public void addToTeam(Name teamToAddTo, Name devToAdd) {
        Team team = getTeam(teamToAddTo);
        Developer developer = (Developer) addressBook.getPerson(devToAdd);
        team.addDev(developer);
    }

}
