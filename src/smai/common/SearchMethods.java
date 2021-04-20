package smai.common;

import smai.domain.SearchMethodItem;
import smai.domain.SearchType;

public class SearchMethods {
    
    public static final SearchMethodItem[] METHODS = {
        new SearchMethodItem(SearchMethods.DEPTH, "Depth-first", SearchType.UNINFORMED),
        new SearchMethodItem(SearchMethods.BREADTH, "Breadth-first", SearchType.UNINFORMED),
        new SearchMethodItem(SearchMethods.A_STAR, "A Start", SearchType.INFORMED),
        new SearchMethodItem(SearchMethods.BEST_FIRST, "Best-first", SearchType.INFORMED),
        new SearchMethodItem(SearchMethods.HILL_CLIMBING, "Hill Climbing", SearchType.INFORMED),
        new SearchMethodItem(SearchMethods.OPTIMAL, "Optimal", SearchType.INFORMED),
    };   
    
    public static final int DEPTH = 0;
    public static final int BREADTH = 1;    
    public static final int A_STAR = 2;  
    public static final int BEST_FIRST = 3;  
    public static final int HILL_CLIMBING = 4;
    public static final int OPTIMAL = 5;
}
