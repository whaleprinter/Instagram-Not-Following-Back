import java.io.File;
import java.util.Scanner;
import java.util.TreeSet;
import java.io.FileWriter;
import java.io.IOException;

public class FollowersComparer {
    public static TreeSet<String> htmlToTreeSet(File file) throws IOException {
        String linkStem = "https://www.instagram.com/";
        TreeSet<String> links = new TreeSet<>();
        
        // Read the file
        Scanner scanner = new Scanner(file);
        StringBuilder fileContent = new StringBuilder();
        
        while (scanner.hasNextLine()) {
            fileContent.append(scanner.nextLine());
        }
        
        scanner.close();
        
        // Extract only the links from the raw HTML file
        String fileContentString = fileContent.toString();
        String[] words = fileContentString.split("\"");

        // Remove the link stem and add the username to the TreeSet
        for (String word : words) {
            if (word.startsWith(linkStem)) {
                String username = word.substring(linkStem.length());
                links.add(username);
            }
        }

        return links;
    }

    public static void main(String[] args) throws IOException {
        // Create the files
        File follower = new File("followers_1.html");
        File following = new File("following.html");
        File output = new File("opps.txt");
        File output2 = new File("youOppin.txt");

        TreeSet<String> followersSet = htmlToTreeSet(follower);
        TreeSet<String> followingSet = htmlToTreeSet(following);
        
        TreeSet<String> notFollowingBack = new TreeSet<>();

        // Write the usernames that don't follow back to the output file
        FileWriter myWriter = new FileWriter(output);
        for (String personFollowed : followingSet) {
            if (!followersSet.contains(personFollowed)) {
                notFollowingBack.add(personFollowed);
                myWriter.write(personFollowed + "\n");
            }
        }
        myWriter.close();

        FileWriter myWriter2 = new FileWriter(output2);
        for (String personFollowed : followersSet) {
            if (!followingSet.contains(personFollowed)) {
                myWriter2.write(personFollowed + "\n");
            }
        }
        myWriter2.close();
    }
}
