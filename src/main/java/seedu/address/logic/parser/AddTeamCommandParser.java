package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAMLEADER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAMNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddTeamCommandParser implements Parser<AddTeamCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTeamCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TEAMNAME, PREFIX_TEAMLEADER);

        if (!arePrefixesPresent(argMultimap, PREFIX_TEAMNAME, PREFIX_TEAMLEADER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTeamCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TEAMNAME, PREFIX_TEAMLEADER);
        //returns the string representation of team
        String teamName = ParserUtil.parseTeamName(argMultimap.getValue(PREFIX_TEAMNAME).get());
        Name teamLeaderName = ParserUtil.parseTeamLeader(argMultimap.getValue(PREFIX_TEAMLEADER).get());


        return new AddTeamCommand(teamName, teamLeaderName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
