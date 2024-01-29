package ru.job4j.cash;

/**
 *
 * @param id уникальный идентификатор В системе будет только один объект с таким ID
 * @param name это поля бизнес модели Это поле имеет get set методы
 * @param version определяет достоверность версии в кеше
 */
public record Base(int id, String name, int version) {
}
