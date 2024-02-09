package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;

public class Branch implements Serializable {
    private String name;
    private static String lastCommit;

    public Branch(String _name, String _lastCommit) {
        name = _name;
        lastCommit = _lastCommit;
    }

    public static void setLastCommit(String newCommit) {
        lastCommit = newCommit;
    }

    @Override
    public String toString() {
        String objContent = name + lastCommit;
        return objContent;
    }

    public String getUID() {
        return sha1(toString());
    }

    public void saveBranch() {
        File bracnhFile = join(Repository.BRANCH_DIR, getUID());
        writeObject(bracnhFile, this);
    }

    public String getLastCommit() {
        return lastCommit;
    }
}
