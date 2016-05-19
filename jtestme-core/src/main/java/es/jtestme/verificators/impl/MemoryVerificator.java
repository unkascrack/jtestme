package es.jtestme.verificators.impl;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.logger.JTestMeLogger;

public final class MemoryVerificator extends AbstractVerificator {

    private static final String PARAM_MEMORY_TYPE = "memorytype";
    private static final String PARAM_MIN_SIZE = "minsize";
    private static final int MB = 1024 * 1024;

    private final TypeMemory type;
    private final Integer minSize;

    public MemoryVerificator(final Map<String, String> params) {
        super(params);
        this.type = TypeMemory.convert(getParamString(PARAM_MEMORY_TYPE));
        this.minSize = getParamInteger(PARAM_MIN_SIZE, null);
    }

    public VerificatorResult execute() {
        final VerificatorResult result = new VerificatorResult();
        if (this.type == null || this.minSize == null) {
            result.setMessage(getClass().getSimpleName()
                    + ": no se ha definido el tipo y/o tamaño mínimo de la memoria para verificar.");
            return result;
        }

        final MemoryUsage memoryUsage = findMemoryUsageByType(this.type);
        if (memoryUsage == null) {
            result.setMessage(getClass().getSimpleName() + ": memory type no encontrada: "
                    + Arrays.toString(TypeMemory.values()));
            return result;
        }

        final int maxMemory = (int) (memoryUsage.getMax() / MB);
        if (this.minSize > maxMemory) {
            result.setMessage(getClass().getSimpleName() + ": memory size menor requerido.");
            return result;
        }

        result.setSuccess(true);
        return result;
    }

    private MemoryUsage findMemoryUsageByType(final TypeMemory type) {
        if (TypeMemory.HEAP.equals(type)) {
            return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        } else if (TypeMemory.NONHEAP.equals(type)) {
            return ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
        }

        final Iterator<MemoryPoolMXBean> iter = ManagementFactory.getMemoryPoolMXBeans().iterator();
        while (iter.hasNext()) {
            final MemoryPoolMXBean item = iter.next();
            if (item.getName().contains(type.getType())) {
                return item.getUsage();
            }
        }
        return null;
    }

    private enum TypeMemory {

        HEAP("Heap"),
        NONHEAP("Non-Heap"),
        PERMGEN("Perm Gen"),
        EDEN("Eden Space"),
        OLDGEM("Old Gen");

        private final String type;

        TypeMemory(final String type) {
            this.type = type;
        }

        String getType() {
            return this.type;
        }

        static TypeMemory convert(final String typeValue) {
            if (typeValue != null) {
                for (final TypeMemory type : values()) {
                    if (type.name().equalsIgnoreCase(typeValue)) {
                        return type;
                    }
                }
                JTestMeLogger.warn(MemoryVerificator.class.getSimpleName() + ": unsupported memory type: "
                        + Arrays.toString(values()));
            }
            return null;
        }
    }
}
