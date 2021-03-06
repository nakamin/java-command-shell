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

import static utilities.JShellConstants.APPEND_OPERATOR;
import static utilities.JShellConstants.OVERWRITE_OPERATOR;

import containers.CommandArgs;
import containers.CommandDescription;
import filesystem.Directory;
import filesystem.FSElementNotFoundException;
import filesystem.FileSystem;
import io.Console;
import java.util.ArrayList;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

/**
 * The tree command.
 *
 * @author chedy
 */
public class CmdTree extends Command {

  /**
   * Constructs a new command instance.
   *
   * @param fileSystem The file system that the command uses.
   * @param commandManager The command manager that the command uses.
   */
  public CmdTree(FileSystem fileSystem, CommandManager commandManager) {
    super(NAME, DESCRIPTION, fileSystem, commandManager);
  }

  /**
   * The name of the command.
   */
  private static final String NAME = "tree";
  /**
   * The description of the command.
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Prints a tree representation of the entire filesystem, "
              + "starting from the root.",
          "tree")
          .additionalComment(
              "Directory content is listed a tab forward, and below"
                  + " the directory name")
          .build();

  /**
   * Executes the command.
   * 
   * @param args The arguments for the command call.
   * @param console The standard console.
   * @param queryConsole The query console.
   * @param errorConsole The error console.
   * @return Returns the ExitCode of the command, SUCCESS or FAILURE.
   */
  @Override
  protected ExitCode run(CommandArgs args, Console<String> console,
      Console<String> queryConsole, Console<String> errorConsole) {
    Directory root = fileSystem.getRoot();
    String result = (root.getName() + "\n");
    result += (addOn(root, 1));

    // Write all the contents read to the Console and return SUCCESS always
    console.write(result);

    return ExitCode.SUCCESS;
  }

  /**
   * Checks if the arguments are valid for this command.
   * 
   * @param args The command arguments.
   * @return whether or not the arguments are valid for this command.
   */
  @Override
  public boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getNumberOfCommandParameters() == 0
        && args.getNumberOfCommandFieldParameters() == 0
        && args.getNumberOfNamedCommandParameters() == 0
        && (args.getRedirectOperator().equals("")
        || args.getRedirectOperator().equals(OVERWRITE_OPERATOR)
        || args.getRedirectOperator().equals(APPEND_OPERATOR));
  }

  /**
   * Adds on the directory and returns the string represenation.
   * 
   * @param curr The current directory to get names from.
   * @param tabs The amount of tabs to indent the newlines.
   * @return Returns a block of String which represents the filesystem from the curr
   * directory down.
   */
  private String addOn(Directory curr, int tabs) {
    // get proper amount of tabs
    StringBuilder spacing = new StringBuilder();
    for (int i = 0; i < tabs; i++) {
      spacing.append("\t");
    }
    // the name of the curr dir gets inserted in the parent recursive call.
    StringBuilder result = new StringBuilder();
    // get the names of all the files in the directory
    ArrayList<String> files = curr.listFileNames();
    if (files.size() > 0) {
      for (String name : files) {
        result.append(spacing).append(name).append("\n");
      }
    }
    // now finally get all of the subdirectories
    // HashMap<String, Directory> childs = curr.getChildDirs();
    ArrayList<String> childs = curr.listDirNames();
    for (String key : childs) {
      result.append(spacing).append(key).append("\n");
      // result+=addOn(childs.get(key), tabs+1);
      result.append(addOn(curr.getChildDirectoryByName(key), tabs + 1));
    }
    return result.toString();
  }
}
