import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LR_1 {

    enum Colors {
        WHITE,
        BLACK,
        GRAY
    }

    static Integer count = 0;
    static Integer n, m;

    static List<Integer> Head = new ArrayList<>(10000);
    static List<Integer> Adj = new ArrayList<>(10000);
    static List<Integer> Next = new ArrayList<>(10000);

    static List<Colors> Color = new ArrayList<>();
    static List<Integer> MNumber = new ArrayList<>();
    static List<Integer> NNumber = new ArrayList<>();
    static List<Integer> Parent = new ArrayList<>();

    public static void main(String[] args) {
        int u = 0, v;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите кол-во вершин графа");
        n = scanner.nextInt();
        InitAdjList();

        System.out.println("Введите кол-во ребер");
        m = scanner.nextInt();

        System.out.println("Введите смежные вершины");
        for (int i = 0; i < m; i++) {
            u = scanner.nextInt();
            v = scanner.nextInt();
            AddDirectArc(u,v);

        }
        System.out.println("M - нумерация");
        count = 0;
        MNumDFS(1);
        for (Integer i: MNumber) {
            if (i != 0)
                System.out.print(i + " ");
        }

        delColorList();
        count = n;
        System.out.println("\nN - нумерация");
        NNumBFS(1);
        for (Integer i: NNumber) {
            if (i != 0)
                System.out.print(i + " ");
        }
    }

    public static void MNumDFS(int U) {
        if (Color.get(U) != Colors.WHITE)
            return;
        Color.set(U, Colors.GRAY);
        MNumber.add(U, ++count);
        int cur = Head.get(U);
        while (cur != 0) {
            int v = Adj.get(cur);
            if (Color.get(v) == Colors.WHITE) {
                Parent.set(v, U);
                MNumDFS(v);
            }
            cur = Next.get(cur);
        }
        Color.set(U, Colors.BLACK);
    }

    public static void NNumBFS(int U) {
        if (Color.get(U) != Colors.WHITE)
            return;
        Color.set(U, Colors.GRAY);
        int cur = Head.get(U);
        while (cur != 0) {
            int v = Adj.get(cur);
            if (Color.get(v) == Colors.WHITE) {
                Parent.set(v, U);
                NNumBFS(v);
            }
            cur = Next.get(cur);
        }
        Color.set(U, Colors.BLACK);
        NNumber.set(U, count--);
    }

    public static void delColorList() {
        for (int i = 0; i < Color.size(); i++) {
            Color.set(i, Colors.WHITE);
        }
    }

    public static void InitAdjList() {
        for (int i = 0; i <= n; i++) {
            Head.add(0);
            Adj.add(0);
            Next.add(0);
            Color.add(Colors.WHITE);
            MNumber.add(0);
            NNumber.add(0);
            Parent.add(0);
        }
    }

    public static void AddDirectArc(int U, int V) {
        count++;
        Adj.add(count, V);
        Next.add(count, Head.get(U));
        Head.add(U, count);
    }

    public static void AddUndirectEdge(int U, int V) {
        AddDirectArc(U, V);
        AddDirectArc(V, U);
    }
}
