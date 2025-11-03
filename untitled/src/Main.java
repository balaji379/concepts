import java.util.stream.Collectors;
import java.util.stream.IntStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    final int key;

    Main() {
        this.key = 0;
    }

    public static void main(String[] args) {
        IntStream.range(1,100).map(i->{
                    System.out.println("hello");
                    System.out.flush();
                    return  i + 1;})
                .count();
    }

    abstract class name {
        void display1() {

        }

        void display2() {

        }

        void display3() {

        }

        void display4() {

        }
    }
}