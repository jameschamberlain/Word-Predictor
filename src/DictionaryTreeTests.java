import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * @author Kelsey McKenna
 */
public class DictionaryTreeTests {
	
	
	/**
	 * Method: size() - Test 1
	 * 
	 * Checks that the size of a tree
	 * with only a root node is 1
	 */
	@Test
	public void size1() {
		DictionaryTree unit = new DictionaryTree();
		Assertions.assertEquals(1, unit.size());
	}
	
	
	/**
	 * Method: size() - Test 2
	 * 
	 * Checks that the size of a tree
	 * with the words "hello" and "have" is of size 9
	 * (the first h of each word is one node)
	 */
	@Test
	public void size2() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("hello");
		unit.insert("have");
		Assertions.assertEquals(9, unit.size());
	}
	

	/**
	 * Method: height() - Test 1
	 * 
	 * Checks that the height of a tree
	 * with just a root is 0
	 */
    @Test
    public void height1() {
        DictionaryTree unit = new DictionaryTree();
        Assertions.assertEquals(0, unit.height());
    }
    
    
    /**
	 * Method: height() - Test 2
	 * 
	 * Checks that the height of a tree
	 * is the length of the longest word
	 */
    @Test
    public void height2() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("hello");
        unit.insert("word");
        unit.insert("to");
        unit.insert("because");
        Assertions.assertEquals("because".length(), unit.height());
    }

    
    /**
	 * Method: maximumBranching() - Test 1
	 * 
	 * Checks that the maximum branching in which
	 * the tree is just a root node is 0
	 */
    @Test
    public void maximumBranching1() {
        DictionaryTree unit = new DictionaryTree();
        Assertions.assertEquals(0, unit.maximumBranching());
    }
    
    
    /**
	 * Method: maximumBranching() - Test 2
	 * 
	 * Checks that the maximum of a branching
	 * in the tree is equal to the maximum
	 * number of children of any node
	 */
    @Test
    public void maximumBranching2() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("hello");
        unit.insert("help");
        unit.insert("hell");
        unit.insert("helping");
        Assertions.assertEquals(2, unit.maximumBranching());
    }
    
    
    /**
	 * Method: longestWord() - Test 1
	 * 
	 * Checks that the longest word in an
	 * empty tree is just an empty string
	 * because there are no words
	 */
    @Test
    public void longestWord1() {
        DictionaryTree unit = new DictionaryTree();
        Assertions.assertEquals("", unit.longestWord());
    }
    
    
    /**
	 * Method: longestWord() - Test 2
	 * 
	 * Checks that the longest word in the
	 * tree is correctly found
	 */
    @Test
    public void longestWord2() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("hello");
        unit.insert("word");
        unit.insert("to");
        unit.insert("because");
        Assertions.assertEquals("because", unit.longestWord());
    }
    
    
    /**
	 * Method: numLeaves() - Test 1
	 * 
	 * Checks that the number of leaves in
	 * an empty tree is 1
	 */
    @Test
    public void numLeaves1() {
        DictionaryTree unit = new DictionaryTree();
        Assertions.assertEquals(1, unit.numLeaves());
    }
    
    
    /**
	 * Method: numLeaves() - Test 2
	 * 
	 * Checks that the number of leaves in
	 * the tree is correctly calculated
	 */
    @Test
    public void numLeaves2() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("hello");
        unit.insert("hell");
        unit.insert("word");
        unit.insert("to");
        unit.insert("because");
        unit.insert("wording");
        Assertions.assertEquals(4, unit.numLeaves());
    }
    
    
    /**
	 * Method: contains() - Test 1
	 * 
	 * Checks whether false is returned
	 * if the word is not in the tree
	 */
    @Test
    public void contains1() {
        DictionaryTree unit = new DictionaryTree();
        Assertions.assertEquals(false, unit.contains("hello"));
    }
    
    
    /**
	 * Method: contains() - Test 2
	 * 
	 * Checks whether true is returned
	 * if the word is in the tree
	 */
    @Test
    public void contains2() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("hello");
        Assertions.assertEquals(true, unit.contains("hello"));
    }
    
    
    /**
	 * Method: allWords() - Test 1
	 * 
	 * Checks whether no words are
	 * returned for an empty tree
	 */
    @Test
    public void allWords1() {
        DictionaryTree unit = new DictionaryTree();
        Assertions.assertEquals(java.util.Collections.EMPTY_LIST, unit.allWords());
    }
    
    
    /**
	 * Method: allWords() - Test 2
	 * 
	 * Checks whether all words are
	 * returned in the tree
	 */
    @Test
    public void allWords2() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("hello");
        unit.insert("hell");
        unit.insert("word");
        unit.insert("to");
        unit.insert("because");
        unit.insert("wording");
        java.util.List<String> words = new ArrayList<>();
        words.add("hell");
        words.add("hello");
        words.add("word");
        words.add("wording");
        words.add("to");
        words.add("because");
        Assertions.assertEquals(words, unit.allWords());
    }
    
    
    /**
	 * Method: insert() - Test 1
	 * 
	 * Checks whether words can be inserted
	 */
    @Test
    public void insert1() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("hello");
        Assertions.assertEquals(true, unit.contains("hello"));
    }
    
    
    /**
	 * Method: remove() - Test 1
	 * 
	 * The method should return true as
	 * the word can be cleanly deleted
	 */
    @Test
    public void remove1() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("hello");
        Assertions.assertEquals(true, unit.remove("hello"));
    }
    
    
    /**
	 * Method: remove() - Test 2
	 * 
	 * The method should return false as
	 * the word cannot be cleanly deleted
	 */
    @Test
    public void remove2() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("hell");
        unit.insert("hello");
        Assertions.assertEquals(false, unit.remove("hell"));
    }
    
    
    
    /**
	 * Method: remove() - Test 3
	 * 
	 * The method should actually remove
	 * the word
	 */
    @Test
    public void remove3() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("hello");
        unit.insert("help");
        unit.remove("hello");
        Assertions.assertEquals(false, unit.contains("hello"));
    }
    
    
    /**
	 * Method: predict() - Test 1
	 * 
	 * The method should return a word
	 * that starts with the prefix
	 */
    @Test
    public void predict1() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("hello");
        unit.insert("help");
        unit.insert("he");
        Assertions.assertEquals(Optional.of("hello"), unit.predict("hel"));
    }
    
    
    /**
	 * Method: insert() advanced - Test 1
	 * 
	 * The method should insert a word with
	 * a given popularity
	 * i.e. return the word with the
	 * higher(lower means higher) popularity
	 * See SOLUTION.md for popularity reasoning
	 */
    @Test
    public void insert1a() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("hello", 9);
        unit.insert("help", 2);
        Assertions.assertEquals(Optional.of("help"), unit.predict("hel"));
    }
    
    
    /**
	 * Method: predict() advanced - Test 1
	 * 
	 * The method should return the n
	 * most popular words
	 */
    @Test
    public void predict1a() {
    	DictionaryTree unit = new DictionaryTree();
        unit.insert("hello", 4);
        unit.insert("hell", 2);
        unit.insert("word", 7);
        unit.insert("to", 3);
        unit.insert("how", 10);
        unit.insert("hi", 1);
        java.util.List<String> words = new ArrayList<>();
        words.add("hi");
        words.add("hell");
        Assertions.assertEquals(words, unit.predict("h", 2));
    }
    
    
    
    
    

}
