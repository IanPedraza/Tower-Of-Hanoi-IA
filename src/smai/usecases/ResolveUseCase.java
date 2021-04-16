package smai.usecases;

import smai.common.utils.Callback;
import smai.data.repositories.SearchMethodsRepository;
import smai.domain.Answer;
import smai.framework.hanoi.HanoiInstance;


public class ResolveUseCase {
    private final SearchMethodsRepository searchMethodsRepository;

    public ResolveUseCase(SearchMethodsRepository repository) {
        this.searchMethodsRepository = repository;
    }
    
    public void invoke(HanoiInstance instance, Callback<Answer> callback) {
        this.searchMethodsRepository.resolve(instance, callback);
    }
    
}
