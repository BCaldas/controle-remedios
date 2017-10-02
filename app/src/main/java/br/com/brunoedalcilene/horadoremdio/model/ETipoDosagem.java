package br.com.brunoedalcilene.horadoremdio.model;

/**
 * Created by bruno on 01/10/2017.
 */

public enum ETipoDosagem {

    Comprimido(1,"CP"),Mililitros(2,"ML");

    private final int value;
    private final String desc;

    private ETipoDosagem(final int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescricao() {
        return this.desc;
    }
}
