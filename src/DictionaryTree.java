import java.io.CharArrayReader;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class DictionaryTree {

    private Map<Character, DictionaryTree> children = new LinkedHashMap<>();
    private boolean endOfWord = false;
    private static int overallPopularity = 1;
    private int popularity;

    /**
     * Inserts the given word into this dictionary.
     * If the word already exists, nothing will change.
     *
     * @param word the word to insert
     */
    void insert(String word) {
        insertHelper(word, overallPopularity, this);
        ++overallPopularity;
    }


    /**
     * Helper method for insert()
     * Recursively adds the characters from the word to maps
     *
     * @param word The word to be inserted
     * @param tree The tree to operate on
     */
    private void insertHelper(String word, int newPopularity, DictionaryTree tree) {
        Optional<String> wordNew = Optional.of(word);
        if (!(word.equals(""))) {
            Character letter = word.charAt(0);
            DictionaryTree letterChildren;
            if (tree.children.containsKey(letter)) {
                letterChildren = tree.children.get(letter);
            }
            else {
                letterChildren = new DictionaryTree();
            }
            tree.children.put(letter, letterChildren);
            insertHelper(word.substring(1), newPopularity, letterChildren);

        }
        else {
            tree.endOfWord = true;
            tree.popularity = newPopularity;

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
        insertHelper(word, popularity, this);
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
        if (contains(word)) {
            return removeHelper(word, false, this);
        }
        else {
            return false;
        }
    }


    /**
     *
     * Helper method for remove()
     * Recursively travels down the branches
     * of the tree using the letters from the
     * word, and if the nodes have no
     * children then the node is deleted
     *
     * @param word The word to be removed
     * @param cleanRemove Whether the word could be cleanly deleted
     * @param tree The tree to operate on
     * @return A boolean for whether the word was cleanly removed or not
     */
    private boolean removeHelper(String word, boolean cleanRemove, DictionaryTree tree) {
        Optional<String> wordNew = Optional.of(word);
        if (!(word.equals(""))) {
            Character c = word.charAt(0);
            if (tree.children.get(c).children.isEmpty()) {
                tree.children.remove(c);
                cleanRemove = true;
            }
            else {
                cleanRemove = false;
                if (word.length() == 1) {
                    tree.children.get(c).endOfWord = false;
                }
                cleanRemove = removeHelper(word.substring(1), cleanRemove, tree.children.get(c));
            }
        }
        return cleanRemove;
    }

    /**
     * Determines whether or not the specified word is in this dictionary.
     *
     * @param word the word whose presence will be checked
     * @return true if the specified word is stored in this tree; false otherwise
     */
    boolean contains(String word) {
        return containsHelper(word, false, "", this, false);
    }


    /**
     *
     * Helper method for contains()
     * Recursively traverses each node of the tree
     * until a word is created that matches the
     * selected word
     *
     * @param word The word to be checked
     * @param isContained Whether the word has been found or not
     * @param currentWord The current word
     * @param tree The tree to operate on
     * @return A boolean for whether the word was found or not
     */
    private boolean containsHelper(String word, boolean isContained, String currentWord, DictionaryTree tree, boolean prefix) {
        if (tree.children.isEmpty()) {
            return isContained;
        }
        else {

            for (Map.Entry<Character, DictionaryTree> entry : tree.children.entrySet()) {
                if (isContained) {
                    break;
                }
                currentWord += entry.getKey();
                if (currentWord.equals(word)) {
                    if (prefix) {
                        isContained = true;
                    }
                    else if (!prefix && entry.getValue().endOfWord) {
                        isContained = true;
                    }
                }
                else {
                    isContained = containsHelper(word, false, currentWord, entry.getValue(), prefix);
                }
                currentWord = currentWord.substring(0, currentWord.length() - 1);
            }
        }
        return isContained;
    }

    /**
     * @param prefix the prefix of the word returned
     * @return a word that starts with the given prefix, or an empty optional
     * if no such word is found.
     */
    Optional<String> predict(String prefix) {
        if (containsHelper(prefix, false, "", this, true)) {
            ArrayList<String> words = new ArrayList<>();
            return Optional.of((predictHelper(prefix, prefix, words, 1, this)).get(0));
        }
        else {
            return Optional.empty();
        }
    }


    private ArrayList<String> predictHelper(String prefix, String newWord, ArrayList<String> words, int numOfWOrds, DictionaryTree tree) {
        Optional<String> prefixNew = Optional.of(prefix);
        if (!(prefix.equals(""))) {
            Character letter = prefix.charAt(0);
            predictHelper(prefix.substring(1), newWord, words, numOfWOrds, tree.children.get(letter));
        }
        else {
            LinkedHashMap<String, Integer> wordsUnsorted = new LinkedHashMap<>();
            wordsUnsorted = allWordsHelper(wordsUnsorted, newWord, tree);
            TreeMap<Integer, String> wordsSorted = new TreeMap<>();
            for (String word : wordsUnsorted.keySet()) {
                wordsSorted.put(wordsUnsorted.get(word), word);
            }
            for (int i = 0; i < numOfWOrds; i++) {
                words.add(wordsSorted.firstEntry().getValue());
                wordsSorted.remove(wordsSorted.firstEntry().getKey());
            }
        }
        return words;
    }

    /**
     * Predicts the (at most) n most popular full English words based on the specified prefix.
     * If no word with the specified prefix is found, an empty list is returned.
     *
     * @param prefix the prefix of the words found
     * @return the (at most) n most popular words with the specified prefix
     */
    List<String> predict(String prefix, int n) {
        ArrayList<String> words = new ArrayList<>();
        return predictHelper(prefix, prefix, words, n, this);
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
     * current longest words
     *
     * @param longestWord The current longest word
     * @param currentWord THe current word
     * @param tree THe tree to operate on
     * @return The longest word in the tree
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
        LinkedHashMap<String, Integer> words = new LinkedHashMap<>();
        words = allWordsHelper(words, "", this);
        System.out.println(words);
        return new ArrayList<>(words.keySet());
    }


    /**
     *
     * Helper method for allWords()
     * Recursively traverses the tree adding
     * words to a list
     *
     * @param words The list of words in the tree
     * @param currentWord THe current word
     * @param tree The tree to operate on
     * @return The list of words in the tree
     */
    private LinkedHashMap<String, Integer> allWordsHelper(LinkedHashMap<String, Integer> words, String currentWord, DictionaryTree tree) {
        if (tree.children.isEmpty()) {
            return words;
        }
        else {
            if (tree.endOfWord) {
                words.put(currentWord, tree.popularity);
            }
            for (Map.Entry<Character, DictionaryTree> entry : tree.children.entrySet()) {
                currentWord += entry.getKey();
                if (entry.getValue().endOfWord) {
                    words.put(currentWord, entry.getValue().popularity);
                }
                words = allWordsHelper(words, currentWord, entry.getValue());
                currentWord = currentWord.substring(0, currentWord.length() - 1);
            }
        }
        return words;
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
