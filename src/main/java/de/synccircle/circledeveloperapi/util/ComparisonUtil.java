package de.synccircle.circledeveloperapi.util;

import java.util.Collection;
import java.util.function.Function;


public class ComparisonUtil<T> {

    private final Function<T, String> toString;

    private T bestMatch;

    private double match = Integer.MAX_VALUE;

    public ComparisonUtil(String input, T[] objects) {
        this(input, objects, Object::toString);
    }

    public ComparisonUtil(String input, T[] objects, Function<T, String> toString) {
        this.toString = toString;
        init(input, objects);
    }

    public ComparisonUtil(String input, Collection<T> objects) {
        this(input, objects, Object::toString);
    }

    @SuppressWarnings("unchecked")
    public ComparisonUtil(String input, Collection<T> objects, Function<T, String> toString) {
        this(input, (T[]) objects.toArray(), toString);
    }

    public ComparisonUtil() {
        this.toString = Object::toString;
    }


    private static int compare(String s1, String s2) {
        int distance = getLevenshteinDistance(s1, s2);
        if (s2.contains(s1)) {
            distance -= Math.min(s1.length(), s2.length());
        }
        if (s2.startsWith(s1)) {
            distance -= 4;
        }
        return distance;
    }

    private void init(String input, T[] objects) {
        int c;
        this.bestMatch = objects[0];
        input = input.toLowerCase();
        for (T o : objects) {
            if ((c = compare(input, getString(o).toLowerCase())) < this.match) {
                this.match = c;
                this.bestMatch = o;
            }
        }
    }

    private String getString(T o) {
        return this.toString.apply(o);
    }

    public String getBestMatch() {
        return getString(this.bestMatch);
    }

    private static int getLevenshteinDistance(String s, String t) {
        int n = s.length();
        int m = t.length();
        if (n == 0) {
            return m;
        } else if (m == 0) {
            return n;
        }
        if (n > m) {
            String tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }
        int[] p = new int[n + 1];
        int[] d = new int[n + 1];
        int i;
        for (i = 0; i <= n; i++) {
            p[i] = i;
        }
        for (int j = 1; j <= m; j++) {
            char t_j = t.charAt(j - 1);
            d[0] = j;

            for (i = 1; i <= n; i++) {
                int cost = s.charAt(i - 1) == t_j ? 0 : 1;
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
            }
            int[] _d = p;
            p = d;
            d = _d;
        }
        return p[n];
    }
}
