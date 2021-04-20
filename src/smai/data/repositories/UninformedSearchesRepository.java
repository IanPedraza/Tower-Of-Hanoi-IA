package smai.data.repositories;

import smai.data.datasources.UninformedSearchLocalDataSource;
import smai.data.Callback;
import smai.domain.Response;
import smai.domain.Instance;

public class UninformedSearchesRepository {

    private UninformedSearchLocalDataSource localDataSource;

    public UninformedSearchesRepository(UninformedSearchLocalDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }

    public void setLocalDataSource(UninformedSearchLocalDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }
    
    public void resolve(Instance instance, Callback<Response> callback) {
        this.localDataSource.resolve(instance, callback);
    }
}
