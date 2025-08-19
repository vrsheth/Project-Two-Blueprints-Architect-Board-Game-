package test.phase1.building;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.building.DiceStack;
import main.building.Die;
import main.violations.ViolationList;

public class DieStackTests {
    private static final Die ANY_DIE = new Die("G2");

    @Nested
    class ConstructorTests {
        @Test
        public void no_arg_constructor_should_create_empty_stack() {
            // Given a dice stack made with the no-arg constructor
            DiceStack stack = new DiceStack();

            // (No When)

            // Then the stack has 0 height, no dice in it and no rules violations
            assertEquals(0, stack.getHeight());
            assertTrue(stack.isEmpty());
            assertFalse(stack.getViolations().hasViolations());
            assertTrue(stack.isValid());
        }

    }

    @Nested
    class GetDieTestsValidCases {
        @Test
        public void getting_from_bottom_of_stack_returns_that_die() {
            // Given a stack with a target die on the bottom
            DiceStack stack = new DiceStack();

            Die targetDie = new Die("W2");
            stack.add(targetDie);

            stack.add(ANY_DIE);
            stack.add(ANY_DIE);

            // When an attempt is made to get the target die from level 1
            Die die = stack.getDie(1);

            // Then the result is the target die
            assertSame(targetDie, die);
        }

        @Test
        public void getting_from_middle_of_stack_returns_that_die() {
            // Given a stack with a target die on the middle
            DiceStack stack = new DiceStack();
            stack.add(ANY_DIE);

            Die targetDie = new Die("W2");
            stack.add(targetDie);

            stack.add(ANY_DIE);

            // When an attempt is made to get the target die from level 1
            Die die = stack.getDie(2);

            // Then the result is the target die
            assertSame(targetDie, die);
        }

        @Test
        public void getting_from_top_of_stack_returns_that_die() {
            // Given a stack with a target die on the top
            DiceStack stack = new DiceStack();
            stack.add(ANY_DIE);
            stack.add(ANY_DIE);

            Die targetDie = new Die("W2");
            stack.add(targetDie);

            // When an attempt is made to get the target die from level 1
            Die die = stack.getDie(3);

            // Then the result is the target die
            assertSame(targetDie, die);
        }
    }

    @Nested
    class GetDiceTests {
        @Test
        public void an_empty_stack_returns_a_different_empty_list() {
            // Given an empty stack
            DiceStack stack = new DiceStack();

            // When you get the list of dice in the stack
            ArrayList<Die> diceList = stack.getDice();

            // And when you then add a die to the stack
            stack.add(ANY_DIE);

            // Then that list is still empty
            assertTrue(diceList.isEmpty());
        }

        @Test
        public void a_stack_with_dice_returns_a_different_list_with_same_dice() {
            // Given a stack with two dice in it
            DiceStack stack = new DiceStack();
            stack.add(new Die("G2"));
            stack.add(new Die("R5"));

            // When you get the list of dice in the stack
            ArrayList<Die> diceList = stack.getDice();

            // And when you then add a die to the stack
            stack.add(ANY_DIE);

            // Then the list only has the original two dice in it
            assertEquals(2, diceList.size());
            assertEquals("[G2, R5]", diceList.toString());
        }
    }

    @Nested
    class GetHeightTests {
        @Test
        public void adding_die_increases_height_by_one() {
            // Given an empty stack
            DiceStack stack = new DiceStack();

            // When you add a die to it multiple times
            // Then the height should increase by 1 each time
            stack.add(ANY_DIE);
            assertEquals(1, stack.getHeight());

            stack.add(ANY_DIE);
            assertEquals(2, stack.getHeight());

            stack.add(ANY_DIE);
            assertEquals(3, stack.getHeight());

        }
    }

    @Nested
    class IsEmptyTests {
        @Test
        public void once_die_are_added_a_list_is_no_longer_empty() {
            // Given an empty stack
            DiceStack stack = new DiceStack();
            assertTrue(stack.isEmpty());

            // When you add a die to it multiple times
            // Then the stack no longer says it's empty
            stack.add(ANY_DIE);
            assertFalse(stack.isEmpty());

            stack.add(ANY_DIE);
            assertFalse(stack.isEmpty());

            stack.add(ANY_DIE);
            assertFalse(stack.isEmpty());
        }
    }

    @Nested
    class GetViolationsAndIsValidTests {
        DiceStack stack;

        @BeforeEach
        public void setUp() {
            // Given an empty stack
            stack = new DiceStack();
        }

        @Nested
        class ValidStackTests {
            @Test
            public void empty_stack() {
                // Given an empty stack

                // When we get its violations
                ViolationList violations = stack.getViolations();

                // Then the stack reports it has no violations and is valid
                assertFalse(violations.hasViolations());
                assertTrue(stack.isValid());
            }

            @Test
            public void single_die_stack() {
                // Given a stack with one die
                stack.add(ANY_DIE);

                // When we get its violations
                ViolationList violations = stack.getViolations();

                // Then the stack reports it has no violations and is valid
                assertFalse(violations.hasViolations());
                assertTrue(stack.isValid());
            }

            @Test
            public void two_dice_stack_with_both_die_having_same_face_and_material() {
                // Given a stack with two dice having same faces and materials
                stack.add(new Die("W2"));
                stack.add(new Die("W2"));

                // When we get its violations
                ViolationList violations = stack.getViolations();

                // Then the stack reports it has no violations and is valid
                assertFalse(violations.hasViolations());
                assertTrue(stack.isValid());
            }

            @Test
            public void two_dice_stack_with_dice_having_same_faces_but_different_materials() {
                // Given a stack with two dice having same faces but different materials
                stack.add(new Die("R6"));
                stack.add(new Die("G6"));

                // When we get its violations
                ViolationList violations = stack.getViolations();

                // Then the stack reports it has no violations and is valid
                assertFalse(violations.hasViolations());
                assertTrue(stack.isValid());
            }

            @Test
            public void two_dice_stack_with_dice_having_same_materials_and_faces_ascending_by_1() {
                // Given a stack with two dice having same materials and faces ascending by 1
                stack.add(new Die("S1"));
                stack.add(new Die("S2"));

                // When we get its violations
                ViolationList violations = stack.getViolations();

                // Then the stack reports it has no violations and is valid
                assertFalse(violations.hasViolations());
                assertTrue(stack.isValid());
            }

            @Test
            public void two_dice_stack_with_dice_having_different_materials_and_faces_ascending_by_5() {
                // Given a stack with two dice having different faces and materials
                stack.add(new Die("G1"));
                stack.add(new Die("W6"));

                // When we get its violations
                ViolationList violations = stack.getViolations();

                // Then the stack reports it has no violations and is valid
                assertFalse(violations.hasViolations());
                assertTrue(stack.isValid());
            }

            @ParameterizedTest
            @CsvSource({
                    "G1",
                    "W2",
                    "R3",
                    "S4"
            })
            public void five_dice_stack_all_same_dice(String dieText) {
                // Given a stack of 5 identical dice
                for (int count = 1; count <= 5; count++) {
                    stack.add(new Die(dieText));
                }

                // When we get its violations
                ViolationList violations = stack.getViolations();

                // Then the stack reports it has no violations and is valid
                assertFalse(violations.hasViolations());
                assertTrue(stack.isValid());
            }

            @ParameterizedTest
            @CsvSource({
                    "G1",
                    "W2",
                    "R3",
                    "S4"
            })
            public void six_dice_stack_all_same_dice(String dieText) {
                // Given a stack of 6 identical dice
                for (int count = 1; count <= 6; count++) {
                    stack.add(new Die(dieText));
                }

                // When we get its violations
                ViolationList violations = stack.getViolations();

                // Then the stack reports it has no violations and is valid
                assertFalse(violations.hasViolations());
                assertTrue(stack.isValid());
            }

            @Test
            public void six_dice_stack_ascending_dice() {
                // Given a stack of 6 ascending dice
                stack.add(new Die("G1"));
                stack.add(new Die("W2"));
                stack.add(new Die("R3"));
                stack.add(new Die("S4"));
                stack.add(new Die("G5"));
                stack.add(new Die("R6"));

                // When we get its violations
                ViolationList violations = stack.getViolations();

                // Then the stack reports it has no violations and is valid
                assertFalse(violations.hasViolations());
                assertTrue(stack.isValid());
            }

        }

    }

    @Nested
    class ToStringTests {
        @Test
        public void empty_stack() {
            // Given an empty stack
            DiceStack stack = new DiceStack();

            // When the toString value is obtained
            String toStringValue = stack.toString();

            // Then it's []
            assertEquals("[]", toStringValue);
        }

        @Test
        public void one_die_stack() {
            // Given stack with one die
            DiceStack stack = new DiceStack();
            stack.add(ANY_DIE);

            // When the toString value is obtained
            String actualToStringValue = stack.toString();

            // Then it's the toString value of the die enclosed in []
            String expectedToStringValue = "[" + ANY_DIE.toString() + "]";
            assertEquals(expectedToStringValue, actualToStringValue);
        }

        @Test
        public void two_die_stack() {
            // Given stack with two die
            DiceStack stack = new DiceStack();
            stack.add(ANY_DIE);

            Die anotherDie = new Die("R5");
            stack.add(anotherDie);

            // When the toString value is obtained
            String actualToStringValue = stack.toString();

            // Then it's the toString values of the two die separated by space
            // and enclosed in []
            String expectedToStringValue = "[" +
                    ANY_DIE.toString() +
                    " " +
                    anotherDie.toString() +
                    "]";
            assertEquals(expectedToStringValue, actualToStringValue);
        }
    }

}
