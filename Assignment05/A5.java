/*
 * Title: COP 3530 - Assignment 05
 * Author: Matthew Boyette
 * Date: 6/5/2013
 */

package Assignment05;

import api.util.Support;
import api.util.datastructures.HuffmanTree;
import api.util.stdlib.StdIn;
import api.util.stdlib.StdOut;

public class A5
{
    public static final void main(final String[] args)
    {
        HuffmanTree<Character> huffmanTree;
        boolean continueFlag;
        
        if (args.length > 0)
        {
            for (String arg: args)
            {
                huffmanTree = new HuffmanTree<Character>(HuffmanTree.ALPHABET_ASCII);
                
                if (huffmanTree.openFile(arg))
                {
                    continueFlag = true;
                    
                    do
                    {
                        StdOut.println("\nPlease select an option from the following menu.\n");
                        StdOut.println("[1]: Print the original program input.");
                        StdOut.println("[2]: Print a pre-order traversal of the huffman tree.");
                        StdOut.println("[3]: Print an in-order traversal of the huffman tree.");
                        StdOut.println("[4]: Print a post-order traversal of the huffman tree.");
                        StdOut.println("[5]: Print the frequency table.");
                        StdOut.println("[6]: Print the code table.");
                        StdOut.println("[7]: Print the compressed binary output using the huffman tree.");
                        StdOut.println("[8]: Print the uncompressed text input using the huffman tree.");
                        StdOut.println("\n*Note: You may enter the word \"stop\" (not case-sensitive) at any time to end the program.\n");
                        StdOut.print("Selection: ");
                        String input = StdIn.readString();
                        
                        if (Support.isStringParsedAsInteger(input))
                        {
                            StdOut.println("");
                            
                            switch (Integer.parseInt(input))
                            {
                            
                                case 1:
                                    
                                    StdOut.println(huffmanTree.formatData(arg, HuffmanTree.DataFormats.ORIGINAL));
                                    break;
                                
                                case 2:
                                    
                                    huffmanTree.traversal_preOrder(huffmanTree.getRoot());
                                    StdOut.println("");
                                    break;
                                
                                case 3:
                                    
                                    huffmanTree.traversal_inOrder(huffmanTree.getRoot());
                                    StdOut.println("");
                                    break;
                                
                                case 4:
                                    
                                    huffmanTree.traversal_postOrder(huffmanTree.getRoot());
                                    StdOut.println("");
                                    break;
                                
                                case 5:
                                    
                                    StdOut.println(huffmanTree.formatFrequencies());
                                    break;
                                
                                case 6:
                                    
                                    StdOut.println(huffmanTree.formatCodeTable());
                                    break;
                                
                                case 7:
                                    
                                    StdOut.println(huffmanTree.formatData(arg, HuffmanTree.DataFormats.COMPRESSED));
                                    break;
                                
                                case 8:
                                    
                                    StdOut.println(huffmanTree.formatData(arg, HuffmanTree.DataFormats.UNCOMPRESSED));
                                    break;
                                
                                default:
                                    
                                    Support.displayException(null,
                                        new Exception("Unable to recognize your selection. Integers 1 through 8 are valid."),
                                        false);
                                    break;
                            }
                        }
                        else
                        {
                            if (input.equalsIgnoreCase("stop"))
                            {
                                continueFlag = false;
                            }
                            else
                            {
                                Support.displayException(null,
                                    new Exception("Unable to recognize your selection. Integers 1 through 8 are valid."),
                                    false);
                            }
                        }
                    }
                    while (continueFlag);
                }
            }
        }
        
        StdOut.println("\nExiting...\n");
        System.exit(0);
    }
}