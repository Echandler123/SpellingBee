import java.io.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Scanner;

/**
 * Spelling Bee
 *
 * This program accepts an input of letters. It prints to an output file
 * all English words that can be generated from those letters.
 *
 * For example: if the user inputs the letters "doggo" the program will generate:
 * do
 * dog
 * doggo
 * go
 * god
 * gog
 * gogo
 * goo
 * good
 *
 * It utilizes recursion to generate the strings, mergesort to sort them, and
 * binary search to find them in a dictionary.
 *
 * @author Zach Blick, Elijah Chandler
 *
 * Written on March 5, 2023 for CS2 @ Menlo School
 *
 * DO NOT MODIFY MAIN OR ANY OF THE METHOD HEADERS.
 */
public class SpellingBee {

    private String letters;
    private ArrayList<String> words;
    public static final int DICTIONARY_SIZE = 143091;
    public static final String[] DICTIONARY = new String[DICTIONARY_SIZE];

    public SpellingBee(String letters) {
        this.letters = letters;
        words = new ArrayList<String>();
    }

    // TODO: generate all possible substrings and permutations of the letters.
    //  Store them all in the ArrayList words. Do this by calling ANOTHER method
    //  that will find the substrings recursively.
    public void generate() {
        makeWords("", letters);
        // YOUR CODE HERE — Call your recursive method!

    }

    // TODO: Apply mergesort to sort all words. Do this by calling ANOTHER method
    //  that will find the substrings recursively.
    public void sort() {
        // YOUR CODE HERE
        mergesort(words, 0, words.size());
    }

    // Removes duplicates from the sorted list.
    public void removeDuplicates() {
        int i = 0;
        while (i < words.size() - 1) {
            String word = words.get(i);
            if (word.equals(words.get(i + 1)))
                words.remove(i + 1);
            else
                i++;
        }
    }

    // TODO: For each word in words, use binary search to see if it is in the dictionary.
    //  If it is not in the dictionary, remove it from words.
    public void checkWords() {
        // YOUR CODE HERE
        for(int i =0; i < words.size();i++) {
            Search(words.get(i),DICTIONARY_SIZE,DICTIONARY);
        }
    }

    // Prints all valid words to wordList.txt
    public void printWords() throws IOException {
        File wordFile = new File("Resources/wordList.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(wordFile, false));
        for (String word : words) {
            writer.append(word);
            writer.newLine();
        }
        writer.close();
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public SpellingBee getBee() {
        return this;
    }

    public static void loadDictionary() {
        Scanner s;
        File dictionaryFile = new File("Resources/dictionary.txt");
        try {
            s = new Scanner(dictionaryFile);
        } catch (FileNotFoundException e) {
            System.out.println("Could not open dictionary file.");
            return;
        }
        int i = 0;
        while(s.hasNextLine()) {
            DICTIONARY[i++] = s.nextLine();
        }
    }

    public static void main(String[] args) {

        // Prompt for letters until given only letters.
        Scanner s = new Scanner(System.in);
        String letters;
        do {
            System.out.print("Enter your letters: ");
            letters = s.nextLine();
        }
        while (!letters.matches("[a-zA-Z]+"));

        // Load the dictionary
        SpellingBee.loadDictionary();

        // Generate and print all valid words from those letters.
        SpellingBee sb = new SpellingBee(letters);
        sb.generate();
        sb.sort();
        sb.removeDuplicates();
        sb.checkWords();
        try {
            sb.printWords();
        } catch (IOException e) {
            System.out.println("Could not write to output file.");
        }
        s.close();
    }
    public void makeWords(String word, String letters)
    {
        if(letters.length() == word.length())
        {
            return;
        }
        String newletters = "";
        String newword = "";
        for(int i = 0; i < letters.length(); i++)
        {
            newword = word + letters.substring(i, i+1);
            words.add(newword);
            newletters = letters.substring(0,i) + letters.substring(i+1);
            makeWords(newword,newletters);
        }
    }
    public ArrayList<String> mergesort(ArrayList <String> arr,int low, int high)
    {
        if (high - low == 0) {			// Base case
            ArrayList <String> newArr = new ArrayList <String>();
            newArr.add(0,arr.get(low));
            return newArr;
        }
        int med = (high + low) / 2;
        ArrayList<String> arr1 = mergesort(arr, low, med);
        ArrayList<String> arr2 = mergesort(arr, med + 1, high);
        return merge(arr1, arr2);
    }
    public ArrayList<String> merge(ArrayList<String> arr1,ArrayList<String> arr2)
    {
        ArrayList<String> arr3 = new ArrayList <String>();
        int i = 0, j = 0;
        while (i < arr1.size() && j < arr2.size()) {
            if (arr1.get(i).compareTo(arr2.get(j)) <= 0) {
                arr3.add(arr1.get(i));
                i++;
            } else {
                arr3.add(arr2.get(j));
                j++;
            }
        }
        if(arr1.size() < arr2.size())
        {
            for(int k = j; k < arr2.size(); k++)
            {
                arr3.add(arr2.get(k));
            }
        }
        else if(arr2.size() < arr1.size())
        {
            for(int k = i; k < arr1.size(); k++)
            {
                arr3.add(arr1.get(k));
            }
        }
        return arr3;
    }
    public boolean Search(String word,int size, String[]newDictionary)
    {
        int midpoint = size/2;
        if(word.equals(newDictionary[midpoint])) {
            return true;
        }
        if(word.compareTo(newDictionary[midpoint]) < 0)
        {
            for(int j =0; j < midpoint;j++)
            {
                newDictionary[j] = DICTIONARY[j];
            }
        }
        else if(word.compareTo(newDictionary[midpoint]) > 0)
        {
            for(int j = midpoint; j < DICTIONARY_SIZE; j++)
            {
                newDictionary[j] = DICTIONARY[j];
            }
        }
        Search(word,midpoint,newDictionary);
        return false;
    }

}
