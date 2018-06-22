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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import containers.CommandArgs;
import utilities.Command;

public class CmdMan extends Command {
  // Setup constants
  private final String NAME = "man";
  private final String DOCUMENTATION_PATH = "../documentation";
  private final String errorOutput = "Invalid command, please try again.";

  /**
   * Executes the man command with the arguments args
   * 
   * @param args The command arguments
   * @return Returns the output of the command
   */
  @Override
  public String execute(CommandArgs args) {
    // Check if the given args are valid for this command
    if (isValidArgs(args) == false) {
      // If invalid then return the error string
      return errorOutput;
    }

    // Initialize the reference to the command's man file
    File cmdManFile = null;

    // Load the documentation directory
    URL url = getClass().getResource(DOCUMENTATION_PATH);
    File manDir = new File(url.getPath());

    // Set up the filter that only accepts valid command manual text files
    FilenameFilter manFilter = new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.startsWith("Man") && name.endsWith(".txt");
      }
    };

    // Get the expected name of the current command's man file
    String manCmdName = args.getCommandParameters()[0];
    String targetName = "Man" + manCmdName.substring(0, 1).toUpperCase()
        + manCmdName.substring(1) + ".txt";

    // Iterate through each man file
    for (File manFile : manDir.listFiles(manFilter)) {
      // If we find the man file for the wanted command
      if (manFile.getName().equals(targetName)) {
        // Store the file and break out of the loop
        cmdManFile = manFile;
        break;
      }
    }

    // If we've actually found the file
    if (cmdManFile != null) {
      try {
        // Open a buffered reader
        BufferedReader br = new BufferedReader(new FileReader(cmdManFile));

        String line = "";
        String output = "";

        // Iterate through each line of the file and append it to the output
        while ((line = br.readLine()) != null) {
          output += line + "\n";
        }

        // Trim any unnecessary new lines and return the output string
        return output.trim();

      } catch (FileNotFoundException e) {
        // Do nothing
      } catch (IOException e) {
        // Do nothing
      }
    }

    // If we get here then that implies something threw and error / went wrong
    // So return the error message
    return errorOutput;
  }

  /**
   * A helper checking if args is a valid CommandArgs instance for this command
   * 
   * @param args The command arguments
   * @return Returns true iff args is a valid for this command
   */
  private boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length == 1
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
}