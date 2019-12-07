/**
 * Author: Jonathan Dawson
 * Created: 12/6/2019
 */
package interview;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The problem as stated was difficult for me to understand due to its grammar and terseness.  So,
 * I made the following presumptions:
 *   1. The number "1" forms a boundary which encloses a 2-dimensional area completely.
 *   2. All locations within that entire enclosed area, including the boundary, should return a
 *      "1", while all the other locations should return a "0".
 *   3. The boundary is fully surrounded by a border of "0"s so that it does not fall outside of
 *      the given map, making it impossible to discern inside from outside.
 *
 * I chose to use a breadth-first search similar to "flood-fill" to solve this problem simply and
 * efficiently.  This search completes in O(N) time complexity for any valid map.
 */
public class BoundaryAreaMap
{
    public class Location
    {
        public Location(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        public final int x, y;
    }

    public final int[][] map;

    public BoundaryAreaMap(int[][] map)
    {
        // copy the incoming map
        this.map = new int[map.length][];
        for (int j = 0; j < map.length; j++)
        {
            int length = map[j].length;
            this.map[j] = new int[length];
            for (int i = 0; i < length; i++)
            {
                this.map[j][i] = map[j][i];
            }
        }

        floodFill();
    }

    public int isInside(int x, int y)
    {
        if (map[y][x] != 2)
        {
            return 1;
        }
        return 0;
    }

    protected void floodFill()
    {
        Queue<Location> q = new ConcurrentLinkedQueue<>();
        int[] row = map[0];
        for (int i = 0; i < row.length; i++) // add first row
        {
            add(i, 0, q);
        }
        int lastRow = map.length - 1;
        row = map[lastRow];
        for (int i = 0; i < row.length; i++) // add last row
        {
            add(i, lastRow, q);
        }

        for (int j = 1; j < lastRow; j++) // add first and last column
        {
            add(0, j, q);
            add(map[j].length - 1, j, q);
        }

        while (!q.isEmpty())
        {
            Location l = q.poll();
            add(l.x - 1, l.y, q);
            add(l.x + 1, l.y, q);
            add(l.x, l.y - 1, q);
            add(l.x, l.y + 1, q);
        }
    }

    private void add(final int i, int j, final Queue<Location> q)
    {
        if (j >= 0 && j < map.length)
        {
            int[] row = map[j];
            if (i >= 0 && i < row.length)
            {
                if (row[i] == 0)
                {
                    q.add(new Location(i, j));
                    row[i] = 2;
                }
            }
        }
    }
}
