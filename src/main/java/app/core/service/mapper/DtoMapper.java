package app.core.service.mapper;

/**
 * @author Karol Bąk
 */
public interface DtoMapper<E, D> {
    E map (D dto);
}
