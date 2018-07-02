// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: ursualex
// UT Student #: 1004357199
// Author: Alexander Ursu
//
// Student2:
// UTORID user_name: greffal1
// UT Student #: 1004254497
// Author: Alexander Greff
//
// Student3:
// UTORID user_name: sankarch
// UT Student #: 1004174895
// Author: Chedy Sankar
//
// Student4:
// UTORID user_name: kamins42
// UT Student #: 1004431992
// Author: Anton Kaminsky
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package commands;

import containers.CommandArgs;
import containers.CommandDescription;
import driver.JShell;
import io.Writable;
import utilities.Command;
import utilities.ExitCode;

/**
 * The exit command.
 *
 * @author greff
 */
public class CmdExit extends Command {
  // Setup command information
  /**
   * The name of the command.
   */
  private final String NAME = "exit";
  /**
   * The description of the command.
   */
  private CommandDescription DESCRIPTION = new CommandDescription(
      "Exits the currently running JShell.", new String[]{"exit"});

  /**
   * Executes the exit command to shut the JShell down.
   *
   * @param args The arguments for the command.
   * @param out The writable for any normal output of the command.
   * @param errOut The writable for any error output of the command.
   * @return Returns the exit status of the command.
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out, Writable errOut) {
    // Tell the JShell to exit
    JShell.exit();
    return ExitCode.SUCCESS;
  }

  /**
   * Checks if args is a valid CommandArgs instance for this command
   *
   * @param args The command arguments
   * @return Returns true iff args is a valid for this command
   */
  @Override
  public boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length == 0
        && args.getNumberOfNamedCommandParameters() == 0
        && args.getRedirectOperator().equals("")
        && args.getTargetDestination().equals("");
  }

  /**
   * Gets the name of the command
   *
   * @return Returns the name of the command
   */
  @Override
  public String getName() {
    return NAME;
  }

  /**
   * Gets the documentation for this command
   *
   * @return The command description
   */
  @Override
  public CommandDescription getDescription() {
    return DESCRIPTION;
  }

}
