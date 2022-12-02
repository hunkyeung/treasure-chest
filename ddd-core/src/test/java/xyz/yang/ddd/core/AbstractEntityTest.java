package xyz.yang.ddd.core;

import org.junit.jupiter.api.Test;
import org.mockito.MockSettings;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AbstractEntityTest {

    @Test
    void whenUseDefaultConstructor_thenIdIsNull() {
        var abstractEntity = spy(AbstractEntity.class);
        assertNull(abstractEntity.getId());
    }

    @Test
    void whenConstructWithNotNullArg_thenIdWhatYouGiven() {
        MockSettings mockSettings = withSettings().useConstructor(1).defaultAnswer(CALLS_REAL_METHODS);
        @SuppressWarnings("rawtypes") AbstractEntity entity = mock(AbstractEntity.class, mockSettings);
        assertEquals(1, entity.getId());
    }

    @Test
    void whenConstructWithNull_thenThrowException() {
        Serializable s = null;
        MockSettings mockSettings = withSettings().useConstructor(s).defaultAnswer(CALLS_REAL_METHODS);
        assertThrows(IllegalArgumentException.class, () -> mock(AbstractEntity.class, mockSettings));
    }
}