
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Developer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Team;

/**
 * Adds a person to the address book.
 */
public class AddDevToTeamCommand extends Command {

    public static final String COMMAND_WORD = "dev2team";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds this developer to the Team. "
            + "Parameters: "
            + PREFIX_NAME + "Developer Name "
            + PREFIX_TEAMNAME + "Team Name "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TEAMNAME + "ABC ";

    public static final String MESSAGE_SUCCESS = "New developer added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This developer already exists in this team/team doesnt exist";

    private final Name devToAdd;
    private final Name teamToAddTo;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddDevToTeamCommand(Name teamName, Name developer) {
        requireNonNull(teamName);
        requireNonNull(developer);
        devToAdd = developer;
        teamToAddTo = teamName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        //duplicate or team doesnt exist
        if (!model.invalidAddToTeam(teamToAddTo, devToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addToTeam(teamToAddTo, devToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(teamToAddTo, devToAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddDevToTeamCommand)) {
            return false;
        }

        AddDevToTeamCommand otherAddCommand = (AddDevToTeamCommand) other;
        return devToAdd.equals(otherAddCommand.devToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("devToAdd", devToAdd)
                .toString();
    }
}
