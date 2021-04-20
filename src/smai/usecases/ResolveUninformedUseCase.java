package smai.usecases;

import smai.data.Callback;
import smai.data.repositories.UninformedSearchesRepository;
import smai.domain.Response;
import smai.domain.Instance;

public class ResolveUninformedUseCase {

    private final UninformedSearchesRepository searchMethodsRepository;

    public ResolveUninformedUseCase(UninformedSearchesRepository repository) {
        this.searchMethodsRepository = repository;
    }

    public void invoke(Instance instance, Callback<Response> callback) {
        this.searchMethodsRepository.resolve(instance, callback);
    }
}
