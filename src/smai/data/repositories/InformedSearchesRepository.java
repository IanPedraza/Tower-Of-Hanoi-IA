package smai.data.repositories;

import smai.common.utils.Callback;
import smai.data.datasources.InformedSearchLocalDataSource;
import smai.domain.Answer;
import smai.domain.Heuristic;
import smai.domain.Instance;

public class InformedSearchesRepository {
    private final InformedSearchLocalDataSource localDataSource;

    public InformedSearchesRepository(InformedSearchLocalDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }
    
    public void resolve(Instance instance, Heuristic heuristic, Callback<Answer> callback) {
        this.localDataSource.resolve(instance, heuristic, callback);
    }
}
