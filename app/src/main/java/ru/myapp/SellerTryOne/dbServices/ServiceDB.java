package ru.myapp.SellerTryOne.dbServices;

interface ServiceDB<T, ID>{
    void add(T item) throws Exception;
    T get(ID id) throws Exception;
    void delete(ID id) throws Exception;
    void update(T newItem) throws Exception;

}
