package smai.data.repositories;

import smai.data.Callback;
import smai.data.datasources.InformedSearchLocalDataSource;
import smai.domain.Response;
import smai.domain.Heuristic;
import smai.domain.Instance;

public class InformedSearchesRepository {

    private InformedSearchLocalDataSource localDataSource;

    public InformedSearchesRepository(InformedSearchLocalDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }
    
    public void setLocalDataSource(InformedSearchLocalDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }

    public void resolve(Instance instance, Heuristic heuristic, Callback<Response> callback) {
        this.localDataSource.resolve(instance, heuristic, callback);
    }
    
}
