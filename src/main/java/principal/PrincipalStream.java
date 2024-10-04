package principal;

import java.util.Arrays;
import java.util.List;

public class PrincipalStream {

    public void principal() {
        List<String> nomes = Arrays.asList("Leo", "Elaine", "Clara", "Manuela", "Ineide");
        nomes.stream()
                .sorted()
                .limit(3)
                .filter(n -> n.startsWith("C"))
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);
    }
}
