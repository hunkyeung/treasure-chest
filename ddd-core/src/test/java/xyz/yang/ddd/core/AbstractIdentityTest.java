package xyz.yang.ddd.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AbstractIdentityTest {

    private ConcreteIdentity one;

    @BeforeEach
    void init() {
        one = new ConcreteIdentity(1L);
    }

    @Test
    void givenNull_whenNew_thenThrowException() {
        Assertions.assertThrows(RuntimeException.class, () -> new ConcreteIdentity(null));
    }

    @Test
    void whenNew_thenGet() {
        assertEquals(1L, one.getValue());
    }

    @Test
    void givenDifferentIdWithTheSameClazzAndValue_whenEquals_thenReturnTrue() {
        ConcreteIdentity another = new ConcreteIdentity(1L);
        assertEquals(one, one);
        assertEquals(one, another);
        assertEquals(one.hashCode(), another.hashCode());
    }

    @Test
    void givenTheInstanceOfDifferentIdClass_whenEquals_thenReturnFalse() {
        AnotherConcreteIdentity strIdentity = new AnotherConcreteIdentity("1");
        assertNotEquals(one, strIdentity);
    }

    static class ConcreteIdentity extends AbstractIdentity<Long> {

        protected ConcreteIdentity(Long value) {
            super(value);
        }
    }

    static class AnotherConcreteIdentity extends AbstractIdentity<String> {

        protected AnotherConcreteIdentity(String value) {
            super(value);
        }
    }

}