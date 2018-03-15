import java.util.*;
import java.util.function.BiFunction;

public class DictionaryTree {

    private Map<Character, DictionaryTree> children = new LinkedHashMap<>();

    /**
     * Inserts the given word into this dictionary.
     * If the word already exists, nothing will change.
     *
     * @param word the word to insert
     */
    void insert(String word) {
        insertHelper(word, this);
    }


    /**
     * Helper method for insert()
     * Recursively adds the characters from the word to maps
     *
     * @param word The word to be inserted
     * @param tree The tree to operate on
     */
    private void insertHelper(String word, DictionaryTree tree) {
        if (!(word == null || word.equals(""))) {
            Character letter = word.charAt(0);
            DictionaryTree letterChildren;
            if (tree.children.containsKey(letter)) {
                letterChildren = tree.children.get(letter);
            }
            else {
                letterChildren = new DictionaryTree();
            }
            tree.children.put(letter, letterChildren);
            insertHelper(word.substring(1), letterChildren);
        }
    }

    /**
     * Inserts the given word into this dictionary with the given popularity.
     * If the word already exists, the popularity will be overriden by the given value.
     *
     * @param word       the word to insert
     * @param popularity the popularity of the inserted word
     */
    void insert(String word, int popularity) {
        throw new RuntimeException("DictionaryTree.insert not implemented yet");
    }

    /**
     * Removes the specified word from this dictionary.
     * Returns true if the caller can delete this node without losing
     * part of the dictionary, i.e. if this node has no children after
     * deleting the specified word.
     *
     * @param word the word to delete from this dictionary
     * @return whether or not the parent can delete this node from its children
     */
    boolean remove(String word) {
        throw new RuntimeException("DictionaryTree.remove not implemented yet");
    }

    /**
     * Determines whether or not the specified word is in this dictionary.
     *
     * @param word the word whose presence will be checked
     * @return true if the specified word is stored in this tree; false otherwise
     */
    boolean contains(String word) {
        throw new RuntimeException("DictionaryTree.contains not implemented yet");
    }

    /**
     * @param prefix the prefix of the word returned
     * @return a word that starts with the given prefix, or an empty optional
     * if no such word is found.
     */
    Optional<String> predict(String prefix) {
        throw new RuntimeException("DictionaryTree.predict not implemented yet");
    }

    /**
     * Predicts the (at most) n most popular full English words based on the specified prefix.
     * If no word with the specified prefix is found, an empty list is returned.
     *
     * @param prefix the prefix of the words found
     * @return the (at most) n most popular words with the specified prefix
     */
    List<String> predict(String prefix, int n) {
        throw new RuntimeException("DictionaryTree.predict not implemented yet");
    }

    /**
     * @return the number of leaves in this tree, i.e. the number of words which are
     * not prefixes of any other word.
     */
    int numLeaves() {
        return numLeavesHelper(0, this);
    }


    /**
     *
     * Helper method for numLeavesHelper()
     * Recursively traverses each level of each subtree
     * until it reaches a leaf node and then adds 1
     * to the counter
     *
     * @param count The current number of leaf nodes
     * @param tree The tree to operate on
     * @return The number of leaf nodes in the tree
     */
    private int numLeavesHelper(int count, DictionaryTree tree) {
        if (tree.children.isEmpty()) {
            return ++count;
        }
        else {
            for (Map.Entry<Character, DictionaryTree> entry : tree.children.entrySet()) {
                count = numLeavesHelper(count, entry.getValue());
            }
        }
        return count;
    }

    /**
     * @return the maximum number of children held by any node in this tree
     */
    int maximumBranching() {
        return maxBranchHelper(0, this);
    }


    /**
     * Helper method for maximumBranch()
     * Recursively traverses each level of each subtree
     * and keeps track of the maximum number of children
     *
     * @param maxBranch Current max amount of children in tree
     * @param tree      The tree to operate on
     * @return The maximum number of children in the tree
     */
    private int maxBranchHelper(int maxBranch, DictionaryTree tree) {
        if (tree.children.isEmpty()) {
            return 0;
        }
        else {

            for (Map.Entry<Character, DictionaryTree> entry : tree.children.entrySet()) {
                int count = 0;
                for (int i = 0; i < tree.children.size(); i++) {
                    ++count;
                }
                maxBranch = Math.max(maxBranch, count);
                maxBranch = Math.max(maxBranch, maxBranchHelper(maxBranch, entry.getValue()));
            }
        }
        return maxBranch;
    }

    /**
     * @return the height of this tree, i.e. the length of the longest branch
     */
    int height() {
        return heightHelper(0, 0, this);
    }


    /**
     * Helper method for height()
     * Recursively goes to end of each subtree
     * and keeps track of the height
     *
     * @param height Current height of tree
     * @param count  Current height of subtree
     * @param tree   The tree to operate on
     * @return The height of the tree
     */
    private int heightHelper(int height, int count, DictionaryTree tree) {
        if (tree.children.isEmpty()) {
            height = Math.max(height, count);
        }
        else {
            for (Map.Entry<Character, DictionaryTree> entry : tree.children.entrySet()) {
                ++count;
                height = heightHelper(height, count, entry.getValue());
                --count;
            }
        }
        return height;
    }

    /**
     * @return the number of nodes in this tree
     */
    int size() {
        return sizeHelper(1, this);
    }


    /**
     * Helper method for size()
     * Recursively counts the number of nodes
     * at each level in each subtree
     *
     * @param count The running total of the number of nodes
     * @param tree  The tree to operate on
     * @return The size of the tree
     */
    private int sizeHelper(int count, DictionaryTree tree) {
        if (!tree.children.isEmpty()) {
            for (Map.Entry<Character, DictionaryTree> entry : tree.children.entrySet()) {
                count = 1 + sizeHelper(count, entry.getValue());
            }
        }

        return count;
    }

    /**
     * @return the longest word in this tree
     */
    String longestWord() {
        return longestWordHelper("", "", this);

    }


    /**
     *
     * Helper method for longestWord()
     * Recursively traverses the tree creating
     * words and checking them against the
     * current longest wordS
     *
     * @param longestWord The current longest word
     * @param currentWord THe current word
     * @param tree THe tree to operate on
     * @return THe longest word in the tree
     */
    private String longestWordHelper(String longestWord, String currentWord, DictionaryTree tree) {
        if (tree.children.isEmpty()) {
            return longestWord;
        }
        else {

            for (Map.Entry<Character, DictionaryTree> entry : tree.children.entrySet()) {
                currentWord += entry.getKey();
                if (currentWord.length() > longestWord.length()) {
                    longestWord = currentWord;
                    longestWord = longestWordHelper(longestWord, currentWord, entry.getValue());
                }
                else {
                    longestWord = longestWordHelper(longestWord, currentWord, entry.getValue());
                }
                currentWord = currentWord.substring(0, currentWord.length() - 1);
            }
        }
        return longestWord;
    }

    /**
     * @return all words stored in this tree as a list
     */
    List<String> allWords() {
        throw new RuntimeException("DictionaryTree.allWords not implemented yet");
    }

    /**
     * Folds the tree using the given function. Each of this node's
     * children is folded with the same function, and these results
     * are stored in a collection, cResults, say, then the final
     * result is calculated using f.apply(this, cResults).
     *
     * @param f   the summarising function, which is passed the result of invoking the given function
     * @param <A> the type of the folded value
     * @return the result of folding the tree using f
     */
    <A> A fold(BiFunction<DictionaryTree, Collection<A>, A> f) {
        throw new RuntimeException("DictionaryTree.fold not implemented yet");
    }


}
