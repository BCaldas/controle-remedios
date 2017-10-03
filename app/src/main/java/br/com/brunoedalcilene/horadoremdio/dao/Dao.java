package br.com.brunoedalcilene.horadoremdio.dao;

import java.util.List;

/**
 * Created by bruno on 03/10/2017.
 */

public interface Dao<T> {

    List<T> obterTodos();
    List<T> obterPorNome(String nome);
}
