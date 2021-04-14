package towerofhanoiia.data;

public interface Callback<T> {
    public void onSuccess(T result);
    public void onFailed(Exception error);
}
