package smai.common.utils;

import smai.domain.SearchMethodItem;

public class SearchMethods {
    
    public static final SearchMethodItem[] METHODS = {
        new SearchMethodItem(SearchMethods.DEPTH, "Depth-first search"),
        new SearchMethodItem(SearchMethods.BREADTH, "Breadth-first search"),
        //new SearchMethodItem(SearchMethods.A_STAR, "A Start"),
    };
    
    public static final int DEPTH = 0;
    public static final int BREADTH = 1;    
    public static final int A_STAR = 2;    
}
