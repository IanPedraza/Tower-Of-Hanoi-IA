package towerofhanoiia.data.domain;

public class SearchMethods {
    
    public static final SearchMethod[] methods = {
        new SearchMethod(SearchMethods.DEPTH, "Depth Search"),
        new SearchMethod(SearchMethods.A_STAR, "A Start"),
    };
    
    public static int DEPTH = 0;
    public static int A_STAR = 1;
    
}
