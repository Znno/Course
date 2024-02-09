package gitlet;

import edu.princeton.cs.algs4.ST;

import java.io.File;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;

import static gitlet.Utils.*;


/**
 * Represents a gitlet repository.
 * does at a high level.
 *
 * @author Elabyad & Znno
 */
public class Repository {
    /**
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File COMMITS_DIR = join(GITLET_DIR, "commits");
    public static final File STAGING_DIR = join(GITLET_DIR, "staging");
    public static final File ADD_STAGE_FILE = join(STAGING_DIR, "add");
    public static final File REMOVE_STAGE_FILE = join(STAGING_DIR, "remove");
    public static final File BLOBS_DIR = join(GITLET_DIR, "blobs");
    public static final File BRANCH_DIR = join(GITLET_DIR, "branches");
    public static final File HEAD = join(GITLET_DIR, "head");
    public static String CURRENT_BRANCH;

    public static void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        GITLET_DIR.mkdir();
        COMMITS_DIR.mkdir();
        STAGING_DIR.mkdir();
        writeObject(ADD_STAGE_FILE, new HashMap<String, String>());
        writeObject(REMOVE_STAGE_FILE, new HashMap<String, String>());
        BLOBS_DIR.mkdir();
        BRANCH_DIR.mkdir();
        Branch master = new Branch("master", null);
        Commit intialCommit = new Commit();
        Branch.setLastCommit(intialCommit.getUID());
        intialCommit.saveCommit();
        writeContents(HEAD, intialCommit.getUID());
        master.saveBranch();
        CURRENT_BRANCH = master.getUID();
    }

    public static void add(String name) {
        Blob newBlob = new Blob(name);
        String sha = readContentsAsString(HEAD);
        File file = join(COMMITS_DIR, sha);
        Commit currentCommit = readObject(file, Commit.class);
        HashMap<String, String> addMap = readObject(ADD_STAGE_FILE, HashMap.class);
        if (currentCommit.getBlob(name) == null) {
            if (addMap.get(name) != null) {
                File temp = join(BLOBS_DIR, addMap.get(name));
                restrictedDelete(temp);
            }
            addMap.put(name, newBlob.getUID());
            newBlob.saveBlob();
        } else {
            if (currentCommit.getBlob(name).equals(newBlob.getUID())) {
                if (addMap.get(name) != null) {
                    File temp = join(BLOBS_DIR, addMap.get(name));
                    restrictedDelete(temp);
                }
                addMap.remove(name);
            } else {
                if (addMap.get(name) != null) {
                    File temp = join(BLOBS_DIR, addMap.get(name));
                    restrictedDelete(temp);
                }
                addMap.put(name, newBlob.getUID());
                newBlob.saveBlob();
            }
        }
        writeObject(ADD_STAGE_FILE, addMap);
        HashMap<String, String> removeMap = readObject(REMOVE_STAGE_FILE, HashMap.class);
        if (removeMap.get(name) != null) {
            removeMap.remove(name);
        }
        writeObject(REMOVE_STAGE_FILE, removeMap);
    }

    public static void rm(String name) {
        HashMap<String, String> addMap = readObject(ADD_STAGE_FILE, HashMap.class);
        String sha = readContentsAsString(HEAD);
        File file = join(COMMITS_DIR, sha);
        Commit currentCommit = readObject(file, Commit.class);
        if (addMap.get(name) == null && currentCommit.getBlob(name) == null) {
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }
        if (addMap.get(name) != null) {
            File temp = join(BLOBS_DIR, addMap.get(name));
            restrictedDelete(temp);
            addMap.remove(name);
        }
        if (currentCommit.getBlob(name) != null) {
            HashMap<String, String> removeMap = readObject(REMOVE_STAGE_FILE, HashMap.class);
            removeMap.put(name, currentCommit.getBlob(name));
            File temp = join(CWD, name);
            restrictedDelete(temp);
            writeObject(REMOVE_STAGE_FILE, removeMap);
        }
        writeObject(ADD_STAGE_FILE, addMap);
    }

    public static void commit(String message) {
        HashMap<String, String> addMap = readObject(ADD_STAGE_FILE, HashMap.class);
        HashMap<String, String> removemap = readObject(REMOVE_STAGE_FILE, HashMap.class);
        if (addMap.isEmpty() && removemap.isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }
        String sha = readContentsAsString(HEAD);
        File file = join(COMMITS_DIR, sha);
        Commit currentCommit = readObject(file, Commit.class);
        Commit newCommit = new Commit(message, currentCommit.getUID());
        newCommit.addBlobs(currentCommit.getBlobs());
        newCommit.addBlobs(addMap);
        newCommit.removeBlobs(removemap);
        addMap.clear();
        removemap.clear();
        writeObject(ADD_STAGE_FILE, addMap);
        writeObject(REMOVE_STAGE_FILE, removemap);
        newCommit.saveCommit();
        writeContents(HEAD, newCommit.getUID());
    }

    public static void log() {
        String sha = readContentsAsString(HEAD);
        File file = join(COMMITS_DIR, sha);
        Commit currentCommit = readObject(file, Commit.class);
        while (currentCommit != null) {
            System.out.println("===");
            System.out.println("commit " + currentCommit.getUID());
            Formatter f = new Formatter().format("%1$ta %1$tb %1$td %1$tT %1$tY %1$tz", currentCommit.getDate());
            String fDate = f.toString();
            System.out.println("Date: " + fDate);
            System.out.println(currentCommit.getMessage());
            System.out.println();
            if (currentCommit.getParent() == null) {
                break;
            }
            File nextCommitFile = join(COMMITS_DIR, currentCommit.getParent());
            currentCommit = readObject(nextCommitFile, Commit.class);
        }
    }

    public static void globalLog() {
        List<String> files = plainFilenamesIn(COMMITS_DIR);
        for (String commitID : files) {
            File file = join(COMMITS_DIR, commitID);
            Commit currentCommit = readObject(file, Commit.class);
            System.out.println("===");
            System.out.println("commit " + currentCommit.getUID());
            Formatter f = new Formatter().format("%1$ta %1$tb %1$td %1$tT %1$tY %1$tz", currentCommit.getDate());
            String fDate = f.toString();
            System.out.println("Date: " + fDate);
            System.out.println(currentCommit.getMessage());
            System.out.println();
        }
    }

    public static void find(String message) {
        List<String> files = plainFilenamesIn(COMMITS_DIR);
        boolean found = false;
        for (String commitID : files) {
            File file = join(COMMITS_DIR, commitID);
            Commit currentCommit = readObject(file, Commit.class);
            if (!(currentCommit.getMessage().equals(message)))
                continue;
            System.out.println(currentCommit.getUID());
            found = true;
        }
        if (!found) {
            System.out.println("Found no commit with that message.");
        }

    }

    public static void checkoutFile(String fileName) {
        String sha = readContentsAsString(HEAD);
        File file = join(COMMITS_DIR, sha);
        Commit currentCommit = readObject(file, Commit.class);
        String blobID = currentCommit.getBlob(fileName);
        if (blobID == null) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
        File blobFile = join(BLOBS_DIR, blobID);
        Blob fileBlob = readObject(blobFile, Blob.class);

        File cwdFile = join(CWD, fileName);
        writeContents(cwdFile, fileBlob.getContent());

    }

    public static void checkoutCommit(String commitID, String fileName) {
        File commitFile = join(COMMITS_DIR, commitID);
        if (!commitFile.exists()) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
        Commit checkCommit = readObject(commitFile, Commit.class);
        String blobID = checkCommit.getBlob(fileName);
        if (blobID == null) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
        File blobFile = join(BLOBS_DIR, blobID);
        Blob fileBlob = readObject(blobFile, Blob.class);
        File cwdFile = join(CWD, fileName);
        writeContents(cwdFile, fileBlob.getContent());
    }
}
