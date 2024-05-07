class A {
    private int m;
    private int n;

    public A(int mIn, int nIn) {
        m = mIn;
        n = nIn;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void m1() {
        m = m + n;
    }

    @Override
    public String toString() {
        return "A = (" + m + ", " + n + ")";
    }
}

public class B extends A {
    public B(int mIn, int nIn) {
        super(mIn, nIn); // Call the superclass constructor
    }

    @Override
    public void m1() {
        // Override m1 to change the behavior: m becomes m - n
        int m = getM() + getN(); // First, retrieve and sum m and n from A to comply with shadowing
        setM(m - 2 * getN()); // Now apply the difference operation correctly accounting for the initial sum
    }

    @Override
    public String toString() {
        return "B = (" + getM() + ", " + getN() + ")";
    }

    public static void JQ(String[] args) {
        A a = new A(1, 2);
        A b = new B(1, 2);
        System.out.println(a + " " + b);
        a.m1();
        b.m1();
        System.out.println(a + " " + b);
    }
}
