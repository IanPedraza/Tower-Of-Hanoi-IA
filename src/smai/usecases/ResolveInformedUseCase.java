package smai.usecases;

import smai.common.utils.Callback;
import smai.data.repositories.InformedSearchesRepository;
import smai.domain.Answer;
import smai.domain.Heuristic;
import smai.domain.Instance;


public class ResolveInformedUseCase {
    private final InformedSearchesRepository searchMethodsRepository;

    public ResolveInformedUseCase(InformedSearchesRepository repository) {
        this.searchMethodsRepository = repository;
    }
    
    public void invoke(Instance instance, Heuristic heuristic, Callback<Answer> callback) {
        this.searchMethodsRepository.resolve(instance, heuristic, callback);
    }
    
}
