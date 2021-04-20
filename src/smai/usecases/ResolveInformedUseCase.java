package smai.usecases;

import smai.data.Callback;
import smai.data.repositories.InformedSearchesRepository;
import smai.domain.Response;
import smai.domain.Heuristic;
import smai.domain.Instance;

public class ResolveInformedUseCase {

    private final InformedSearchesRepository searchMethodsRepository;

    public ResolveInformedUseCase(InformedSearchesRepository repository) {
        this.searchMethodsRepository = repository;
    }

    public void invoke(Instance instance, Heuristic heuristic, Callback<Response> callback) {
        this.searchMethodsRepository.resolve(instance, heuristic, callback);
    }

}
