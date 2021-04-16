package smai.data.repositories;

import smai.data.datasources.SearchLocalDataSource;
import smai.common.utils.Callback;
import smai.domain.Answer;
import smai.domain.Instance;

public class SearchMethodsRepository {
    
    private final SearchLocalDataSource localDataSource;

    public SearchMethodsRepository(SearchLocalDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }
    
    public void resolve(Instance instance, Callback<Answer> callback) {
        this.localDataSource.resolve(instance, callback);
    }
    
}
