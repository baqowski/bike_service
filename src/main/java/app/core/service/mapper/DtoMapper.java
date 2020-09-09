package app.core.service.mapper;

import javassist.NotFoundException;

/**
 * @author Karol Bąk
 */
public interface DtoMapper<E, D> {
    E map (D dto) throws NotFoundException;
}
