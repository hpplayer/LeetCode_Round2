/*
Minimum Window Substring

Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).

For example,
S = "ADOBECODEBANC"
T = "ABC"
Minimum window is "BANC".

Note:
If there is no such window in S that covers all characters in T, return the emtpy string "".

If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.

*/

/**
 * Two pointer problem.
 * 
 * We use two pointer to build a sliding window in s. We will begin shrink the window once we found current window contains s
 * How can we find if current win contains t?
 * We use a counter, each time we found a char that needs to build t, we will increase it. Here we require the char appeared in t 
 * and its occurrence does not exceed the occurrence in t.
 * How can we shrink the win?
 * We need two arrays. One array gives the information on chars need by t, and one array records current information in s.
 * After we our counter is same with t.len, then we will begin shrink the win. As long as left pointer points to a char not appeared in s
 * or points to a char appeared in s but with extra occurrence, we can safely move left pointer forward. The win size can be calculated by 
 * right bound - left bound + 1. Finally we will find the min bound and return the minWin accordingly.
 * 
 * Remark:
 * 1) Time complexity: O(n). We have two pointers that only move forward. Each of them can only move n steps in total. So it is O(n) time
 * 
 * 2) This problem is similar to problem:
 *  Longest_Substring_Without_Repeating_Characters_p3_sol1,
 *  Substring_with_Concatenation_of_All_Words_p30_sol1
 *  Longest_Substring_with_At_Most_Two_Distinct_Characters_p159_sol1
 *  
 * @author hpPlayer
 * @date Sep 19, 2015 3:18:34 PM
 */
public class Minimum_Window_Substring_p76_sol1 {
    public String minWindow(String s, String t) {
        //what we need to builld t
        int expect[] = new int[256];
        //what we really get from s
        int real[] = new int[256];
        
        for(int i =0 ; i < t.length();i++){
            expect[t.charAt(i)] ++;
        }
        
        int left = 0;//left pointer
        int minLeft = 0;//minWin left pointer
        int minSize = Integer.MAX_VALUE;//minWin size
        int count = 0;//how many chars in t we found so far
        
        for(int i = 0; i < s.length(); i++){
        	//we are moving right boundary and searching for a char appeared in string t
            if(expect[s.charAt(i)] == 0) continue;
            
            //we can't increase count for each new char, we only increase it when we need this new char
            if(expect[s.charAt(i)] > real[s.charAt(i)]) count ++;
            	
            //we use a lazy update here, we will move left bound of window only after we found all chars in t
            //so current char may not necessary included in final window.
            real[s.charAt(i)] ++;
            
            //if we have found all chars in t, then we know remaining window will at least contains t
            //so no need to reduce count
            if(count == t.length()){
                while(expect[s.charAt(left)] == 0 || expect[s.charAt(left)] < real[s.charAt(left)]){
                    real[s.charAt(left)] --;
                    left ++;
                }
                
                //we cannot shrink the window any more, so let's compare win size now
                
                if(i - left + 1 < minSize){
                    minSize = i - left + 1;
                    minLeft = left;
                }
            }
        }
        
        
        if(count < t.length()) return "";//in case we can't find string t in s
        
        return s.substring(minLeft, minLeft + minSize);//otherwise we will return minWin
    }	
}
