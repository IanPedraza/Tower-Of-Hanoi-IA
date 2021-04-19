package smai.usecases;

import smai.common.utils.Callback;
import smai.data.repositories.UninformedSearchesRepository;
import smai.domain.Answer;
import smai.domain.Instance;


public class ResolveUninformedUseCase {
    private final UninformedSearchesRepository searchMethodsRepository;

    public ResolveUninformedUseCase(UninformedSearchesRepository repository) {
        this.searchMethodsRepository = repository;
    }
    
    public void invoke(Instance instance, Callback<Answer> callback) {
        this.searchMethodsRepository.resolve(instance, callback);
    }
    
}
