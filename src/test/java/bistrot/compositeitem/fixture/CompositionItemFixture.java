package bistrot.compositeitem.fixture;

import bistrot.compositionitem.entity.CompositionItem;
import org.assertj.core.util.Lists;

import java.util.List;

public class CompositionItemFixture {
    public static List<CompositionItem> createCompositionItems() {
        CompositionItem mocha = createCompositionItemMocha();

        CompositionItem espresso = createCompositionItemEspresso();

        return Lists.list(mocha,espresso);
    }

    public static CompositionItem createCompositionItemMocha() {
        CompositionItem mocha = CompositionItem.builder()
                .id(1L)
                .name("mocha")
                .price(5d)
                .quantity(14)
                .recipe("milk, coffee, ...")
                .build();
        return mocha;
    }

    public static CompositionItem createCompositionItemEspresso() {
        return CompositionItem.builder()
                .id(2L)
                .name("espresso")
                .price(2.5)
                .quantity(1)
                .recipe("coffee, ...")
                .build();
    }

    public static CompositionItem createCompositionItemNameEmpty() {
        return CompositionItem.builder()
                .name("")
                .price(2.5)
                .quantity(1)
                .recipe("coffee, ...")
                .build();
    }
}
