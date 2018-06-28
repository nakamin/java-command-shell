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
import filesystem.DirectoryStack;
import utilities.Command;

public class CmdPushd extends Command {

  private final String NAME = "pushd";
  private DirectoryStack dirStack = DirectoryStack.getInstance();

  @Override
  public String execute(CommandArgs args) {
    String curPath = fileSystem.getWorkingDirPath();
    dirStack.push(curPath);
    // make command args to call the cd command with
    String[] fileNameArg = args.getCommandParameters();
    CommandArgs cdArgs = new CommandArgs("cd", fileNameArg);
    // execute the cd command to go to the given directory
    commandManager.executeCommand(cdArgs);
    // this command does not print anything
    return "";
  }

  @Override
  public boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length == 1
        && args.getNumberOfNamedCommandParameters() == 0
        && args.getRedirectOperator().equals("")
        && args.getTargetDestination().equals("");
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getDescription() {
    return "Pushd command description:\n"
        + "Description:\n"
        + "    - pushd: pushes the current directory to the top of the directory"
        + " stack and changes the current working directory to the"
        + " given directory\n"
        + "\n\nUsage:\n"
        + "    - pushd DIRECTORY";
  }

}
