package org.nthing;

import io.smallrye.mutiny.Uni;

import java.lang.reflect.Field;

public class BeanUtilsMutiny {
    public static <S, T> Uni<T> copyProperties(S source, T target) {
        return Uni.createFrom().item(() -> {
            try {
                for (Field sourceField : source.getClass().getDeclaredFields()) {
                    sourceField.setAccessible(true);
                    Field targetField = null;
                    try {
                        targetField = target.getClass().getDeclaredField(sourceField.getName());
                    } catch (NoSuchFieldException e) {
                        // Campo não existe na entidade de destino, ignorar
                        continue;
                    }
                    targetField.setAccessible(true);
                    targetField.set(target, sourceField.get(source));
                }
                return target;
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Falha ao copiar propriedades", e);
            }
        });

    }
}
