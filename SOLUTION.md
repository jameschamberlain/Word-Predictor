# Assignment 1 - Solution

## GitLab link

https://git.cs.bham.ac.uk/jxc1089/sww-assignment2.git

## Solution

To explain my approach, I believe it is best to look at all the methods individually.

### size

This method recursively travels to each node in the tree adding 1 to the counter each time it does. This
counter is then returned.

### height

In this method I recursively travelled down each branch in the tree incrementing a counter at each level
until I got to a node with no children, meaning I had reached a leaf node. I would keep track of the maximum
this count reached and then return it.

### maximumBranching

For this method I traversed each node of the tree. For each node I counted the number of children and then
just kept a record of the maximum number of children at any one node.

### longestWord

To implement this method, I recursively travel to each node in the tree building words at each level and then keeping
track of the longest word. Whenever a new word it is created it is compared to the current longest word to see if 
it is larger or not.

### numLeaves

This method was relatively simple. I traversed through each branch and level in the tree. When I got to
a point where there were no more children, signifying a leaf, I added one to the counter.

### contains

The word to be checked is broken down letter by letter and the helper method traverses each level of the
tree using these letters. When it reaches the end of the word the endOfWord boolean is checked. If this is
true then true is returned and if it is false then false is returned.

### allWords

For this I used a helper method that recursively traverses the tree, building words and then once a node is
reached where the endOfWord boolean is true, I add this word to a hash map with its corresponding popularity.
For the allWords method the popularity isn't important, and simply all the words in the hash map are returned
but, the allWords helper method is used to help the predict methods which is why it deals with popularity as
well.

### insert

For insert I simply passed the word into a helper method. I then took that word and took the first letter and
added it to the children map. I would then enter the dictionary tree of that specific letter and do the same
for the rest of the word until the word was empty. I then switched a boolean called endOfWord to true.

### remove

For this method I implement a helper method that traversed down the tree using the letters of the word until
I reached the end of the word and the children were empty. I then began removing the letters by traversing
back up the array. For the situation where the word to be deleted is a prefix of another word, the letters
are not removed, and simply the endOfWord boolean is switched to false.

### predict

The tree is traversed using the prefix supplied. Once it reaches the end of the prefix it calls the allWords
method to return all the words that start with that prefix along with each word's popularity. A tree map is
then used to sort these words based on their popularity. Popularity was implemented later on and so this will
return the most popular word beginning with that prefix.

## Challenges

### insert - advanced

To implement this, like the endOfWord boolean, each word had a popularity boolean. This would keep a record of
the popularity for that particular word. I decided to implement popularity with the lower the value meaning
the more popular a word is. This made more sense as it could then be said that word x was the n most popular
word.

### predict - advanced

This was implemented very similar to the regular predict method. This was because my predict helper method
just takes in an integer n which dictates the amount of words that the method returns. So, to return multiple
words an integer greater than 1 should be passed and the words are already sorted due to the tree map.

### advantages/disadvantages of using a tree for predicting multiple words with ranked popularities

To begin with, I would say that the search required to find the words in the tree is efficient when compared
with using other data structures. The time complexity of O(logn) provides a significant improvement to the O(n)
involved in a naive algorithm. This is especially true as the predicting returns multiple words. Also, the
implementation does not provide any significant difficulties. This means getting a prediction algorithm that
ranks words by popularity can be built in a relatively short time.
