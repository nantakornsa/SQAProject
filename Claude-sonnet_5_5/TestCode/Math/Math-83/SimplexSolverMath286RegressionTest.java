package org.apache.commons.math.optimization.linear;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.OptimizationException;
import org.apache.commons.math.optimization.RealPointValuePair;

public class SimplexSolverMath286RegressionTest extends TestCase {

    public void testMath286() throws OptimizationException {
        LinearObjectiveFunction f = new LinearObjectiveFunction(new double[] { 0.2, 0.3 }, 0);
        Collection<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
        constraints.add(new LinearConstraint(new double[] { 1, 1 }, Relationship.EQ, 23.0));

        RealPointValuePair solution = new SimplexSolver().optimize(f, constraints, GoalType.MAXIMIZE, true);

        assertEquals(6.9, solution.getValue(), .0000001);
        assertEquals(0.0, solution.getPoint()[0], .0000001);
        assertEquals(23.0, solution.getPoint()[1], .0000001);
    }
}