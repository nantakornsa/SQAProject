package org.apache.commons.math.stat.clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class KMeansPlusPlusClustererTest {

    /**
     * Non-regression test for MATH-546: when distances are small, the sum of
     * squared distances must not be truncated to an int, otherwise the
     * unique (far) point is never chosen as an initial center.
     */
    @Test
    public void testSmallDistances() {
        // Create a bunch of CloseIntegerPoints. Most are identical, but one is different by a
        // small distance.
        int[] repeatedArray = { 0 };
        int[] uniqueArray = { 1 };
        CloseIntegerPoint repeatedPoint =
            new CloseIntegerPoint(new EuclideanIntegerPoint(repeatedArray));
        CloseIntegerPoint uniquePoint =
            new CloseIntegerPoint(new EuclideanIntegerPoint(uniqueArray));

        Collection<CloseIntegerPoint> points = new ArrayList<CloseIntegerPoint>();
        final int NUM_REPEATED_POINTS = 10 * 1000;
        for (int i = 0; i < NUM_REPEATED_POINTS; ++i) {
            points.add(repeatedPoint);
        }
        points.add(uniquePoint);

        // Ask a KMeansPlusPlusClusterer to run zero iterations (i.e., to simply choose initial
        // cluster centers).
        final long RANDOM_SEED = 0;
        KMeansPlusPlusClusterer<CloseIntegerPoint> clusterer =
            new KMeansPlusPlusClusterer<CloseIntegerPoint>(new Random(RANDOM_SEED));
        final int NUM_CLUSTERS = 2;
        final int NUM_ITERATIONS = 0;
        List<Cluster<CloseIntegerPoint>> clusters =
            clusterer.cluster(points, NUM_CLUSTERS, NUM_ITERATIONS);

        // Check that one of the chosen centers is the unique point.
        boolean uniquePointIsCenter = false;
        for (Cluster<CloseIntegerPoint> cluster : clusters) {
            if (cluster.getCenter().equals(uniquePoint)) {
                uniquePointIsCenter = true;
            }
        }
        Assert.assertTrue(uniquePointIsCenter);
    }

    /**
     * A helper class for testSmallDistances(). This class is similar to EuclideanIntegerPoint, but
     * it assumes that the distance between any two points is very small.
     */
    private static class CloseIntegerPoint implements Clusterable<CloseIntegerPoint> {
        private final EuclideanIntegerPoint point;

        public CloseIntegerPoint(EuclideanIntegerPoint point) {
            this.point = point;
        }

        public double distanceFrom(CloseIntegerPoint p) {
            return point.distanceFrom(p.point) * 0.001;
        }

        public CloseIntegerPoint centroidOf(Collection<CloseIntegerPoint> p) {
            Collection<EuclideanIntegerPoint> euclideanPoints =
                new ArrayList<EuclideanIntegerPoint>();
            for (CloseIntegerPoint cip : p) {
                euclideanPoints.add(cip.point);
            }
            return new CloseIntegerPoint(point.centroidOf(euclideanPoints));
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof CloseIntegerPoint)) {
                return false;
            }
            CloseIntegerPoint cip = (CloseIntegerPoint) o;
            return point.equals(cip.point);
        }

        @Override
        public int hashCode() {
            return point.hashCode();
        }
    }
}