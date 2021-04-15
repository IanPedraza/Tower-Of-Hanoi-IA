package smai.data;

import smai.common.utils.Callback;
import smai.domain.Answer;
import smai.domain.Instance;

public interface SearchLocalDataSource {
    public void resolve(Instance instance, Callback<Answer> callback);
}
