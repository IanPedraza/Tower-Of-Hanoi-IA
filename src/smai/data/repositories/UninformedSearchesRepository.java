package smai.data.repositories;

import smai.data.datasources.UninformedSearchLocalDataSource;
import smai.common.utils.Callback;
import smai.domain.Answer;
import smai.domain.Instance;

public class UninformedSearchesRepository {
    private final UninformedSearchLocalDataSource localDataSource;

    public UninformedSearchesRepository(UninformedSearchLocalDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }
    
    public void resolve(Instance instance, Callback<Answer> callback) {
        this.localDataSource.resolve(instance, callback);
    }
}
