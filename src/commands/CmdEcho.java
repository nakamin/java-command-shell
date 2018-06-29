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
import filesystem.Directory;
import filesystem.File;
import filesystem.FileAlreadyExistsException;
import filesystem.FileNotFoundException;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
import utilities.Command;
import utilities.ExitCode;

public class CmdEcho extends Command {

  private final String NAME = "echo";
  private CommandDescription DESCRIPTION =
      new CommandDescription("Appends or writes a string to a file.",
          new String[]{"echo STRING", "echo STRING [> OUTFILE]",
              "echo STRING [>> OUTFILE]"},
          new String[]{
              "The \">\" character signals to overwrite the file conents.",
              "The \">>\" character signals to append to the file conents."});


  private final String OVERWRITE_OPERATOR = ">";
  private final String APPEND_OPERATOR = ">>";

  @Override
  public ExitCode execute(CommandArgs args, Writable out,
      Writable errOut) {
    // Initialize default command output
    String output = "";
    ExitCode exitValue = ExitCode.SUCCESS;

    // If there is a redirect operator provided
    if (args.getRedirectOperator().length() > 0) {
      // Write to the file
      try {
        exitValue = writeToFile(args, out, errOut);
      } catch (FileAlreadyExistsException e) {
        exitValue = ExitCode.FAILURE;
        errOut.writeln("File already exists");
      }
    }
    // If not
    else {
      // Set the string parameter to the ouput
      output = args.getCommandParameters()[0];
    }

    if (!output.equals("")) {
      out.writeln(output);
    }

    // Return the output
    return exitValue;
  }

  /**
   * A helper function that writes the given command args to a file
   *
   * @param args The command args
   * @param out The standard output to use.
   * @param errOut The error output to use.
   * @return Returns 0 if the write succeeded, 1 if not.
   */
  private ExitCode writeToFile(CommandArgs args, Writable out,
      Writable
          errOut)
      throws FileAlreadyExistsException {
    // Setup references
    String redirOper = args.getRedirectOperator();
    String strContents = args.getCommandParameters()[0];
    String filePathStr = args.getTargetDestination();

    try {
      FileSystem fs = FileSystem.getInstance();

      // Get the path of the file
      Path filePath = new Path(filePathStr);

      // Get the File
      File file;
      try {
        file = fs.getFileByPath(filePath);
        // If the file does not exist
      } catch (FileNotFoundException e) {
        // Make the new file
        String[] fileSplit = filePathStr.split("/");
        String fileName = fileSplit[fileSplit.length - 1];
        file = new File(fileName);

        int lastSlash = filePathStr.lastIndexOf('/');

        // Get the directory that the file is in
        String dirPathStr =
            (lastSlash > -1) ? filePathStr.substring(0, lastSlash)
                : "";

        if (dirPathStr.equals("")) {
          dirPathStr = "/";
        }

        // Path dirPath = new Path(dirPathStr);
        // Directory dirOfFile = getDirOfFile(dirPath);

        try {
          Path dirPath = new Path(dirPathStr);
          Directory dirOfFile = fs.getDirByPath(dirPath);

          // Add the file to the directory
          dirOfFile.addFile(file);

        } catch (FileNotFoundException e1) {
          errOut.writeln("Error: No directory found");
          return ExitCode.FAILURE;
        }


      } catch (MalformedPathException e1) {
        errOut.writeln("Error: Not a valid file path");
        return ExitCode.FAILURE;
      }

      // Wipe the file contents if the overwrite operator is given in the args
      if (redirOper.equals(OVERWRITE_OPERATOR)) {
        file.clear();
      }
      // Add a new line to the beginning of the string contents of the redirect
      // operator is given
      else if (redirOper.equals(APPEND_OPERATOR)) {
        // write a new line to the file
        file.write("\n");
      }

      // Add the string contents to the file
      file.write(strContents);

    } catch (MalformedPathException e) {
      errOut.write("Error: Invalid path \"" + filePathStr + "\"");
      return ExitCode.FAILURE;
    }

    return ExitCode.SUCCESS;
  }


  /**
   * A helper checking if args is a valid CommandArgs instance for
   * this command
   *
   * @param args The command arguments
   * @return Returns true iff args is a valid for this command
   */
  @Override
  public boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length == 1
        && (args.getRedirectOperator().equals("")
        || args.getRedirectOperator().equals(OVERWRITE_OPERATOR)
        || args.getRedirectOperator().equals(APPEND_OPERATOR))
        && args.getNumberOfNamedCommandParameters() == 0;
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
