package angledefense.draw;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by Sam Sartor on 12/4/2016.
 */
public class OBJLoader {
    private static class Ind {
        public final int v;
        public final int n;
        public final int t;

        public Ind(int v, int n, int t, int vs, int ns, int ts) {
            if (v < 0) v += vs;
            else v -= 1;
            this.v = v;

            if (n < 0) n += ns;
            else n -= 1;
            this.n = n;

            if (t < 0) t += ts;
            else t -= 1;
            this.t = t;
        }

        public void add(VertexBuffer.Builder b, List<V3> vs, List<V3> ns, List<V2> ts) {
            vs.get(v).add(b);
            ns.get(n).add(b);
            ts.get(t).add(b);
        }

        static Pattern slash = Pattern.compile("\\s*\\/\\s*");
        static Pattern whitespace = Pattern.compile("\\s*");

        public static Ind read(Scanner in, int vs, int ns, int ts) {
            Pattern original = in.delimiter();
            in.skip(whitespace);

            in.useDelimiter(slash);
            int v = in.nextInt();
            int t = in.nextInt();
            in.useDelimiter(original);
            in.skip(slash);
            int n = in.nextInt();

            return new Ind(v, n, t, vs, ns, ts);
        }
    }

    private static class V3 {
        public final float x;
        public final float y;
        public final float z;

        public V3(Scanner in) {
           this(in.nextFloat(), in.nextFloat(), in.nextFloat());
        }

        public V3(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void add(VertexBuffer.Builder b) {
            b.add(x, y, z);
        }
    }

    private static class V2 {
        public final float x;
        public final float y;

        public V2(Scanner in) {
            this(in.nextFloat(), in.nextFloat());
        }

        public V2(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public void add(VertexBuffer.Builder b) {
            b.add(x, y);
        }
    }

    private static void addInd(VertexBuffer.Builder b, int v, int n, int t, List<V3> vs, List<V3> ns, List<V2> ts) {
        if (v < 0) v += vs.size();
        else v -= 1;
        vs.get(v).add(b);

        if (n < 0) n += ns.size();
        else n -= 1;
        ns.get(n).add(b);

        if (t < 0) t += ts.size();
        else t -= 1;

        if (t  == -1) new V2(0, 0).add(b);
        else ts.get(t).add(b);
    }

    private static Pattern linepre = Pattern.compile("#|\\w+");

    public static VertexBuffer load(InputStream stream) {
        Scanner in = new Scanner(stream);

        List<V3> verts = new ArrayList<>();
        List<V3> norms = new ArrayList<>();
        List<V2> tex = new ArrayList<>();
        List<Ind> inds = new ArrayList<>();

        while (in.hasNextLine()) {
            switch(in.next(linepre)) {
                case "v":
                    verts.add(new V3(in));
                    break;
                case "vn":
                    norms.add(new V3(in));
                    break;
                case "vt":
                    tex.add(new V2(in));
                    break;
                case "f":
                    for (int i = 0; i < 3; i++) {
                        inds.add(Ind.read(in, verts.size(), norms.size(), tex.size()));
                    }
                    break;
                case "#":
                    break;
            }
            in.nextLine();
        }

        VertexBuffer.Builder b = VertexBuffer.builder(inds.size());
        for (Ind i : inds) {
            i.add(b, verts, norms, tex);
        }
        return b.build();
    }
}
