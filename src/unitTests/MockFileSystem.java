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
package unitTests;

import filesystem.Directory;
import filesystem.FSElement;
import filesystem.FSElementNotFoundException;
import filesystem.File;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;

public class MockFileSystem implements FileSystem {

  /**
   * Simulates changing the working directory. Use "validPath" to simulate a
   * valid path behavior. Use "invalidPath" and "nonExistentFilePath" to throw
   * MalformedPathException and FSElementNotFoundException, respectively.
   *
   * @param path The path.
   */
  @Override
  public void changeWorkingDir(Path path)
      throws MalformedPathException, FSElementNotFoundException {
    switch (path.removeFirst()) {
      case "validPath":
        break;
      case "invalidPath":
        throw new MalformedPathException();
      case "nonExistentFilePath":
        throw new FSElementNotFoundException();
    }
  }

  /**
   * Simulates getting the absolute path of an element in the filesystem.
   *
   * @param theElement The wanted element.
   * @return Returns "/some/valid/directory/[theDir.getName()]"
   */
  @Override
  public String getAbsolutePathOfFSElement(FSElement theElement) {
    return "/some/valid/directory/" + theElement.getName();
  }

  /**
   * Simulates getting a file by its path string. Use "validPath" to simulate a
   * valid path behavior. Use "invalidPath" and "nonExistentFilePath" to throw
   * MalformedPathException and FSElementNotFoundException, respectively. Using
   * any other string for pathString will throw a FSElementNotFoundException by
   * default.
   *
   * @param path The path.
   * @return Returns a file with the name "someFile" and the contents "some file
   * contents".
   */
  @Override
  public File<?> getFileByPath(Path path)
      throws MalformedPathException, FSElementNotFoundException {
    switch (path.removeFirst()) {
      case "validPath":
        return new File<>("someFile", "some file contents", null);
      case "invalidPath":
        throw new MalformedPathException();
      case "nonExistentFilePath":
        throw new FSElementNotFoundException();
      default:
        throw new FSElementNotFoundException();
    }
  }

  /**
   * Simulates getting a directory by its path string. Use "validPath" to
   * simulate a valid path behavior. Use "invalidPath" and "nonExistentFilePath"
   * to throw MalformedPathException and FSElementNotFoundException,
   * respectively. Using any other string for pathString will throw a
   * FSElementNotFoundException by default.
   *
   * @param path The path.
   * @return Returns a directory with the name "someDirectory" with no parent or
   * children.
   */
  @Override
  public Directory getDirByPath(Path path)
      throws MalformedPathException, FSElementNotFoundException {
    switch (path.removeFirst()) {
      case "validPath":
        return new Directory("someDirectory", null);
      case "invalidPath":
        throw new MalformedPathException();
      case "nonExistentFilePath":
        throw new FSElementNotFoundException();
      default:
        throw new FSElementNotFoundException();
    }
  }

  /**
   * Simulates getting a FS element by its path string. Use "validPath" to
   * simulate a valid path behavior. Use "invalidPath" and "nonExistentFilePath"
   * to throw MalformedPathException and FSElementNotFoundException,
   * respectively. Using any other string for pathString will throw a
   * FSElementNotFoundException by default.
   *
   * @param path The path.
   * @return Returns an FS element with the name "someFSElement"
   */
  @Override
  public FSElement getFSElementByPath(Path path)
      throws MalformedPathException, FSElementNotFoundException {
    switch (path.removeFirst()) {
      case "validPath":
        return new FSElement("someFSElement", null);
      case "invalidPath":
        throw new MalformedPathException();
      case "nonExistentFilePath":
        throw new FSElementNotFoundException();
      default:
        throw new FSElementNotFoundException();
    }
  }

  /**
   * Simulates getting the working directory.
   *
   * @return Returns a directory with name "myWorkingDirectory" with no parent
   * or children.
   */
  @Override
  public Directory getWorkingDir() {
    return new Directory("myWorkingDirectory", null);
  }

  /**
   * Simulates getting the path to the working directory.
   *
   * @return Returns the string "/some/path/to/myWorkingDirectory".
   */
  @Override
  public String getWorkingDirPath() {
    return "/some/path/to/myWorkingDirectory";
  }

  /**
   * Simulates getting the root directory.
   *
   * @return Returns a directory with name "root" with no parent or children.
   */
  @Override
  public Directory getRoot() {
    return new Directory("root", null);
  }
}
