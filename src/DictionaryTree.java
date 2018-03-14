import java.util.Collection;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
        insertWord(word, this);
    }
    
    
    /**
     * 
     * Helper method for insert()
     * Recursively adds the characters from the word to maps
     * 
     * @param word The word to be inserted
     * @param tree The tree to operate on
     */
    private void insertWord(String word, DictionaryTree tree) {
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
    		insertWord(word.substring(1), letterChildren);
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
        throw new RuntimeException("DictionaryTree.numLeaves not implemented yet");
    }

    /**
     * @return the maximum number of children held by any node in this tree
     */
    int maximumBranching() {
        return maxBranch(0, this);
    }


    int maxBranch(int maxBranch, DictionaryTree tree) {
        if (tree.children.isEmpty()) {
            return 0;
        }
        else {

            for (Map.Entry<Character, DictionaryTree> entry : tree.children.entrySet()) {
                DictionaryTree letterTree = entry.getValue();
                int count = 0;
                for (int i = 0; i < tree.children.size(); i++) {
                    ++count;
                }
                maxBranch = Math.max(maxBranch, count);
                maxBranch = Math.max(maxBranch, maxBranch(maxBranch, letterTree));
            }
        }
        return maxBranch;
    }

    /**
     * @return the height of this tree, i.e. the length of the longest branch
     */
    int height() {
        return heightOfTree(0, 0, this);
    }


    /**
     *
     * Helper method for height()
     * Recursively goes to end of each subtree
     * and keeps track of the height
     *
     * @param height Current height of tree
     * @param count Current height of subtree
     * @param tree The tree to operate on
     * @return The height of the tree
     */
    int heightOfTree(int height, int count, DictionaryTree tree) {
    	if (tree.children.isEmpty()) {
    		height = Math.max(height, count);
		}
    	else {
    		for (Map.Entry<Character, DictionaryTree> entry : tree.children.entrySet()) {
    			DictionaryTree letterTree = entry.getValue();
    			++count;
				height = heightOfTree(height, count, letterTree);
				--count;
			}
    	}
    	return height;
    }

    /**
     * @return the number of nodes in this tree
     */
    int size() {
        return sizeOfTree(1, this);
    }
    
    
    /**
     * 
     * Helper method for size()
     * Recursively counts the number of nodes
     * at each level in each subtree
     * 
     * @param count The running total of the number of nodes
     * @param tree The tree to operate on
     * @return The size of the tree
     */
    int sizeOfTree(int count, DictionaryTree tree) {
    	if (!tree.children.isEmpty()) {
    		for (Map.Entry<Character, DictionaryTree> entry : tree.children.entrySet()) {
    			DictionaryTree letterTree = entry.getValue();
				count = 1 + sizeOfTree(count, letterTree);
			}
		}
    	
    	return count;
    }

    /**
     * @return the longest word in this tree
     */
    String longestWord() {
        throw new RuntimeException("DictionaryTree.longestWord not implemented yet");
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
