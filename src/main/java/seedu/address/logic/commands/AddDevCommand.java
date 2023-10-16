package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddDevCommand extends Command {

    public static final String COMMAND_WORD = "newdev";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a developer to the Team. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New developer added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This developer already exists in the team";

    private final Person devToAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddDevCommand(Person person) {
        requireNonNull(person);
        devToAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(devToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(devToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(devToAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddDevCommand)) {
            return false;
        }

        AddDevCommand otherAddCommand = (AddDevCommand) other;
        return devToAdd.equals(otherAddCommand.devToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("devToAdd", devToAdd)
                .toString();
    }
}
