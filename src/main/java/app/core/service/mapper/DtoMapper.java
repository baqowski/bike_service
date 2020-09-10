package app.core.service.mapper;

import javassist.NotFoundException;

/**
 * @author Karol Bąk
 */
public interface DtoMapper<E, D> {
    E toEntity (D dto) throws NotFoundException;
    D toDto (E e);

}
